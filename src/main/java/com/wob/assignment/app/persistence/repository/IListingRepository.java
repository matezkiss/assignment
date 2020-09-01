package com.wob.assignment.app.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wob.assignment.app.persistence.entity.ListingEntity;
import com.wob.assignment.app.reporting.generate.data.queryresult.MonthlyBestListerRow;
import com.wob.assignment.app.reporting.generate.data.queryresult.MonthlyStatsRow;

@Repository
@Transactional(readOnly = true)
public interface IListingRepository extends JpaRepository<ListingEntity, UUID> {

	/**
	 * @return the email address(es) with the greatest number of listings
	 */
	@Query(nativeQuery = true, value = "select email from (select owner_email_address as email, count(owner_email_address) cnt from listing group by owner_email_address) lc join (select count(owner_email_address) cnt from listing group by owner_email_address order by count(owner_email_address) desc limit 1) ml on lc.cnt = ml.cnt")
	public List<String> getBestLister();

	/**
	 * Queries the best lister(s) per month. NULL month allowed
	 * 
	 * @return the email address of the best lister(s) per month
	 */
	@Query(nativeQuery = true, value = "select  str_to_date(concat(s1.month, '-01'), '%Y-%m-%d') as month, s1.email from (select date_format(upload_time, '%Y-%m') month, owner_email_address as email, count(*) cnt from listing li join marketplace m on li.marketplace = m.id group by date_format(upload_time, '%Y-%m'), owner_email_address) s1 join (select month, max(cnt) cnt from (select date_format(upload_time, '%Y-%m') month, owner_email_address, count(*) cnt from listing li join marketplace m on li.marketplace = m.id group by date_format(upload_time, '%Y-%m'), owner_email_address) s2 group by month) s3 on ifnull(s1.month, 'null') = ifnull(s3.month, 'null') and s1.cnt = s3.cnt order by s1.cnt, s1.month")
	public List<MonthlyBestListerRow> getMonthlyBestLister();

	/**
	 * Queries the monthly stats. NULL month is allowed, listings with CANCELLED
	 * status are excluded
	 * 
	 * @return listing count, total price, avg price per marketplace per month
	 */
	@Query(nativeQuery = true, value = "select str_to_date(concat(r.strmonth, '-01'), '%Y-%m-%d') as month, r.marketplace, r.listingCount, r.totalListingPrice, r.averageListingPrice from (select date_format(upload_time, '%Y-%m') as strmonth, marketplace_name as marketplace, count(*) as listingCount, sum(listing_price) as totalListingPrice, avg(listing_price) as averageListingPrice from listing li join marketplace m on li.marketplace = m.id join listing_status ls on li.listing_status = ls.id where status_name in :statuses and marketplace_name in :marketplaces group by date_format(upload_time, '%Y-%m'), marketplace_name) r")
	public List<MonthlyStatsRow> getMonthlyStats(final List<String> statuses, final List<String> marketplaces);

}
