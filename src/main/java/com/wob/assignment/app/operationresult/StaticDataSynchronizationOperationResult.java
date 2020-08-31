package com.wob.assignment.app.operationresult;

import com.wob.assignment.app.synchronization.data.StaticDataStorage;

/**
 * Operation result class for static data synchronization. (i.e. marketplace,
 * location, listing status)
 * 
 * @author matezoltankiss
 *
 */
public class StaticDataSynchronizationOperationResult extends OperationResult<StaticDataStorage> {

	public StaticDataSynchronizationOperationResult(final boolean isSuccess, final String message,
			final StaticDataStorage staticDataIdCache) {
		super(isSuccess, message, staticDataIdCache);
	}

	public static StaticDataSynchronizationOperationResult success(final StaticDataStorage staticDataIdCache) {
		return new StaticDataSynchronizationOperationResult(true, "static data synchronization successful",
				staticDataIdCache);
	}

	public static StaticDataSynchronizationOperationResult failure(final String failureMessage) {
		return new StaticDataSynchronizationOperationResult(false, failureMessage, null);
	}

}
