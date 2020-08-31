package com.wob.assignment.app.persistence.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "listing")
public class ListingEntity {

	@Id
	@Column(name = "id")
	@Type(type = "uuid-binary")
	private UUID id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "inventory_item_location_id")
	private LocationEntity location;

	@Column(name = "listing_price")
	private double price;

	@Column(name = "currency")
	private String currency;

	@Column(name = "quantity")
	private long quantity;

	@ManyToOne
	@JoinColumn(name = "listing_status")
	private ListingStatusEntity status;

	@ManyToOne
	@JoinColumn(name = "marketplace")
	private MarketplaceEntity marketplace;

	@Column(name = "upload_time")
	private Date uploadDate;

	@Column(name = "owner_email_address")
	private String ownerEmailAddress;

	public ListingEntity() {

	}

	public ListingEntity(UUID id, String title, String description, LocationEntity location, double price,
			String currency, long quantity, ListingStatusEntity status, MarketplaceEntity marketplace, Date uploadDate,
			String ownerEmailAddress) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.location = location;
		this.price = price;
		this.currency = currency;
		this.quantity = quantity;
		this.status = status;
		this.marketplace = marketplace;
		this.uploadDate = uploadDate;
		this.ownerEmailAddress = ownerEmailAddress;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocationEntity getLocation() {
		return location;
	}

	public void setLocation(LocationEntity location) {
		this.location = location;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public ListingStatusEntity getStatus() {
		return status;
	}

	public void setStatus(ListingStatusEntity status) {
		this.status = status;
	}

	public MarketplaceEntity getMarketplace() {
		return marketplace;
	}

	public void setMarketplace(MarketplaceEntity marketplace) {
		this.marketplace = marketplace;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getOwnerEmailAddress() {
		return ownerEmailAddress;
	}

	public void setOwnerEmailAddress(String ownerEmailAddress) {
		this.ownerEmailAddress = ownerEmailAddress;
	}

}
