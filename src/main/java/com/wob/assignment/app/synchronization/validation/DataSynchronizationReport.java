package com.wob.assignment.app.synchronization.validation;

public class DataSynchronizationReport {

	private String importlogPath;
	private int validListingCount;
	private int invalidListingCount;

	public void incrementValidListingCount() {
		this.validListingCount++;
	}

	public void incrementInvalidListingCount() {
		this.invalidListingCount++;
	}

	public int getValidListingCount() {
		return this.validListingCount;
	}

	public int getInvalidListingCount() {
		return this.invalidListingCount;
	}

	public void setImportlogPath(final String importlogPath) {
		this.importlogPath = importlogPath;
	}

	public String getImportlogPath() {
		return this.importlogPath;
	}

}
