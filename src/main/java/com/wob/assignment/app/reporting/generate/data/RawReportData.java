package com.wob.assignment.app.reporting.generate.data;

import java.util.List;

import com.wob.assignment.app.reporting.generate.data.queryresult.MonthlyBestListerRow;
import com.wob.assignment.app.reporting.generate.data.queryresult.MonthlyStatsRow;

/**
 * POJO for a while report.
 * 
 * @author matezoltankiss
 *
 */
public class RawReportData {

	private final List<MonthlyStatsRow> monthlyStats;

	private final List<MonthlyBestListerRow> monthlyBestLister;

	private final List<String> alltimeBestLister;

	public RawReportData(List<MonthlyStatsRow> monthlyStats, List<MonthlyBestListerRow> monthlyBestLister,
			List<String> alltimeBestLister) {
		this.monthlyStats = monthlyStats;
		this.monthlyBestLister = monthlyBestLister;
		this.alltimeBestLister = alltimeBestLister;
	}

	public List<MonthlyStatsRow> getMonthlyStats() {
		return monthlyStats;
	}

	public List<MonthlyBestListerRow> getMonthlyBestLister() {
		return monthlyBestLister;
	}

	public List<String> getAlltimeBestLister() {
		return alltimeBestLister;
	}

}
