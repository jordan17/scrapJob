package per.qoq.scrap.jobsdb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import per.qoq.scrap.jobsdb.entity.Job;
import per.qoq.scrap.jobsdb.entity.SavedJob;
import per.qoq.scrap.jobsdb.helper.JobListAnalyser;
import per.qoq.scrap.jobsdb.helper.MongoDbConnecter;
import per.qoq.scrap.jobsdb.helper.SavedJobJDBCTemplate;
import per.qoq.scrap.jobsdb.model.FilterCompany;

/*
 * Author: QoQ Li
 * 
 */
@Controller
@SessionAttributes("jobList")
public class JobsDbHelloWorld {
	
	@RequestMapping("/welcome")
	public ModelAndView helloWorld() {
 
		/*String message = "<br><div style='text-align:center;'>"
				+ "<h3>********** Hello World,I am QoQ</h3>This message is coming from CrunchifyHelloWorld.java **********</div><br><br>";
		*/
		
		List<Job> message =MongoDbConnecter.getTestDB();
		ModelAndView view = new ModelAndView("/welcome");
		view.addObject("jobList", message);
		
		return view;
	}
	
	@RequestMapping("/test1")
	public ModelAndView testTable(Model model) {
		 
		/*String message = "<br><div style='text-align:center;'>"
				+ "<h3>********** Hello World,I am QoQ</h3>This message is coming from CrunchifyHelloWorld.java **********</div><br><br>";
		*/
		
		List<Job> message =MongoDbConnecter.getTestDB();
		FilterCompany form = new FilterCompany();
		ModelAndView view = new ModelAndView("/test1");
		
		List<String> companys = MongoDbConnecter.getCompanySet();
		Map<String,Integer> companyCount = JobListAnalyser.getCompanyCount();
		view.addObject("companyList",companys);
		view.addObject("filterCompany", form);
		view.addObject("companyCount",companyCount);
		
		/* function of saved job start*/
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

	    /*SavedJobJDBCTemplate studentJDBCTemplate = 
	      (SavedJobJDBCTemplate)context.getBean("savedJobJDBCTemplate");
	    List<SavedJob> sjs = studentJDBCTemplate.getSavedJob();
	    Map<String,List<String>> company_jobMap = new HashMap<String,List<String>>();
	    for(SavedJob sj : sjs) {
	    	String company = sj.getCompany();
	    	String title = sj.getTitle();
	    	if(company_jobMap.get(company)==null) {
	    		List<String> titleList = new ArrayList<String>();
	    		titleList.add(title);
	    		company_jobMap.put(company,titleList);
	    	}
	    	else {
	    		List<String> titleList = (List<String>)company_jobMap.get(company);
	    		titleList.add(title);
	    		company_jobMap.put(company,titleList);
	    	}
	    }
	    
	    for(Job job:message) {
	    	if(company_jobMap.containsKey(job.getCompany())) {
	    		List<String> titleList = company_jobMap.get(job.getCompany());
	    		if(titleList.contains(job.getJobTtile())) {
	    			job.setSaved(true);
	    		}
	    	}
	    }*/
		Utils.getSavedJob(message);
	    /* function of saved job end */
	    model.addAttribute("jobList", message);
	    view.addObject("jobList", message);
	    return view;
	}
	
	@RequestMapping("/test2")
	public ModelAndView companyCount() {
		
		Map<String,Integer> companyCount = JobListAnalyser.getCompanyCount();
		ModelAndView view = new ModelAndView("/test2");
		view.addObject("companyList",companyCount);
		return view;
	}
	@RequestMapping("/test3")
	public ModelAndView mapTest() {
		
		//Map<String,Integer> companyCount = JobListAnalyser.getCompanyCount();
		ModelAndView view = new ModelAndView("/test3");
		//view.addObject("companyList",companyCount);
		return view;
	}
}
