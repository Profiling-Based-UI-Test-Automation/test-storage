package teststorage.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class TestDeviceLogResult {
		
	@Id
	private String _id;
	private String testId;
	
	private ArrayList<String> log;
	
	public String getTestId() {
		return testId;
	}

	public void setTestId(String id) {
		this.testId = id;
	}
	
	public void setLog(ArrayList<String> log){
		this.log = log;
	}
	
	public ArrayList<String> getLog(){
		return log;
	}
	
	@Override
	public String toString() {
		return "Result [ testid = " + testId + "]";	
	}
	


}