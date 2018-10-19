package teststorage.model;

import java.util.UUID;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class ApplicationInfo {
	@Id
	private String _id;
	
	private String appId;
	
	private String appName;
	
	private String companyName;
	
	public ApplicationInfo(){
		appId = UUID.randomUUID().toString();
	}
	
	public String getAppId() {
		return appId;
	}

	private void setAppId(String _appId) {
		this.appId = _appId;
	}
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String _appName) {
		this.appName = _appName;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String _companyName) {
		this.companyName = _companyName;
	}
	
	
	@Override
	public String toString() {
		return "ApplicationInfo [ appId = " + appId + ", appName = " + appName + ", companyName = " + companyName  + "]";	
	}
	


}