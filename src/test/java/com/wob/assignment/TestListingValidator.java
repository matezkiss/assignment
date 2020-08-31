package com.wob.assignment;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.CoreMatchers.*;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.wob.assignment.app.persistence.entity.ListingStatusEntity;
import com.wob.assignment.app.persistence.entity.LocationEntity;
import com.wob.assignment.app.persistence.entity.MarketplaceEntity;
import com.wob.assignment.app.synchronization.data.Listing;
import com.wob.assignment.app.synchronization.data.StaticDataStorage;
import com.wob.assignment.app.synchronization.validation.ListingValidator;

/**
 * 
 * Tests to ensure the desired behavior of the validator. The testcases
 * following the arrange-act-assert model. Listing is mocked since it its fields
 * are immutable. For every testcase a valid Listing instance is returned, then
 * the getter return value is mocked when an invalid value is needed.
 * 
 * @author matezoltankiss
 *
 */
public class TestListingValidator {

	private static ListingValidator validator;
	private static final UUID existingLocationUUID = UUID.randomUUID();
	private static final String nonexistentLocationUUIDString = UUID.randomUUID().toString();
	private static final String validListingUUIDString = UUID.randomUUID().toString();
	private static final String invalidListingUUIDString = "123-ab4-12-a";
	private static final Integer validMarketplaceId = 1;
	private static final Integer invalidMarketplaceId = -1;
	private static final Integer validListingStatusId = 2;
	private static final Integer invalidListingStatusId = -2;
	private static final String empty = "";

	@Test
	public void whenAllFieldsAreValid_thenValid() {

		final Listing validListing = getListingWithValidFields();
		final Listing[] listings = { validListing };

		validator.processListings(listings);

		assertThat(validator.getValidListings().size(), is(1));
	}

	@Test
	public void whenIdIsNotUUID_thenInvalid() {

		final Listing listingWithNullId = getListingWithValidFields();
		when(listingWithNullId.getId()).thenReturn(null);
		final Listing listingWithEmptyStringId = getListingWithValidFields();
		when(listingWithEmptyStringId.getId()).thenReturn(empty);
		final Listing listingWithNonUUIDStringId = getListingWithValidFields();
		when(listingWithNonUUIDStringId.getId()).thenReturn(invalidListingUUIDString);
		final Listing[] listings = { listingWithNullId, listingWithEmptyStringId, listingWithNonUUIDStringId };

		validator.processListings(listings);

		assertThat(validator.getValidListings(), is(empty()));
	}

	@Test
	public void whenUUIDAlreadyExists_thenInvalid() {

		final Listing validListing = getListingWithValidFields();
		final Listing listingWithAlreadyUsedUUID = getListingWithValidFields();
		final Listing[] listings = { validListing, listingWithAlreadyUsedUUID };

		validator.processListings(listings);

		assertThat(validator.getValidListings().size(), is(1));
	}

	@Test
	public void whenTitleIsNullOrEmpty_thenInvalid() {

		final Listing listingWithNullTitle = getListingWithValidFields();
		when(listingWithNullTitle.getTitle()).thenReturn(null);
		final Listing listingWithEmptyStringTitle = getListingWithValidFields();
		when(listingWithEmptyStringTitle.getTitle()).thenReturn(empty);
		final Listing[] listings = { listingWithNullTitle, listingWithEmptyStringTitle };

		validator.processListings(listings);

		assertThat(validator.getValidListings(), is(empty()));
	}

	@Test
	public void whenDescriptionIsNullOrEmpty_thenInvalid() {

		final Listing listingWithNullDescription = getListingWithValidFields();
		when(listingWithNullDescription.getDescription()).thenReturn(null);
		final Listing listingWithEmptyStringDescription = getListingWithValidFields();
		when(listingWithEmptyStringDescription.getDescription()).thenReturn(empty);
		final Listing[] listings = { listingWithNullDescription, listingWithEmptyStringDescription };

		validator.processListings(listings);

		assertThat(validator.getValidListings(), is(empty()));
	}

	@Test
	public void whenLocationIsNullOrEmptyOrInvalid_thenInvalid() {

		final Listing listingWithNullLocation = getListingWithValidFields();
		when(listingWithNullLocation.getLocationId()).thenReturn(null);
		final Listing listingWithEmptyLocation = getListingWithValidFields();
		when(listingWithEmptyLocation.getLocationId()).thenReturn(empty);
		final Listing listingWithNonexistentLocation = getListingWithValidFields();
		when(listingWithNonexistentLocation.getLocationId()).thenReturn(nonexistentLocationUUIDString);
		final Listing[] listings = { listingWithNullLocation, listingWithEmptyLocation,
				listingWithNonexistentLocation };

		validator.processListings(listings);

		assertThat(validator.getValidListings(), is(empty()));
	}

