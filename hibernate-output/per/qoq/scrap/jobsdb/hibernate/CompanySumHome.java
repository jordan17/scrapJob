package per.qoq.scrap.jobsdb.hibernate;
// Generated 2016/5/28 ¤W¤È 12:08:03 by Hibernate Tools 4.3.1.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class CompanySum.
 * @see per.qoq.scrap.jobsdb.hibernate.CompanySum
 * @author Hibernate Tools
 */
public class CompanySumHome {

	private static final Log log = LogFactory.getLog(CompanySumHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(CompanySum transientInstance) {
		log.debug("persisting CompanySum instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(CompanySum instance) {
		log.debug("attaching dirty CompanySum instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CompanySum instance) {
		log.debug("attaching clean CompanySum instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(CompanySum persistentInstance) {
		log.debug("deleting CompanySum instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CompanySum merge(CompanySum detachedInstance) {
		log.debug("merging CompanySum instance");
		try {
			CompanySum result = (CompanySum) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public CompanySum findById(per.qoq.scrap.jobsdb.hibernate.CompanySumId id) {
		log.debug("getting CompanySum instance with id: " + id);
		try {
			CompanySum instance = (CompanySum) sessionFactory.getCurrentSession()
					.get("per.qoq.scrap.jobsdb.hibernate.CompanySum", id);
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

	public List findByExample(CompanySum instance) {
		log.debug("finding CompanySum instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("per.qoq.scrap.jobsdb.hibernate.CompanySum").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
