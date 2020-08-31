package com.wob.assignment.app.reporting.publish;

public class PublishToFtpResult {

	private final String username;

	private final String server;

	private final String filepath;

	public PublishToFtpResult(String username, String destination, String filename) {
		this.username = username;
		this.server = destination;
		this.filepath = filename;
	}

	public String getFtpPath() {
		return String.format("%s@%s:%s", this.username, this.server, this.filepath);
	}

}
