package com.newmoneyrest.boot.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@IdClass(InsiderForm4Id.class)
@Table(name = "insider_form_4")
public class InsiderForm4 implements Serializable {

	private static final long serialVersionUID = 1L;

	public InsiderForm4() {}
	
	public InsiderForm4(String headerForm4Id, Insider insider, Company company, Boolean isDirector, Boolean isOfficer,
			Boolean isTenPercent, Boolean isOther, String officerTitle, String date) {
		super();
		this.headerForm4Id = headerForm4Id;
		this.insider = insider;
		this.company = company;
		this.isDirector = isDirector;
		this.isOfficer = isOfficer;
		this.isTenPercent = isTenPercent;
		this.isOther = isOther;
		this.officerTitle = officerTitle;
		this.date = date;
	}

	@Id
	@Column(name = "header_form_4_id", length = 255)
	private String headerForm4Id;
	
	@Id
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
	
	@Column(length = 255)
	private String date;

	public String getHeaderForm4Id() {
		return headerForm4Id;
	}

	public void setHeaderForm4Id(String headerForm4Id) {
		this.headerForm4Id = headerForm4Id;
	}

	@JsonBackReference
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHeaderForm4() {
		return headerForm4Id;
	}

	public void setHeaderForm4(String headerForm4Id) {
		this.headerForm4Id = headerForm4Id;
	}

	@JsonBackReference
	public Insider getInsider() {
		return insider;
	}

	public void setInsider(Insider insider) {
		this.insider = insider;
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
