package per.qoq.scrap.jobsdb.hibernate;
// Generated 2016/5/28 ¤W¤È 12:05:06 by Hibernate Tools 4.3.1.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class SavedJobs.
 * @see per.qoq.scrap.jobsdb.hibernate.SavedJobs
 * @author Hibernate Tools
 */
public class SavedJobsHome {

	private static final Log log = LogFactory.getLog(SavedJobsHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(SavedJobs transientInstance) {
		log.debug("persisting SavedJobs instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(SavedJobs instance) {
		log.debug("attaching dirty SavedJobs instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SavedJobs instance) {
		log.debug("attaching clean SavedJobs instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(SavedJobs persistentInstance) {
		log.debug("deleting SavedJobs instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SavedJobs merge(SavedJobs detachedInstance) {
		log.debug("merging SavedJobs instance");
		try {
			SavedJobs result = (SavedJobs) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SavedJobs findById(java.lang.Integer id) {
		log.debug("getting SavedJobs instance with id: " + id);
		try {
			SavedJobs instance = (SavedJobs) sessionFactory.getCurrentSession()
					.get("per.qoq.scrap.jobsdb.hibernate.SavedJobs", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SavedJobs instance) {
		log.debug("finding SavedJobs instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("per.qoq.scrap.jobsdb.hibernate.SavedJobs")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
