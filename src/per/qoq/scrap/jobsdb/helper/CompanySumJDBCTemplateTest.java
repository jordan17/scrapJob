package per.qoq.scrap.jobsdb.helper;

import static org.junit.Assert.*;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CompanySumJDBCTemplateTest {

	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	@Test
	public void testGetAgentName() {
		String SQL = "select company_name from company_sum where verified = true";
		List<String> sjs = jdbcTemplateObject.queryForList(SQL,String.class);
		
	}

}
