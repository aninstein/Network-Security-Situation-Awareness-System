package nssa.uc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nssa.uc.util.DataBaseUtil;
import nssa.uc.vo.HostAsset;

public class HostAssetDao {
	public int countAll() throws SQLException {
		String sql = "SELECT COUNT(*) FROM hostasset";
		return DataBaseUtil.getCount(sql);
	}
	public List<HostAsset> listAll() throws SQLException {
		String sql = "SELECT * FROM hostasset ORDER BY time DESC;";

		List<HostAsset> hostAssetList = new ArrayList<HostAsset>();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				HostAsset hostAsset = new HostAsset();
				hostAsset.setIp(rs.getString("ip"));
				hostAsset.setTime(rs.getString("time"));
				hostAsset.setName(rs.getString("name"));
				hostAsset.setDomain(rs.getString("domain"));
				hostAsset.setOs(rs.getString("os"));
				hostAsset.setCpu(rs.getString("cpu"));
				hostAsset.setMemory(rs.getString("memory"));
				hostAsset.setDisk(rs.getString("disk"));
				hostAsset.setDetail(rs.getString("detail"));
				hostAssetList.add(hostAsset);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

		return hostAssetList;
	}
	public List<HostAsset> listAll(int pageNo) throws SQLException {
		String sql = "SELECT * FROM hostasset ORDER BY time DESC LIMIT ?,10;";

		List<HostAsset> hostAssetList = new ArrayList<HostAsset>();
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			conn = DataBaseUtil.getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, pageNo*10-10);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				HostAsset hostAsset = new HostAsset();
				hostAsset.setIp(rs.getString("ip"));
				hostAsset.setTime(rs.getString("time"));
				hostAsset.setName(rs.getString("name"));
				hostAsset.setDomain(rs.getString("domain"));
				hostAsset.setOs(rs.getString("os"));
				hostAsset.setCpu(rs.getString("cpu"));
				hostAsset.setMemory(rs.getString("memory"));
				hostAsset.setDisk(rs.getString("disk"));
				hostAsset.setDetail(rs.getString("detail"));
				hostAssetList.add(hostAsset);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (preStmt != null)
				preStmt.close();
			if (conn != null)
				conn.close();
		}

		return hostAssetList;
	}
}
