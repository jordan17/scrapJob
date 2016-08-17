package per.qoq.scrap.jobsdb.entity;

import java.util.Date;

public class SavedJobAnalyze extends Job {

	private Date lastHit;
	private int hitCount;
	private int max_id;
	
	public Date getLastHit() {
		return lastHit;
	}
	public void setLastHit(Date lastHit) {
		this.lastHit = lastHit;
	}
	public int getHitCount() {
		return hitCount;
	}
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}
	public int getMax_id() {
		return max_id;
	}
	public void setMax_id(int max_id) {
		this.max_id = max_id;
	}
	
}
