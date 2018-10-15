package teststorage.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class TestResourceResult {
		
	@Id
	private String _id;
	private String testId;
	
	private ArrayList<HashMap<String, List>> resources = null;
	
	public String getTestId() {
		return testId;
	}

	public void setTestId(String id) {
		this.testId = id;
	}
	
	public void setResources(ArrayList<HashMap<String, List>> resource) {
		this.resources = resource;
	}

	
	public ArrayList<HashMap<String, List>> getResources(){
		return resources;	
	}
	
	@Override
	public String toString() {
		return "Result [ testid = " + testId + "]";	
	}
	


}