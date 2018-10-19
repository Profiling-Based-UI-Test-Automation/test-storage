package teststorage.userInput;

public class ApplicationInfoInput {
	
	private String appName;
	
	private String companyName;
	
	
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
		return "ApplicationInfo [ appName = " + appName + ", companyName = " + companyName  + "]";	
	}
	


}