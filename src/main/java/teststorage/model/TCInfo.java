package teststorage.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

@Data
public class TCInfo {
	@Id
	private String _id;
	
	private ObjectId tcId;
	
	private String tcFileName;
	
	private ObjectId apkId;
	
	private String versionNum;
	
	public ObjectId getTCId() {
		return tcId;
	}

	public void setTCId(ObjectId _tcId) {
		this.tcId = _tcId;
	}
	
	public String getTCFileName() {
		return tcFileName;
	}

	public void setTCFileName(String _tcFileName) {
		this.tcFileName = _tcFileName;
	}
	
	public ObjectId getApkId() {
		return apkId;
	}

	public void setApkId(ObjectId _apkId) {
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