package com.wob.assignment.app.reporting.generate.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wob.assignment.app.operationresult.ReportGenerationOperationResult;
import com.wob.assignment.app.persistence.repository.IListingRepository;
import com.wob.assignment.app.reporting.config.ReportConfig;
import com.wob.assignment.app.reporting.generate.data.RawReportData;
import com.wob.assignment.app.reporting.generate.data.queryresult.MonthlyBestListerRow;
import com.wob.assignment.app.reporting.generate.data.queryresult.MonthlyStatsRow;
import com.wob.assignment.app.reporting.report.Report;
import com.wob.assignment.app.reporting.report.ReportFactory;
import com.wob.assignment.app.reporting.report.ReportingResult;

/**
 * Implementation of {@code IReportingService}. Report generation is done in a
 * bottom-up manner: first, monthly reports are created and their results are
 * used to calculate the total values. This approach can not be used to
 * calculate the best lister.
 * 
 * @author matezoltankiss
 *
 */
@Service
public class ReportGeneratingService implements IReportGeneratingService {

	private static Logger logger = LoggerFactory.getLogger(ReportGeneratingService.class);

	@Autowired
	private ReportConfig reportConfig;

	@Autowired
	private IListingRepository listingRepo;

	@Autowired
	private ReportFactory reportFactory;

	@Override
	public ReportGenerationOperationResult generateReport() {

		// collect data from the database
		final RawReportData rawData;
		try {
			logger.info("collecting necessary data from the database.");
			rawData = collectData();
		} catch (final Exception e) {
			logger.error("failed collect necessary data from the database.", e);
			return ReportGenerationOperationResult.failure(e.getMessage());
		}

		// process and transform the retrieved data
		final Report report;
		try {
			logger.info("processing retrieved data.");
			report = processData(rawData);
		} catch (final Exception e) {
			logger.error("failed process necessary data.", e);
			return ReportGenerationOperationResult.failure(e.getMessage());
		}

		// save the report
		final File savedReport;
		try {
			logger.info("saving report to temp file.");
			savedReport = saveReportAsJSON(report);
		} catch (final Exception e) {
			logger.error("failed to save report.", e);
			return ReportGenerationOperationResult.failure(e.getMessage());
		}

		return ReportGenerationOperationResult.success(new ReportingResult(savedReport));
	}

	private RawReportData collectData() {
		final List<MonthlyStatsRow> monthlyStats = collectMonthlyStatsData();
		final List<MonthlyBestListerRow> monthlyBestListers = collectMonthlyBestListers();
		final List<String> alltimeBestLister = getAlltimeBestLister();

		return new RawReportData(monthlyStats, monthlyBestListers, alltimeBestLister);
	}

	@Transactional
	private List<MonthlyStatsRow> collectMonthlyStatsData() {
		return this.listingRepo.getMonthlyStats(this.reportConfig.getStatuses(), this.reportConfig.getMarketplaces());
	}

	@Transactional
	private List<MonthlyBestListerRow> collectMonthlyBestListers() {
		return this.listingRepo.getMonthlyBestLister();
	}

	@Transactional
	private List<String> getAlltimeBestLister() {
		return this.listingRepo.getBestLister();
	}

	private Report processData(final RawReportData rawData) {
		return this.reportFactory.createReport(rawData);
	}

	// saves the report to a temporary file in JSON format
	private File saveReportAsJSON(final Report report)
			throws JsonGenerationException, JsonMappingException, IOException {
		final ObjectMapper objectMapper = new ObjectMapper();
		final File reportFile = new File(reportConfig.getFilename());
		objectMapper.writeValue(reportFile, report);
		return reportFile;
	}

}
