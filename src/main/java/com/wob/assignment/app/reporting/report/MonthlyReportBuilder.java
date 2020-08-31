package com.wob.assignment.app.reporting.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wob.assignment.app.reporting.generate.data.MarketplaceStats;

public class MonthlyReportBuilder {

	private Date month;

	private List<String> bestLister;

	private Map<String, MarketplaceStats> statistics;

	public MonthlyReportBuilder(Date month) {
		this.month = month;
		this.bestLister = new ArrayList<>();
		this.statistics = new HashMap<>();
	}

	public Date getMonth() {
		return month;
	}

	public List<String> getBestLister() {
		return bestLister;
	}

	public Map<String, MarketplaceStats> getStatistics() {
		return statistics;
	}

	public void addStatistics(final String marketplace, final MarketplaceStats statistics) {
		this.statistics.put(marketplace, statistics);
	}

	public MonthlyReport build() {
		return new MonthlyReport(this.month, this.bestLister, this.statistics);
	}

	public void addBestLister(final String bestLister) {
		this.bestLister.add(bestLister);
	}

}
