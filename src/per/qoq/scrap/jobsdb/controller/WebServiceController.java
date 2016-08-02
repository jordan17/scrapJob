package per.qoq.scrap.jobsdb.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import per.qoq.scrap.jobsdb.dao.HateJobDAO;
import per.qoq.scrap.jobsdb.dao.SavedJobDAO;
import per.qoq.scrap.jobsdb.dao.SavedJobDAOImpl;
import per.qoq.scrap.jobsdb.entity.HateJob;
import per.qoq.scrap.jobsdb.entity.Job;
import per.qoq.scrap.jobsdb.entity.SavedJob;
import per.qoq.scrap.jobsdb.helper.JobListAnalyser;
import per.qoq.scrap.jobsdb.helper.MongoDbConnecter;
import per.qoq.scrap.jobsdb.helper.SavedJobJDBCTemplate;
import per.qoq.scrap.jobsdb.hibernate.SavedJobs;
import per.qoq.scrap.jobsdb.model.FilterCompany;

@Controller
@RequestMapping(value="/service")
@SessionAttributes("jobList")
public class WebServiceController {
	
	@Autowired
	private SavedJobDAO savedJobDao;
	@Autowired
	private HateJobDAO hateJobDao;
	
	static Logger log = Logger.getLogger(WebServiceController.class.getName());
	
	@RequestMapping(value = "/filterCompany" ,method=RequestMethod.POST)
	public ModelAndView filterCompany(@ModelAttribute("filterCompany") FilterCompany user,Model model) {
		
		Map<String,Object> map = model.asMap();
		List<Job> message =null;
		
		if(map.get("jobList")!=null) {
			message = (List<Job>) map.get("jobList");
		}
		else {
			 message =MongoDbConnecter.getTestDB();
		}
		List<Job> filterJob = new ArrayList<Job>();
		ModelAndView view = new ModelAndView("/test1");
		List<String> list = new ArrayList<String>();
		for(String s:user.getCompanys()) {
			list.add(s);
		}
		for(Job job:message) {
			if(list.contains(job.getCompany())) {
				filterJob.add(job);
			}
		}
		view.addObject("jobList",filterJob);
		return view;
	}
	
	@RequestMapping(value = "/excludeCompany" ,method=RequestMethod.POST)
	public ModelAndView excludeCompany(@ModelAttribute("filterCompany") FilterCompany user,Model model) {
		
		Map<String,Object> map = model.asMap();
		List<Job> message =null;
		
		if(map.get("jobList")!=null) {
			message = (List<Job>) map.get("jobList");
		}
		else {
			 message =MongoDbConnecter.getTestDB();
		}
		List<Job> finalResult = new ArrayList<Job>(message);
		List<Job> filterJob = new ArrayList<Job>();
		
		
		ModelAndView view = new ModelAndView("/test1");
		List<String> list = new ArrayList<String>();
		for(String s:user.getCompanys()) {
			list.add(s);
		}
		for(Job job:message) {
			if(list.contains(job.getCompany())) {
				finalResult.remove(job);
			}
		}
		model.addAttribute("jobList",finalResult);
		view.addObject("jobList",finalResult);
		List<String> companys = MongoDbConnecter.getCompanySet();
		//view.addObject("companyList",companys);
		return view;
	}
	

	@RequestMapping(value = "/filterByDate" ,method=RequestMethod.POST)
	public ModelAndView filterByDate(@RequestParam String dateAfter,@RequestParam String dateBefore,Model model) throws ParseException {
		
		SimpleDateFormat simple = new SimpleDateFormat("MM/dd/yyyy",Locale.ENGLISH);
		Map<String,Object> map = model.asMap();
		List<Job> message =  MongoDbConnecter.filterByDate(simple.parse(dateBefore),simple.parse(dateAfter));
		Utils.getSavedJob(message);
		ModelAndView view = new ModelAndView("/test1");
		FilterCompany form = new FilterCompany();
		view.addObject("jobList",message);
		Map<String,Integer> companyCount = JobListAnalyser.getCompanyCount(message);
		Set<String> companys = new TreeSet<String>(companyCount.keySet());
		view.addObject("companyList",companys);
		model.addAttribute("jobList", message);
		view.addObject("filterCompany", form);
		view.addObject("companyCount",companyCount);
		return view;
	}
	
	@RequestMapping(value = "/saveJob" ,method=RequestMethod.POST)
	public @ResponseBody String saveJob(@RequestParam("objId") String objId,Model model) throws ParseException {
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

	    SavedJobJDBCTemplate studentJDBCTemplate = 
	      (SavedJobJDBCTemplate)context.getBean("savedJobJDBCTemplate");
		Map<String,Object> map = model.asMap();
		List<Job> jobs = (List<Job>) map.get("jobList");
		for(Job job:jobs) {
			if(job.getObjId().equals(objId)) {
				studentJDBCTemplate.addSavedJob(job);
				
				break;
			}
		}
		//http://www.ctgoodjobs.hk/english/search/clipsummary_process.asp?action=clip&iac=saved&iacType=num&job_id=04185316
		return "true";
	}
	
	@RequestMapping(value = "/deleteSaveJob" ,method=RequestMethod.POST)
	@ResponseBody
	public String deleteJob(@RequestParam String objId,Model model) throws ParseException {
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

	    SavedJobJDBCTemplate studentJDBCTemplate = 
	      (SavedJobJDBCTemplate)context.getBean("savedJobJDBCTemplate");
		Map<String,Object> map = model.asMap();
		List<Job> jobs = (List<Job>) map.get("jobList");
		for(Job job:jobs) {
			if(job.getObjId().equals(objId)) {
				studentJDBCTemplate.deleteSavedJob(job);
			}
		}
		return "true";
	}
	@RequestMapping(value = "/getSavedJobs" ,method=RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String getSavedJobs() throws JsonProcessingException {
		List<SavedJobs> list = savedJobDao.getSavedJobs();
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(list);
		return jsonString;
	}
	
	@RequestMapping(value = "/hateJob" ,method=RequestMethod.POST)
	public @ResponseBody String hateJob(@RequestParam("objId") String objId,Model model) throws ParseException {
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

	    SavedJobJDBCTemplate studentJDBCTemplate = 
	      (SavedJobJDBCTemplate)context.getBean("savedJobJDBCTemplate");
		Map<String,Object> map = model.asMap();
		List<Job> jobs = (List<Job>) map.get("jobList");
		for(Job job:jobs) {
			if(job.getObjId().equals(objId)) {
				studentJDBCTemplate.addHatedJob(job);
				break;
			}
		}
		//http://www.ctgoodjobs.hk/english/search/clipsummary_process.asp?action=clip&iac=saved&iacType=num&job_id=04185316
		return "true";
	}
	
	@RequestMapping(value = "/deleteHateJob" ,method=RequestMethod.POST)
	@ResponseBody
	public String deleteHateJob(@RequestParam String objId,Model model) throws ParseException {
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

	    SavedJobJDBCTemplate studentJDBCTemplate = 
	      (SavedJobJDBCTemplate)context.getBean("savedJobJDBCTemplate");
		Map<String,Object> map = model.asMap();
		List<Job> jobs = (List<Job>) map.get("jobList");
		for(Job job:jobs) {
			if(job.getObjId().equals(objId)) {
				studentJDBCTemplate.deleteHatedJob(job);
			}
		}
		return "true";
	}
	@RequestMapping(value = "/getHateJobs" ,method=RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String getHateJobs() throws JsonProcessingException {
		List<HateJob> list = hateJobDao.getHateJobs();
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(list);
		return jsonString;
	}
}

