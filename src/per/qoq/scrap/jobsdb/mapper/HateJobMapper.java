package per.qoq.scrap.jobsdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import per.qoq.scrap.jobsdb.entity.HateJob;
import per.qoq.scrap.jobsdb.entity.SavedJob;
public class HateJobMapper implements RowMapper<HateJob>{

	public HateJobMapper() {}
	@Override
	public HateJob mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		HateJob sj = new HateJob();
		sj.setTitle(rs.getString("title"));
		sj.setCompany(rs.getString("company"));
		sj.setSavedTime(rs.getDate("saved_time"));
		sj.setJobId(rs.getString("job_id"));
		return sj;
	}

}
