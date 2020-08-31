package com.wob.assignment.app.operationresult;

import com.wob.assignment.app.synchronization.validation.DataSynchronizationReport;

/**
 * Operation result class for dynamic data synchronization. (i.e. listings)
 * 
 * @author matezoltankiss
 *
 */
public class DynamicDataSynchronizationOperationResult extends OperationResult<DataSynchronizationReport> {

	public DynamicDataSynchronizationOperationResult(final boolean isSuccess, final String message,
			final DataSynchronizationReport value) {
		super(isSuccess, message, value);
	}

	public static DynamicDataSynchronizationOperationResult success(final DataSynchronizationReport staticDataIdCache) {
		return new DynamicDataSynchronizationOperationResult(true, "dynamic data synchronization successful",
				staticDataIdCache);
	}

	public static DynamicDataSynchronizationOperationResult failure(final String failureMessage) {
		return new DynamicDataSynchronizationOperationResult(false, failureMessage, null);
	}

}
