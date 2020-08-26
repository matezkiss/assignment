package com.wob.assignment.app.synchronization.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wob.assignment.app.synchronization.data.ListingStatus;
import com.wob.assignment.app.synchronization.data.Location;
import com.wob.assignment.app.synchronization.data.Marketplace;

/**
 * 
 * A single service to pull all data from the REST endpoints.
 * 
 * @author matezoltankiss
 *
 */
@Service
public class DataSynchronizationService implements IDataSynchronizationService {

	private static Logger logger = LoggerFactory.getLogger(DataSynchronizationService.class);

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Override
	public StaticDataSynchronizationOperationResult synchronizeStaticData() {
		logger.info("beginning synchronization of static data");
		synchronizeMarketplaceData();
		synchronizeListingStatusData();
		synchronizeLocationData();
		logger.info("finished synchronization of static data");
		return StaticDataSynchronizationOperationResult.failure("not yet impletmented");
	}

	private void synchronizeMarketplaceData() {
		final Marketplace[] marketplace = restTemplate
				.getForObject("https://my.api.mockaroo.com/marketplace?key=63304c70", Marketplace[].class);
		logger.info(marketplace[0].getName());
	}

	private void synchronizeListingStatusData() {
		final ListingStatus[] listingStatuses = restTemplate
				.getForObject("https://my.api.mockaroo.com/listingStatus?key=63304c70", ListingStatus[].class);
		logger.info(listingStatuses[0].getName());
	}

	private void synchronizeLocationData() {
		final Location[] locations = restTemplate.getForObject("https://my.api.mockaroo.com/location?key=63304c70",
				Location[].class);
		logger.info(locations[0].getPrimaryAddress());
	}

}
