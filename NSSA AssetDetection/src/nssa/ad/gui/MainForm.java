package nssa.ad.gui;

import nssa.ad.detect.DetectCore;
import nssa.ad.message.ADMessage;
import nssa.ad.message.MessageCenter;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.alibaba.fastjson.JSONObject;
import com.sun.http.HttpRequest;

public class MainForm {
	
	private static Text textname;
	private static Text textdomain;
	private static Text textos;
	private static Text textcpu;
	private static Text textmemory;
	private static Text textdisk;
	private static Text textdetail;	
	public static String HostAddress="http://192.168.1.115:8080/";

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		Display display = Display.getDefault();
		Shell shlNssa = new Shell();
		shlNssa.setSize(450, 300);
		shlNssa.setText("NSSA AssetDetection");
		
		Button buttondetect = new Button(shlNssa, SWT.NONE);
		buttondetect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textname.setText(DetectCore.name());
				textdomain.setText(DetectCore.domain());
				textos.setText(DetectCore.os());
				textcpu.setText(DetectCore.cpu());
				textmemory.setText(DetectCore.memory());
				textdisk.setText(DetectCore.disk());
				
				JSONObject obj= new JSONObject();
				
				obj.put("textname", textname.getText());
				obj.put("textdomain", textdomain.getText());
				obj.put("textos", textos.getText());
				obj.put("textcpu", textcpu.getText());
				obj.put("textmemory", textmemory.getText());
				obj.put("textdisk", textdisk.getText());
				
				String objStr=obj.toString();
				
				//*************************
				//HttpRequest.sendPost(HostAddress+"TestXinanServer/Test", "json="+objStr);
				System.out.println("objStr*********\n"+objStr);
				
				
			}
		});
		buttondetect.setBounds(233, 212, 80, 27);
		buttondetect.setText("\u8D44\u4EA7\u68C0\u6D4B");
		
		Button buttonsend = new Button(shlNssa, SWT.NONE);
		buttonsend.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ADMessage msg = new ADMessage();
				msg.setName(textname.getText().trim());
				msg.setDomain(textdomain.getText().trim());
				msg.setOs(textos.getText().trim());
				msg.setCpu(textcpu.getText().trim());
				msg.setMemory(textmemory.getText().trim());
				msg.setDisk(textdisk.getText().trim());
				msg.setDetail(textdetail.getText().trim());
				MessageCenter.sendMessage(msg);
			}
		});
		buttonsend.setBounds(330, 212, 80, 27);
		buttonsend.setText("\u53D1\u9001\u4FE1\u606F");
		
		Label lblName = new Label(shlNssa, SWT.NONE);
		lblName.setBounds(10, 10, 61, 17);
		lblName.setText("name");
		
		Label lblDomain = new Label(shlNssa, SWT.NONE);
		lblDomain.setBounds(10, 33, 61, 17);
		lblDomain.setText("domain");
		
		Label lblOs = new Label(shlNssa, SWT.NONE);
		lblOs.setBounds(10, 56, 61, 17);
		lblOs.setText("os");
		
		Label lblCpu = new Label(shlNssa, SWT.NONE);
		lblCpu.setBounds(10, 79, 61, 17);
		lblCpu.setText("cpu");
		
		Label lblMemory = new Label(shlNssa, SWT.NONE);
		lblMemory.setBounds(10, 102, 61, 17);
		lblMemory.setText("memory");
		
		Label lblDisk = new Label(shlNssa, SWT.NONE);
		lblDisk.setBounds(10, 125, 61, 17);
		lblDisk.setText("disk");
		
		Label lblDetail = new Label(shlNssa, SWT.NONE);
		lblDetail.setBounds(10, 148, 61, 17);
		lblDetail.setText("detail");
		
		textname = new Text(shlNssa, SWT.BORDER);
		textname.setBounds(77, 10, 333, 23);
		
		textdomain = new Text(shlNssa, SWT.BORDER);
		textdomain.setBounds(77, 33, 333, 23);
		
		textos = new Text(shlNssa, SWT.BORDER);
		textos.setText("");
		textos.setBounds(77, 56, 333, 23);
		
		textcpu = new Text(shlNssa, SWT.BORDER);
		textcpu.setText("");
		textcpu.setBounds(77, 79, 333, 23);
		
		textmemory = new Text(shlNssa, SWT.BORDER);
		textmemory.setText("");
		textmemory.setBounds(77, 102, 333, 23);
		
		textdisk = new Text(shlNssa, SWT.BORDER);
		textdisk.setBounds(77, 125, 333, 23);
		
		textdetail = new Text(shlNssa, SWT.BORDER);
		textdetail.setBounds(77, 148, 333, 23);
		
		shlNssa.open();
		shlNssa.layout();
		while (!shlNssa.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
