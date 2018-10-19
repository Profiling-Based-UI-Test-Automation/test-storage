package teststorage.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Test {
	@Id
	private String _id;
	
	private String testId;
	
	private String testName;
	
	private String testDescription;
	
	private String appId;
	
	private String apkId; 
	
	private String tcId;
	
	private LocalDateTime registerDate;
	
	private List<String> testDevices;
	
	public Test(){
		testId = UUID.randomUUID().toString();	
		registerDate = LocalDateTime.now();
	}
	
	public String getTestId() {
		return testId;
	}

	private void setTestId(String testId) {
		this.testId = testId;
	}
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String _appId) {
		this.appId = _appId;
	}
	
	public String getTestName() {
		return testName;
	}

	public void setTestName(String _testName) {
		this.testName = _testName;
	}
	
	public String getTestDescription() {
		return testDescription;
	}

	public void setTestDescription(String _testDescription) {
		this.testDescription = _testDescription;
	}
	
	public LocalDateTime getRegDate() {
	    return registerDate;
	}
	public void setRegDate(LocalDateTime regDate) {
	    this.registerDate = regDate;
	}

	public String getApkId() {
		return apkId;
	}

	public void setApkId(String _apkId) {
		this.apkId = _apkId;
	}
	
	public String getTcId() {
		return tcId;
	}

	public void setTcId(String _tcId) {
		this.tcId = _tcId;
	}	
	
	public List<String> getTestDevices() {
		return testDevices;
	}

	public void setTestDevices(List<String> serials) {
		this.testDevices = serials;
	}	
	
	
	@Override
	public String toString() {
		return "Test [ testId = " + testId + ", testName = " + testName + ", testDescription = " + testDescription + ", appId = " + appId   + ", apkId = " + apkId + ", tcId = " + tcId + ", registerDate = " + registerDate + ", devices = " + testDevices +  "]";	
	}

}