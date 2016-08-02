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
 * Home object for domain model class ExtractedJob.
 * @see per.qoq.scrap.jobsdb.hibernate.ExtractedJob
 * @author Hibernate Tools
 */
public class ExtractedJobHome {

	private static final Log log = LogFactory.getLog(ExtractedJobHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(ExtractedJob transientInstance) {
		log.debug("persisting ExtractedJob instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(ExtractedJob instance) {
		log.debug("attaching dirty ExtractedJob instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ExtractedJob instance) {
		log.debug("attaching clean ExtractedJob instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(ExtractedJob persistentInstance) {
		log.debug("deleting ExtractedJob instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ExtractedJob merge(ExtractedJob detachedInstance) {
		log.debug("merging ExtractedJob instance");
		try {
			ExtractedJob result = (ExtractedJob) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ExtractedJob findById(java.lang.Integer id) {
		log.debug("getting ExtractedJob instance with id: " + id);
		try {
			ExtractedJob instance = (ExtractedJob) sessionFactory.getCurrentSession()
					.get("per.qoq.scrap.jobsdb.hibernate.ExtractedJob", id);
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

	public List findByExample(ExtractedJob instance) {
		log.debug("finding ExtractedJob instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("per.qoq.scrap.jobsdb.hibernate.ExtractedJob").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
