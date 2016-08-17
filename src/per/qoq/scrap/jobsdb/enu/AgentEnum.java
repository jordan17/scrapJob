package per.qoq.scrap.jobsdb.enu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AgentEnum {
	static List<String> skills = new ArrayList<String>(Arrays.asList("Java","PHP","J2EE",".NET","C#"));

	public static List<String> getSkills() {
		return skills;
	}

	public static void setSkills(List<String> skills) {
		AgentEnum.skills = skills;
	}
}
