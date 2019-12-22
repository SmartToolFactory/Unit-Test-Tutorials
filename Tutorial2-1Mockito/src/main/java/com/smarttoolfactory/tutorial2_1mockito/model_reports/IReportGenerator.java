package com.smarttoolfactory.tutorial2_1mockito.model_reports;

/**
 * Interface for generating reports.
 * @author Meraj
 */
public interface IReportGenerator {
	
	/**
	 * Generate report.
	 * @param report Report entity.
	 */
	void generateReport(ReportEntity report);

}
