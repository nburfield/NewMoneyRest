package com.newmoneyrest.boot.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "historical_data")
@IdClass(HistoricalDatumId.class)
public class HistoricalDatum implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne
    @JoinColumn(name = "company_id")
	private Company companyId;
	
	@Id
	private Integer year;
	
	@Id
	private Integer month;
	
	@Id
	private Integer day;
	
	private Double open;
	private Double high;
	private Double low;
	private Double close;
	private Double volume;
	private Double adjustedClose;
	
	public HistoricalDatum() {}
	
	public HistoricalDatum(Company companyId, Integer year, Integer month, Integer day, Double open, Double high,
			Double low, Double close, Double volume, Double adjustedClose) {
		super();
		this.companyId = companyId;
		this.year = year;
		this.month = month;
		this.day = day;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		this.adjustedClose = adjustedClose;
	}
	
	@JsonBackReference
	public Company getCompany() {
		return companyId;
	}
	public void setCompany(Company company) {
		this.companyId = company;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Double getOpen() {
		return open;
	}
	public void setOpen(Double open) {
		this.open = open;
	}
	public Double getHigh() {
		return high;
	}
	public void setHigh(Double high) {
		this.high = high;
	}
	public Double getLow() {
		return low;
	}
	public void setLow(Double low) {
		this.low = low;
	}
	public Double getClose() {
		return close;
	}
	public void setClose(Double close) {
		this.close = close;
	}
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	public Double getAdjustedClose() {
		return adjustedClose;
	}
	public void setAdjustedClose(Double adjustedClose) {
		this.adjustedClose = adjustedClose;
	}
}
