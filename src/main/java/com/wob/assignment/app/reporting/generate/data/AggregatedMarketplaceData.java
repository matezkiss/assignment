package com.wob.assignment.app.reporting.generate.data;

public class AggregatedMarketplaceData {

	private int totalListingCount;

	private double totalListingPrice;

	private double averageListingPrice;

	public AggregatedMarketplaceData() {
		this.totalListingCount = 0;
		this.totalListingPrice = 0.0;
		this.averageListingPrice = 0.0;
	}

	public void incrementListingCount(final int countToAdd) {
		this.totalListingCount += countToAdd;
	}

	public void incrementTotalPrice(final double priceToAdd) {
		this.totalListingPrice += priceToAdd;
	}

	public int getTotalListingCount() {
		return this.totalListingCount;
	}

	public double getTotalListingPrice() {
		return this.totalListingPrice;
	}

	public double getAverageListingPrice() {
		this.averageListingPrice = this.totalListingPrice / (double) this.totalListingCount;
		return this.averageListingPrice;
	}

}
