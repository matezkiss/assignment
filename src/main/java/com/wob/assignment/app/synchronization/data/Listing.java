package com.wob.assignment.app.synchronization.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Listing {

	private final String id;
	private final String title;
	private final String description;
	private final int locationId;
	private final double price;
	private final String currency;
	private final long quantity;
	private final int status;
	private final int marketplace;
	private final Date uploadDate;
	private final String ownerEmailAddress;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public Listing(@JsonProperty("id") String id, @JsonProperty("title") String title,
			@JsonProperty("description") String description, @JsonProperty("inventory_item_location_id") int locationId,
			@JsonProperty("listing_price") double price, @JsonProperty("currency") String currency,
			@JsonProperty("quantity") long quantity, @JsonProperty("listing_status") int status,
			@JsonProperty("marketplace") int marketplace, @JsonProperty("upload_time") Date uploadDate,
			@JsonProperty("owner_email_address") String ownerEmailAddress) {
		
		this.id = id;
		this.title = title;
		this.description = description;
		this.locationId = locationId;
		this.price = price;
		this.currency = currency;
		this.quantity = quantity;
		this.status = status;
		this.marketplace = marketplace;
		this.uploadDate = uploadDate;
		this.ownerEmailAddress = ownerEmailAddress;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public int getLocationId() {
		return locationId;
	}

	public double getPrice() {
		return price;
	}

	public String getCurrency() {
		return currency;
	}

	public long getQuantity() {
		return quantity;
	}

	public int getStatus() {
		return status;
	}

	public int getMarketplace() {
		return marketplace;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public String getOwnerEmailAddress() {
		return ownerEmailAddress;
	}

}
