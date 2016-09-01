package per.qoq.scrap.jobsdb.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import per.qoq.scrap.jobsdb.dao.HateJobDAO;
import per.qoq.scrap.jobsdb.dao.SavedJobDAO;
import per.qoq.scrap.jobsdb.dao.SavedJobDAOImpl;
import per.qoq.scrap.jobsdb.entity.HateJob;
import per.qoq.scrap.jobsdb.entity.Job;
import per.qoq.scrap.jobsdb.entity.SavedJob;
import per.qoq.scrap.jobsdb.enu.AgentEnum;
import per.qoq.scrap.jobsdb.helper.CompanySumJDBCTemplate;
import per.qoq.scrap.jobsdb.helper.ExtractJobJdbcTemplate;
import per.qoq.scrap.jobsdb.helper.JobListAnalyser;
import per.qoq.scrap.jobsdb.helper.MongoDbConnecter;
import per.qoq.scrap.jobsdb.helper.SavedJobJDBCTemplate;
import per.qoq.scrap.jobsdb.hibernate.SavedJobs;
import per.qoq.scrap.jobsdb.model.FilterCompany;

@Controller
@RequestMapping(value="/service")
@Scope("session")
@SessionAttributes("jobList")
public class WebServiceController {
	
	@Autowired
	private SavedJobDAO savedJobDao;
	@Autowired
	private HateJobDAO hateJobDao;
	
	
	static Logger log = Logger.getLogger(WebServiceController.class.getName());
	ApplicationContext context =  new ClassPathXmlApplicationContext("Beans.xml");

	
	@ModelAttribute("skillList")
	public List<String> getSkillList() {
		return AgentEnum.getSkills();
	}
	
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
	public ModelAndView filterByDate(@RequestParam String dateAfter,@RequestParam String dateBefore,ModelMap map) throws ParseException {
		
		SimpleDateFormat simple = new SimpleDateFormat("MM/dd/yyyy",Locale.ENGLISH);
		//SimpleDateFormat simple = new SimpleDateFormat("yyyyMMDD",Locale.ENGLISH);
		//Map<String,Object> map = model.asMap();
		//List<Job> message =  MongoDbConnecter.filterByDate(simple.parse(dateBefore),simple.parse(dateAfter));
		
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

	    ExtractJobJdbcTemplate studentJDBCTemplate = 
	      (ExtractJobJdbcTemplate)context.getBean("extractedJobJDBCTemplate");
	    Timestamp db = new Timestamp(simple.parse(dateBefore).getTime());
	    Timestamp da = new Timestamp(simple.parse(dateAfter).getTime());
	    List<Job> message = studentJDBCTemplate.filterByDate(simple.parse(dateBefore),simple.parse(dateAfter));
		Utils.getSavedJob(message);
		
		ModelAndView view = new ModelAndView("/test1");
		FilterCompany form = new FilterCompany();
		//view.addObject("jobList",message);
		
		Map<String,Integer> companyCount = JobListAnalyser.getCompanyCount(message);
		Set<String> companys = new TreeSet<String>(companyCount.keySet());
		//view.addObject("companyList",companys);
		map.addAttribute("companyList",companys);
		map.addAttribute("jobList", message);
		//view.addObject("filterCompany", form);
		//map.addAttribute("filterCompany", form);
		//view.addObject("companyCount",companyCount);
		map.addAttribute("companyCount",companyCount);
		view.addAllObjects(map);
		return view;
	}
	
