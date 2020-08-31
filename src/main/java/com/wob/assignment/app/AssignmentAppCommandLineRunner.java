package com.wob.assignment.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.wob.assignment.app.operationresult.DynamicDataSynchronizationOperationResult;
import com.wob.assignment.app.operationresult.ReportGenerationOperationResult;
import com.wob.assignment.app.operationresult.ReportPublishingOperationResult;
import com.wob.assignment.app.operationresult.StaticDataSynchronizationOperationResult;
import com.wob.assignment.app.reporting.generate.service.IReportGeneratingService;
import com.wob.assignment.app.reporting.publish.IReportPublisherService;
import com.wob.assignment.app.reporting.publish.PublishToFtpResult;
import com.wob.assignment.app.reporting.report.ReportingResult;
import com.wob.assignment.app.synchronization.data.StaticDataStorage;
import com.wob.assignment.app.synchronization.service.IDataSynchronizationService;
import com.wob.assignment.app.synchronization.validation.DataSynchronizationReport;

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

	@Autowired
	private IReportGeneratingService reportGenService;

	@Autowired
	private IReportPublisherService reportPubService;

	@Override
	public void run(String... args) throws Exception {
		logger.info("Assignment app started.");

		// sync static data
		final StaticDataSynchronizationOperationResult staticDataSynchronizationOperationResult = this.dataSyncService
				.synchronizeStaticData();
		if (!staticDataSynchronizationOperationResult.isSuccess()) {
			logger.error("static data synchronization failed. {}",
					staticDataSynchronizationOperationResult.getMessage());
			return;
		}
		logger.info(staticDataSynchronizationOperationResult.getMessage());

		final StaticDataStorage staticDataStorage = staticDataSynchronizationOperationResult.getResult();

		// sync and validate dynamic data
		final DynamicDataSynchronizationOperationResult dynamicDataSynchronizationOperationResult = this.dataSyncService
				.synchronizeDynamicData(staticDataStorage);
		if (!dynamicDataSynchronizationOperationResult.isSuccess()) {
			logger.error("static data synchronization failed. {}",
					dynamicDataSynchronizationOperationResult.getMessage());
			return;
		}
		logger.info(dynamicDataSynchronizationOperationResult.getMessage());
		final DataSynchronizationReport dataSyncReport = dynamicDataSynchronizationOperationResult.getResult();
		logger.info("found {} valid and {} invalid listings. see importlog: {}", dataSyncReport.getValidListingCount(),
				dataSyncReport.getInvalidListingCount(), dataSyncReport.getImportlogPath());

		// generate report
		final ReportGenerationOperationResult reportGenerationOperationResult = this.reportGenService.generateReport();
		if (!reportGenerationOperationResult.isSuccess()) {
			logger.error("report generation failed. {}", reportGenerationOperationResult.getMessage());
			return;
		}

		final ReportingResult report = reportGenerationOperationResult.getResult();
		logger.info("report generation successful. saved report into tempfile {}",
				report.getSavedReport().getAbsolutePath());

		// publish report
		final ReportPublishingOperationResult reportPublishingOperationResult = this.reportPubService
				.publishReport(report.getSavedReport());
		if (!reportPublishingOperationResult.isSuccess()) {
			logger.error("report publishing failed. {}", reportPublishingOperationResult.getMessage());
			return;
		}

		final PublishToFtpResult ftpResult = reportPublishingOperationResult.getResult();
		logger.info("report successfully published to {}", ftpResult.getFtpPath());

		logger.info("Assignment app finished.");
	}

}
