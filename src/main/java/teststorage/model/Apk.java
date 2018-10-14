package teststorage.model;


import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Apk {
	@Id
	private String _id;
	
	private String apkId;
	
	private String apkFileName;
	
	private String appId;
	
	private String versionNum;
	
	public String getApkId() {
		return apkId;
	}

	public void setApkId(String _apkId) {
		this.apkId = _apkId;
	}
	
	public String getApkFileName() {
		return apkFileName;
	}

	public void setApkFileName(String _apkFileName) {
		this.apkFileName = _apkFileName;
	}
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String _appId) {
		this.appId = _appId;
	}
	
	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String _version) {
		this.versionNum = _version;
	}
	
	
	@Override
	public String toString() {
		return "ApkInfo [ apkId = " + apkId + ", apkFileName = " + apkFileName + ", versionNum = " + versionNum +  ", appId = " + appId  + "]";	
	}
	
}