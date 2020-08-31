package com.wob.assignment.app.persistence.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "location")
public class LocationEntity {

	@Id
	@Column(name = "id")
	@Type(type = "uuid-binary")
	private UUID id;

	@Column(name = "manager_name")
	private String managerName;

	@Column(name = "phone")
	private String phone;

	@Column(name = "address_primary")
	private String addressPrimary;

	@Column(name = "address_secondary")
	private String addressSecondary;

	@Column(name = "country")
	private String country;

	@Column(name = "town")
	private String town;

	@Column(name = "postal_code")
	private String postalCode;

	public LocationEntity() {

	}

	public LocationEntity(UUID id, String managerName, String phoneNumber, String addressPrimary,
			String addressSecondary, String country, String town, String postalCode) {
		this.id = id;
		this.managerName = managerName;
		this.phone = phoneNumber;
		this.addressPrimary = addressPrimary;
		this.addressSecondary = addressSecondary;
		this.country = country;
		this.town = town;
		this.postalCode = postalCode;
	}

	@Id
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phoneNumber) {
		this.phone = phoneNumber;
	}

	public String getAddressPrimary() {
		return addressPrimary;
	}

	public void setAddressPrimary(final String addressPrimary) {
		this.addressPrimary = addressPrimary;
	}

	public String getAddressSecondary() {
		return addressSecondary;
	}

	public void setAddressSecondary(final String addressSecondary) {
		this.addressSecondary = addressSecondary;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

}
