package com.wob.assignment.app.reporting.generate.data.queryresult;

import java.util.Date;

/**
 * POJO for a monthly report data returned by query.
 * 
 * @author matezoltankiss
 *
 */
public interface MonthlyStatsRow {

	public Date getMonth();

	public String getMarketplace();

	public int getListingCount();

	public double getTotalListingPrice();

	public double getAverageListingPrice();

}
