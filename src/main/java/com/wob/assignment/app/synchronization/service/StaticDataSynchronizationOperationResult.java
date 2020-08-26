package com.wob.assignment.app.synchronization.service;

import com.wob.assignment.app.operationresult.OperationResult;
import com.wob.assignment.app.synchronization.data.StaticDataIdStorage;

/**
 * Operation result class for static data synchronization.
 * 
 * @author matezoltankiss
 *
 */
public class StaticDataSynchronizationOperationResult extends OperationResult<StaticDataIdStorage> {

	public StaticDataSynchronizationOperationResult(final boolean isSuccess, final String message,
			final StaticDataIdStorage staticDataIdCache) {
		super(isSuccess, message, staticDataIdCache);
	}

	public static StaticDataSynchronizationOperationResult success(final StaticDataIdStorage staticDataIdCache) {
		return new StaticDataSynchronizationOperationResult(true, "static data synchronization successful",
				staticDataIdCache);
	}

	public static StaticDataSynchronizationOperationResult failure(final String failureMessage) {
		return new StaticDataSynchronizationOperationResult(false, failureMessage, null);
	}

}
