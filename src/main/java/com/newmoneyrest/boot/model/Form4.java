package com.newmoneyrest.boot.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "form_4s")
public class Form4 {
	public Form4() {}
	
	public Form4(String form4Header, Insider insider, Company company, Boolean isDirector, Boolean isOfficer,
			Boolean isTenPercent, Boolean isOther, String officerTitle) {
		super();
		this.form4Header = form4Header;
		this.insider = insider;
		this.company = company;
		this.isDirector = isDirector;
		this.isOfficer = isOfficer;
		this.isTenPercent = isTenPercent;
		this.isOther = isOther;
		this.officerTitle = officerTitle;
	}

	@Id
	@Column(length = 255)
	private String form4Header;
	
	@ManyToOne
    @JoinColumn(name = "insider_id")
	private Insider insider;

	@ManyToOne
    @JoinColumn(name = "company_id")
	private Company company;
	
	private Boolean isDirector;
	private Boolean isOfficer;
	private Boolean isTenPercent;
	private Boolean isOther;
	
	@Column(length = 30)
	private String officerTitle;
	
	@OneToMany(mappedBy = "form4", cascade = CascadeType.ALL)
	Set<Nonderivative> nonderivative;
	
	@OneToMany(mappedBy = "form4", cascade = CascadeType.ALL)
	Set<Derivative> derivative;
	
	public Set<Nonderivative> getNonderivative() {
		return nonderivative;
	}

	public void setNonderivative(Set<Nonderivative> nonderivative) {
		this.nonderivative = nonderivative;
	}

	public Set<Derivative> getDerivative() {
		return derivative;
	}

	public void setDerivative(Set<Derivative> derivative) {
		this.derivative = derivative;
	}

	public String getForm4Header() {
		return form4Header;
	}

	public void setForm4Header(String form4Header) {
		this.form4Header = form4Header;
	}

	public Insider getInsider() {
		return insider;
	}

	public void setInsider(Insider insider) {
		this.insider = insider;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Boolean getIsDirector() {
		return isDirector;
	}

	public void setIsDirector(Boolean isDirector) {
		this.isDirector = isDirector;
	}

	public Boolean getIsOfficer() {
		return isOfficer;
	}

	public void setIsOfficer(Boolean isOfficer) {
		this.isOfficer = isOfficer;
	}

	public Boolean getIsTenPercent() {
		return isTenPercent;
	}

	public void setIsTenPercent(Boolean isTenPercent) {
		this.isTenPercent = isTenPercent;
	}

	public Boolean getIsOther() {
		return isOther;
	}

	public void setIsOther(Boolean isOther) {
		this.isOther = isOther;
	}

	public String getOfficerTitle() {
		return officerTitle;
	}

	public void setOfficerTitle(String officerTitle) {
		this.officerTitle = officerTitle;
	}
}
