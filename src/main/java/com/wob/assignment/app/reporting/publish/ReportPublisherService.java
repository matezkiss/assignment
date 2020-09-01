package com.wob.assignment.app.reporting.publish;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wob.assignment.app.operationresult.ReportPublishingOperationResult;
import com.wob.assignment.app.reporting.publish.config.FTPConfig;

/**
 * Implementation of {@ IReportPublisherService}.
 * 
 * @author matezoltankiss
 *
 */
@Service
public class ReportPublisherService implements IReportPublisherService {

	private static Logger logger = LoggerFactory.getLogger(ReportPublisherService.class);

	@Autowired
	private FTPConfig ftpConfig;

	@Override
	public ReportPublishingOperationResult publishReport(final File report) {

		final FTPClient ftpClient = new FTPClient();

		try (final FileInputStream fileInputStream = new FileInputStream(report)) {

			ftpClient.connect(this.ftpConfig.getServer(), this.ftpConfig.getPort());
			int reply = ftpClient.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				throw new IOException("Exception in connecting to FTP Server");
			}

			ftpClient.login(this.ftpConfig.getUser(), this.ftpConfig.getPassword());
			final String destinationFile = this.ftpConfig.getFolder() + report.getName();
			ftpClient.storeFile(destinationFile, fileInputStream);

			return ReportPublishingOperationResult.success(
					new PublishToFtpResult(this.ftpConfig.getUser(), this.ftpConfig.getServer(), destinationFile));

		} catch (final Exception e) {
			logger.error("failed to publish report.");
			return ReportPublishingOperationResult.failure(e.getMessage());
		} finally {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				logger.error("could not disconnect from ftp server.");
			}
			report.delete();
			logger.info("deleted report temp file.");
		}
	}

}
