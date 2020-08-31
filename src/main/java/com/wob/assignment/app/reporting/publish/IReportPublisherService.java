package com.wob.assignment.app.reporting.publish;

import java.io.File;

import com.wob.assignment.app.operationresult.ReportPublishingOperationResult;

/**
 * Service to publish the generater report via FTP.
 * 
 * @author matezoltankiss
 *
 */
public interface IReportPublisherService {

	public ReportPublishingOperationResult publishReport(final File report);

}
