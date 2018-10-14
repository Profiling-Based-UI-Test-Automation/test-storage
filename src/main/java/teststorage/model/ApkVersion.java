package teststorage.model;


import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class ApkVersion {
	@Id
	private String _id;
	
	private String apkId;
	
	private String versionNum;
	
	public String getApkId() {
		return apkId;
	}

	public void setApkId(String _apkId) {
		this.apkId = _apkId;
	}
	
	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String _version) {
		this.versionNum = _version;
	}
	
	
	@Override
	public String toString() {
		return "ApkInfo [ apkId = " + apkId + ", versionNum = " + versionNum + "]";	
	}
	
}