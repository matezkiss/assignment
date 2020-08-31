package com.wob.assignment.app.operationresult;

import com.wob.assignment.app.reporting.publish.PublishToFtpResult;

/**
 * Operation result class for report publishing.
 * 
 * @author matezoltankiss
 *
 */
public class ReportPublishingOperationResult extends OperationResult<PublishToFtpResult> {

	public ReportPublishingOperationResult(final boolean isSuccess, final String message,
			final PublishToFtpResult value) {
		super(isSuccess, message, value);
	}

	public static ReportPublishingOperationResult success(final PublishToFtpResult result) {
		return new ReportPublishingOperationResult(true, "report successfully published", result);
	}

	public static ReportPublishingOperationResult failure(final String message) {
		return new ReportPublishingOperationResult(false, message, null);
	}

}
