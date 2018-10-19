package teststorage.userInput;

import java.util.List;
import org.springframework.data.annotation.Id;

public class TestInput {
	@Id
	private String _id;
		
	private String testName;
	
	private String testDescription;
	
	private String appId;
	
	private String apkId; 
	
	private String tcId;
	
	private List<String> testDevices;
	
	
	public TestInput(){

	}
	
	public String getAppId() {
		return appId;
	}

	private void setAppId(String _appId) {
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
		return "Test [ testName = " + testName + ", testDescription = " + testDescription + ", appId = " + appId   + ", apkId = " + apkId + ", tcId = " + tcId + ", devices = " + testDevices + "]";	
	}

}