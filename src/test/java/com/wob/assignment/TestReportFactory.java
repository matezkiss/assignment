package com.wob.assignment;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.wob.assignment.app.reporting.config.ReportConfig;
import com.wob.assignment.app.reporting.generate.data.RawReportData;
import com.wob.assignment.app.reporting.generate.data.queryresult.MonthlyBestListerRow;
import com.wob.assignment.app.reporting.generate.data.queryresult.MonthlyStatsRow;
import com.wob.assignment.app.reporting.report.Report;
import com.wob.assignment.app.reporting.report.ReportFactory;

public class TestReportFactory {

	private static ReportFactory reportFactory;

	private static final String marketplace = "MARKET";
	private static final int monthlyListingCount = 10;
	private static final double monthlyTotalPrice = 50.00;
	private static final double monthlyAveragePrice = 5.00;
	private static final String absoluteBestLister = "absolute-best-lister@bookworm.com";
	private static final String startMonthBestLister = "start.month.bestlister@email.com";
	private static final String endMonthBestLister = "end.month.bestlister@email.com";

	@Test
	public void whenMonthWithoutListingIsBetweenTwoMonthsWithListing_thenThreeMonthlyReportsAreCreated() {

		final RawReportData rawReportData = createReportData(false);

		final Report report = reportFactory.createReport(rawReportData);

		assertThat(report.getMonthlyReports().size(), is(3));
	}

	@Test
	public void testIfMonthlyNumbersAreCorrectlyAggregated() {

		final RawReportData rawReportData = createReportData(false);

		final Report report = reportFactory.createReport(rawReportData);

		assertThat(report.getAggregatedMarketplaceData().get(marketplace).getAverageListingPrice(),
				is(monthlyAveragePrice));
		assertThat(report.getAggregatedMarketplaceData().get(marketplace).getTotalListingCount(),
				is(2 * monthlyListingCount));
		assertThat(report.getAggregatedMarketplaceData().get(marketplace).getTotalListingPrice(),
				is(2 * monthlyTotalPrice));
	}

	@Test
	public void testIfMonthlyNumbersAreCorrectlyAggregatedWithNullMonth() {

		final RawReportData rawReportData = createReportData(true);

		final Report report = reportFactory.createReport(rawReportData);

		assertThat(report.getAggregatedMarketplaceData().get(marketplace).getAverageListingPrice(),
				is(monthlyAveragePrice));
		assertThat(report.getAggregatedMarketplaceData().get(marketplace).getTotalListingCount(),
				is(3 * monthlyListingCount));
		assertThat(report.getAggregatedMarketplaceData().get(marketplace).getTotalListingPrice(),
				is(3 * monthlyTotalPrice));
	}

	private RawReportData createReportData(final boolean addNullMonth) {
		final MonthlyStatsRow startMonthStats = mock(MonthlyStatsRow.class);
		final LocalDate current = LocalDate.now();
		final Date startDate = Date.from(current.minusMonths(2).atStartOfDay(ZoneId.systemDefault()).toInstant());
		final Date endDate = Date.from(current.atStartOfDay(ZoneId.systemDefault()).toInstant());
		when(startMonthStats.getMonth()).thenReturn(startDate);
		when(startMonthStats.getListingCount()).thenReturn(monthlyListingCount);
		when(startMonthStats.getTotalListingPrice()).thenReturn(monthlyTotalPrice);
		when(startMonthStats.getAverageListingPrice()).thenReturn(monthlyAveragePrice);
		when(startMonthStats.getMarketplace()).thenReturn(marketplace);
		final MonthlyStatsRow endMonthStats = mock(MonthlyStatsRow.class);
		when(endMonthStats.getMonth()).thenReturn(endDate);
		when(endMonthStats.getListingCount()).thenReturn(monthlyListingCount);
		when(endMonthStats.getTotalListingPrice()).thenReturn(monthlyTotalPrice);
		when(endMonthStats.getAverageListingPrice()).thenReturn(monthlyAveragePrice);
		when(endMonthStats.getMarketplace()).thenReturn(marketplace);
		final List<MonthlyStatsRow> monthlyStats = new ArrayList<>();
		monthlyStats.add(endMonthStats);
		monthlyStats.add(startMonthStats);

		if (addNullMonth) {
			final MonthlyStatsRow nullMonthStats = mock(MonthlyStatsRow.class);
			when(nullMonthStats.getMonth()).thenReturn(null);
			when(nullMonthStats.getListingCount()).thenReturn(monthlyListingCount);
			when(nullMonthStats.getTotalListingPrice()).thenReturn(monthlyTotalPrice);
			when(nullMonthStats.getAverageListingPrice()).thenReturn(monthlyAveragePrice);
			when(nullMonthStats.getMarketplace()).thenReturn(marketplace);
			monthlyStats.add(nullMonthStats);
		}

		final MonthlyBestListerRow bestListerStart = mock(MonthlyBestListerRow.class);
		when(bestListerStart.getMonth()).thenReturn(startDate);
		when(bestListerStart.getEmail()).thenReturn(startMonthBestLister);
		final MonthlyBestListerRow bestListerEnd = mock(MonthlyBestListerRow.class);
		when(bestListerEnd.getMonth()).thenReturn(endDate);
		when(bestListerEnd.getEmail()).thenReturn(endMonthBestLister);
		final List<MonthlyBestListerRow> monthlyBestLister = Arrays
				.asList(new MonthlyBestListerRow[] { bestListerEnd, bestListerStart });

		final List<String> alltimeBestLister = Arrays.asList(new String[] { absoluteBestLister });

		return new RawReportData(monthlyStats, monthlyBestLister, alltimeBestLister);
	}

	@BeforeClass
	public static void setup() {

		final ReportFactory reportFactoryWithMockedConfig = new ReportFactory();
		final ReportConfig reportConfig = mock(ReportConfig.class);
		when(reportConfig.getMarketplaces()).thenReturn(Arrays.asList(new String[] { marketplace }));
		ReflectionTestUtils.setField(reportFactoryWithMockedConfig, "reportConfig", reportConfig);
		reportFactory = reportFactoryWithMockedConfig;

	}

}