	@RequestMapping(value = "/filterBySkill" ,method=RequestMethod.POST)
	public ModelAndView filterBySkill(@RequestParam String skill,Model model) throws ParseException {
		
		
		String[] skills = skill.split(",");
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

		CompanySumJDBCTemplate studentJDBCTemplate = 
	      (CompanySumJDBCTemplate)context.getBean("CompanySumJDBCTemplate");
		
		Map<String,Object> map = model.asMap();
		List<Job> message =null;
		
		if(map.get("jobList")!=null) {
			message = (List<Job>) map.get("jobList");
		}
		else {
			 message =MongoDbConnecter.getTestDB();
		}
		Map<Integer,Job> jobMap = new HashMap<Integer,Job>();
		List<Integer>	ids = new ArrayList<Integer>();
		for(Job job:message) {
			ids.add(job.getJobId());
			jobMap.put(job.getJobId(), job);
		}
		List<Integer> resultIds = studentJDBCTemplate.getSkillJob(ids, skills);
		List<Job> resultJobs = resultIds.stream().map(id -> jobMap.get(id)).collect(Collectors.toList());
		Utils.getSavedJob(resultJobs);
		ModelAndView view = new ModelAndView("/test1");
		FilterCompany form = new FilterCompany();
		view.addObject("jobList",resultJobs);
		Map<String,Integer> companyCount = JobListAnalyser.getCompanyCount(message);
		Set<String> companys = new TreeSet<String>(companyCount.keySet());
		view.addObject("companyList",companys);
		model.addAttribute("jobList", resultJobs);
		view.addObject("filterCompany", form);
		view.addObject("companyCount",companyCount);
		return view;
	}
	
	@RequestMapping(value = "/filterAgent" ,method=RequestMethod.POST)
	public ModelAndView filterAgent(@RequestParam(required=false) String true_False,@RequestParam(required=false) String PCCW,Model model) throws ParseException {
		
		Optional<String> pccw = Optional.ofNullable(PCCW);
		Optional<String> agent = Optional.ofNullable(true_False);
		boolean filter = false;
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

		CompanySumJDBCTemplate studentJDBCTemplate = 
	      (CompanySumJDBCTemplate)context.getBean("CompanySumJDBCTemplate");
		
		if(agent.orElse("false").equalsIgnoreCase("True")) filter = true;
		
		Map<String,Object> map = model.asMap();
		List<Job> message =null;
		
		if(map.get("jobList")!=null) {
			message = (List<Job>) map.get("jobList");
		}
		else {
			 message =MongoDbConnecter.getTestDB();
		}
		List<String> agentNames = studentJDBCTemplate.getAgentName();
		Set<String> agentSet = agentNames.stream().collect(Collectors.toSet());
		if(pccw.orElse("false").equalsIgnoreCase("true")) {
			agentSet.add("PCCW Solutions Ltd");
			agentSet.add("PCCW Solutions Limited");
		}
		List<Job> resultJobs = message.stream().filter(job -> !agentSet.contains(job.getCompany()) ).collect(Collectors.toList());
		Utils.getSavedJob(resultJobs);
		ModelAndView view = new ModelAndView("/test1");
		FilterCompany form = new FilterCompany();
		view.addObject("jobList",resultJobs);
		Map<String,Integer> companyCount = JobListAnalyser.getCompanyCount(message);
		Set<String> companys = new TreeSet<String>(companyCount.keySet());
		view.addObject("companyList",companys);
		model.addAttribute("jobList", resultJobs);
		view.addObject("filterCompany", form);
		view.addObject("companyCount",companyCount);
		return view;
	}
	@RequestMapping(value = "/filterAgentJSON" ,method=RequestMethod.POST)
	public @ResponseBody List<Job> filterAgentJSON(@RequestParam(required=false) String true_False,@RequestParam(required=false) String PCCW,Model model) throws ParseException {
		
		Optional<String> pccw = Optional.ofNullable(PCCW);
		Optional<String> agent = Optional.ofNullable(true_False);
		boolean filter = false;
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

		CompanySumJDBCTemplate studentJDBCTemplate = 
	      (CompanySumJDBCTemplate)context.getBean("CompanySumJDBCTemplate");
		
		if(agent.orElse("false").equalsIgnoreCase("True")) filter = true;
		
		Map<String,Object> map = model.asMap();
		List<Job> message =null;
		
		if(map.get("jobList")!=null) {
			message = (List<Job>) map.get("jobList");
		}
		else {
			 message =MongoDbConnecter.getTestDB();
		}
		List<String> agentNames = studentJDBCTemplate.getAgentName();
		Set<String> agentSet = agentNames.stream().collect(Collectors.toSet());
		if(pccw.orElse("false").equalsIgnoreCase("true")) {
			agentSet.add("PCCW Solutions Ltd");
			agentSet.add("PCCW Solutions Limited");
		}
		List<Job> resultJobs = message.stream().filter(job -> !agentSet.contains(job.getCompany()) ).collect(Collectors.toList());
		Utils.getSavedJob(resultJobs);
		FilterCompany form = new FilterCompany();
		model.addAttribute("jobList", resultJobs);
		return resultJobs;
	}
	@RequestMapping(value = "/saveJob" ,method=RequestMethod.POST)
	public @ResponseBody String saveJob(@RequestParam("objId") Integer objId,Model model) throws ParseException {
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

	    SavedJobJDBCTemplate studentJDBCTemplate = 
	      (SavedJobJDBCTemplate)context.getBean("savedJobJDBCTemplate");
		Map<String,Object> map = model.asMap();
		List<Job> jobs = (List<Job>) map.get("jobList");
		for(Job job:jobs) {
			if(job.getJobId()== objId) {
				studentJDBCTemplate.addSavedJob(job);
				
				break;
			}
		}
		//http://www.ctgoodjobs.hk/english/search/clipsummary_process.asp?action=clip&iac=saved&iacType=num&job_id=04185316
		return "true";
	}
	
