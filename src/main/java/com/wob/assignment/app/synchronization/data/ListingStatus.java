package com.wob.assignment.app.synchronization.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListingStatus {

	private final int id;
	private final String name;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public ListingStatus(@JsonProperty("id") int id, @JsonProperty("status_name") String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
