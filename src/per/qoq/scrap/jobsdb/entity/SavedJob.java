package per.qoq.scrap.jobsdb.entity;

import java.util.Date;

public class SavedJob {

	private String userId;
	
	private String title;
	
	private String company;
	
	private String jobDesc;
	
	private Date savedTime;
	
	private String saved;
	private String jobId;
	public SavedJob(String userId, String title, String company, String jobDesc, Date savedTime, String saved,String jobId) {
		super();
		this.userId = userId;
		this.title = title;
		this.company = company;
		this.jobDesc = jobDesc;
		this.savedTime = savedTime;
		this.saved = saved;
		this.jobId = jobId;
	}

	public SavedJob() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public Date getSavedTime() {
		return savedTime;
	}

	public void setSavedTime(Date savedTime) {
		this.savedTime = savedTime;
	}

	public String getSaved() {
		return saved;
	}

	public void setSaved(String saved) {
		this.saved = saved;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	
}
