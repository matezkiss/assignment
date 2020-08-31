package com.wob.assignment.app.synchronization.service;

import com.wob.assignment.app.operationresult.DynamicDataSynchronizationOperationResult;
import com.wob.assignment.app.operationresult.StaticDataSynchronizationOperationResult;
import com.wob.assignment.app.synchronization.data.StaticDataStorage;

/**
 * 
 * A single service interface to pull all data from the REST endpoints.
 * 
 * @author matezoltankiss
 *
 */
public interface IDataSynchronizationService {

	public StaticDataSynchronizationOperationResult synchronizeStaticData();

	public DynamicDataSynchronizationOperationResult synchronizeDynamicData(final StaticDataStorage staticDataStorage);

}
