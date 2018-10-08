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
public class ApkInfo {
	@Id
	private String _id;
	
	private ObjectId apkId;
	
	private String apkFileName;
	
	private String appId;
	
	private String versionNum;
	
	public ObjectId getApkId() {
		return apkId;
	}

	public void setApkId(ObjectId _apkId) {
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