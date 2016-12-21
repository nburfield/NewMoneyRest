package com.newmoneyrest.ResourceBuilder.SECFormParsers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.newmoneyrest.ResourceBuilder.FundamentalData.DailyData;
import com.newmoneyrest.ResourceBuilder.NetworkRequest.Ftp;
import com.newmoneyrest.ResourceBuilder.error.Logger.ErrorLog;
import com.newmoneyrest.ResourceBuilder.parser.Xml;
import com.newmoneyrest.boot.model.Company;
import com.newmoneyrest.boot.model.Derivative;
import com.newmoneyrest.boot.model.Insider;
import com.newmoneyrest.boot.model.InsiderForm4;
import com.newmoneyrest.boot.model.Nonderivative;
import com.newmoneyrest.boot.repository.CompanyRepository;
import com.newmoneyrest.boot.repository.DerivativeRepository;
import com.newmoneyrest.boot.repository.InsiderRepository;
import com.newmoneyrest.boot.repository.NonderivativeRepository;

public class Form4 implements FormParser {

	private String server = "ftp.sec.gov";
	private String user = "anonymous";
	private String password = "n.burf@yahoo.com";

	private NodeList footnotes;
	private Integer counter;
	
	private CompanyRepository companyRepository;
	private InsiderRepository insiderRepository;
	private NonderivativeRepository nonderivativeRepository;
	private DerivativeRepository derivativeRepository;
	private InsiderForm4 insiderForm4;
	private String headerForm4;
	private String date;
	private Company company;
	
	public Form4(CompanyRepository cr, InsiderRepository ir, NonderivativeRepository ndr, DerivativeRepository dr) {
		companyRepository = cr;
		insiderRepository = ir;
		nonderivativeRepository = ndr; 
		derivativeRepository= dr;
	}

	@Override
	public void Init(List<DailyData> dd) {
		
		for(DailyData item : dd) {
			// Get the XML file if exists
			String Location = item.fileName.replaceAll(".txt", "/");
			Location = Location.replaceAll("-", "");
			Ftp ftpConnection = new Ftp(server, user, password);
			if(!ftpConnection.IsConnected()) {
				ftpConnection.Connect();
			}
			List<String> list = ftpConnection.GetFiles(Location);
			for(String i : list) {
				if(i.endsWith(".xml")) {
					Location += i;
				}
			}
			
			//StringBuilder data = new StringBuilder();
			if(Location.endsWith(".xml")) {
				headerForm4 = Location.replace('/', '-');
				headerForm4 = headerForm4.replaceAll(".xml", "");
				System.out.println(headerForm4);
			}
			else {
				headerForm4 = item.fileName.replace('/', '_');
				Location = item.fileName;
				ErrorLog.Write("Error in getting the XML file: " + item.fileName);
			}

			// Update needed data, and the database to reflect the new information
			company = companyRepository.findByCik(item.CIK);
			date = item.dateFiled;
			
			// Run the current data through the parser
			if(!ftpConnection.IsConnected()) {
				ftpConnection.Connect();
			}
			RunFormParse(ftpConnection.Request(Location));			
		}
	}
	
	
	private void RunFormParse(StringBuilder data) {
		Document domTree = Xml.GetDocumantFromString(data);

		footnotes = domTree.getElementsByTagName("footnote");

		NodeList ele = domTree.getDocumentElement().getChildNodes();

		Recurse(ele);
	}
	
	
	private StringBuilder pullValue(String tag, Element root, int choice) {
		StringBuilder temp = new StringBuilder();
		try {
			String value = root.getElementsByTagName(tag).item(0).getTextContent();
			if(value.startsWith("\n") || value.isEmpty()) {
				if(choice == 1) {
					temp.append("NULL,"); 
				} else {
					temp.append("NULL");
				}
			}
			else {
				if(choice == 1) {
					temp.append(value.replace('\'', ' ') + ",");
				} else {
					temp.append(value.replace('\'', ' '));
				}
			}
		} catch(Exception e) {
			if(choice == 1) {
				temp.append("NULL,"); 
			} else {
				temp.append("NULL");
			}
		}
		return temp;
	}
	private String pullString(String tag, Element root, int choice) {
		StringBuilder temp = new StringBuilder();
		try {
			String value = root.getElementsByTagName(tag).item(0).getTextContent();
			if(value.startsWith("\n") || value.isEmpty()) {
				if(choice == 1) {
					temp.append("NULL,"); 
				}
				else if(choice == 2) {
					return null;
				}
				else {
					temp.append("NULL");
				}
			}
			else {
				if(choice == 1) {
					temp.append("'" + value.replace('\'', ' ') + "',");
				}
				else if(choice == 2) {
					return value.replace('\'', ' ');
				}
				else {
					temp.append("'" + value.replace('\'', ' ') + "'");
				}
			}
		} catch(Exception e) {
			if(choice == 1){
				temp.append("NULL,");   
			}
			else if(choice == 2) {
				return null;
			}
			else {
				temp.append("NULL");  
			}
		}

		return temp.toString();
	}
	
