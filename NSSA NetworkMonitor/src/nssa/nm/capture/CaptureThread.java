package nssa.nm.capture;

public class CaptureThread extends Thread {
	
	private int num;
	
	public CaptureThread(int num) {
		this.num = num;
	}

	@Override
	public void run() {
		while(true) {
			CaptureCore.startCaptureAt(num);
		}
	}

}
