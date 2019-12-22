package com.smarttoolfactory.tutorial2_1mockito.model_reports;

import java.util.Date;

/**
 * Service class for generating report.
 * @author Meraj
 */
public class ReportGeneratorService {
	
	private IReportGenerator reportGenerator;
	
	/**
	 * Generate report.
	 * @param startDate start date
	 * @param endDate end date
	 * @param content report content
	 */
	public void generateReport(Date startDate, Date endDate, byte[] content) {
		ReportEntity report = new ReportEntity();
		report.setContent(content);
		report.setStartDate(startDate);
		report.setEndDate(endDate);
		reportGenerator.generateReport(report);
	}

}
