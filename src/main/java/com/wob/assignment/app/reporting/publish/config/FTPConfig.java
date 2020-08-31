package com.wob.assignment.app.reporting.publish.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FTPConfig {

	@Value("${ftp.server}")
	private String server;

	@Value("${ftp.port}")
	private Integer port;

	@Value("${ftp.user}")
	private String user;

	@Value("${ftp.password}")
	private String password;

	@Value("${ftp.folder}")
	private String folder;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

}
