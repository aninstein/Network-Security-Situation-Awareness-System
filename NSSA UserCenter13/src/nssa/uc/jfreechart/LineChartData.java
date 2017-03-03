package nssa.uc.jfreechart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nssa.uc.dao.SecuritySituationDao;
import nssa.uc.util.DateUtil;
import nssa.uc.vo.SecuritySituation;

public class LineChartData {

	private String title;
	private List<Integer> dateList;
	private String[] lines = { "资产", "风险", "威胁" ,"态势"};
	private float[][] values;

	public LineChartData(String date) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("最近");
		if (date.equals("today")) {
			sb.append("一天");
		}
		if (date.equals("lastweek")) {
			sb.append("一周");
		}
		sb.append("安全态势折线图");
		title = sb.toString();
		dateList = new ArrayList<Integer>();
		createData(date);
	}

	private void createData(String date) throws SQLException {
		// TODO Auto-generated method stub
		SecuritySituationDao securitySituationDao = new SecuritySituationDao();
		List<SecuritySituation> securitySituationList = null;
		if (date.equals("today")) {
			// 获取securityEventList
			securitySituationList = securitySituationDao.listTodayAll();
			// 设施日期坐标
			for (int i = 0; i < securitySituationList.size(); i++) {
				if (!dateList.contains(DateUtil.getHour(securitySituationList
						.get(i).getTime()))) {
					dateList.add(DateUtil.getHour(securitySituationList.get(i)
							.getTime()));
				}
			}
			// 初始化统计数目
			values = new float[dateList.size()][lines.length];
			// 统计
			for (int n = 0; n < securitySituationList.size(); n++) {
				for (int i = 0; i < dateList.size(); i++) {
					if (DateUtil.getHour(securitySituationList.get(n).getTime()) == dateList.get(i).intValue()) {
						values[i][0] = securitySituationList.get(n).getAsset();
						values[i][1] = securitySituationList.get(n).getRisk();
						values[i][2] = securitySituationList.get(n).getThreat();
						values[i][3] = securitySituationList.get(n).getSituation();
					}
				}
			}
		} else if (date.equals("lastweek")) {
			// 获取securityEventList
			securitySituationList = securitySituationDao.listLastWeekAll();
			// 设施日期坐标
			for (int i = 0; i < securitySituationList.size(); i++) {
				if (!dateList.contains(DateUtil.getDay(securitySituationList
						.get(i).getTime()))) {
					dateList.add(DateUtil.getDay(securitySituationList.get(i)
							.getTime()));
				}
			}
			// 初始化统计数目
			values = new float[dateList.size()][lines.length];
			// 统计
			for (int n = 0; n < securitySituationList.size(); n++) {
				for (int i = 0; i < dateList.size(); i++) {
					if (DateUtil.getDay(securitySituationList.get(n).getTime()) == dateList.get(i).intValue()) {
						if(values[i][0] < securitySituationList.get(n).getAsset())
							values[i][0] = securitySituationList.get(n).getAsset();
						if(values[i][1] < securitySituationList.get(n).getRisk())
							values[i][1] = securitySituationList.get(n).getRisk();
						if(values[i][2] < securitySituationList.get(n).getThreat())
							values[i][2] = securitySituationList.get(n).getThreat();
						if(values[i][3] < securitySituationList.get(n).getSituation())
							values[i][3] = securitySituationList.get(n).getSituation();
					}
				}
			}
		}
	}

	public String[] getLineNames() {
		return lines;
	}

	public float[][] getValues() {
		return values;
	}

	public List<Integer> getdateList() {
		return dateList;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}

}
