package per.qoq.scrap.jobsdb.dao;

import java.util.List;

import per.qoq.scrap.jobsdb.entity.Job;
import per.qoq.scrap.jobsdb.entity.SavedJob;
import per.qoq.scrap.jobsdb.hibernate.SavedJobs;

public interface SavedJobDAO {
	
	void addSavedJob(SavedJobs job);
	
	void updateSavedJob(SavedJobs job);
	
	void deleteSavedJob(String id);
		
	public SavedJobs getSavedJob(String id) ;
	List<SavedJobs> getSavedJobs();

}
