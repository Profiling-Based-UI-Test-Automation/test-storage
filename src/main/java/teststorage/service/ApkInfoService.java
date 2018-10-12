package teststorage.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

import teststorage.model.ApkInfo;
import teststorage.model.ApplicationInfo;
import teststorage.repository.ApkInfoRepository;
import teststorage.repository.ApplicationInfoRepository;
import org.springframework.core.io.Resource;

@Service
public class ApkInfoService extends GridFSOperations{
	@Autowired
	private ApkInfoRepository repository;
	
	public void insertApkInfo(String _appId, String _version, MultipartFile apkfile){
		
		String fileName = apkfile.getName();
		ObjectId _apkId = null;
		try {
			_apkId = saveFiles(apkfile, fileName, GridFSOperations.APK_FILE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		copyAppFile(_appId, _version, apkfile);
		
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
	
	public Resource readApkInfo(ObjectId apkId){
		ApkInfo findedinfo = this.repository.findByApkId(apkId);
		
		String appId = findedinfo.getAppId();		
		String fileName = findedinfo.getApkFileName();
		String version = findedinfo.getVersionNum();
		String apkFullPath = apkFullPath(appId, version, fileName);
		return loadFileAsResource(apkFullPath);

	}
	
	public boolean updateApkInfo(ObjectId _apkId, String _version, MultipartFile _apkfile){
		ApkInfo findedinfo = this.repository.findByApkId(_apkId);
		
		if(findedinfo != null){
			String fileName = _apkfile.getName();
			String appId = findedinfo.getAppId();

			ObjectId apkId = null;
			try {
				apkId = saveFiles(_apkfile, fileName, GridFSOperations.APK_FILE);
				copyAppFile(appId, _version, _apkfile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(apkId != null){
				deleteFile(_apkId);
				deleteAppFile(findedinfo.getAppId(), findedinfo.getVersionNum(), findedinfo.getApkFileName());
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
			if(apkId != null) {
				deleteAppFile(findedinfo.getAppId(), findedinfo.getVersionNum(), findedinfo.getApkFileName());
				deleteFile(apkId);
			}
			this.repository.deleteByApkId(_apkId);
		}
		
		return ;
	}
	
}
