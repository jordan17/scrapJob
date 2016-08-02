package per.qoq.scrap.jobsdb.hibernate;
// Generated 2016/5/28 �W�� 12:05:06 by Hibernate Tools 4.3.1.Final

import java.util.Date;

/**
 * SavedJobs generated by hbm2java
 */
public class SavedJobs implements java.io.Serializable {

	private Integer id;
	private String userId;
	private String title;
	private String company;
	private String jobDesc;
	private String saved;
	private Date savedTime;
	private String jobId;

	public SavedJobs() {
	}

	public SavedJobs(String title, String company) {
		this.title = title;
		this.company = company;
	}

	public SavedJobs(String userId, String title, String company, String jobDesc, String saved, Date savedTime,
			String jobId) {
		this.userId = userId;
		this.title = title;
		this.company = company;
		this.jobDesc = jobDesc;
		this.saved = saved;
		this.savedTime = savedTime;
		this.jobId = jobId;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getJobDesc() {
		return this.jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public String getSaved() {
		return this.saved;
	}

	public void setSaved(String saved) {
		this.saved = saved;
	}

	public Date getSavedTime() {
		return this.savedTime;
	}

	public void setSavedTime(Date savedTime) {
		this.savedTime = savedTime;
	}

	public String getJobId() {
		return this.jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

}
