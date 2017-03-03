package nssa.uc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nssa.uc.util.DataBaseUtil;
import nssa.uc.vo.HostStat;

public class HostStatDao {
	public int countAll() throws SQLException {
		String sql = "SELECT COUNT(*) FROM hoststat";
		return DataBaseUtil.getCount(sql);
	}
	public List<HostStat> listAll() throws SQLException {
		String sql = "SELECT * FROM hoststat ORDER BY time DESC;";

		List<HostStat> hostStatList = new ArrayList<HostStat>();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				HostStat hostStat = new HostStat();
				hostStat.setIp(rs.getString("ip"));
				hostStat.setTime(rs.getString("time"));
				hostStat.setPorts(rs.getString("ports"));
				hostStatList.add(hostStat);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

		return hostStatList;
	}
	public List<HostStat> listAll(int pageNo) throws SQLException {
		String sql = "SELECT * FROM hoststat ORDER BY time DESC LIMIT ?,10;";

		List<HostStat> hostStatList = new ArrayList<HostStat>();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, pageNo*10-10);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				HostStat hostStat = new HostStat();
				hostStat.setIp(rs.getString("ip"));
				hostStat.setTime(rs.getString("time"));
				hostStat.setPorts(rs.getString("ports"));
				hostStatList.add(hostStat);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

		return hostStatList;
	}
}
