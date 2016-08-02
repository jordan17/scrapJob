package per.qoq.scrap.jobsdb.dao;

import java.util.List;

import per.qoq.scrap.jobsdb.entity.HateJob;
import per.qoq.scrap.jobsdb.entity.Job;
import per.qoq.scrap.jobsdb.entity.SavedJob;
import per.qoq.scrap.jobsdb.hibernate.SavedJobs;

public interface HateJobDAO {
	
	void addHateJob(HateJob job);
	
	void updateHateJob(HateJob job);
	
	void deleteHateJob(String id);
		
	public HateJob getHateJob(String id) ;
	List<HateJob> getHateJobs();

}
