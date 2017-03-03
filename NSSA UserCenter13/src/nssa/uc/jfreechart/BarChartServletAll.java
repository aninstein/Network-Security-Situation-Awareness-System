package nssa.uc.jfreechart;

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
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChartServletAll extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public BarChartServletAll() {
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
		
		String date = request.getParameter("date");
		
		//从数据库中查询数据
		BarChartDataAll barChartData = null;
		try {
			barChartData = new BarChartDataAll(date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//将数据放入数据集中
		DefaultCategoryDataset defaultDataset = new DefaultCategoryDataset();
		for (int i = 0; i < barChartData.getDateList().size(); i++) {
			String dateCatagory = barChartData.getDateList().get(i).toString();
			for (int j = 0; j < barChartData.getMsgs().length; j++) {
				String nameCatagory = barChartData.getMsgs()[j];
				int value = barChartData.getValues()[i][j];
				defaultDataset.addValue(value, nameCatagory, dateCatagory);
			}
		}
		//获取数据集对象
		CategoryDataset dataset = defaultDataset;
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
		JFreeChart jfreechart = ChartFactory.createBarChart3D(barChartData.getTitle(), "时间", "次数", dataset, PlotOrientation.VERTICAL, true, true, false);
		//获得图表区域对象
		CategoryPlot categoryPlot = (CategoryPlot)jfreechart.getPlot();
		//设置网格线可见
		categoryPlot.setDomainGridlinesVisible(true);
		//获得x轴对象
		CategoryAxis categoryAxis = categoryPlot.getDomainAxis();
		//设置x轴显示的分类名称的显示位置
		categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.39269908169872414D));
		categoryAxis.setCategoryMargin(0.1D);
		//获得y轴对象
		ValueAxis rangeAxis = categoryPlot.getRangeAxis();
		//数据轴的数据标签是否自动确定（默认为true） 
		rangeAxis.setAutoTickUnitSelection(false);
		//数据轴的数据标签
		rangeAxis.setStandardTickUnits(rangeAxis.getStandardTickUnits());
		//数据轴上的显示最小值
		rangeAxis.setLowerBound(0.0);
		//1为一个间隔单位 
		rangeAxis.setAutoRangeMinimumSize(1.0);
		//获得显示图形对象
		BarRenderer3D barRenderer3d = (BarRenderer3D)categoryPlot.getRenderer();
		//设置不显示边框线
		barRenderer3d.setDrawBarOutline(false);
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
