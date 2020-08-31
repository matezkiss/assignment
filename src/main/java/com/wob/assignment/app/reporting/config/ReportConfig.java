package com.wob.assignment.app.reporting.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReportConfig {

	@Value("${reporting.filename}")
	private String filename;

	@Value("#{'${reporting.marketplaces}'.split(',')}")
	private List<String> marketplaces;

	@Value("#{'${reporting.statuses}'.split(',')}")
	private List<String> statuses;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<String> getMarketplaces() {
		return marketplaces;
	}

	public void setMarketplaces(List<String> marketplaces) {
		this.marketplaces = marketplaces;
	}

	public List<String> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}

}
