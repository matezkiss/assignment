package com.wob.assignment.app.operationresult;

import com.wob.assignment.app.reporting.report.ReportingResult;

/**
 * Operation result class for generating reports.
 * 
 * @author matezoltankiss
 *
 */
public class ReportGenerationOperationResult extends OperationResult<ReportingResult> {

	public ReportGenerationOperationResult(final boolean isSuccess, final String message, final ReportingResult value) {
		super(isSuccess, message, value);
	}

	public static ReportGenerationOperationResult success(final ReportingResult report) {
		return new ReportGenerationOperationResult(true, "report generation successful", report);
	}

	public static ReportGenerationOperationResult failure(final String message) {
		return new ReportGenerationOperationResult(false, message, null);
	}

}