	@Test
	public void whenPriceIsNotPositiveWithTwoDecimals_thenInvalid() {

		final Listing listingWithNullPrice = getListingWithValidFields();
		when(listingWithNullPrice.getPrice()).thenReturn(null);
		final Listing listingWithNegativePrice = getListingWithValidFields();
		when(listingWithNegativePrice.getPrice()).thenReturn(-1.0);
		final Listing listingWithZeroPrice = getListingWithValidFields();
		when(listingWithZeroPrice.getPrice()).thenReturn(0.0);
		final Listing listingWithThreeDecimalPrice = getListingWithValidFields();
		when(listingWithThreeDecimalPrice.getPrice()).thenReturn(1.111);
		final Listing[] listings = { listingWithNullPrice, listingWithNegativePrice, listingWithZeroPrice,
				listingWithThreeDecimalPrice };

		validator.processListings(listings);

		assertThat(validator.getValidListings(), is(empty()));
	}

	@Test
	public void whenCurrencyIsNotStringWithLengthOfThree_thenInvalid() {

		final Listing listingWithNullCurrency = getListingWithValidFields();
		when(listingWithNullCurrency.getCurrency()).thenReturn(null);
		final Listing listingWithEmptyCurrency = getListingWithValidFields();
		when(listingWithEmptyCurrency.getCurrency()).thenReturn(empty);
		final Listing listingWithTwoLetterCurrency = getListingWithValidFields();
		when(listingWithTwoLetterCurrency.getCurrency()).thenReturn("HU");
		final Listing listingWithFourLetterCurrency = getListingWithValidFields();
		when(listingWithFourLetterCurrency.getCurrency()).thenReturn("FUHH");
		final Listing listingWithNonAlphaCurrency = getListingWithValidFields();
		when(listingWithNonAlphaCurrency.getCurrency()).thenReturn("U5D"); // note the 5 instead of S
		final Listing[] listings = { listingWithNullCurrency, listingWithEmptyCurrency, listingWithTwoLetterCurrency,
				listingWithFourLetterCurrency, listingWithNonAlphaCurrency };

		validator.processListings(listings);

		assertThat(validator.getValidListings(), is(empty()));
	}

	@Test
	public void whenQuantityIsNotPositiveInteger_thenInvalid() {

		final Listing listingWithNullQuantity = getListingWithValidFields();
		when(listingWithNullQuantity.getQuantity()).thenReturn(null);
		final Listing listingWithNegativeQuantity = getListingWithValidFields();
		when(listingWithNegativeQuantity.getQuantity()).thenReturn(-1);
		final Listing listingWithZeroQuantity = getListingWithValidFields();
		when(listingWithZeroQuantity.getQuantity()).thenReturn(0);
		final Listing[] listings = { listingWithNullQuantity, listingWithNegativeQuantity, listingWithZeroQuantity };

		validator.processListings(listings);

		assertThat(validator.getValidListings(), is(empty()));
	}

	@Test
	public void whenListingDoesNotReferenceValidListingStatus_thenInvalid() {

		final Listing listingWithNullStatus = getListingWithValidFields();
		when(listingWithNullStatus.getStatus()).thenReturn(null);
		final Listing listingWithInvalidStatusId = getListingWithValidFields();
		when(listingWithInvalidStatusId.getStatus()).thenReturn(invalidListingStatusId);
		final Listing[] listings = { listingWithNullStatus, listingWithInvalidStatusId };

		validator.processListings(listings);

		assertThat(validator.getValidListings(), is(empty()));
	}

	@Test
	public void whenListingDoesNotReferenceValidMarketplace_thenInvalid() {

		final Listing listingWithNullMarketplace = getListingWithValidFields();
		when(listingWithNullMarketplace.getMarketplace()).thenReturn(null);
		final Listing listingWithInvalidMarketplaceId = getListingWithValidFields();
		when(listingWithInvalidMarketplaceId.getMarketplace()).thenReturn(invalidMarketplaceId);
		final Listing[] listings = { listingWithNullMarketplace, listingWithInvalidMarketplaceId };

		validator.processListings(listings);

		assertThat(validator.getValidListings(), is(empty()));
	}

