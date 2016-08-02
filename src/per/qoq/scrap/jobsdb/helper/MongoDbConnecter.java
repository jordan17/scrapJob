package per.qoq.scrap.jobsdb.helper;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import per.qoq.scrap.jobsdb.entity.Job;

public class MongoDbConnecter {

	private static MongoClient client= null;
	private static List<Job> jobList = null;
	private static Calendar jobTimeStamp = null;
	private static void connect() {
		MongoCredential credential = MongoCredential.createCredential("admin02", "admin", "Jordan17".toCharArray());
		ServerAddress address = new ServerAddress("localhost",27071);
		List<MongoCredential> cList = new ArrayList<MongoCredential>();
		cList.add(credential);
		client = new MongoClient(address,cList);
	}
	public MongoDbConnecter() {
		if(client==null) {
			connect();	
		}
	}

	public static List<Job> getTestDB() {
		if(client ==null) {
			connect();
		}
		if(jobList!=null) {
			return jobList;
		}
		if(jobTimeStamp != null) {
			Calendar current = Calendar.getInstance();
			current.add(Calendar.MINUTE, -10);
			if(current.before(jobTimeStamp)) {
				return jobList;
			}
		}
		MongoDatabase db = client.getDatabase("test");
		FindIterable<Document> documents = db.getCollection("jobsdb").find().limit(400);
		List<Job> jobs = new ArrayList<Job>();
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy",Locale.ENGLISH);
		
		documents.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				jobs.add(importJob(document));
			}
		});
		jobTimeStamp = Calendar.getInstance();
		setJobList(jobs);
		return jobs;
	}
	
	public static List<String> getCompanySet() {
		if(client ==null) {
			connect();
		}
		MongoDatabase db = client.getDatabase("test");
		MongoCollection<Document> coll =  db.getCollection("jobsdb");
		DistinctIterable<String> companys= coll.distinct("company", String.class);
		Set<String> jobs = new HashSet<String>();
		companys.forEach(new Block<String>() {
			@Override
			public void apply(String t) {
				jobs.add(t);
			}
		});
		List<String> list = new ArrayList<String>(jobs);
		Collections.sort(list);
		
		return list;
	}
	
	public static List<Job> filterByDate(Date dateBefore,Date dateAfter) {
		if(client ==null) {
			connect();
		}
		MongoDatabase db = client.getDatabase("test");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		BasicDBObject query = new BasicDBObject("datePosted",new BasicDBObject("$gte",dateAfter).append("$lte",dateBefore));
		/*MongoCollection<Document> coll =  db.getCollection("jobsdb");
		Document criteria_1 = new Document("datePosted",new Document("$gte","ISODate('"+df.format(dateAfter)+"')"));
		Document criteria_2 = new Document("datePosted",new Document("$lte",df.format(dateBefore)));
		*/
		FindIterable<Document> documents = db.getCollection("jobsdb").find(query);
		List<Job> jobs = new ArrayList<Job>();
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy",Locale.ENGLISH);
		
		documents.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				jobs.add(importJob(document));
			}
		});
		List<Job> jobss = jobs.parallelStream().sorted((a,b) -> a.getJobTtile().compareTo(b.getJobTtile())).collect(Collectors.toList());
		return jobss;
	}
	public static void main(String[] args) {
		MongoDbConnecter mongo = new MongoDbConnecter();
		//List<Job> jobs = mongo.getTestDB();
		mongo.getCompanySet();
		System.out.println("123");
	}
	public static List<Job> getJobList() {
		if(jobList==null) {
			getTestDB();
		}
		return jobList;
	}
	public static void setJobList(List<Job> jobList) {
		MongoDbConnecter.jobList = jobList;
	}
	
	private static Job importJob(Document document) {
		Job job = new Job();
		job.setCompany(document.getString("company"));
		Date date = document.getDate("datePosted");
		try {
			job.setDatePosted(date);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		job.setJobCate(document.getString("jobCate"));
		job.setJobTtile(document.getString("jobTtile"));
		job.setJobDesc(document.getString("jobDesc"));
		job.setUrl(document.getString("url"));
		job.setObjId(document.getObjectId("_id").toString());
		/*List<String>  strs = (List<String>) document.get("experience");
		if(strs !=null && strs.size()>0) {
			StringBuilder sb = new StringBuilder();
			for(String s:strs) {
				sb.append(s);
				sb.append("\n");
			}

			job.setExperience(sb.toString());
		}
*/		job.setExperience(document.getString("experience"));
		return job;
	}
}
