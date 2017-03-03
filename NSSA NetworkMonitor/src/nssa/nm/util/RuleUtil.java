/*package nssa.nm.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nssa.nm.vo.Rule;
import nssa.sc.util.DataBaseUtil;

public class RuleUtil {
	
	public List<Rule> list() throws SQLException {
		String sql = "SELECT * FROM rule;";

		List<Rule> list = new ArrayList<Rule>();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				Rule rule = new Rule();
				rule.setId(rs.getInt("id"));
				rule.setAction(rs.getString("action"));
				rule.setProtocol(rs.getString("protocol"));
				rule.setSrcIP(rs.getString("srcIP"));
				rule.setSrcPort(rs.getString("srcPort"));
				rule.setDstIP(rs.getString("dstIP"));
				rule.setDstPort(rs.getString("dstPort"));
				rule.setContent(rs.getString("content"));
				rule.setMsg(rs.getString("msg"));
				list.add(rule);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

		return list;
	}
	
	public List<Rule> list(String protocol) throws SQLException {
		String sql = "SELECT * FROM rule WHERE protocol=?;";

		List<Rule> list = new ArrayList<Rule>();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, protocol);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				Rule rule = new Rule();
				rule.setId(rs.getInt("id"));
				rule.setAction(rs.getString("action"));
				rule.setProtocol(rs.getString("protocol"));
				rule.setSrcIP(rs.getString("srcIP"));
				rule.setSrcPort(rs.getString("srcPort"));
				rule.setDstIP(rs.getString("dstIP"));
				rule.setDstPort(rs.getString("dstPort"));
				rule.setContent(rs.getString("content"));
				rule.setMsg(rs.getString("msg"));
				list.add(rule);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

		return list;
	}
}
*/