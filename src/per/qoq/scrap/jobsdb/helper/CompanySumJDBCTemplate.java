package per.qoq.scrap.jobsdb.helper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import per.qoq.scrap.jobsdb.controller.WebServiceController;
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
	static Logger log = Logger.getLogger(CompanySumJDBCTemplate.class.getName());
	

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	public List<String> getAgentName() {
		// TODO Auto-generated method stub
		String SQL = "select company_name from agents where verified = true";
		List<String> sjs = jdbcTemplateObject.queryForList(SQL,String.class);
		return sjs;
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

	public List<Integer> getSkillJob(List<Integer> ids,String[] skills) {
		// TODO Auto-generated method stub
		StringBuilder SQL = new StringBuilder("select ej.job_id from extracted_job ej inner join group_by_skill gbs on ej.job_id = gbs.job_id where ej.job_id in (:ids) and (");
		int count = 0;
		for(String skill : skills) {
			if(count>0) SQL.append(" or ");
			if(skill.equalsIgnoreCase("java")) {
				SQL.append(" gbs.Java = true");
			}
			if(skill.equalsIgnoreCase("php")) {
				SQL.append("  gbs.PHP = true");
			}
			if(skill.equalsIgnoreCase("C#")) {
				SQL.append("  gbs.C# = true");
			}
			if(skill.equalsIgnoreCase("J2EE")) {
				SQL.append("  gbs.J2EE = true");
			}
			if(skill.equalsIgnoreCase("Spring")) {
				SQL.append(" gbs.Spring = true");
			}
			count++;
		}
		SQL.append(")");
		
		Integer[] y = ids.toArray(new Integer[0]);
		Map<String,List> paramMap = Collections.singletonMap("ids", ids);
		List<Integer> resultIds = new ArrayList<Integer>();
		try {
			NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplateObject.getDataSource());
			resultIds = template.queryForList(SQL.toString(),paramMap,Integer.class);
			
			//resultIds = jdbcTemplateObject.query(SQL.toString(),y,(ResultSet rs,int rowNum) -> { log.debug(rs.getString("job_id"));return rs.getString("job_id");});
			//resultIds = jdbcTemplateObject.queryForList(SQL.toString(),y);
		}
		catch(DataAccessException de) {
			log.error(de);
		}
		return resultIds;
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
