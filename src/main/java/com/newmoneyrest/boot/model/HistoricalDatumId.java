package com.newmoneyrest.boot.model;

import java.io.Serializable;

public class HistoricalDatumId implements Serializable {

	private static final long serialVersionUID = 1L;
	private Company companyId;
	private Integer year;
	private Integer month;
	private Integer day;
}
