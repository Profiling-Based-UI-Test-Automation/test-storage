package teststorage.model;

import org.springframework.data.annotation.Id;


import lombok.Data;

@Data
public class TcVersion {
	@Id
	private String _id;
	
	private String tcId;

	private String versionNum;
	
	public String getTCId() {
		return tcId;
	}

	public void setTCId(String _tcId) {
		this.tcId = _tcId;
	}
		
	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String _version) {
		this.versionNum = _version;
	}
	
	
	@Override
	public String toString() {
		return "TCInfo [ tcId = " + tcId + ", versionNum = " + versionNum + "]";	
	}
	
}