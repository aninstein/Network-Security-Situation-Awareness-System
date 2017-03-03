package nssa.hs.gui;

import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import nssa.hs.scanner.ScannerThread;
import nssa.hs.util.IPUtil;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;

import swing2swt.layout.BorderLayout;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.sun.http.HttpRequest;

public class MainForm {
	private static Text texthost;
	private static Text textport;
	private static ScannerThread thread;
	public static String HostAddress="http://127.0.0.1：8080/";
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		Display display = Display.getDefault();
		Shell shlNssaHostscanner = new Shell();
		shlNssaHostscanner.setSize(450, 300);
		shlNssaHostscanner.setText("NSSA HostScanner");
		shlNssaHostscanner.setLayout(new BorderLayout(0, 0));
		
		Composite compositewest = new Composite(shlNssaHostscanner, SWT.NONE);
		compositewest.setLayoutData(BorderLayout.WEST);
		compositewest.setLayout(new GridLayout(1, false));
		
		Composite compositecenter = new Composite(shlNssaHostscanner, SWT.NONE);
		compositecenter.setLayoutData(BorderLayout.CENTER);
		compositecenter.setLayout(new GridLayout(3, false));
		new Label(compositecenter, SWT.NONE);
		new Label(compositecenter, SWT.NONE);
		new Label(compositecenter, SWT.NONE);
		new Label(compositecenter, SWT.NONE);
		
		Label labelhost = new Label(compositecenter, SWT.NONE);
		labelhost.setText("\u4E3B\u673A\u5217\u8868");
		
		texthost = new Text(compositecenter, SWT.BORDER);
		texthost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(compositecenter, SWT.NONE);
		new Label(compositecenter, SWT.NONE);
		new Label(compositecenter, SWT.NONE);
		new Label(compositecenter, SWT.NONE);
		
		Label labelport = new Label(compositecenter, SWT.NONE);
		labelport.setText("\u7AEF\u53E3\u5217\u8868");
		
		textport = new Text(compositecenter, SWT.BORDER);
		textport.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite compositeeast = new Composite(shlNssaHostscanner, SWT.NONE);
		compositeeast.setLayoutData(BorderLayout.EAST);
		compositeeast.setLayout(new GridLayout(1, false));
		new Label(compositeeast, SWT.NONE);
		new Label(compositeeast, SWT.NONE);
		new Label(compositeeast, SWT.NONE);
		new Label(compositeeast, SWT.NONE);
		new Label(compositeeast, SWT.NONE);
		new Label(compositeeast, SWT.NONE);
		
		final Button buttonstart = new Button(compositeeast, SWT.NONE);
		buttonstart.setText("\u5F00\u59CB\u626B\u63CF");
		
		final Button buttonstop = new Button(compositeeast, SWT.NONE);
		buttonstop.setEnabled(false);
		
	
		buttonstart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String sip=null;
				String eip=null;
				int eport=0;
				int sport=0;
				try {
					String ip = texthost.getText().trim();
					MainForm.ip = texthost.getText().trim();
					
					
					
					Date date=new Date();
					
					DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time=format.format(date);
					TIME = time+"";
					
					
					
					
					
					sip = ip.split("-")[0];
					eip = ip.split("-")[1];
					if(!IPUtil.valid(sip) || !IPUtil.valid(eip)) {
						throw new Exception();
					}
					if(!IPUtil.compare(sip, eip)) {
						throw new Exception();
					}
					String port = textport.getText().trim();
					MainForm.ports = textport.getText().trim();
					
					//*****************/
					//InsertDataToDB();
					
					sport = Integer.parseInt(port.split("-")[0]);
					eport = Integer.parseInt(port.split("-")[1]);
					if(sport < 0 || eport < sport || eport > 65535) {
						throw new Exception();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,"输入信息有误，请确定输入的地址，端口信息合法且格式为“初始-终止”。","错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
//				Date date=new Date();
//				DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				String time=format.format(date);
//				TIME = time+"";
//				
//				JSONObject obj= new JSONObject();	
//				obj.put("ip",texthost.getText());
//				obj.put("TIME",time);
//				obj.put("ports",textport.getText());
//				String objStr=obj.toString();
//				HttpRequest.sendPost(HostAddress+"TestXinanServer/Test", "json="+objStr);
				//System.out.println("\n发送 GET 请求******"+DetectCore.name()+"*********\n");
				thread = new ScannerThread(sip, eip, sport, eport);
				thread.start();
				buttonstop.setEnabled(true);
				buttonstart.setEnabled(false);
				JOptionPane.showMessageDialog(null,"扫描开始","信息",JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
		
		buttonstop.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent e) {
				thread.stop();
				buttonstop.setEnabled(false);
				buttonstart.setEnabled(true);
				JOptionPane.showMessageDialog(null,"扫描停止","信息",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		buttonstop.setText("\u505C\u6B62\u626B\u63CF");

		shlNssaHostscanner.open();
		shlNssaHostscanner.layout();
		while (!shlNssaHostscanner.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
	}
	static String ip;
	static String TIME;
	public static String ports;
	

	public static void InsertDataToDB()
	{
		try
		{
			// 加载驱动 //驱动注册
			Class.forName("com.mysql.jdbc.Driver");
			// 创建连接
			Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nssa?useUnicode=true&characterEncoding=UTF-8", "root", "root");
			// 创建与数据库交互指令集
			Statement stmt = (Statement) conn.createStatement();
			String sql = "insert into hoststat (ip,TIME,portS) values ('"+ip+"' ,'"+TIME+"','"+ports+"')";
			// String sql =
			// "update test_table1 set score = 78.1 where name = 'Think in C++' ";
			// String sql = "delete from test_table1 where id = 1";
			stmt.execute(sql);

			// 关闭 或 释放资源
			stmt.close();
			conn.close();
			
			
			
			
		} catch (Exception e)
		{

			//e.printStackTrace();
		}
	}
	
	
}
