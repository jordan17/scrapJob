package per.qoq.scrap.jobsdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import per.qoq.scrap.jobsdb.entity.Job;
import per.qoq.scrap.jobsdb.entity.SavedJob;
public class ExtractedJobMapper implements RowMapper<Job>{

	public ExtractedJobMapper() {}
	@Override
	public Job mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Job sj = new Job();
		sj.setJobId(rs.getInt("job_id"));
		sj.setJobTtile(rs.getString("job_title"));
		sj.setCompany(rs.getString("company_name"));
		sj.setJobDesc(rs.getString("job_desc"));
		sj.setDatePosted(rs.getDate("date_posted"));
		sj.setUrl(rs.getString("url"));
		sj.setExperience(rs.getString("experience"));
		return sj;
	}

}
