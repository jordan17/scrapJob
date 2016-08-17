package per.qoq.scrap.jobsdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import per.qoq.scrap.jobsdb.entity.SavedJob;
public class SavedJobMapper implements RowMapper<SavedJob>{

	public SavedJobMapper() {}
	@Override
	public SavedJob mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		SavedJob sj = new SavedJob();
		sj.setUserId(rs.getString("user_id"));
		sj.setTitle(rs.getString("title"));
		sj.setCompany(rs.getString("company"));
		sj.setSavedTime(rs.getDate("saved_time"));
		sj.setSaved(rs.getString("saved"));
		sj.setJobId(rs.getString("job_id"));
		return sj;
	}

}