	@RequestMapping(value = "/deleteSaveJob" ,method=RequestMethod.POST)
	@ResponseBody
	public String deleteJob(@RequestParam Integer objId,Model model) throws ParseException {
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

	    SavedJobJDBCTemplate studentJDBCTemplate = 
	      (SavedJobJDBCTemplate)context.getBean("savedJobJDBCTemplate");
		Map<String,Object> map = model.asMap();
		List<Job> jobs = (List<Job>) map.get("jobList");
		for(Job job:jobs) {
			if(job.getJobId() == objId) {
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
	public @ResponseBody String hateJob(@RequestParam("objId") Integer objId,Model model) throws ParseException {
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

	    SavedJobJDBCTemplate studentJDBCTemplate = 
	      (SavedJobJDBCTemplate)context.getBean("savedJobJDBCTemplate");
		Map<String,Object> map = model.asMap();
		List<Job> jobs = (List<Job>) map.get("jobList");
		for(Job job:jobs) {
			if(job.getJobId() == objId) {
				studentJDBCTemplate.addHatedJob(job);
				break;
			}
		}
		//http://www.ctgoodjobs.hk/english/search/clipsummary_process.asp?action=clip&iac=saved&iacType=num&job_id=04185316
		return "true";
	}
	
	@RequestMapping(value = "/deleteHateJob" ,method=RequestMethod.POST)
	@ResponseBody
	public String deleteHateJob(@RequestParam Integer objId,Model model) throws ParseException {
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

	    SavedJobJDBCTemplate studentJDBCTemplate = 
	      (SavedJobJDBCTemplate)context.getBean("savedJobJDBCTemplate");
		Map<String,Object> map = model.asMap();
		List<Job> jobs = (List<Job>) map.get("jobList");
		for(Job job:jobs) {
			if(job.getJobId() == objId) {
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
	
	@RequestMapping(value = "/getCompany/{company}" ,method=RequestMethod.GET)
	@ResponseBody
	@Transactional
	public List<Job> getCompany(@PathVariable(value="company") String company) throws JsonProcessingException {
		ExtractJobJdbcTemplate studentJDBCTemplate = (ExtractJobJdbcTemplate)context.getBean("extractedJobJDBCTemplate");
		
		List<Job> resultList = studentJDBCTemplate.getCompanyJob(company);
		return resultList;
	}
}

