package per.qoq.scrap.jobsdb.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

/**
 * @author jordan17
 *
 */
public class Job {

	private String jobCate;
	private Date datePosted;
	private String company;
	private String jobTtile;
	private String jobDesc;
	private String objId;
	private int jobId;
	
	private String url;
	private int sequence;
	private boolean saved;
	private boolean hated;
	private String experience;
	private boolean manyJobs;
	
	public String getJobCate() {
		return jobCate;
	}
	public void setJobCate(String jobCate) {
		this.jobCate = jobCate;
	}
	public Date getDatePosted() {
		return datePosted;
	}
	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getJobTtile() {
		return jobTtile;
	}
	public void setJobTtile(String jobTtile) {
		this.jobTtile = jobTtile;
	}
	public String getJobDesc() {
		return jobDesc;
	}
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isSaved() {
		return saved;
	}
	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String object) {
		// TODO Auto-generated method stub
		this.experience = object;
	}
	public boolean isHated() {
		return hated;
	}
	public void setHated(boolean hated) {
		this.hated = hated;
	}
	public boolean isManyJobs() {
		return manyJobs;
	}
	public void setManyJobs(boolean manyJobs) {
		this.manyJobs = manyJobs;
	}
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

}
