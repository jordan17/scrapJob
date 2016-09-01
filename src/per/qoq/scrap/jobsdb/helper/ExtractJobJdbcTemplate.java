package per.qoq.scrap.jobsdb.helper;

import java.util.Date;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import per.qoq.scrap.jobsdb.entity.Job;
import per.qoq.scrap.jobsdb.entity.SavedJob;
import per.qoq.scrap.jobsdb.mapper.ExtractedJobMapper;
import per.qoq.scrap.jobsdb.mapper.SavedJobMapper;

public class ExtractJobJdbcTemplate {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	public List<Job> getExtractedJob() {
		String SQL = "select * from extracted_job order by date_posted desc limit 400";
		List<Job> jobList = new ArrayList<Job>();
		try {
			jobList = jdbcTemplateObject.query(SQL,new ExtractedJobMapper());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return jobList;
	}
	
	public List<Job> getCompanyJob(String companyName) {
		String SQL = "select * from extracted_job where company_name LIKE ? order by date_posted desc limit 400";
		List<Job> jobList = new ArrayList<Job>();
		StringBuilder sb = new StringBuilder("%").append(companyName).append("%");
		try {
			jobList = jdbcTemplateObject.query(SQL,new Object[]{sb.toString()},new int[]{Types.VARCHAR},new ExtractedJobMapper());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return jobList;
	}
	public List<Job> filterByDate(Date dateBefore,Date dateAfter) {
		String SQL = "select * from extracted_job where date_posted <= ? AND date_posted >= ?";
		List<Job> jobList = new ArrayList<Job>();
		MapSqlParameterSource mps = new MapSqlParameterSource();
		
		mps.addValue("db", dateBefore,Types.DATE);
		mps.addValue("da", dateAfter,Types.DATE);
		try {
			jobList = jdbcTemplateObject.query(SQL,new Object[]{dateBefore,dateAfter},new int[]{Types.DATE,Types.DATE},new ExtractedJobMapper());
			//jobList = jdbcTemplateObject.query(SQL,(ps) -> {ps.setTimestamp(1, dateBefore);ps.setTimestamp(2, dateAfter);},new ExtractedJobMapper());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return jobList;
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
	
	@Transactional
	public Job getJobById(int jobId) {
		String SQL = "select * from extracted_job where job_id = ?";
		List<Job> job = new ArrayList<>();
		try{
			job = jdbcTemplateObject.query(SQL,new Object[]{jobId},new int[]{Types.INTEGER},new ExtractedJobMapper());
		}
		catch(DataAccessException de) {
			de.printStackTrace();
		}
		return job.get(0);
	}
	
	@Transactional
	public List<Job> getJobByIdList(List<Integer> jobId) {
		String SQL = "select * from extracted_job where job_id IN (:ids)";
		List<Job> job = new ArrayList<>();
		Map<String,List> paramMap = Collections.singletonMap("ids", jobId);
		List<Integer> resultIds = new ArrayList<Integer>();
		try {
			NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplateObject.getDataSource());
			//job = template.queryForList(SQL,paramMap,Integer.class,new ExtractedJobMapper());
			job = template.query(SQL, paramMap,new ExtractedJobMapper());
			//resultIds = jdbcTemplateObject.query(SQL.toString(),y,(ResultSet rs,int rowNum) -> { log.debug(rs.getString("job_id"));return rs.getString("job_id");});
			//resultIds = jdbcTemplateObject.queryForList(SQL.toString(),y);
		}
		catch(DataAccessException de) {
			de.printStackTrace();
		}
		return job;
	}
}
