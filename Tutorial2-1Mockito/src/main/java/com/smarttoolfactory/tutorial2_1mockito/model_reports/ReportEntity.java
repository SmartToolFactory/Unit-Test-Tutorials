package com.smarttoolfactory.tutorial2_1mockito.model_reports;

import java.util.Date;

/**
 * Report entity.
 * @author Meraj
 */
public class ReportEntity {
	
	private Long reportId;
	private Date startDate;
	private Date endDate;
	private byte[] content;
	
	public Long getReportId() {
		return reportId;
	}
	
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public byte[] getContent() {
		return content;
	}
	
	public void setContent(byte[] content) {
		this.content = content;
	}
}
