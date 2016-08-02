package per.qoq.scrap.jobsdb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import per.qoq.scrap.jobsdb.entity.HateJob;
import per.qoq.scrap.jobsdb.entity.Job;
import per.qoq.scrap.jobsdb.entity.SavedJob;
import per.qoq.scrap.jobsdb.helper.CompanySumJDBCTemplate;
import per.qoq.scrap.jobsdb.helper.SavedJobJDBCTemplate;
import per.qoq.scrap.jobsdb.hibernate.CompanySum;
import per.qoq.scrap.jobsdb.hibernate.CompanySumId;

public class Utils {

	static String regex = "<img\\s+[^>]*src="+'"'+"([^"+'"'+"]*)"+'"'+"[^>]*>";
	static void getSavedJob(List<Job> message) {
		/* function of saved job start*/
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

		SavedJobJDBCTemplate studentJDBCTemplate = 
			      (SavedJobJDBCTemplate)context.getBean("savedJobJDBCTemplate");
			    List<SavedJob> sjs = studentJDBCTemplate.getSavedJob();
			    Map<String,Set<String>> company_jobMap = new HashMap<String,Set<String>>();
			    Set<String> jobIdList = new HashSet<String>();
			    for(SavedJob sj : sjs) {
			    	jobIdList.add(sj.getJobId());
			    	if(company_jobMap.get(sj.getCompany())==null) {
			    		company_jobMap.put(sj.getCompany(), new HashSet<String>());
			    	}
			    	((Set<String>)company_jobMap.get(sj.getCompany())).add(sj.getTitle());
			    }
			    StringBuilder sb = new StringBuilder("");
			    for(Job job:message) {
			    	String jobDesc = job.getJobDesc();
			    	if(jobDesc.contains("counter.adcourier.com")) {
			    		job.setJobDesc(jobDesc.replaceAll(regex, ""));
			    	}
			    	/*
			    	if(jobIdList.contains(job.getObjId())) {
			    			job.setSaved(true);
			    	}
			    	*/
			    	if(company_jobMap.get(job.getCompany())!=null) {
			    		if(((Set<String>)company_jobMap.get(job.getCompany())).contains(job.getJobTtile())) {
			    			sb = new StringBuilder(job.getJobTtile());
			    			//sb.insert(0, "<span style='color:red'>");
			    			//sb.append("</span>");
			    			job.setJobTtile(sb.toString());
			    			job.setSaved(true);
			    		}
			    	}
			    }
			    getCompanySum(message);
			    getHatedJob(message);
	}
	static void getCompanySum(List<Job> message) {
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

		CompanySumJDBCTemplate studentJDBCTemplate = 
			      (CompanySumJDBCTemplate)context.getBean("CompanySumJDBCTemplate");
			    List<CompanySumId> sjs = studentJDBCTemplate.getSavedJob();
			    Map<String,Integer> map = new HashMap<String,Integer>();
			    for(CompanySumId cs:sjs) {
			    	if(cs.getSum().intValue() >=10)
			    		map.put(cs.getCompanyName(),cs.getSum().intValue());
			    }
			    for(Job job:message) {
			    	if(map.get(job.getCompany())!=null) {
			    		//job.setCompany("<span style='color:red'>"+job.getCompany()+"</span>");
			    		job.setManyJobs(true);
			    	}
			    }
	}
	static void getHatedJob(List<Job> message) {
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

		SavedJobJDBCTemplate studentJDBCTemplate = 
			      (SavedJobJDBCTemplate)context.getBean("savedJobJDBCTemplate");
			    List<HateJob> sjs = studentJDBCTemplate.getHatedJob();
			    Map<String,Set<String>> company_jobMap = new HashMap<String,Set<String>>();
			    Set<String> jobIdList = new HashSet<String>();
			    for(HateJob sj : sjs) {
			    	jobIdList.add(sj.getJobId());
			    	if(company_jobMap.get(sj.getCompany())==null) {
			    		company_jobMap.put(sj.getCompany(), new HashSet<String>());
			    	}
			    	((Set<String>)company_jobMap.get(sj.getCompany())).add(sj.getTitle());
			    }
			    StringBuilder sb = new StringBuilder("");
			    for(Job job:message) {
			    	/*
			    	if(jobIdList.contains(job.getObjId())) {
			    			job.setHated(true);
			    	}
			    	*/
			    	if(company_jobMap.get(job.getCompany())!=null) {
			    		if(((Set<String>)company_jobMap.get(job.getCompany())).contains(job.getJobTtile())) {
			    			sb = new StringBuilder(job.getJobTtile());
			    			//sb.insert(0, "<span style='color:blue'>");
			    			//sb.append("</span>");
			    			job.setJobTtile(sb.toString());
			    			job.setHated(true);
			    		}
			    	}
			    }
	}
}
