package teststorage.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import teststorage.model.ApkInfo;
import teststorage.model.ApplicationInfo;
import teststorage.repository.ApkInfoRepository;
import teststorage.repository.ApplicationInfoRepository;

@Service
public class ApkInfoService {
	@Autowired
	private ApkInfoRepository repository;
	
	
	@Autowired
	GridFsOperations gridOperations;
	
	public ObjectId saveFiles(MultipartFile file, String name) throws FileNotFoundException {
		// Define metaData
		DBObject metaData = new BasicDBObject();
		/**
		 * 1. save an image file to MongoDB
		 */
		// Get input file
		InputStream fileStream = null;
		try {
			fileStream = file.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		metaData.put("type", "apkfile");
		// Store file to MongoDB
		ObjectId fileId = gridOperations.store(fileStream, name, "application/octet-stream", metaData);
		
		return fileId;
	}
	
	public boolean deleteFile(ObjectId apkId){
		// delete image via id
		gridOperations.delete(new Query(Criteria.where("_id").is(apkId)));
		
		return true;
	}
	
	public void insertApkInfo(String _appId, String _version, MultipartFile apkfile){
		
		String fileName = apkfile.getOriginalFilename();
		ObjectId _apkId = null;
		try {
			_apkId = saveFiles(apkfile, fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(_apkId != null){
			ApkInfo apkInfo = new ApkInfo();
			apkInfo.setApkFileName(fileName);
			apkInfo.setAppId(_appId);
			apkInfo.setApkId(_apkId);
			apkInfo.setVersionNum(_version);
			this.repository.save(apkInfo);	
		}

		return;
	}
	
	public ApkInfo readApkInfo(ObjectId apkId){
		ApkInfo findedinfo = this.repository.findByApkId(apkId);
		return findedinfo;
	}
	
	public boolean updateApkInfo(ObjectId _apkId, String _version, MultipartFile _apkfile){
		ApkInfo findedinfo = this.repository.findByApkId(_apkId);
		
		if(findedinfo != null){
			String fileName = _apkfile.getOriginalFilename();
			String appId = findedinfo.getAppId();
			ObjectId apkId = null;
			try {
				apkId = saveFiles(_apkfile, fileName);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(apkId != null){
				deleteFile(_apkId);
				findedinfo.setApkFileName(fileName);
				findedinfo.setVersionNum(_version);
				this.repository.save(findedinfo);	
				return true;
			}
			else {
	    			return false;
	    		}
		}
	    	else {
	    		return false;
	    	}	
    	
	}
	
	public void deleteApkInfo(ObjectId _apkId){
		ApkInfo findedinfo = this.repository.findByApkId(_apkId);
		
		if(findedinfo != null){
			ObjectId apkId = findedinfo.getApkId();
			if(apkId != null)
				deleteFile(apkId);
			this.repository.deleteByApkId(_apkId);
		}
		
		return ;
	}
	
}
