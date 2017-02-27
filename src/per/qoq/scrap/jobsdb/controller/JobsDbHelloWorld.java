package per.qoq.scrap.jobsdb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import per.qoq.scrap.jobsdb.entity.Job;
import per.qoq.scrap.jobsdb.entity.SavedJob;
import per.qoq.scrap.jobsdb.entity.SavedJobAnalyze;
import per.qoq.scrap.jobsdb.enu.AgentEnum;
import per.qoq.scrap.jobsdb.helper.ExtractJobJdbcTemplate;
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
	
	static Logger logger = Logger.getLogger(JobsDbHelloWorld.class.getName());
	
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
	
	@RequestMapping("/home")
	public ModelAndView testTable(ModelMap modelView) {
		 
		//List<Job> message =MongoDbConnecter.getTestDB();
		logger.info("enter testTable");
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");
		
		
	    ExtractJobJdbcTemplate studentJDBCTemplate = 
	      (ExtractJobJdbcTemplate)context.getBean("extractedJobJDBCTemplate");
		
	    if(modelView.get("redirect")!=null) {
	    	return new ModelAndView("test1",modelView);
	    }
	    List<Job> message = studentJDBCTemplate.getExtractedJob();
		FilterCompany form = new FilterCompany();
		ModelAndView view = new ModelAndView("/test1");
		
		List<String> companys = MongoDbConnecter.getCompanySet();
		Map<String,Integer> companyCount = JobListAnalyser.getCompanyCount();
		Map model=  view.getModel();
		model.put("skillList", AgentEnum.getSkills());
		view.addObject("skillList", AgentEnum.getSkills());
		view.addObject("companyList",companys);
		view.addObject("filterCompany", form);
		view.addObject("companyCount",companyCount);
		
		/* function of saved job start*/
	   
		Utils.getSavedJob(message);
	    /* function of saved job end */
	    model.put("jobList", message);
	    view.addObject("jobList", message);
	    view.addAllObjects(model);
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
	@RequestMapping("/iReserveTable")
	public ModelAndView iReserveTable() {
		
		//Map<String,Integer> companyCount = JobListAnalyser.getCompanyCount();
		ModelAndView view = new ModelAndView("/iReserveTable");
		//view.addObject("companyList",companyCount);
		return view;
	}
	@RequestMapping("/SavedJobAnalyze")
	public ModelAndView analyzeSavedJob() {
		
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

	    SavedJobJDBCTemplate savedJobJDBCTemplate = 
	      (SavedJobJDBCTemplate)context.getBean("savedJobJDBCTemplate");
		ExtractJobJdbcTemplate extractedJobJDBCTemplate = 
				(ExtractJobJdbcTemplate)context.getBean("extractedJobJDBCTemplate");
		//Map<String,Integer> companyCount = JobListAnalyser.getCompanyCount();
		ModelAndView view = new ModelAndView("/SavedJobAnalyze");
		List<SavedJobAnalyze> resultList = savedJobJDBCTemplate.getSavedJobAnalyze();
		List<Integer> idList = resultList.stream().map(job -> job.getMax_id()).collect(Collectors.toList());
		Map<Integer,SavedJobAnalyze> resultSet =  resultList.stream().collect(Collectors.toMap(SavedJobAnalyze::getMax_id, Function.identity()));
		/*for(SavedJobAnalyze sja : resultList) {
			Job job = extractedJobJDBCTemplate.getJobById(sja.getMax_id());
			if(job!=null) sja.setUrl(job.getUrl());
		}*/
		List<Job> jobList = extractedJobJDBCTemplate.getJobByIdList(idList);
		for(Job job:jobList) {
			resultSet.get(job.getJobId()).setUrl(job.getUrl());
		}
		resultList = new ArrayList<SavedJobAnalyze>(resultSet.values());
		view.addObject("jobList",resultList);
		return view;
	}
	
}
