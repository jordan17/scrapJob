package per.qoq.scrap.jobsdb.helper;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import per.qoq.scrap.jobsdb.entity.Job;
import per.qoq.scrap.jobsdb.entity.SavedJob;
import per.qoq.scrap.jobsdb.mapper.SavedJobMapper;

public class ExtractJobJdbcTemplate {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	@Transactional
	public void addSavedJob(Job job) {
		// TODO Auto-generated method stub
		String SQL = "insert into extracted_job(company_name,job_title,date_posted,job_cate,url,job_desc) values(?,?,?,?,?,?)";
		try {
			jdbcTemplateObject.update(SQL,job.getCompany(),job.getJobTtile(),job.getDatePosted(),job.getJobCate(),job.getUrl(),job.getJobDesc());
		}
		catch(DataAccessException de) {
			if(de instanceof DuplicateKeyException) {
				System.out.println("duplicated");
				return;
			}
			System.out.println(job.getJobTtile()+"\n"+job.getCompany());
			de.printStackTrace();
		}
		finally {
			
		}
		return;
	}

}
