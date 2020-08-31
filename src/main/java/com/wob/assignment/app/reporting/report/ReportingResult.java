package com.wob.assignment.app.reporting.report;

import java.io.File;

public class ReportingResult {

	private final File savedReport;

	public ReportingResult(final File savedReport) {
		this.savedReport = savedReport;
	}

	public File getSavedReport() {
		return this.savedReport;
	}

}
