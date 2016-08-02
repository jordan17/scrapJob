package per.qoq.scrap.jobsdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import per.qoq.scrap.jobsdb.hibernate.CompanySumId;
public class CompanySumMapper implements RowMapper<CompanySumId>{

	public CompanySumMapper() {}
	@Override
	public CompanySumId mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		CompanySumId sj = new CompanySumId();
		sj.setCompanyName(rs.getString("company_name"));
		sj.setSum(rs.getBigDecimal("sum"));
		return sj;
	}

}
