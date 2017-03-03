package nssa.uc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nssa.uc.util.DataBaseUtil;
import nssa.uc.vo.SecuritySituation;

public class SecuritySituationDao {

	public List<SecuritySituation> listTodayAll() throws SQLException {
		String sql = "SELECT * FROM securitysituation WHERE DATEDIFF(time,NOW()) =0 ORDER BY time ASC;";

		List<SecuritySituation> securitySituationList = new ArrayList<SecuritySituation>();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				SecuritySituation securitySituation = new SecuritySituation();
				securitySituation.setTime(rs.getString("time"));
				securitySituation.setAsset(rs.getFloat("asset"));
				securitySituation.setRisk(rs.getFloat("risk"));
				securitySituation.setThreat(rs.getFloat("threat"));
				securitySituation.setSituation(rs.getFloat("situation"));
				securitySituationList.add(securitySituation);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

		return securitySituationList;
	}
	
	public List<SecuritySituation> listLastWeekAll() throws SQLException {
		String sql = "SELECT * FROM securitysituation WHERE time >= date_format(date_sub(date_sub(now(), INTERVAL WEEKDAY(NOW()) DAY), INTERVAL 1 WEEK), '%Y-%m-%d') ORDER BY time ASC;";

		List<SecuritySituation> securitySituationList = new ArrayList<SecuritySituation>();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				SecuritySituation securitySituation = new SecuritySituation();
				securitySituation.setTime(rs.getString("time"));
				securitySituation.setAsset(rs.getFloat("asset"));
				securitySituation.setRisk(rs.getFloat("risk"));
				securitySituation.setThreat(rs.getFloat("threat"));
				securitySituation.setSituation(rs.getFloat("situation"));
				securitySituationList.add(securitySituation);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

		return securitySituationList;
	}

}
