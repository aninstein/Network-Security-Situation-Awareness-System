package nssa.uc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import nssa.uc.util.DataBaseUtil;
import nssa.uc.util.MD5Util;

public class AdminDao {
	
	public boolean login(String username, String password) throws SQLException {
		String sql = "select * from admin where username=? and password=?";

		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;

		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, username);
//			preStmt.setString(2, MD5Util.calc(password));
			preStmt.setString(2, password);
			
			rs = preStmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

		return false;
	}
	
	public boolean modify(String username, String oldPassword, String newPassword) throws SQLException {
		if(login(username, oldPassword)) {
			String sql = "UPDATE admin SET password = ? WHERE username = ?";
			DataBaseUtil.executeUpdate(sql, MD5Util.calc(newPassword), username);
			return true;
		} else {
			return false;
		}
	}

}
