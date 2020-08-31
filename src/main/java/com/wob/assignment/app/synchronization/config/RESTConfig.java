package com.wob.assignment.app.synchronization.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class storing the configuration for data synchronization through the REST
 * API.
 * 
 * @author matezoltankiss
 *
 */
@Component
public class RESTConfig {

	@Value("${rest.url.listing}")
	private String listingUrl;

	@Value("${rest.url.location}")
	private String locationUrl;

	@Value("${rest.url.marketplace}")
	private String marketplaceUrl;

	@Value("${rest.url.listing_status}")
	private String listingStatusUrl;

	public String getListingUrl() {
		return this.listingUrl;
	}

	public String getLocationUrl() {
		return this.locationUrl;
	}

	public String getMarketplaceUrl() {
		return this.marketplaceUrl;
	}

	public String getListingStatusUrl() {
		return this.listingStatusUrl;
	}

}
