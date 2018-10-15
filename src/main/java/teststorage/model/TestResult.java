package teststorage.model;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;


@Data
public class TestResult {
		
	@Id
	private String _id;
	private String testId;
	
	private ArrayList<String> testlog;
	
	private ArrayList<String> testcaseResult;
	
	private List<HashMap<String, String>> testTime;
	
	private int totalTestCount;
	
	private int failCount;
	
	private int passCount;
	
	public String getTestId() {
		return testId;
	}

	public void setTestId(String id) {
		this.testId = id;
	}
	
	public void setLog(ArrayList<String> log){
		this.testlog = log;
	}
	
	public ArrayList<String> getLog(){
		return testlog;
	}
	
	public void setTestcaseResult(ArrayList<String> result){
		this.testcaseResult = result;
	}
	
	public ArrayList<String> getTestcaseResult(){
		return testcaseResult;
	}
	
	public void setTestTime(List<HashMap<String, String>> time){
		this.testTime = time;
	}
	
	public List<HashMap<String, String>> getTestTime(){
		return testTime;
	}
	
	public void setTotalTestCount(int count){
		this.totalTestCount = count;
	}
	
	public int getTotalTestCount(){
		return totalTestCount;
	}	
	
	public void setFailCount(int count){
		this.failCount = count;
	}
	
	public int getFailCount(){
		return failCount;
	}
	
	public void setPassCount(int count){
		this.passCount = count;
	}
	
	public int getPassCount(){
		return passCount;
	}
	
	@Override
	public String toString() {
		return "Result [ testid = " + testId + "]";	
	}
	


}