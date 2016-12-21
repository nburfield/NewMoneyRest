package com.newmoneyrest.ResourceBuilder.SECFormParsers;

import java.util.List;

import com.newmoneyrest.ResourceBuilder.FundamentalData.DailyData;

public interface FormParser {
	void Init(List<DailyData> dd);
}
