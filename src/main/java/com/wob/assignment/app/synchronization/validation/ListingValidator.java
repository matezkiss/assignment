package com.wob.assignment.app.synchronization.validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.validator.routines.EmailValidator;

import com.wob.assignment.app.synchronization.data.Listing;
import com.wob.assignment.app.synchronization.data.StaticDataStorage;

import liquibase.util.StringUtils;

/**
 * 
 * Validated the listings for the constraints given in the assignment pdf.
 * 
 * @author matezoltankiss
 *
 */
public class ListingValidator {

	private final StringBuilder importlogBuilder;
	private final DataSynchronizationReport validationReport;
	private final List<Listing> validListings;
	private final StaticDataStorage staticDataStorage;
	private final Set<String> listingIds;

	public ListingValidator(final StaticDataStorage staticDataStorage) {
		this.importlogBuilder = new StringBuilder();
		this.validationReport = new DataSynchronizationReport();
		this.validListings = new ArrayList<>();
		this.staticDataStorage = staticDataStorage;
		this.listingIds = new HashSet<>();
	}

	public void processListings(final Listing[] listings) {

		for (final Listing listing : listings) {

			boolean valid = true;

			final String marketplace = listing.getMarketplace() == null ? "null"
					: (this.staticDataStorage.getMarketplace(listing.getMarketplace()) == null ? "null"
							: this.staticDataStorage.getMarketplace(listing.getMarketplace()).getName());
			final String prefix = String.format("%s;%s;", listing.getId(), marketplace);

			if (!validId(listing.getId())) {
				valid = false;
				appendImportlogLine(prefix, "id");
			} else {
				this.listingIds.add(listing.getId());
			}

			if (StringUtils.isEmpty(listing.getTitle())) {
				valid = false;
				appendImportlogLine(prefix, "title");
			}

			if (StringUtils.isEmpty(listing.getDescription())) {
				valid = false;
				appendImportlogLine(prefix, "description");
			}

			if (!validLocation(listing.getLocationId())) {
				valid = false;
				appendImportlogLine(prefix, "location_id");
			}

			if (!validPrice(listing.getPrice())) {
				valid = false;
				appendImportlogLine(prefix, "listing_price");
			}

			if (!validCurrency(listing.getCurrency())) {
				valid = false;
				appendImportlogLine(prefix, "currency");
			}

			if (!validQuantity(listing.getQuantity())) {
				valid = false;
				appendImportlogLine(prefix, "quantity");
			}

			if (!validListingStatus(listing.getStatus())) {
				valid = false;
				appendImportlogLine(prefix, "listing_status");
			}

			if (!validMarketplace(listing.getMarketplace())) {
				valid = false;
				appendImportlogLine(prefix, "marketplace");
			}

			if (!validOwnerEmailAddress(listing.getOwnerEmailAddress())) {
				valid = false;
				appendImportlogLine(prefix, "owner_email_address");
			}

			if (valid) {
				this.validListings.add(listing);
				this.validationReport.incrementValidListingCount();
			} else {
				this.validationReport.incrementInvalidListingCount();
			}
		}
	}

	private void appendImportlogLine(final String prefix, final String invalidField) {
		this.importlogBuilder.append(prefix).append(invalidField).append(System.lineSeparator());
	}

	private boolean validId(final String id) {
		final UUID uuid = parseUUID(id);
		return uuid != null && !this.listingIds.contains(id);
	}

	private UUID parseUUID(final String id) {

		if (id != null && id.length() == 36) {
			try {
				return UUID.fromString(id);
			} catch (final IllegalArgumentException e) {
				// nothing to do here
			}
		}

		return null;
	}

	private boolean validLocation(final String locationId) {
		final UUID uuid = parseUUID(locationId);
		return uuid != null && this.staticDataStorage.isValidLocation(uuid);
	}

	private boolean validPrice(final Double price) {
		return Objects.nonNull(price) && price.doubleValue() > 0.0 && (BigDecimal.valueOf(price).scale() == 2);
	}

	private boolean validCurrency(final String currency) {
		return StringUtils.isNotEmpty(currency) && currency.toUpperCase().matches("[A-Z]{3}");
	}

	private boolean validQuantity(final Integer quantity) {
		return Objects.nonNull(quantity) && quantity.intValue() > 0;
	}

	private boolean validListingStatus(final Integer listingStatusId) {
		return Objects.nonNull(listingStatusId) && this.staticDataStorage.isValidListingStatus(listingStatusId);
	}

	private boolean validMarketplace(final Integer marketplaceId) {
		return Objects.nonNull(marketplaceId) && this.staticDataStorage.isValidMarketplace(marketplaceId);
	}

	private boolean validOwnerEmailAddress(final String emailAddress) {
		return StringUtils.isNotEmpty(emailAddress) && EmailValidator.getInstance().isValid(emailAddress);

	}

	public void writeImportlog(final String importlogFilePath) throws FileNotFoundException {

		final File importlogfile = new File(importlogFilePath);

		try (final PrintWriter importlogWriter = new PrintWriter(importlogfile)) {
			importlogWriter.println(this.importlogBuilder.toString());
		}

		this.validationReport.setImportlogPath(importlogfile.getAbsolutePath());
	}

	public DataSynchronizationReport getValidationReport() {
		return this.validationReport;
	}

	public List<Listing> getValidListings() {
		return this.validListings;
	}

	public Set<String> getListingIds() {
		return this.listingIds;
	}

	public StringBuilder getImportlogBuilder() {
		return this.importlogBuilder;
	}

}
