package per.qoq.scrap.jobsdb.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.bson.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import per.qoq.scrap.jobsdb.entity.Job;

public class MongoDbToMySql {

	private static MongoClient client= null;
	private static List<Job> jobList = null;
	private static Calendar jobTimeStamp = null;
	 static int count=1;
	private static void connect() {
		MongoCredential credential = MongoCredential.createCredential("admin02", "admin", "Jordan17".toCharArray());
		ServerAddress address = new ServerAddress("localhost",27071);
		List<MongoCredential> cList = new ArrayList<MongoCredential>();
		cList.add(credential);
		client = new MongoClient(address,cList);
	}

	public static void main(String[] args) throws ParseException {
		if(client ==null) {
			connect();
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		
		String currentDate = df.format(Calendar.getInstance().getTime());
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DATE, -15);
		String weekBefore = df.format(ca.getTime());
		MongoDatabase db = client.getDatabase("test");
		BasicDBObject query = new BasicDBObject("datePosted",new BasicDBObject("$gte",df.parse(weekBefore)).append("$lte",df.parse(currentDate)));
		
		FindIterable<Document> documents = db.getCollection("jobsdb").find(query);
		List<Job> jobs = new ArrayList<Job>();
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy",Locale.ENGLISH);
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");
		
	    ExtractJobJdbcTemplate studentJDBCTemplate = 
	      (ExtractJobJdbcTemplate)context.getBean("extractedJobJDBCTemplate");
		documents.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				Job job = new Job();
				job.setCompany(document.getString("company"));
				//String date = document.getString("datePosted");
				//date = date.substring(0,date.lastIndexOf("-")+1) + "20" + date.substring(date.lastIndexOf("-")+1,date.length());
				Date date = document.getDate("datePosted");
				try {
					//job.setDatePosted(format.parse(date));
					job.setDatePosted(date);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				job.setJobCate(document.getString("jobCate"));
				job.setJobTtile(document.getString("jobTtile"));
				job.setJobDesc(document.getString("jobDesc"));
				job.setUrl(document.getString("url"));
				//job.setExperience(document.getString("experience"));
				//job.setObjId(document.getObjectId("_id").toString());
				studentJDBCTemplate.addSavedJob(job);
				System.out.println(count++);
			}
		});
		jobTimeStamp = Calendar.getInstance();
	}
}
