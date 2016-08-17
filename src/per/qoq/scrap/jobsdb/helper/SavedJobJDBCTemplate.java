package per.qoq.scrap.jobsdb.helper;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import per.qoq.scrap.jobsdb.controller.WebServiceController;
import per.qoq.scrap.jobsdb.dao.SavedJobDAO;
import per.qoq.scrap.jobsdb.entity.HateJob;
import per.qoq.scrap.jobsdb.entity.Job;
import per.qoq.scrap.jobsdb.entity.SavedJob;
import per.qoq.scrap.jobsdb.entity.SavedJobAnalyze;
import per.qoq.scrap.jobsdb.hibernate.SavedJobs;
import per.qoq.scrap.jobsdb.mapper.HateJobMapper;
import per.qoq.scrap.jobsdb.mapper.SavedJobAnalyzeMapper;
import per.qoq.scrap.jobsdb.mapper.SavedJobMapper;

public class SavedJobJDBCTemplate{

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	static Logger log = Logger.getLogger(SavedJobJDBCTemplate.class.getName());

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	public List<SavedJob> getSavedJob() {
		// TODO Auto-generated method stub
		String SQL = "select * from saved_jobs";
		List<SavedJob> sjs = jdbcTemplateObject.query(SQL,new SavedJobMapper());
		return sjs;
	}
	public List<SavedJobAnalyze> getSavedJobAnalyze() {
		// TODO Auto-generated method stub
		String SQL = "select * from saved_job_analyze";
		List<SavedJobAnalyze> sjs = jdbcTemplateObject.query(SQL,new SavedJobAnalyzeMapper());
		return sjs;
	}
	public void addSavedJob(Job job) {
		// TODO Auto-generated method stub
		String SQL = "insert into saved_jobs(user_id,title,company,saved,job_desc,job_id,saved_time,url) values(?,?,?,'T',?,?,NOW(),?)";
		try {
			jdbcTemplateObject.update(SQL,"123",job.getJobTtile(),job.getCompany(),job.getJobDesc(),job.getObjId(),job.getUrl());
			log.info(job.getJobTtile()+" added");
		}
		catch(DataAccessException de) {
			log.error(job.getJobTtile(),de);
		}
		return;
	}


	public void deleteSavedJob(Job job) {
		// TODO Auto-generated method stub
		String SQL = "delete from saved_jobs where title LIKE ? AND company LIKE ? ";
		jdbcTemplateObject.update(SQL,job.getJobTtile(),job.getCompany());
		log.info(job.getJobTtile()+" removed");
		return;
	}

	public List<HateJob> getHatedJob() {
		// TODO Auto-generated method stub
		String SQL = "select * from hate_jobs";
		List<HateJob> sjs = jdbcTemplateObject.query(SQL,new HateJobMapper());
		return sjs;
	}

	
	public void addHatedJob(Job job) {
		// TODO Auto-generated method stub
		String SQL = "insert into hate_jobs(title,company,job_id,saved_time) values(?,?,?,NOW())";
		try {
			jdbcTemplateObject.update(SQL,job.getJobTtile(),job.getCompany(),job.getObjId());
			log.info(job.getJobTtile()+" hated");
		}
		catch(DataAccessException de) {
			log.error(job.getJobTtile(),de);
		}
		return;
	}


	public void deleteHatedJob(Job job) {
		// TODO Auto-generated method stub
		String SQL = "delete from hate_jobs where title = ? AND company = ? ";
		jdbcTemplateObject.update(SQL,job.getJobTtile(),job.getCompany());
		log.debug(job.getJobTtile()+" removed");
		return;
	}
}
