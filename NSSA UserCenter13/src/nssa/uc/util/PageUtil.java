package nssa.uc.util;

import java.util.List;

public class PageUtil {

	/** 当前页 */
	private int pageNo;
	/** 每页数据量 */
	private int pageSize;
	/** 总数据量 */
	private int allCount;
	/** 总页数 */
	private int allPage;
	/** 每页开始 */
	private int recordStart;
	/** 每页结束 */
	private int recordEnd;
	/** 显示页数 */
	private int showCount;
	/** 显示开始 */
	private int showStart;
	/** 显示结束 */
	private int showEnd;
	/** RUL地址 */
	private String url;// 页面传过来的URL（具体有什么用等下会解释，上面的应该都很容易理解了吧）
	/** 样式名字 */
	//private String styleClass;

	/**
	 * 构造方法,缺省每页记录数为10、显示页码数为9、样式名字为
	 * 
	 * @param pageNo
	 *            当前页
	 * @param allCount
	 *            总记录数
	 * @param url
	 *            分页URL
	 */
	public PageUtil(int pageNo, int allCount, String url) {
		this(pageNo, 10, allCount, 9, url/*, ""*/);
	}

	/**
	 * 构造方法
	 * 
	 * @param pageNo
	 *            当前页
	 * @param pageSize
	 *            每页记录数
	 * @param allCount
	 *            总记录数
	 * @param showCount
	 *            显示页码数
	 * @param url
	 *            分页URL
	 * @param styleClass
	 *            样式名字
	 */
	public PageUtil(int pageNo, int pageSize, int allCount, int showCount,
			String url/*, String styleClass*/) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.allCount = allCount;
		this.showCount = showCount;
		this.url = url;
		//this.styleClass = styleClass;
		init();
	}

	/**
	 * 初始化分页各种参数
	 */
	private void init() {
		allPage = allCount % pageSize == 0 ? allCount / pageSize : allCount
				/ pageSize + 1;
		if (pageNo > allPage) {
			pageNo = allPage;
		}
		if (pageNo < 1) {
			pageNo = 1;
		}
		recordStart = (pageNo - 1) * pageSize;
		recordEnd = Math.min(recordStart + pageSize, allCount);
		showCount = Math.min(showCount, allPage);
		if (showCount >= allPage) {
			showStart = 1;
			showEnd = allPage;
		} else {
			if (pageNo <= (showCount + 1) / 2) {
				showStart = 1;
				showEnd = showCount;
			} else if (pageNo > allPage - (showCount + 1) / 2) {
				showStart = allPage - showCount + 1;
				showEnd = allPage;
			} else {
				showStart = pageNo - showCount / 2;
				showEnd = showStart + showCount - 1;
			}
		}
	}

	/**
	 * 获取分页数据
	 * 
	 * @param <T>
	 *            范型参数
	 * @param list
	 *            需要分页的总数据集合
	 * @return
	 */
	public <T> List<T> getPageDate(List<T> list) {
		return list.subList(recordStart, recordEnd);
	}

	/**
	 * 生成页面的分页元素(分页html页面标签)
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (pageNo < 2) {
			sb.append("<a href='#'>首页</a>");
			sb.append("<a href='#'>上一页</a>");
		} else {
			sb.append("<a href='" + url + "pageNo=1'>首页</a>");
			sb.append("<a href='" + url + "pageNo=" + (pageNo - 1) + "'>上一页</a>");
		}
		for (int i = showStart; i < pageNo; i++) {
			sb.append("<a href='" + url + "pageNo=" + i + "'>" + i + "</a>");
		}
		sb.append("<a href='#'>" + pageNo + "</a>");
		for (int i = pageNo + 1; i <= showEnd; i++) {
			sb.append("<a href='" + url + "pageNo=" + i + "'>" + i + "</a>");
		}
		if (pageNo >= allPage) {
			sb.append("<a href='#'>下一页</a>");
			sb.append("<a href='#'>尾页</a>");
		} else {
			sb.append("<a href='" + url + "pageNo=" + (pageNo + 1) + "'>下一页</a>");
			sb.append("<a href='" + url + "pageNo=" + allPage + "'>尾页</a>");
		}
		return sb.toString();
	}

}