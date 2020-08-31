package com.wob.assignment.app.reporting.report;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wob.assignment.app.reporting.generate.data.MarketplaceStats;

public class MonthlyReport {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
	private final Date month;

	private final List<String> bestLister;

	private final Map<String, MarketplaceStats> statistics;

	public MonthlyReport(Date month, List<String> bestLister, Map<String, MarketplaceStats> stats) {
		this.month = month;
		this.bestLister = bestLister;
		this.statistics = Collections.unmodifiableMap(stats);
	}

	public Date getMonth() {
		return month;
	}

	public Map<String, MarketplaceStats> getStatistics() {
		return statistics;
	}

	public List<String> getBestLister() {
		return bestLister;
	}

}
