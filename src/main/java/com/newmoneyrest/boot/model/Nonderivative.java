package com.newmoneyrest.boot.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "nonderivative")
public class Nonderivative implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long nonderivativeId;
	
	@Id
	@ManyToOne
    @JoinColumn(name = "form_4_id")
	private Form4 form4;
	
	@Column(length = 255)
	private String securityTitle;

	@Column(length = 30)
	private String transactionDate;

	@Column(length = 30)
	private String deemedExecutionDate;
	
	private char transactionFormType;
	private char transactionCode;
	private Boolean equitySwapInvolved;
	private char transcationTimeliness;
	private Double transactionShares;
	private Double transactionPricePerShare;
	private char transactionAcquiredDisposedCode;
	private Double sharesOwnedFollowingTransaction;
	private Double valueOwnedFollowingTransaction;
	private char directOrIndirectOwnership;

	@Column(length = 255)
	private String natureOfOwnership;

	@Lob
	private byte[] securityTitleFootnote;

	@Lob
	private byte[] transactionDateFootnote;

	@Lob
	private byte[] deemedExecutionDateFootnote;

	@Lob
	private byte[] transactionCodingFootnote;

	@Lob
	private byte[] transcationTimelinessFootnote;

	@Lob
	private byte[] transactionSharesFootnote;

	@Lob
	private byte[] transactionPricePerShareFootnote;

	@Lob
	private byte[] transactionAcquiredDisposedCodeFootnote;

	@Lob
	private byte[] sharesOwnedFollowingTransactionFootnote;

	@Lob
	private byte[] valueOwnedFollowingTransactionFootnote;

	@Lob
	private byte[] directOrIndirectOwnershipFootnote;

	@Lob
	private byte[] natureOfOwnershipFootnote;

	public long getDerivativeId() {
		return nonderivativeId;
	}

	public void setDerivativeId(long derivativeId) {
		this.nonderivativeId = derivativeId;
	}

	public Form4 getForm4() {
		return form4;
	}

	public void setForm4(Form4 form4) {
		this.form4 = form4;
	}

	public String getSecurityTitle() {
		return securityTitle;
	}

	public void setSecurityTitle(String securityTitle) {
		this.securityTitle = securityTitle;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getDeemedExecutionDate() {
		return deemedExecutionDate;
	}

	public void setDeemedExecutionDate(String deemedExecutionDate) {
		this.deemedExecutionDate = deemedExecutionDate;
	}

	public char getTransactionFormType() {
		return transactionFormType;
	}

	public void setTransactionFormType(char transactionFormType) {
		this.transactionFormType = transactionFormType;
	}

	public char getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(char transactionCode) {
		this.transactionCode = transactionCode;
	}

	public Boolean getEquitySwapInvolved() {
		return equitySwapInvolved;
	}

	public void setEquitySwapInvolved(Boolean equitySwapInvolved) {
		this.equitySwapInvolved = equitySwapInvolved;
	}

	public char getTranscationTimeliness() {
		return transcationTimeliness;
	}

	public void setTranscationTimeliness(char transcationTimeliness) {
		this.transcationTimeliness = transcationTimeliness;
	}

	public Double getTransactionShares() {
		return transactionShares;
	}

	public void setTransactionShares(Double transactionShares) {
		this.transactionShares = transactionShares;
	}

	public Double getTransactionPricePerShare() {
		return transactionPricePerShare;
	}

	public void setTransactionPricePerShare(Double transactionPricePerShare) {
		this.transactionPricePerShare = transactionPricePerShare;
	}

	public char getTransactionAcquiredDisposedCode() {
		return transactionAcquiredDisposedCode;
	}

	public void setTransactionAcquiredDisposedCode(char transactionAcquiredDisposedCode) {
		this.transactionAcquiredDisposedCode = transactionAcquiredDisposedCode;
	}

	public Double getSharesOwnedFollowingTransaction() {
		return sharesOwnedFollowingTransaction;
	}

	public void setSharesOwnedFollowingTransaction(Double sharesOwnedFollowingTransaction) {
		this.sharesOwnedFollowingTransaction = sharesOwnedFollowingTransaction;
	}

	public Double getValueOwnedFollowingTransaction() {
		return valueOwnedFollowingTransaction;
	}

	public void setValueOwnedFollowingTransaction(Double valueOwnedFollowingTransaction) {
		this.valueOwnedFollowingTransaction = valueOwnedFollowingTransaction;
	}

	public char getDirectOrIndirectOwnership() {
		return directOrIndirectOwnership;
	}

	public void setDirectOrIndirectOwnership(char directOrIndirectOwnership) {
		this.directOrIndirectOwnership = directOrIndirectOwnership;
	}

	public String getNatureOfOwnership() {
		return natureOfOwnership;
	}

	public void setNatureOfOwnership(String natureOfOwnership) {
		this.natureOfOwnership = natureOfOwnership;
	}

	public byte[] getSecurityTitleFootnote() {
		return securityTitleFootnote;
	}

	public void setSecurityTitleFootnote(byte[] securityTitleFootnote) {
		this.securityTitleFootnote = securityTitleFootnote;
	}

	public byte[] getTransactionDateFootnote() {
		return transactionDateFootnote;
	}

	public void setTransactionDateFootnote(byte[] transactionDateFootnote) {
		this.transactionDateFootnote = transactionDateFootnote;
	}

	public byte[] getDeemedExecutionDateFootnote() {
		return deemedExecutionDateFootnote;
	}

	public void setDeemedExecutionDateFootnote(byte[] deemedExecutionDateFootnote) {
		this.deemedExecutionDateFootnote = deemedExecutionDateFootnote;
	}

	public byte[] getTransactionCodingFootnote() {
		return transactionCodingFootnote;
	}

	public void setTransactionCodingFootnote(byte[] transactionCodingFootnote) {
		this.transactionCodingFootnote = transactionCodingFootnote;
	}

	public byte[] getTranscationTimelinessFootnote() {
		return transcationTimelinessFootnote;
	}

	public void setTranscationTimelinessFootnote(byte[] transcationTimelinessFootnote) {
		this.transcationTimelinessFootnote = transcationTimelinessFootnote;
	}

	public byte[] getTransactionSharesFootnote() {
		return transactionSharesFootnote;
	}

	public void setTransactionSharesFootnote(byte[] transactionSharesFootnote) {
		this.transactionSharesFootnote = transactionSharesFootnote;
	}

	public byte[] getTransactionPricePerShareFootnote() {
		return transactionPricePerShareFootnote;
	}

	public void setTransactionPricePerShareFootnote(byte[] transactionPricePerShareFootnote) {
		this.transactionPricePerShareFootnote = transactionPricePerShareFootnote;
	}

	public byte[] getTransactionAcquiredDisposedCodeFootnote() {
		return transactionAcquiredDisposedCodeFootnote;
	}

	public void setTransactionAcquiredDisposedCodeFootnote(byte[] transactionAcquiredDisposedCodeFootnote) {
		this.transactionAcquiredDisposedCodeFootnote = transactionAcquiredDisposedCodeFootnote;
	}

	public byte[] getSharesOwnedFollowingTransactionFootnote() {
		return sharesOwnedFollowingTransactionFootnote;
	}

	public void setSharesOwnedFollowingTransactionFootnote(byte[] sharesOwnedFollowingTransactionFootnote) {
		this.sharesOwnedFollowingTransactionFootnote = sharesOwnedFollowingTransactionFootnote;
	}

	public byte[] getValueOwnedFollowingTransactionFootnote() {
		return valueOwnedFollowingTransactionFootnote;
	}

	public void setValueOwnedFollowingTransactionFootnote(byte[] valueOwnedFollowingTransactionFootnote) {
		this.valueOwnedFollowingTransactionFootnote = valueOwnedFollowingTransactionFootnote;
	}

	public byte[] getDirectOrIndirectOwnershipFootnote() {
		return directOrIndirectOwnershipFootnote;
	}

	public void setDirectOrIndirectOwnershipFootnote(byte[] directOrIndirectOwnershipFootnote) {
		this.directOrIndirectOwnershipFootnote = directOrIndirectOwnershipFootnote;
	}

	public byte[] getNatureOfOwnershipFootnote() {
		return natureOfOwnershipFootnote;
	}

	public void setNatureOfOwnershipFootnote(byte[] natureOfOwnershipFootnote) {
		this.natureOfOwnershipFootnote = natureOfOwnershipFootnote;
	}
}
