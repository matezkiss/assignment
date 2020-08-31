package com.wob.assignment.app.synchronization.data;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.wob.assignment.app.persistence.entity.ListingStatusEntity;
import com.wob.assignment.app.persistence.entity.LocationEntity;
import com.wob.assignment.app.persistence.entity.MarketplaceEntity;

/**
 * Simple class to store the static data (i.e. location, status, marketplace)
 * retrieved from the database.
 * 
 * @author matezoltankiss
 *
 */
public class StaticDataStorage {

	private final Map<UUID, LocationEntity> locationToId;
	private final Map<Integer, MarketplaceEntity> marketplaceToId;
	private final Map<Integer, ListingStatusEntity> listingStatusToId;

	public StaticDataStorage(final Map<UUID, LocationEntity> locationToId,
			final Map<Integer, MarketplaceEntity> marketplaceToId,
			final Map<Integer, ListingStatusEntity> listingStatusToId) {
		this.locationToId = locationToId;
		this.marketplaceToId = marketplaceToId;
		this.listingStatusToId = listingStatusToId;
	}

	public boolean isValidLocation(final UUID locationId) {
		return this.locationToId.containsKey(locationId);
	}

	public LocationEntity getLocation(final UUID locationId) {
		return this.locationToId.get(locationId);
	}

	public boolean isValidMarketplace(final Integer marketplaceId) {
		return this.marketplaceToId.containsKey(marketplaceId);
	}

	public MarketplaceEntity getMarketplace(final Integer marketplaceId) {
		return this.marketplaceToId.get(marketplaceId);
	}

	public boolean isValidListingStatus(final Integer listingStatusId) {
		return this.listingStatusToId.containsKey(listingStatusId);
	}

	public ListingStatusEntity getListingStatus(final int listingStatusId) {
		return this.listingStatusToId.get(listingStatusId);
	}

	public static StaticDataStorage create(final List<LocationEntity> locations,
			final List<MarketplaceEntity> marketplaces, final List<ListingStatusEntity> listingStatuses) {

		final Map<UUID, LocationEntity> locationToId = locations.stream()
				.collect(Collectors.toMap(LocationEntity::getId, l -> l));

		final Map<Integer, MarketplaceEntity> marketplaceToId = marketplaces.stream()
				.collect(Collectors.toMap(MarketplaceEntity::getId, m -> m));

		final Map<Integer, ListingStatusEntity> listingStatusToId = listingStatuses.stream()
				.collect(Collectors.toMap(ListingStatusEntity::getId, s -> s));

		return new StaticDataStorage(locationToId, marketplaceToId, listingStatusToId);
	}

}
