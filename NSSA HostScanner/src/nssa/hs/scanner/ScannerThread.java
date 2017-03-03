package nssa.hs.scanner;

public class ScannerThread extends Thread {
	
	private String sip;
	private String eip;
	private int sport;
	private int eport;
	
	public ScannerThread(String sip, String eip, int sport, int eport) {
		this.sip = sip;
		this.eip = eip;
		this.sport = sport;
		this.eport = eport;
	}

	@Override
	public void run() {
		while(true) {
			ScannerCore scanner = new ScannerCore();
			scanner.scan(sip, eip, sport, eport);
		}
	}

}
