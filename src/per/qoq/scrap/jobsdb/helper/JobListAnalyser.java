package per.qoq.scrap.jobsdb.helper;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import per.qoq.scrap.jobsdb.entity.Job;

public class JobListAnalyser {

	private static Calendar jobTimeStamp = null;
	private static Map<String,Integer> companyCount = null;
	
	public static Map<String,Integer> getCompanyCount() {
		
		if(jobTimeStamp != null) {
			Calendar current = Calendar.getInstance();
			current.add(Calendar.MINUTE, -10);
			if(current.before(jobTimeStamp)&&companyCount!=null) {
				return companyCount;
			}
		}
		 companyCount = new TreeMap<String,Integer>();
		List<Job> message = MongoDbConnecter.getJobList();
		for(Job job:message) {
			companyCount.put(job.getCompany(),(companyCount.get(job.getCompany())==null?0:companyCount.get(job.getCompany()))+1 );
		}
		Map<String,Integer> tempCount = new TreeMap<String,Integer>(companyCount);
		for(Entry<String,Integer> entry:tempCount.entrySet()) {
			if(entry.getValue()==1) {
				companyCount.remove(entry.getKey());
			}
		}
		jobTimeStamp =Calendar.getInstance();
		return companyCount;
	}
	
	public static Map<String,Integer> getCompanyCount(List<Job> jobList) {
		companyCount = new TreeMap<String,Integer>();
		for(Job job:jobList) {
			companyCount.put(job.getCompany(),(companyCount.get(job.getCompany())==null?0:companyCount.get(job.getCompany()))+1 );
		}
		Map<String,Integer> tempCount = new TreeMap<String,Integer>(companyCount);
		for(Entry<String,Integer> entry:tempCount.entrySet()) {
			if(entry.getValue()==1) {
				companyCount.remove(entry.getKey());
			}
		}
		return companyCount;
	}
}
