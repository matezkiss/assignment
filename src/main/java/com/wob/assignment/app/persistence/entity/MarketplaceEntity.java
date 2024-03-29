package com.wob.assignment.app.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "marketplace")
public class MarketplaceEntity {

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "marketplace_name")
	private String name;

	public MarketplaceEntity() {

	}

	public MarketplaceEntity(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
