package com.wob.assignment.app.reporting.report;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wob.assignment.app.reporting.config.ReportConfig;
import com.wob.assignment.app.reporting.generate.data.AggregatedMarketplaceData;
import com.wob.assignment.app.reporting.generate.data.MarketplaceStats;
import com.wob.assignment.app.reporting.generate.data.RawReportData;
import com.wob.assignment.app.reporting.generate.data.queryresult.MonthlyBestListerRow;
import com.wob.assignment.app.reporting.generate.data.queryresult.MonthlyStatsRow;

@Component
public class ReportFactory {

	@Autowired
	private ReportConfig reportConfig;

	public Report createReport(final RawReportData rawData) {

		final Map<String, AggregatedMarketplaceData> aggregators = initializeAggergators();

		// collect best listers with non-null month
		final List<MonthlyBestListerRow> monthlyBestListers = rawData.getMonthlyBestLister().stream()
				.filter(l -> Objects.nonNull(l.getMonth())).collect(Collectors.toList());

		// collect months for monthly report builders
		final TreeSet<Date> months = rawData.getMonthlyBestLister().stream().filter(m -> Objects.nonNull(m.getMonth()))
				.map(MonthlyBestListerRow::getMonth).collect(Collectors.toCollection(TreeSet::new));

		final Map<Date, MonthlyReportBuilder> builders = initializeBuilders(months);

		// add monthly best listers
		for (final MonthlyBestListerRow bestlister : monthlyBestListers) {
			builders.get(bestlister.getMonth()).addBestLister(bestlister.getEmail());
		}

		// add monthly statistics for non-null months
		final List<MonthlyStatsRow> monthlyStats = rawData.getMonthlyStats().stream()
				.filter(s -> Objects.nonNull(s.getMonth())).collect(Collectors.toList());

		for (final MonthlyStatsRow monthlyStat : monthlyStats) {
			final MarketplaceStats marketStats = new MarketplaceStats(monthlyStat.getListingCount(),
					monthlyStat.getTotalListingPrice(), monthlyStat.getAverageListingPrice());
			builders.get(monthlyStat.getMonth()).addStatistics(monthlyStat.getMarketplace(), marketStats);
			final AggregatedMarketplaceData aggregator = aggregators.get(monthlyStat.getMarketplace());
			aggregator.incrementListingCount(monthlyStat.getListingCount());
			aggregator.incrementTotalPrice(monthlyStat.getTotalListingPrice());
		}

		// global data
		final int totalListingCount = aggregators.values().stream().map(AggregatedMarketplaceData::getTotalListingCount)
				.reduce(0, Integer::sum);
		final List<String> allTimeBestLister = rawData.getAlltimeBestLister();

		// build monthly reports
		final List<MonthlyReport> monthlyReports = builders.values().stream().map(MonthlyReportBuilder::build)
				.collect(Collectors.toList());

		// collect and add NULL month data (i.e. data not belonging to any month
		final MonthlyReport nullMonthReport = collectNullMonthData(rawData);
		monthlyReports.add(nullMonthReport);

		return new Report(totalListingCount, aggregators, allTimeBestLister, monthlyReports);
	}

	private MonthlyReport collectNullMonthData(final RawReportData rawData) {
		final List<String> nullMonthBestLister = rawData.getMonthlyBestLister().stream()
				.filter(l -> Objects.isNull(l.getMonth())).map(MonthlyBestListerRow::getEmail)
				.collect(Collectors.toList());

		final Map<String, List<MonthlyStatsRow>> nullMonthRows = rawData.getMonthlyStats().stream()
				.filter(m -> m.getMonth() == null).collect(Collectors.groupingBy(MonthlyStatsRow::getMarketplace));

		final Map<String, MarketplaceStats> nullMontMarketplaceStats = new HashMap<>();

		for (final Entry<String, List<MonthlyStatsRow>> nullMonthSatEntry : nullMonthRows.entrySet()) {

			final String marketplace = nullMonthSatEntry.getKey();

			int listingCount = 0;
			double totalPrice = 0.0;

			for (final MonthlyStatsRow stat : nullMonthRows.get(marketplace)) {
				listingCount += stat.getListingCount();
				totalPrice += stat.getTotalListingPrice();
			}

			final double avgPrice = listingCount == 0 ? 0.0 : totalPrice / (double) listingCount;
			nullMontMarketplaceStats.put(marketplace, new MarketplaceStats(listingCount, totalPrice, avgPrice));
		}

		return new MonthlyReport(null, nullMonthBestLister, nullMontMarketplaceStats);
	}

	/**
	 * initializes a map with a total data aggregator for every marketplace
	 */
	private Map<String, AggregatedMarketplaceData> initializeAggergators() {

		final Map<String, AggregatedMarketplaceData> map = new HashMap<>();

		for (final String marketplace : this.reportConfig.getMarketplaces()) {
			map.put(marketplace, new AggregatedMarketplaceData());
		}

		return map;
	}

	/**
	 * initializes a map with a monthly report builder for every month between the
	 * first and last one
	 */
	private Map<Date, MonthlyReportBuilder> initializeBuilders(final TreeSet<Date> sortedNonNullMonths) {

		final Map<Date, MonthlyReportBuilder> map = new TreeMap<>();

		final Date firstMonth = sortedNonNullMonths.first();
		final Date lastMonth = sortedNonNullMonths.last();

		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(firstMonth);
		map.put(firstMonth, new MonthlyReportBuilder(firstMonth));

		while (calendar.getTime().before(lastMonth)) {
			calendar.add(Calendar.MONTH, 1);
			final Date nextMonth = calendar.getTime();
			map.put(nextMonth, new MonthlyReportBuilder(nextMonth));
		}

		return map;
	}

}