	@Test
	public void whenOwnerEmailAddressIsInvalid_thenInvalid() {

		final Listing listingWithNullEmail = getListingWithValidFields();
		when(listingWithNullEmail.getOwnerEmailAddress()).thenReturn(null);
		final Listing listingWithEmptyEmail = getListingWithValidFields();
		when(listingWithEmptyEmail.getOwnerEmailAddress()).thenReturn(empty);
		final Listing listingWithBadEmail1 = getListingWithValidFields();
		when(listingWithBadEmail1.getOwnerEmailAddress()).thenReturn("@email.com");
		final Listing listingWithBadEmail2 = getListingWithValidFields();
		when(listingWithBadEmail2.getOwnerEmailAddress()).thenReturn("email@.com");
		final Listing listingWithBadEmail3 = getListingWithValidFields();
		when(listingWithBadEmail3.getOwnerEmailAddress()).thenReturn("user.email.com");
		final Listing listingWithBadEmail4 = getListingWithValidFields();
		when(listingWithBadEmail3.getOwnerEmailAddress()).thenReturn("user@email");
		final Listing listingWithBadEmail5 = getListingWithValidFields();
		when(listingWithBadEmail3.getOwnerEmailAddress()).thenReturn("user@email.");
		final Listing listingWithBadEmail6 = getListingWithValidFields();
		when(listingWithBadEmail6.getOwnerEmailAddress()).thenReturn("this is a funky email address");
		final Listing listingWithBadEmail7 = getListingWithValidFields();
		when(listingWithBadEmail7.getOwnerEmailAddress()).thenReturn("   \n");

		final Listing[] listings = { listingWithNullEmail, listingWithEmptyEmail, listingWithBadEmail1,
				listingWithBadEmail2, listingWithBadEmail3, listingWithBadEmail4, listingWithBadEmail5,
				listingWithBadEmail6, listingWithBadEmail7 };

		validator.processListings(listings);

		assertThat(validator.getValidListings(), is(empty()));
	}

	@BeforeClass
	public static void setup() {
		generateValidator(generateStaticDataStorage());
	}

	private static void generateValidator(final StaticDataStorage staticDataStorage) {
		final ListingValidator lv = new ListingValidator(staticDataStorage);
		validator = lv;
	}

	private static StaticDataStorage generateStaticDataStorage() {
		final Map<UUID, LocationEntity> locationToId = new HashMap<>();
		final Map<Integer, MarketplaceEntity> marketplaceToId = new HashMap<>();
		final Map<Integer, ListingStatusEntity> listingStatusToId = new HashMap<>();

		// create a valid location
		final LocationEntity location = new LocationEntity(existingLocationUUID, "Manager", "0620202020",
				"Primary street 1", "Secondary lane 2", "Nice Country", "Pretty City", "P0 5T4L");
		locationToId.put(existingLocationUUID, location);

		// create a valid marketplace
		final MarketplaceEntity marketplace = new MarketplaceEntity(validMarketplaceId, "Online Shop");
		marketplaceToId.put(validMarketplaceId, marketplace);

		// create a valid listing status
		final ListingStatusEntity listingStatus = new ListingStatusEntity(validListingStatusId, "505");
		listingStatusToId.put(validListingStatusId, listingStatus);

		final StaticDataStorage sds = new StaticDataStorage(locationToId, marketplaceToId, listingStatusToId);
		return sds;
	}

	private static Listing getListingWithValidFields() {
		final Listing mockedListing = mock(Listing.class);
		when(mockedListing.getId()).thenReturn(validListingUUIDString);
		when(mockedListing.getTitle()).thenReturn("Judge Me By My Cover");
		when(mockedListing.getDescription())
				.thenReturn("The cover is so sticky, you won't put it down. -- The Onion");
		when(mockedListing.getLocationId()).thenReturn(existingLocationUUID.toString());
		when(mockedListing.getPrice()).thenReturn(12.99);
		when(mockedListing.getCurrency()).thenReturn("JMF");
		when(mockedListing.getQuantity()).thenReturn(3);
		when(mockedListing.getStatus()).thenReturn(validListingStatusId);
		when(mockedListing.getMarketplace()).thenReturn(validMarketplaceId);
		when(mockedListing.getUploadDate()).thenReturn(Date.from(Instant.now()));
		when(mockedListing.getOwnerEmailAddress()).thenReturn("nice-owner@email.com");

		return mockedListing;
	}

	@After
	public void clearValidListins() {
		validator.getValidListings().clear();
		validator.getListingIds().clear();
		validator.getImportlogBuilder().setLength(0);
	}

}
