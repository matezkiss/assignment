package com.wob.assignment.app.synchronization.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Listing {

	private final String id;
	private final String title;
	private final String description;
	private final String locationId;
	private final Double price;
	private final String currency;
	private final Integer quantity;
	private final Integer status;
	private final Integer marketplace;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private final Date uploadDate;
	private final String ownerEmailAddress;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public Listing(@JsonProperty("id") String id, @JsonProperty("title") String title,
			@JsonProperty("description") String description,
			@JsonProperty("location_id") String locationId, @JsonProperty("listing_price") Double price,
			@JsonProperty("currency") String currency, @JsonProperty("quantity") Integer quantity,
			@JsonProperty("listing_status") Integer status, @JsonProperty("marketplace") Integer marketplace,
			@JsonProperty("upload_time") Date uploadDate,
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

	public String getLocationId() {
		return locationId;
	}

	public Double getPrice() {
		return price;
	}

	public String getCurrency() {
		return currency;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getMarketplace() {
		return marketplace;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public String getOwnerEmailAddress() {
		return ownerEmailAddress;
	}

}
