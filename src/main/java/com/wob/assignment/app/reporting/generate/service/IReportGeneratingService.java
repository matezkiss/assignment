package com.wob.assignment.app.reporting.generate.service;

import com.wob.assignment.app.operationresult.ReportGenerationOperationResult;

/**
 * Service generating reports from the synchronized data.
 * 
 * @author matezoltankiss
 *
 */
public interface IReportGeneratingService {

	public ReportGenerationOperationResult generateReport();

}
