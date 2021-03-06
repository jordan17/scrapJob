package per.qoq.scrap.jobsdb.hibernate;
// Generated 2016/5/28 �W�� 12:05:06 by Hibernate Tools 4.3.1.Final

import java.util.Date;

/**
 * ExtractedJob generated by hbm2java
 */
public class ExtractedJob implements java.io.Serializable {

	private Integer jobId;
	private String companyName;
	private String jobTitle;
	private Date datePosted;
	private String jobCate;
	private String url;
	private String jobDesc;
	private String experience;

	public ExtractedJob() {
	}

	public ExtractedJob(String companyName, String jobTitle, Date datePosted, String jobCate, String url,
			String jobDesc, String experience) {
		this.companyName = companyName;
		this.jobTitle = jobTitle;
		this.datePosted = datePosted;
		this.jobCate = jobCate;
		this.url = url;
		this.jobDesc = jobDesc;
		this.experience = experience;
	}

	public Integer getJobId() {
		return this.jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getJobTitle() {
		return this.jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Date getDatePosted() {
		return this.datePosted;
	}

	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}

	public String getJobCate() {
		return this.jobCate;
	}

	public void setJobCate(String jobCate) {
		this.jobCate = jobCate;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getJobDesc() {
		return this.jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public String getExperience() {
		return this.experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

}
