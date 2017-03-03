package nssa.uc.jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYBarDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class BarChartServletOne extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public BarChartServletOne() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String msg = request.getParameter("msg");
		String date = request.getParameter("date");
		
		//从数据库中查询数据
		BarChartDataOne barChartData = null;
		try {
			barChartData = new BarChartDataOne(msg, date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//将数据放入数据集中
		XYSeries xyseries = new XYSeries(msg);
		for(int i = 0; i < barChartData.getValues().length; i++) {
			xyseries.add(barChartData.getDateList().get(i).intValue(), barChartData.getValues()[i]);
		}
		XYSeriesCollection xyseriescollection = new XYSeriesCollection();
		xyseriescollection.addSeries(xyseries);
		IntervalXYDataset dataset = new XYBarDataset(xyseriescollection, 0.90000000000000002D);
		//创建主题样式
		StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
		//设置标题字体  
		standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));  
		//设置图例的字体  
		standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));  
		//设置轴向的字体  
		standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));  
		//应用主题样式  
		ChartFactory.setChartTheme(standardChartTheme); 
		//创建图形对象
		JFreeChart jfreechart = ChartFactory.createXYBarChart(barChartData.getTitle(), "时间", false, "次数", dataset, PlotOrientation.VERTICAL, true, false, false);
		//设置背景为白色
		jfreechart.setBackgroundPaint(Color.WHITE);
		//获得图表区域对象
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		//设置区域对象背景为灰色
		xyplot.setBackgroundPaint(Color.LIGHT_GRAY);
		//设置区域对象网格线调为白色
		xyplot.setDomainGridlinePaint(Color.WHITE);
		xyplot.setRangeGridlinePaint(Color.WHITE);
		//获得x轴对象
		ValueAxis catagoryAxis = xyplot.getDomainAxis();
		//数据轴的数据标签是否自动确定（默认为true） 
		catagoryAxis.setAutoTickUnitSelection(false);
		//数据轴的数据标签
		catagoryAxis.setStandardTickUnits(catagoryAxis.getStandardTickUnits());
		//1为一个间隔单位 
		catagoryAxis.setAutoRangeMinimumSize(1.0);
		//获得y轴对象
		ValueAxis rangeAxis = xyplot.getRangeAxis();
		//数据轴的数据标签是否自动确定（默认为true） 
		rangeAxis.setAutoTickUnitSelection(false);
		//数据轴的数据标签
		rangeAxis.setStandardTickUnits(rangeAxis.getStandardTickUnits());
		//数据轴上的显示最小值
		rangeAxis.setLowerBound(0.0);
		//1为一个间隔单位 
		rangeAxis.setAutoRangeMinimumSize(1.0);
		//获显示图形对象
		XYBarRenderer xybarrenderer = (XYBarRenderer) xyplot.getRenderer();
		//设置柱子为蓝色
		xybarrenderer.setSeriesPaint(0, Color.BLUE);
		//设置不显示边框线
		xybarrenderer.setDrawBarOutline(false);
		//设置返回类型为图片
		response.setContentType("image/png");
		//将图表以数据流的方式返回给客户端
		ChartUtilities.writeChartAsPNG(response.getOutputStream(), jfreechart, 500, 320);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
