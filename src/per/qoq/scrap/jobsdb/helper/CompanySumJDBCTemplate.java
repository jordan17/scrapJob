package per.qoq.scrap.jobsdb.helper;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import per.qoq.scrap.jobsdb.dao.SavedJobDAO;
import per.qoq.scrap.jobsdb.entity.Job;
import per.qoq.scrap.jobsdb.entity.SavedJob;
import per.qoq.scrap.jobsdb.hibernate.CompanySum;
import per.qoq.scrap.jobsdb.hibernate.CompanySumId;
import per.qoq.scrap.jobsdb.hibernate.SavedJobs;
import per.qoq.scrap.jobsdb.mapper.CompanySumMapper;
import per.qoq.scrap.jobsdb.mapper.SavedJobMapper;

public class CompanySumJDBCTemplate{

	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	public List<CompanySumId> getSavedJob() {
		// TODO Auto-generated method stub
		String SQL = "select * from company_sum";
		List<CompanySumId> sjs = jdbcTemplateObject.query(SQL,new CompanySumMapper());
		return sjs;
	}

	
	public void addSavedJob(Job job) {
		// TODO Auto-generated method stub
		String SQL = "insert into saved_jobs(user_id,title,company,saved,job_desc,job_id,saved_time) values(?,?,?,'T',?,?,NOW())";
		try {
			jdbcTemplateObject.update(SQL,"123",job.getJobTtile(),job.getCompany(),job.getJobDesc(),job.getObjId());
		}
		catch(DataAccessException de) {
			
		}
		return;
	}

	public void updateSavedJob(Job job) {
		// TODO Auto-generated method stub
		
	}

	public void deleteSavedJob(Job job) {
		// TODO Auto-generated method stub
		String SQL = "delete from saved_jobs where title = ? AND company = ? ";
		jdbcTemplateObject.update(SQL,job.getJobTtile(),job.getCompany());
		return;
	}

	public void addSavedJob(SavedJobs job) {
		// TODO Auto-generated method stub
		
	}

	public void updateSavedJob(SavedJobs job) {
		// TODO Auto-generated method stub
		
	}

	public void deleteSavedJob(String id) {
		// TODO Auto-generated method stub
		
	}

	public SavedJobs getSavedJob(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SavedJobs> getSavedJobs() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
