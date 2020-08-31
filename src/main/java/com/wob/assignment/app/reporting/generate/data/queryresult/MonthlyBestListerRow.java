package com.wob.assignment.app.reporting.generate.data.queryresult;

import java.util.Date;

/**
 * POJO for a monthly best lister report data returned by query.
 * 
 * @author matezoltankiss
 *
 */
public interface MonthlyBestListerRow {

	public Date getMonth();

	public String getEmail();
}
