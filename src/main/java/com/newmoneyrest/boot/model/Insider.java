package com.newmoneyrest.boot.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "insiders")
public class Insider {
	
	public Insider() {}
	
	public Insider(String cik, String name, String street1, String street2, String city, String state, String zip,
			String stateDescription) {
		super();
		this.cik = cik;
		this.name = name;
		this.street1 = street1;
		this.street2 = street2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.stateDescription = stateDescription;
	}

	@Id
	@Column(length = 30, nullable = false)
	private String cik;
	
	@Column(length = 255, nullable = false)
	private String name;
	
	@Column(length = 255)
	private String street1;
	
	@Column(length = 255)
	private String street2;
	
	@Column(length = 30)
	private String city;
	
	@Column(length = 30)
	private String state;
	
	@Column(length = 30)
	private String zip;
	
	@Column(length = 4)
	private String stateDescription;
	
	@OneToMany(mappedBy = "insider", cascade = CascadeType.ALL)
	Set<InsiderForm4> insiderForm4;

	@JsonManagedReference
	public Set<InsiderForm4> getForm4() {
		return insiderForm4;
	}

	public void setForm4(Set<InsiderForm4> insiderForm4) {
		this.insiderForm4 = insiderForm4;
	}

	public String getCik() {
		return cik;
	}

	public void setCik(String cik) {
		this.cik = cik;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getStateDescription() {
		return stateDescription;
	}

	public void setStateDescription(String stateDescription) {
		this.stateDescription = stateDescription;
	}
}
