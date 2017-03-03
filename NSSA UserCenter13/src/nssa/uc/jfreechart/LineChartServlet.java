package nssa.uc.jfreechart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public LineChartServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String date = request.getParameter("date");

		// 实例化DefaultCategoryDataset对象
		
		LineChartData lineChartData = null;
		try {
			lineChartData = new LineChartData(date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String title = lineChartData.getTitle();
		// 图例名称
		String[] line =lineChartData.getLineNames();
		// 类别
		List<Integer> dateList = lineChartData.getdateList();
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		// 使用循环向数据集合中添加数据
		for (int i = 0; i < line.length; i++) {
			for (int j = 0; j < dateList.size(); j++) {
				dataSet.addValue(lineChartData.getValues()[j][i], line[i],
						dateList.get(j));
			}
		}
		Font PLOT_FONT = new Font("宋体", Font.BOLD, 15);
		JFreeChart chart = null;
		chart = ChartFactory.createLineChart(title, // 图表标题
				"时间", // X轴标题
				"评分", // Y轴标题
				dataSet, // 绘图数据集
				PlotOrientation.VERTICAL, // 绘制方向
				true, // 显示图例
				true, // 采用标准生成器
				false // 是否生成超链接
				);

		// 设置标题字体
		chart.getTitle().setFont(new Font("隶书", Font.BOLD, 23));
		// 设置图例类别字体
		chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 15));
		chart.setBackgroundPaint(Color.WHITE);
		// 设置背景色
		// 获取绘图区对象
		CategoryPlot plot = chart.getCategoryPlot();
		plot.getDomainAxis().setLabelFont(PLOT_FONT);
		// 设置横轴字体
		plot.getDomainAxis().setTickLabelFont(PLOT_FONT);// 设置坐标轴标尺值字体
		plot.getRangeAxis().setLabelFont(PLOT_FONT); // 设置纵轴字体
		plot.getRangeAxis().setUpperBound(10.0);
		plot.setBackgroundPaint(Color.WHITE); // 设置绘图区背景色
		plot.setRangeGridlinePaint(Color.RED); // 设置水平方向背景线颜色
		plot.setRangeGridlinesVisible(true); // 设置是否显示水平方向背景线,默认值为true
		plot.setDomainGridlinePaint(Color.RED); // 设置垂直方向背景线颜色
		plot.setDomainGridlinesVisible(true); // 设置是否显示垂直方向背景线,默认值为false
		// 获取折线对象
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
				.getRenderer();

		//renderer.setShapesVisible(true);
		renderer.setDrawOutlines(true);
		renderer.setUseFillPaint(true);
		//renderer.setFillPaint(java.awt.Color.white);
		// renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator(
		// "{2} ",new
		// DecimalFormat()));//前面参数是标签，可以是{0},{1}或者{2},{2}代表数值，后面那个参数就是数据格式

		BasicStroke realLine = new BasicStroke(1.6f); // 设置实线
		float dashes[] = { 8.0f }; // 定义虚线数组
		BasicStroke brokenLine = new BasicStroke(1.6f, // 线条粗细
				BasicStroke.CAP_SQUARE, // 端点风格
				BasicStroke.JOIN_MITER, // 折点风格
				8.f, // 折点处理办法
				dashes, // 虚线数组
				0.0f); // 虚线偏移量
		renderer.setSeriesStroke(1, brokenLine); // 利用虚线绘制
		renderer.setSeriesStroke(2, brokenLine); // 利用虚线绘制
		renderer.setSeriesStroke(3, realLine); // 利用实线绘制
		response.setContentType("image/png");
		//将图表以数据流的方式返回给客户端
		ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, 500, 320);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
