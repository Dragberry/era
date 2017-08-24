package org.dragberry.era.business.reporting;

public class ReportTemplateInfo {

	private Long reportTemplateKey;
	
	private String fileName;
	
	private String mime;
	
	public Long getReportTemplateKey() {
		return reportTemplateKey;
	}

	public void setReportTemplateKey(Long reportTemplateKey) {
		this.reportTemplateKey = reportTemplateKey;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

}
