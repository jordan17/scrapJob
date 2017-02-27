package per.qoq.scrap.jobsdb.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import per.qoq.scrap.jobsdb.entity.Job;
import per.qoq.scrap.jobsdb.entity.SavedJob;
import per.qoq.scrap.jobsdb.hibernate.SavedJobs;


public class SavedJobDAOImpl implements SavedJobDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addSavedJob(SavedJobs job) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(job);
	}

	@Override
	public void updateSavedJob(SavedJobs job) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(job);
	}

	@Override
	public void deleteSavedJob(String id) {
		// TODO Auto-generated method stub
		SavedJobs job = getSavedJob(id);
		sessionFactory.getCurrentSession().delete(job);
	}

	@Override
	public SavedJobs getSavedJob(String id) {
		// TODO Auto-generated method stub
		return (SavedJobs) sessionFactory.getCurrentSession().load(SavedJobs.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SavedJobs> getSavedJobs() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from SavedJobs").list();
	}

}
