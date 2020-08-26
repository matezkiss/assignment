package com.wob.assignment.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.wob.assignment.app.synchronization.service.IDataSynchronizationService;
import com.wob.assignment.app.synchronization.service.StaticDataSynchronizationOperationResult;

/**
 * Implementation of CommandLineRunner for executing the specified tasks.
 * 
 * @author matezoltankiss
 *
 */
@Component
public class AssignmentAppCommandLineRunner implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(AssignmentAppCommandLineRunner.class);

	@Autowired
	private IDataSynchronizationService dataSyncService;

	@Override
	public void run(String... args) throws Exception {
		logger.info("Assignment app started.");

		final StaticDataSynchronizationOperationResult operationResult = dataSyncService.synchronizeStaticData();
		if (!operationResult.isSuccess()) {
			logger.error("static data synchronization failed. {}", operationResult.getMessage());
			return;
		}

		logger.info("Assignment app finished.");
	}

}