	private String getValueFromString(String value) {
		if(value.equals("NULL")) {
			return null;
		}
		return value;
	}
	
	private Boolean isTrue(String value) {		
		if(Integer.parseInt(value) == 1) {
			return true;
		}
		return false;
	}

	private byte[] pullFootnote(Element test) {
		try {
			NodeList foot = test.getElementsByTagName("footnoteId");
			String footnoteBuild = new String();
			
			for(int i = 0; i < foot.getLength(); i++) {
				Element footnote = (Element)foot.item(i);
				int footnoteid = Integer.parseInt(footnote.getAttribute("id").substring(1, footnote.getAttribute("id").length())) - 1;
				footnoteBuild += footnotes.item(footnoteid).getTextContent() + "\n";
			}
			
			return footnoteBuild.getBytes();
		} catch(Exception e) {
			return null;
		}
	}

	private StringBuilder Recurse(NodeList parent) {
		StringBuilder buildValues = new StringBuilder();
		for(int i = 0; i < parent.getLength(); i++) {
			if(parent.item(i).getNodeType() == Node.TEXT_NODE) {
				if(!parent.item(i).getNodeValue().startsWith("\n")) {
					System.out.println(parent.item(i).getNodeName() + " Text: " + parent.item(i).getTextContent());
				}
			}
			else {
				try {
					Object obj = this;
					// System.out.println(parent.item(i).getNodeName());
					Method method = obj.getClass().getMethod(parent.item(i).getNodeName(), Element.class);
					StringBuilder temp = (StringBuilder) method.invoke(obj, (Element)parent.item(i));
					if(temp != null) {
						buildValues.append(temp); 
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
					Recurse(parent.item(i).getChildNodes());
					ErrorLog.Write(parent.item(i).getNodeName() + " not Implemented.");
				}
			}
		}
		return buildValues;
	}

	public void schemaVersion(Element items) {
		//System.out.println("Running schemaversion");
	}
	public void documentType(Element items) {
		//System.out.println("Running documenttype4");
	}
	public void periodOfReport(Element items) {
		//System.out.println("Running periodofreport");
	}
	public void notSubjectToSection16(Element items) {
		//System.out.println("Running notSubjectToSection16");
	}
	public void ownerSignature(Element items) {
		//System.out.println("Running ownersignature");
	}
	public void footnotes(Element items) {
		//System.out.println("Running footnotes");
	}
	public void remarks(Element items) {
		//System.out.println("Running remarks");
	}

	public void issuer(Element items) {
		//System.out.println("Running issuer");
	}
	
	public void reportingOwner(Element items) {
		// name,cik,street1,street2,city,state,zip,stateDescription
		
		insiderForm4 = new InsiderForm4();
		StringBuilder value = Recurse(items.getChildNodes());
		value.replace(value.length()-1, value.length(), "");
		String [] new_insider = value.toString().split(",");
		
		Insider insider;
		try {
			insider = insiderRepository.findByCik(new_insider[1]);
		}
		catch(Exception e) {
			insider = null;
		}
		
		if(insider == null) {
			insider = new Insider(new_insider[1], new_insider[0], getValueFromString(new_insider[2]), getValueFromString(new_insider[3]), getValueFromString(new_insider[4]), getValueFromString(new_insider[5]), getValueFromString(new_insider[6]), getValueFromString(new_insider[7]));
			insider.setForm4(new HashSet<InsiderForm4>());
			insiderRepository.save(insider);
		}
		
		Set<InsiderForm4> inForm4 = insider.getForm4();
		insiderForm4.setInsider(insider);
		inForm4.add(insiderForm4);
		insider.setForm4(inForm4);
		insiderRepository.save(insider);
	}
	
	public StringBuilder reportingOwnerId(Element items) {
		List<String> tags = new ArrayList<>(Arrays.asList("rptOwnerName", "rptOwnerCik"));
		StringBuilder returnValue = new StringBuilder("");
		for(String name : tags) {
			returnValue.append(pullValue(name, items, 1));
		}
		return returnValue;
	}
	public StringBuilder reportingOwnerAddress(Element items) {
		List<String> tags = new ArrayList<>(Arrays.asList("rptOwnerStreet1", "rptOwnerStreet2", "rptOwnerCity", 
		"rptOwnerState", "rptOwnerZipCode", "rptOwnerStateDescription"));
		StringBuilder returnValue = new StringBuilder("");
		for(String name : tags) {
			returnValue.append(pullValue(name, items, 1));
		}
		return returnValue;
	}
	
	
	public void reportingOwnerRelationship(Element items) {
		// 4_header_forms_id, insiders_id, companies_id, isDirector, isOfficer, isTenPercent, isOther
		insiderForm4.setHeaderForm4(headerForm4);
		insiderForm4.setCompany(company);
		
		insiderForm4.setIsDirector(isTrue(pullString("isDirector", items, 2)));
		insiderForm4.setIsOfficer(isTrue(pullString("isOfficer", items, 2)));
		insiderForm4.setIsTenPercent(isTrue(pullString("isTenPercentOwner", items, 2)));
		insiderForm4.setIsOther(isTrue(pullString("isOther", items, 2)));
		insiderForm4.setOfficerTitle(pullString("officerTitle", items, 2));
		insiderForm4.setDate(date);
	}
	
	
	public void derivativeTable(Element items) {
		//System.out.println("Running derivativetable");
		counter = 0;
		Recurse(items.getChildNodes());
	}
	
	
	public void nonDerivativeTable(Element items) {
		//System.out.println("Running nonDerivativeTable");
		counter = 0;
		Recurse(items.getChildNodes());
	}
	
	
	  public void nonDerivativeTransaction(Element element) {
		    /*    structure:
		    securityTitle:              value, footnoteId
		    transactionDate:            value, footnoteID
		    deemedExecutionDate:          value, footnoteID
		    transactionCoding:            transactionFormType, 
		    transactionCode, 
		    equitySwapInvolved,
		    footnoteId
		    transactionTimeliness:          value, footnoteID
		    transactionShares:            value, footnoteId
		    transactionPricePerShare:       value, footnoteId
		    transactionAcquiredDisposedCode :   value, footnoteId
		    sharesOwnedFollowingTransaction :   value, footnoteId
		    valueOwnedFollowingTransaction :    value, footnoteId
		    directOrIndirectOwnership :       value, footnoteId
		    natureOfOwnership :         value, footnoteId
		    */

		    // Init the table data
		    Nonderivative nonderivative = new Nonderivative();
		    
		    nonderivative.setDerivativeId(counter);
		    counter++;
		    nonderivative.setForm4(headerForm4);

		    //securityTitle
		    Element test = (Element) element.getElementsByTagName("securityTitle").item(0);
		    nonderivative.setSecurityTitle(pullString("value", test, 2));
		    nonderivative.setSecurityTitleFootnote(pullFootnote(test));
		    test = null;

		    //transactionDate:            value, footnoteID
		    test = (Element) element.getElementsByTagName("transactionDate").item(0);
		    nonderivative.setTransactionDate(pullString("value", test, 2));
		    nonderivative.setTransactionDateFootnote(pullFootnote(test));
		    test = null;

		    //deemedExecutionDate:          value, footnoteID
		    test = (Element) element.getElementsByTagName("deemedExecutionDate").item(0);
		    nonderivative.setDeemedExecutionDate(pullString("value", test, 2));
		    nonderivative.setDeemedExecutionDateFootnote(pullFootnote(test));
		    test = null;

		    //transactionCoding:            transactionFormType, transactionCode, equitySwapInvolved,footnoteId
		    test = (Element) element.getElementsByTagName("transactionCoding").item(0);
		    try {
		      nonderivative.setTransactionFormType(pullString("transactionFormType", test, 2).charAt(0));
		    }
		    catch(Exception e) {}
		    try {
		      nonderivative.setTransactionCode(pullString("transactionCode", test, 2).charAt(0));
		    }
		    catch(Exception e) {}
		    
		    try {
		      nonderivative.setEquitySwapInvolved(Boolean.parseBoolean(pullString("equitySwapInvolved", test, 2)));
		    }
		    catch(Exception e) {}
		  
		    nonderivative.setTransactionCodingFootnote(pullFootnote(test));
		    test = null;

		    //transactionTimeliness:          value, footnoteID
		    test = (Element) element.getElementsByTagName("transactionTimeliness").item(0);
		    try {
		      nonderivative.setTranscationTimeliness(pullString("value", test, 2).charAt(0));
		    }
		    catch(Exception e) {}
		    nonderivative.setTranscationTimelinessFootnote(pullFootnote(test));
		    test = null;

		    //transactionShares:            value, footnoteId
		    test = (Element) element.getElementsByTagName("transactionShares").item(0);
		    try {
		      nonderivative.setTransactionShares(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    nonderivative.setTransactionSharesFootnote(pullFootnote(test));
		    test = null;

		    //transactionPricePerShare:       value, footnoteId
		    test = (Element) element.getElementsByTagName("transactionPricePerShare").item(0);
		    try {
		      nonderivative.setTransactionPricePerShare(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    nonderivative.setTransactionPricePerShareFootnote(pullFootnote(test));
		    test = null;

		    //transactionAcquiredDisposedCode :   value, footnoteId
		    test = (Element) element.getElementsByTagName("transactionAcquiredDisposedCode").item(0);
		    try {
		      nonderivative.setTransactionAcquiredDisposedCode(pullString("value", test, 2).charAt(0));
		    }
		    catch(Exception e) {}
		    nonderivative.setTransactionAcquiredDisposedCodeFootnote(pullFootnote(test));
		    test = null;
		    
		    //sharesOwnedFollowingTransaction :   value, footnoteId
		    test = (Element) element.getElementsByTagName("sharesOwnedFollowingTransaction").item(0);
		    try {
		      nonderivative.setSharesOwnedFollowingTransaction(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    nonderivative.setSharesOwnedFollowingTransactionFootnote(pullFootnote(test));
		    test = null;

		    //valueOwnedFollowingTransaction :    value, footnoteId
		    test = (Element) element.getElementsByTagName("valueOwnedFollowingTransaction").item(0);
		    try {
		      nonderivative.setValueOwnedFollowingTransaction(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    nonderivative.setValueOwnedFollowingTransactionFootnote(pullFootnote(test));
		    test = null;

		    //directOrIndirectOwnership :       value, footnoteId
		    test = (Element) element.getElementsByTagName("directOrIndirectOwnership").item(0);
		    try {
		      nonderivative.setDirectOrIndirectOwnership(pullString("value", test, 2).charAt(0));
		    }
		    catch(Exception e) {}
		    nonderivative.setDirectOrIndirectOwnershipFootnote(pullFootnote(test));
		    test = null;

		    //natureOfOwnership :         value, footnoteId   
		    test = (Element) element.getElementsByTagName("natureOfOwnership").item(0);
		    nonderivative.setNatureOfOwnership(pullString("value", test, 2));
		    nonderivative.setNatureOfOwnershipFootnote(pullFootnote(test));
		    test = null;
		    
		    nonderivativeRepository.save(nonderivative);
		  }
		  
		  
		  public void nonDerivativeHolding(Element element){
		    /*     structure:
		    securityTitle:              value, footnoteId
		    sharesOwnedFollowingTransaction :   value, footnoteId
		    valueOwnedFollowingTransaction :    value, footnoteId
		    directOrIndirectOwnership :       value, footnoteId
		    natureOfOwnership :         value, footnoteId
		    */
		    
		    // Init the table data
		    Nonderivative nonderivative = new Nonderivative();
		    
		    nonderivative.setDerivativeId(counter);
		    counter++;
		    nonderivative.setForm4(headerForm4);

		    //securityTitle
		    Element test = (Element) element.getElementsByTagName("securityTitle").item(0);
		    nonderivative.setSecurityTitle(pullString("value", test, 2));
		    nonderivative.setSecurityTitleFootnote(pullFootnote(test));
		    test = null;

		    //sharesOwnedFollowingTransaction :   value, footnoteId
		    test = (Element) element.getElementsByTagName("sharesOwnedFollowingTransaction").item(0);
		    try {
		      nonderivative.setSharesOwnedFollowingTransaction(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    nonderivative.setSharesOwnedFollowingTransactionFootnote(pullFootnote(test));
		    test = null;
		    
		    //valueOwnedFollowingTransaction :    value, footnoteId
		    test = (Element) element.getElementsByTagName("valueOwnedFollowingTransaction").item(0);
		    try {
		      nonderivative.setValueOwnedFollowingTransaction(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    nonderivative.setValueOwnedFollowingTransactionFootnote(pullFootnote(test));
		    test = null;
		    
		    //directOrIndirectOwnership :       value, footnoteId
		    test = (Element) element.getElementsByTagName("directOrIndirectOwnership").item(0);
		    try {
		      nonderivative.setDirectOrIndirectOwnership(pullString("value", test, 2).charAt(0));
		    }
		    catch(Exception e) {}
		    nonderivative.setDirectOrIndirectOwnershipFootnote(pullFootnote(test));
		    test = null;
		    
		    //natureOfOwnership :         value, footnoteId   
		    test = (Element) element.getElementsByTagName("natureOfOwnership").item(0);
		    nonderivative.setNatureOfOwnership(pullString("value", test, 2));
		    nonderivative.setNatureOfOwnershipFootnote(pullFootnote(test));
		    test = null;

		    nonderivativeRepository.save(nonderivative);
		  }
		  
		  
		  public void derivativeTransaction(Element element){
		    /*structure:
		    securityTitle:              value, footnoteId
		    conversionOrExercisePrice       value, footnoteId
		    transactionDate:            value, footnoteID
		    deemedExecutionDate:          value, footnoteID
		    transactionCoding:            transactionFormType, 
		    transactionCode, 
		    equitySwapInvolved,
		    footnoteId
		    transactionTimeliness:          value, footnoteID
		    transactionShares:            value, footnoteId
		    transactionTotalValue         value, footnoteId
		    transactionPricePerShare:       value, footnoteId
		    transactionAcquiredDisposedCode :   value
		    exerciseDate              value, footnoteId
		    expirationDate              value, footnoteId
		    underlyingSecurityTitle         value, footnoteId
		    underlyingSecurityShares        value, footnoteId 
		    underlyingSecurityValue         value, footnoteId
		    sharesOwnedFollowingTransaction :   value, footnoteId
		    valueOwnedFollowingTransaction :    value, footnoteId
		    directOrIndirectOwnership :       value, footnoteId
		    natureOfOwnership :         value, footnoteId
		    */

		    // Init the table data
		    Derivative derivative = new Derivative();
		    
		    derivative.setDerivativeId(counter);
		    counter++;
		    derivative.setForm4(headerForm4);

		    //securityTitle
		    Element test = (Element) element.getElementsByTagName("securityTitle").item(0);
		    derivative.setSecurityTitle(pullString("value", test, 2));
		    derivative.setSecurityTitleFootnote(pullFootnote(test));
		    test = null;

		    //conversionOrExercisePrice
		    test = (Element) element.getElementsByTagName("conversionOrExercisePrice").item(0);
		    try {
		      derivative.setConversionOrExercisePrice(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    derivative.setConversionOrExercisePriceFootnote(pullFootnote(test));
		    test = null;        

		    //transactionDate:            value, footnoteID
		    test = (Element) element.getElementsByTagName("transactionDate").item(0);
		    derivative.setTransactionDate(pullString("value", test, 2));
		    derivative.setTransactionDateFootnote(pullFootnote(test));
		    test = null;

		    //deemedExecutionDate:          value, footnoteID
		    test = (Element) element.getElementsByTagName("deemedExecutionDate").item(0);
		    derivative.setDeemedExecutionDate(pullString("value", test, 2));
		    derivative.setDeemedExecutionDateFootnote(pullFootnote(test));
		    test = null;

		    //transactionCoding:            transactionFormType, transactionCode, equitySwapInvolved,footnoteId
		    test = (Element) element.getElementsByTagName("transactionCoding").item(0);
		    try {
		      derivative.setTransactionFormType(pullString("transactionFormType", test, 2).charAt(0));
		    }
		    catch(Exception e) {}
		    try {
		      derivative.setTransactionCode(pullString("transactionCode", test, 2).charAt(0));
		    }
		    catch(Exception e) {}
		    
		    try {
		      derivative.setEquitySwapInvolved(Boolean.parseBoolean(pullString("equitySwapInvolved", test, 2)));
		    }
		    catch(Exception e) {}
		  
		    derivative.setTransactionCodingFootnote(pullFootnote(test));
		    test = null;

		    //transactionTimeliness:          value, footnoteID
		    test = (Element) element.getElementsByTagName("transactionTimeliness").item(0);
		    try {
		      derivative.setTranscationTimeliness(pullString("value", test, 2).charAt(0));
		    }
		    catch(Exception e) {}
		    derivative.setTranscationTimelinessFootnote(pullFootnote(test));
		    test = null;

		    //transactionShares:            value, footnoteId
		    test = (Element) element.getElementsByTagName("transactionShares").item(0);
		    try {
		      derivative.setTransactionShares(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    derivative.setTransactionSharesFootnote(pullFootnote(test));
		    test = null;

		    //transactionTotalValue         value, footnoteId
		    test = (Element) element.getElementsByTagName("transactionTotalValue").item(0);
		    try {
		      derivative.setTransactionTotalValue(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    derivative.setTransactionTotalValueFootnote(pullFootnote(test));
		    test = null;        
		    
		    //transactionPricePerShare:       value, footnoteId
		    test = (Element) element.getElementsByTagName("transactionPricePerShare").item(0);
		    try {
		      derivative.setTransactionPricePerShare(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    derivative.setTransactionPricePerShareFootnote(pullFootnote(test));
		    test = null;

		    //transactionAcquiredDisposedCode :   value, footnoteId
		    test = (Element) element.getElementsByTagName("transactionAcquiredDisposedCode").item(0);
		    try {
		      derivative.setTransactionAcquiredDisposedCode(pullString("value", test, 2).charAt(0));
		    }
		    catch(Exception e) {}
		    derivative.setTransactionAcquiredDisposedCodeFootnote(pullFootnote(test));
		    test = null;    

		    //exerciseDate                                                  value, footnoteId
		    test = (Element) element.getElementsByTagName("exerciseDate").item(0);
		    derivative.setExerciseDate(pullString("value", test, 2));
		    derivative.setExerciseDateFootnote(pullFootnote(test));
		    test = null;

		    //expirationDate                                                        value, footnoteId
		    test = (Element) element.getElementsByTagName("expirationDate").item(0);
		    derivative.setExpirationDate(pullString("value", test, 2));
		    derivative.setExpirationDateFootnote(pullFootnote(test));
		    test = null;

		    //underlyingSecurityTitle                               value, footnoteId
		    test = (Element) element.getElementsByTagName("underlyingSecurityTitle").item(0);
		    derivative.setUnderlyingSecurityTitle(pullString("value", test, 2));
		    derivative.setUnderlyingSecurityTitleFootnote(pullFootnote(test));
		    test = null;

		    //underlyingSecurityShares                              value, footnoteId
		    test = (Element) element.getElementsByTagName("underlyingSecurityShares").item(0);
		    try {
		      derivative.setUnderlyingSecurityShares(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    derivative.setUnderlyingSecuritySharesFootnote(pullFootnote(test));
		    test = null;

		    //underlyingSecurityValue                                       value, footnoteId
		    test = (Element) element.getElementsByTagName("underlyingSecurityValue").item(0);
		    try {
		      derivative.setUnderlyingSecurityValue(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    derivative.setUnderlyingSecurityValueFootnote(pullFootnote(test));
		    test = null;
		    
		    //sharesOwnedFollowingTransaction :   value, footnoteId
		    test = (Element) element.getElementsByTagName("sharesOwnedFollowingTransaction").item(0);
		    try {
		      derivative.setSharesOwnedFollowingTransaction(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    derivative.setSharesOwnedFollowingTransactionFootnote(pullFootnote(test));
		    test = null;
		    
		    //valueOwnedFollowingTransaction :    value, footnoteId
		    test = (Element) element.getElementsByTagName("valueOwnedFollowingTransaction").item(0);
		    try {
		      derivative.setValueOwnedFollowingTransaction(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    derivative.setValueOwnedFollowingTransactionFootnote(pullFootnote(test));
		    test = null;
		    
		    //directOrIndirectOwnership :       value, footnoteId
		    test = (Element) element.getElementsByTagName("directOrIndirectOwnership").item(0);
		    try {
		      derivative.setDirectOrIndirectOwnership(pullString("value", test, 2).charAt(0));
		    }
		    catch(Exception e) {}
		    derivative.setDirectOrIndirectOwnershipFootnote(pullFootnote(test));
		    test = null;
		    
		    //natureOfOwnership :         value, footnoteId   
		    test = (Element) element.getElementsByTagName("natureOfOwnership").item(0);
		    derivative.setNatureOfOwnership(pullString("value", test, 2));
		    derivative.setNatureOfOwnershipFootnote(pullFootnote(test));
		    test = null;
		    

		    derivativeRepository.save(derivative);
		  }

		  public void derivativeHolding(Element element){
		    /*     structure:
		    securityTitle:              value, footnoteId
		    conversionOrExercisePrice       value, footnoteId

		    exerciseDate              value, footnoteId
		    expirationDate              value, footnoteId
		    underlyingSecurityTitle         value, footnoteId
		    underlyingSecurityShares        value, footnoteId 
		    underlyingSecurityValue         value, footnoteId
		    sharesOwnedFollowingTransaction :   value, footnoteId
		    valueOwnedFollowingTransaction :    value, footnoteId
		    directOrIndirectOwnership :       value, footnoteId
		    natureOfOwnership :         value, footnoteId
		    */

		    // Init the table data
		    Derivative derivative = new Derivative();
		    
		    derivative.setDerivativeId(counter);
		    counter++;
		    derivative.setForm4(headerForm4);

		    //securityTitle
		    Element test = (Element) element.getElementsByTagName("securityTitle").item(0);
		    derivative.setSecurityTitle(pullString("value", test, 2));
		    derivative.setSecurityTitleFootnote(pullFootnote(test));
		    test = null;

		    //conversionOrExercisePrice
		    test = (Element) element.getElementsByTagName("conversionOrExercisePrice").item(0);
		    try {
		      derivative.setConversionOrExercisePrice(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    derivative.setConversionOrExercisePriceFootnote(pullFootnote(test));
		    test = null;   

		    //exerciseDate                                                  value, footnoteId
		    test = (Element) element.getElementsByTagName("exerciseDate").item(0);
		    derivative.setExerciseDate(pullString("value", test, 2));
		    derivative.setExerciseDateFootnote(pullFootnote(test));
		    test = null;

		    //expirationDate                                                        value, footnoteId
		    test = (Element) element.getElementsByTagName("expirationDate").item(0);
		    derivative.setExpirationDate(pullString("value", test, 2));
		    derivative.setExpirationDateFootnote(pullFootnote(test));
		    test = null;

		    //underlyingSecurityTitle                               value, footnoteId
		    test = (Element) element.getElementsByTagName("underlyingSecurityTitle").item(0);
		    derivative.setUnderlyingSecurityTitle(pullString("value", test, 2));
		    derivative.setUnderlyingSecurityTitleFootnote(pullFootnote(test));
		    test = null;

		    //underlyingSecurityShares                              value, footnoteId
		    test = (Element) element.getElementsByTagName("underlyingSecurityShares").item(0);
		    try {
		      derivative.setUnderlyingSecurityShares(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    derivative.setUnderlyingSecuritySharesFootnote(pullFootnote(test));
		    test = null;

		    //underlyingSecurityValue                                       value, footnoteId
		    test = (Element) element.getElementsByTagName("underlyingSecurityValue").item(0);
		    try {
		      derivative.setUnderlyingSecurityValue(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    derivative.setUnderlyingSecurityValueFootnote(pullFootnote(test));
		    test = null;

		    //sharesOwnedFollowingTransaction :   value, footnoteId
		    test = (Element) element.getElementsByTagName("sharesOwnedFollowingTransaction").item(0);
		    try {
		      derivative.setSharesOwnedFollowingTransaction(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    derivative.setSharesOwnedFollowingTransactionFootnote(pullFootnote(test));
		    test = null;
		    
		    //valueOwnedFollowingTransaction :    value, footnoteId
		    test = (Element) element.getElementsByTagName("valueOwnedFollowingTransaction").item(0);
		    try {
		      derivative.setValueOwnedFollowingTransaction(Double.parseDouble(pullString("value", test, 2)));
		    }
		    catch(Exception e) {}
		    derivative.setValueOwnedFollowingTransactionFootnote(pullFootnote(test));
		    test = null;
		    
		    //directOrIndirectOwnership :       value, footnoteId
		    test = (Element) element.getElementsByTagName("directOrIndirectOwnership").item(0);
		    try {
		      derivative.setDirectOrIndirectOwnership(pullString("value", test, 2).charAt(0));
		    }
		    catch(Exception e) {}
		    derivative.setDirectOrIndirectOwnershipFootnote(pullFootnote(test));
		    test = null;
		    
		    //natureOfOwnership :         value, footnoteId   
		    test = (Element) element.getElementsByTagName("natureOfOwnership").item(0);
		    derivative.setNatureOfOwnership(pullString("value", test, 2));
		    derivative.setNatureOfOwnershipFootnote(pullFootnote(test));
		    test = null;
		    
		    
		    derivativeRepository.save(derivative);
		  }
}
