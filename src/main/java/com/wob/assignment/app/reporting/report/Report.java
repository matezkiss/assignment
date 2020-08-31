package com.wob.assignment.app.reporting.report;

import java.util.List;
import java.util.Map;

import com.wob.assignment.app.reporting.generate.data.AggregatedMarketplaceData;

public class Report {

	private final int totalListingCount;

	final Map<String, AggregatedMarketplaceData> aggregatedMarketplaceData;

	private final List<String> bestListerEmailAddress;

	private final List<MonthlyReport> monthlyReports;

	public Report(int totalListingCount, Map<String, AggregatedMarketplaceData> aggregatedMarketplaceData,
			List<String> bestListerEmailAddress, List<MonthlyReport> monthlyReports) {
		this.totalListingCount = totalListingCount;
		this.aggregatedMarketplaceData = aggregatedMarketplaceData;
		this.bestListerEmailAddress = bestListerEmailAddress;
		this.monthlyReports = monthlyReports;
	}

	public int getTotalListingCount() {
		return totalListingCount;
	}

	public Map<String, AggregatedMarketplaceData> getAggregatedMarketplaceData() {
		return aggregatedMarketplaceData;
	}

	public List<String> getBestListerEmailAddress() {
		return bestListerEmailAddress;
	}

	public List<MonthlyReport> getMonthlyReports() {
		return monthlyReports;
	}

}
