package com.wob.assignment.app.reporting.generate.data;

public class MarketplaceStats {

	private final int listingCount;

	private final double totalPrice;

	private final double averagePrice;

	public MarketplaceStats(int listingCount, double totalPrice, double averagePrice) {
		this.listingCount = listingCount;
		this.totalPrice = totalPrice;
		this.averagePrice = averagePrice;
	}

	public int getListingCount() {
		return listingCount;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public double getAveragePrice() {
		return averagePrice;
	}

}
