package per.qoq.scrap.jobsdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import per.qoq.scrap.jobsdb.entity.SavedJobAnalyze;

public class SavedJobAnalyzeMapper implements RowMapper<SavedJobAnalyze>{

	@Override
	public SavedJobAnalyze mapRow(ResultSet rs, int rowNum) throws SQLException {

		SavedJobAnalyze sja = new SavedJobAnalyze();
		sja.setCompany(rs.getString("company"));
		sja.setJobTtile(rs.getString("title"));
		sja.setDatePosted(rs.getDate("saved_time"));
		sja.setMax_id(rs.getInt("max_id"));
		sja.setHitCount(rs.getInt("hit_count"));
		sja.setLastHit(rs.getDate("last_hit"));
		return sja;
	}
	
	
}
