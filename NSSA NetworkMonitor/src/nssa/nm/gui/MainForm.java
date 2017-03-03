package nssa.nm.gui;

import java.sql.DriverManager;
import java.util.List;

import javax.swing.JOptionPane;

import nssa.nm.capture.CaptureCore;
import nssa.nm.capture.CaptureThread;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import swing2swt.layout.BorderLayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.jnetpcap.PcapIf;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import swing2swt.layout.FlowLayout;

public class MainForm {
	
	private static CaptureThread thread;
	public static String HostAddress="http://127.0.0.1:8080/";

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shlNssaNetworkmonitor = new Shell();
		shlNssaNetworkmonitor.setSize(450, 300);
		shlNssaNetworkmonitor.setText("NSSA NetworkMonitor");
		shlNssaNetworkmonitor.setLayout(new BorderLayout(0, 0));
		
		Composite compositewest = new Composite(shlNssaNetworkmonitor, SWT.NONE);
		compositewest.setLayoutData(BorderLayout.WEST);
		compositewest.setLayout(new GridLayout(1, false));
		
		Composite compositeeast = new Composite(shlNssaNetworkmonitor, SWT.NONE);
		compositeeast.setLayoutData(BorderLayout.EAST);
		compositeeast.setLayout(new GridLayout(1, false));
		
		Composite compositecenter = new Composite(shlNssaNetworkmonitor, SWT.NONE);
		compositecenter.setLayoutData(BorderLayout.CENTER);
		compositecenter.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Button buttoninit = new Button(compositecenter, SWT.NONE);
		buttoninit.setText("\u68C0\u6D4B\u7F51\u5361");
		
		final Combo comboadapter = new Combo(compositecenter, SWT.NONE);
		comboadapter.setText("------\u8BF7\u9009\u62E9\u8981\u76D1\u542C\u7684\u7F51\u7EDC\u9002\u914D\u5668------");
		
		final Button buttonstart = new Button(compositecenter, SWT.NONE);
		buttonstart.setText("\u5F00\u59CB\u76D1\u542C");
		
		buttoninit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				List<PcapIf> devs = CaptureCore.getDevs();
				comboadapter.removeAll();
				comboadapter.setText("------请选择要监听的网络适配器------");
				for(PcapIf dev : devs) {
					comboadapter.add(dev.getDescription());
				}
				JOptionPane.showMessageDialog(null, "网卡检测完成", "信息", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		buttonstart.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(buttonstart.getText().trim().equals("开始监听")) {
					int num = comboadapter.getSelectionIndex();
					if(num == -1) {
						JOptionPane.showMessageDialog(null, "请选择一块网卡", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					}
					thread = new CaptureThread(num);
					thread.start();
					buttonstart.setText("停止监听");
					JOptionPane.showMessageDialog(null, "监听已开始", "信息", JOptionPane.INFORMATION_MESSAGE);
				} else {
					thread.stop();
					buttonstart.setText("开始监听");
					JOptionPane.showMessageDialog(null, "监听已结束", "信息", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		shlNssaNetworkmonitor.open();
		shlNssaNetworkmonitor.layout();
		while (!shlNssaNetworkmonitor.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
}
