package nssa.uc.vo;

public class SecuritySituation {

	private String time;
	private float asset;
	private float risk;
	private float threat;
	private float situation;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public float getAsset() {
		return asset;
	}
	public void setAsset(float asset) {
		this.asset = asset;
	}
	public float getRisk() {
		return risk;
	}
	public void setRisk(float risk) {
		this.risk = risk;
	}
	public float getThreat() {
		return threat;
	}
	public void setThreat(float threat) {
		this.threat = threat;
	}
	public float getSituation() {
		return situation;
	}
	public void setSituation(float situation) {
		this.situation = situation;
	}
	
}
