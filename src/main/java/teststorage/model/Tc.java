package teststorage.model;

import org.springframework.data.annotation.Id;


import lombok.Data;

@Data
public class Tc {
	@Id
	private String _id;
	
	private String tcId;
	
	private String tcFileName;
	
	private String apkId;
	
	private String versionNum;
	
	public String getTCId() {
		return tcId;
	}

	public void setTCId(String _tcId) {
		this.tcId = _tcId;
	}
	
	public String getTCFileName() {
		return tcFileName;
	}

	public void setTCFileName(String _tcFileName) {
		this.tcFileName = _tcFileName;
	}
	
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
		return "TCInfo [ tcId = " + tcId + ", versionNum = " + versionNum + ", tcFileName = " + tcFileName + ", apkId = " + apkId  + "]";	
	}
	
}