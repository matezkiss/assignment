package com.wob.assignment.app.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "listing_status")
public class ListingStatusEntity {

	@Id
	private int id;

	@Column(name = "status_name")
	private String name;
	
	public ListingStatusEntity() {
		
	}

	public ListingStatusEntity(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
