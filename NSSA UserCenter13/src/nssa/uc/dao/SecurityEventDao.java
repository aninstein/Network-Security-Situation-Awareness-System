package nssa.uc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nssa.uc.util.DataBaseUtil;
import nssa.uc.vo.SecurityEvent;


public class SecurityEventDao {
	
	public int countAll() throws SQLException {
		String sql = "SELECT COUNT(*) FROM securityevent";
		return DataBaseUtil.getCount(sql);
	}
	
	public int countByMsg(String msg) throws SQLException {
		String sql = "SELECT COUNT(*) FROM securityevent WHERE msg ='" + msg + "';";
		return DataBaseUtil.getCount(sql);
	}
	
	public String getPacketById(int id) throws SQLException {
		String sql = "SELECT packet FROM securityevent WHERE id = ?;";
		
		String packet = null;
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, id);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				packet = rs.getString("packet");
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}
		return packet;
	}
	
	public List<String> getMsgs() throws SQLException {
		String sql = "SELECT msg FROM securityevent GROUP BY msg DESC;";
		
		List<String> msgList = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				String msg;
				msg = rs.getString("msg");
				msgList.add(msg);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}
		return msgList;
	}
	
	public List<SecurityEvent> listAll() throws SQLException {
		String sql = "SELECT * FROM securityevent ORDER BY time DESC;";

		List<SecurityEvent> securityEventList = new ArrayList<SecurityEvent>();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				SecurityEvent securityEvent = new SecurityEvent();
				securityEvent.setId(rs.getInt("id"));
				securityEvent.setMsg(rs.getString("msg"));
				securityEvent.setTime(rs.getString("time"));
				securityEvent.setPacket(rs.getString("packet"));
				securityEvent.setSip(rs.getString("sip"));
				securityEvent.setDip(rs.getString("dip"));
				securityEvent.setSport(rs.getString("sport"));
				securityEvent.setDport(rs.getString("dport"));
				securityEventList.add(securityEvent);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

		return securityEventList;
	}
	
	public List<SecurityEvent> listAll(int pageNo) throws SQLException {
		String sql = "SELECT * FROM securityevent ORDER BY time DESC LIMIT ?,10;";

		List<SecurityEvent> securityEventList = new ArrayList<SecurityEvent>();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, pageNo*10-10);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				SecurityEvent securityEvent = new SecurityEvent();
				securityEvent.setId(rs.getInt("id"));
				securityEvent.setMsg(rs.getString("msg"));
				securityEvent.setTime(rs.getString("time"));
				securityEvent.setPacket(rs.getString("packet"));
				securityEvent.setSip(rs.getString("sip"));
				securityEvent.setDip(rs.getString("dip"));
				securityEvent.setSport(rs.getString("sport"));
				securityEvent.setDport(rs.getString("dport"));
				securityEventList.add(securityEvent);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

		return securityEventList;
	}
	
	public List<SecurityEvent> listAllByMsg(String msg) throws SQLException {
		String sql = "SELECT * FROM securityevent WHERE msg = ? ORDER BY time DESC;";

		List<SecurityEvent> securityEventList = new ArrayList<SecurityEvent>();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, msg);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				SecurityEvent securityEvent = new SecurityEvent();
				securityEvent.setId(rs.getInt("id"));
				securityEvent.setMsg(rs.getString("msg"));
				securityEvent.setTime(rs.getString("time"));
				securityEvent.setPacket(rs.getString("packet"));
				securityEvent.setSip(rs.getString("sip"));
				securityEvent.setDip(rs.getString("dip"));
				securityEvent.setSport(rs.getString("sport"));
				securityEvent.setDport(rs.getString("dport"));
				securityEventList.add(securityEvent);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

		return securityEventList;
	}
	
	public List<SecurityEvent> listAllByMsg(String msg, int pageNo) throws SQLException {
		String sql = "SELECT * FROM securityevent WHERE msg = ? ORDER BY time DESC LIMIT ?,10;";
		
		List<SecurityEvent> securityEventList = new ArrayList<SecurityEvent>();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, msg);
			preStmt.setInt(2, pageNo*10-10);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				SecurityEvent securityEvent = new SecurityEvent();
				securityEvent.setId(rs.getInt("id"));
				securityEvent.setMsg(rs.getString("msg"));
				securityEvent.setTime(rs.getString("time"));
				securityEvent.setPacket(rs.getString("packet"));
				securityEvent.setSip(rs.getString("sip"));
				securityEvent.setDip(rs.getString("dip"));
				securityEvent.setSport(rs.getString("sport"));
				securityEvent.setDport(rs.getString("dport"));
				securityEventList.add(securityEvent);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

		return securityEventList;
	}
	
	public List<SecurityEvent> listTodayAll() throws SQLException {
		String sql = "SELECT * FROM securityevent WHERE DATEDIFF(time,NOW()) =0 ORDER BY time ASC;";

		List<SecurityEvent> securityEventList = new ArrayList<SecurityEvent>();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				SecurityEvent securityEvent = new SecurityEvent();
				securityEvent.setId(rs.getInt("id"));
				securityEvent.setMsg(rs.getString("msg"));
				securityEvent.setTime(rs.getString("time"));
				securityEvent.setPacket(rs.getString("packet"));
				securityEvent.setSip(rs.getString("sip"));
				securityEvent.setDip(rs.getString("dip"));
				securityEvent.setSport(rs.getString("sport"));
				securityEvent.setDport(rs.getString("dport"));
				securityEventList.add(securityEvent);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

		return securityEventList;
	}
	
	public List<SecurityEvent> listLastWeekAll() throws SQLException {
		String sql = "SELECT * FROM securityevent WHERE time >= date_format(date_sub(date_sub(now(), INTERVAL WEEKDAY(NOW()) DAY), INTERVAL 1 WEEK), '%Y-%m-%d') ORDER BY time ASC;";
		
		List<SecurityEvent> securityEventList = new ArrayList<SecurityEvent>();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				SecurityEvent securityEvent = new SecurityEvent();
				securityEvent.setId(rs.getInt("id"));
				securityEvent.setMsg(rs.getString("msg"));
				securityEvent.setTime(rs.getString("time"));
				securityEvent.setPacket(rs.getString("packet"));
				securityEvent.setSip(rs.getString("sip"));
				securityEvent.setDip(rs.getString("dip"));
				securityEvent.setSport(rs.getString("sport"));
				securityEvent.setDport(rs.getString("dport"));
				securityEventList.add(securityEvent);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

		return securityEventList;
	}
	
	public List<SecurityEvent> listTodayByMsg(String msg) throws SQLException {
		String sql = "SELECT * FROM securityevent WHERE msg = ? AND DATEDIFF(time,NOW()) =0 ORDER BY time ASC;";

		List<SecurityEvent> securityEventList = new ArrayList<SecurityEvent>();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, msg);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				SecurityEvent securityEvent = new SecurityEvent();
				securityEvent.setId(rs.getInt("id"));
				securityEvent.setMsg(rs.getString("msg"));
				securityEvent.setTime(rs.getString("time"));
				securityEvent.setPacket(rs.getString("packet"));
				securityEvent.setSip(rs.getString("sip"));
				securityEvent.setDip(rs.getString("dip"));
				securityEvent.setSport(rs.getString("sport"));
				securityEvent.setDport(rs.getString("dport"));
				securityEventList.add(securityEvent);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

		return securityEventList;
	}
	
	public List<SecurityEvent> listLastWeekByMsg(String msg) throws SQLException {
		String sql = "SELECT * FROM securityevent WHERE msg = ? AND time >= date_format(date_sub(date_sub(now(), INTERVAL WEEKDAY(NOW()) DAY), INTERVAL 1 WEEK), '%Y-%m-%d') ORDER BY time ASC;";
		
		List<SecurityEvent> securityEventList = new ArrayList<SecurityEvent>();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, msg);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				SecurityEvent securityEvent = new SecurityEvent();
				securityEvent.setId(rs.getInt("id"));
				securityEvent.setMsg(rs.getString("msg"));
				securityEvent.setTime(rs.getString("time"));
				securityEvent.setPacket(rs.getString("packet"));
				securityEvent.setSip(rs.getString("sip"));
				securityEvent.setDip(rs.getString("dip"));
				securityEvent.setSport(rs.getString("sport"));
				securityEvent.setDport(rs.getString("dport"));
				securityEventList.add(securityEvent);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

		return securityEventList;
	}
	
}
