package com.wob.assignment.app.synchronization.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

	private final String id;
	private final String managerName;
	private final String phoneNumber;
	private final String primaryAddress;
	private final String secondaryAddress;
	private final String country;
	private final String town;
	private final String postalCode;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public Location(@JsonProperty("id") String id, @JsonProperty("manager_name") String managerName,
			@JsonProperty("phone") String phoneNumber, @JsonProperty("address_primary") String primaryAddress,
			@JsonProperty("address_secondary") String secondaryAddress, @JsonProperty("country") String country,
			@JsonProperty("town") String town, @JsonProperty("postal_code") String postalCode) {
		
		this.id = id;
		this.managerName = managerName;
		this.phoneNumber = phoneNumber;
		this.primaryAddress = primaryAddress;
		this.secondaryAddress = secondaryAddress;
		this.country = country;
		this.town = town;
		this.postalCode = postalCode;
	}

	public String getId() {
		return id;
	}

	public String getManagerName() {
		return managerName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getPrimaryAddress() {
		return primaryAddress;
	}

	public String getSecondaryAddress() {
		return secondaryAddress;
	}

	public String getCountry() {
		return country;
	}

	public String getTown() {
		return town;
	}

	public String getPostalCode() {
		return postalCode;
	}

}
