package per.qoq.scrap.jobsdb.entity;

import java.util.Date;

public class HateJob {

	
	private String title;
	
	private String company;
	
	
	private Date savedTime;
	
	private String jobId;
	public HateJob(String title, String company, Date savedTime, String jobId) {
		super();
		this.title = title;
		this.company = company;
		this.savedTime = savedTime;
		this.jobId = jobId;
	}

	public HateJob() {
		super();
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}


	public Date getSavedTime() {
		return savedTime;
	}

	public void setSavedTime(Date savedTime) {
		this.savedTime = savedTime;
	}


	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	
}
