package com.wob.assignment.app.synchronization.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wob.assignment.app.operationresult.DynamicDataSynchronizationOperationResult;
import com.wob.assignment.app.operationresult.StaticDataSynchronizationOperationResult;
import com.wob.assignment.app.persistence.entity.ListingEntity;
import com.wob.assignment.app.persistence.entity.ListingStatusEntity;
import com.wob.assignment.app.persistence.entity.LocationEntity;
import com.wob.assignment.app.persistence.entity.MarketplaceEntity;
import com.wob.assignment.app.persistence.repository.IListingRepository;
import com.wob.assignment.app.persistence.repository.IListingStatusRepository;
import com.wob.assignment.app.persistence.repository.ILocationRepository;
import com.wob.assignment.app.persistence.repository.IMarketplaceRepository;
import com.wob.assignment.app.synchronization.config.RESTConfig;
import com.wob.assignment.app.synchronization.data.Listing;
import com.wob.assignment.app.synchronization.data.ListingStatus;
import com.wob.assignment.app.synchronization.data.Location;
import com.wob.assignment.app.synchronization.data.Marketplace;
import com.wob.assignment.app.synchronization.data.StaticDataStorage;
import com.wob.assignment.app.synchronization.validation.ListingValidator;

/**
 * 
 * Implementation of {@code IDataSynchronizationService}.
 * 
 * @author matezoltankiss
 *
 */
@Service
public class DataSynchronizationService implements IDataSynchronizationService {

	private static Logger logger = LoggerFactory.getLogger(DataSynchronizationService.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RESTConfig restConfig;

	@Autowired
	private ILocationRepository locationRepo;

	@Autowired
	private IMarketplaceRepository marketplaceRepo;

	@Autowired
	private IListingStatusRepository listingStatusRepo;

	@Autowired
	private IListingRepository listingRepo;

	@Value("${importlog_file}")
	private String importlogfile;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Override
	public StaticDataSynchronizationOperationResult synchronizeStaticData() {

		logger.info("beginning synchronization of static data");

		try {
			final List<MarketplaceEntity> marketplaces = synchronizeMarketplaceData();
			final List<ListingStatusEntity> listingStatuses = synchronizeListingStatusData();
			final List<LocationEntity> locations = synchronizeLocationData();
			logger.info("finished synchronization of static data");
			final StaticDataStorage staticDataCache = StaticDataStorage.create(locations, marketplaces,
					listingStatuses);
			return StaticDataSynchronizationOperationResult.success(staticDataCache);
		} catch (final Exception e) {
			logger.error("failed to synchronized static data", e);
			return StaticDataSynchronizationOperationResult.failure(e.getMessage());
		}

	}

	private List<MarketplaceEntity> synchronizeMarketplaceData() {

		final Marketplace[] marketplaceResponse = restTemplate.getForObject(restConfig.getMarketplaceUrl(),
				Marketplace[].class);

		List<MarketplaceEntity> marketplaces = new ArrayList<>(marketplaceResponse.length);

		for (final Marketplace m : marketplaceResponse) {
			final MarketplaceEntity me = new MarketplaceEntity(m.getId(), m.getName());
			marketplaces.add(me);
		}

		marketplaces = this.marketplaceRepo.saveAll(marketplaces);
		logger.info("persisted {} marketplace(s)", marketplaces.size());

		return marketplaces;
	}

	private List<ListingStatusEntity> synchronizeListingStatusData() {

		final ListingStatus[] listingStatusResponse = restTemplate.getForObject(restConfig.getListingStatusUrl(),
				ListingStatus[].class);

		List<ListingStatusEntity> listingStatuses = new ArrayList<>(listingStatusResponse.length);

		for (final ListingStatus ls : listingStatusResponse) {
			final ListingStatusEntity lse = new ListingStatusEntity(ls.getId(), ls.getName());
			listingStatuses.add(lse);
		}

		listingStatuses = this.listingStatusRepo.saveAll(listingStatuses);
		logger.info("persisted {} listingStatus(es)", listingStatuses.size());

		return listingStatuses;
	}

	private List<LocationEntity> synchronizeLocationData() {

		final Location[] locationResponse = restTemplate.getForObject(restConfig.getLocationUrl(), Location[].class);

		List<LocationEntity> locations = new ArrayList<>(locationResponse.length);

		for (final Location l : locationResponse) {
			final LocationEntity le = new LocationEntity(l.getId(), l.getManagerName(), l.getPhone(),
					l.getPrimaryAddress(), l.getSecondaryAddress(), l.getCountry(), l.getTown(), l.getPostalCode());
			locations.add(le);
		}

		locations = this.locationRepo.saveAll(locations);
		logger.info("persisted {} locations(s)", locations.size());

		return locations;
	}

	public DynamicDataSynchronizationOperationResult synchronizeDynamicData(final StaticDataStorage staticDataStorage) {

		logger.info("beginning synchronization of dynamic data");

		try {
			final Listing[] listingResponse = restTemplate.getForObject(restConfig.getListingUrl(), Listing[].class);
			logger.info("retieved {} listings. beginning validation", listingResponse.length);

			final ListingValidator validator = new ListingValidator(staticDataStorage);

			validator.processListings(listingResponse);
			final List<Listing> validListings = validator.getValidListings();
			List<ListingEntity> listings = new ArrayList<>(validListings.size());

			for (final Listing l : validListings) {
				final ListingEntity le = new ListingEntity(UUID.fromString(l.getId()), l.getTitle(), l.getDescription(),
						staticDataStorage.getLocation(UUID.fromString(l.getLocationId())), l.getPrice(),
						l.getCurrency(), l.getQuantity(), staticDataStorage.getListingStatus(l.getStatus()),
						staticDataStorage.getMarketplace(l.getMarketplace()), l.getUploadDate(),
						l.getOwnerEmailAddress());
				listings.add(le);
			}

			listings = this.listingRepo.saveAll(listings);

			validator.writeImportlog(this.importlogfile);
			logger.info("persisted {} listing(s)", listings.size());
			logger.info("finished synchronization of dynamic data");
			return DynamicDataSynchronizationOperationResult.success(validator.getValidationReport());
		} catch (final Exception e) {
			logger.error("failed to synchronized dynamic data", e);
			return DynamicDataSynchronizationOperationResult.failure(e.getMessage());
		}

	}

}
