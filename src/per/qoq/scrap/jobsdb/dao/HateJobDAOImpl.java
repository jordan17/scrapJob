package per.qoq.scrap.jobsdb.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import per.qoq.scrap.jobsdb.entity.HateJob;
import per.qoq.scrap.jobsdb.entity.Job;
import per.qoq.scrap.jobsdb.entity.SavedJob;
import per.qoq.scrap.jobsdb.hibernate.SavedJobs;


public class HateJobDAOImpl implements HateJobDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addHateJob(HateJob job) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(job);
	}

	@Override
	public void updateHateJob(HateJob job) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(job);
	}

	@Override
	public void deleteHateJob(String id) {
		// TODO Auto-generated method stub
		HateJob job = getHateJob(id);
		sessionFactory.getCurrentSession().delete(job);
	}

	@Override
	public HateJob getHateJob(String id) {
		// TODO Auto-generated method stub
		return (HateJob) sessionFactory.getCurrentSession().load(HateJob.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HateJob> getHateJobs() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from HateJob").list();
	}

}
