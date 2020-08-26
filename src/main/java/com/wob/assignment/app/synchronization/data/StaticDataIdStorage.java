package com.wob.assignment.app.synchronization.data;

import java.util.Set;

/**
 * Simple class to store the id static data (i.e. location, status, marketplace
 * ids) retrieved from the database.
 * 
 * @author matezoltankiss
 *
 */
public class StaticDataIdStorage {

	private final Set<Integer> validLocationIds;
	private final Set<Integer> validMarketplaceIds;
	private final Set<Integer> validListingStatusIds;

	public StaticDataIdStorage(Set<Integer> validLocationIds, Set<Integer> validMarketplaceIds,
			Set<Integer> validListingStatusIds) {
		super();
		this.validLocationIds = validLocationIds;
		this.validMarketplaceIds = validMarketplaceIds;
		this.validListingStatusIds = validListingStatusIds;
	}

	public boolean containsLocation(final int locationId) {
		return this.validLocationIds.contains(locationId);
	}

	public boolean containsMarketplace(final int marketplaceId) {
		return this.validMarketplaceIds.contains(marketplaceId);
	}

	public boolean containsListingStatus(final int listingStatusId) {
		return this.validListingStatusIds.contains(listingStatusId);
	}

}
