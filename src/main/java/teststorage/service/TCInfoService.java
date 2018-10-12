package teststorage.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import teststorage.model.ApkInfo;
import teststorage.model.ApplicationInfo;
import teststorage.model.TCInfo;
import teststorage.repository.ApkInfoRepository;
import teststorage.repository.ApplicationInfoRepository;
import teststorage.repository.TCInfoRepository;

@Service
public class TCInfoService extends GridFSOperations{
	@Autowired
	private TCInfoRepository repository;
	
	public void insertTCInfo(ObjectId _apkId, String _version, MultipartFile tcfile){
		
		String fileName = tcfile.getName();
		ObjectId _tcId = null;
		try {
			_tcId = saveFiles(tcfile, fileName, GridFSOperations.TC_FILE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		copyTcFile(_apkId, _version, tcfile);
		
		if(_tcId != null){
			TCInfo tcInfo = new TCInfo();
			tcInfo.setTCFileName(fileName);
			tcInfo.setTCId(_tcId);
			tcInfo.setApkId(_apkId);
			tcInfo.setVersionNum(_version);
			this.repository.save(tcInfo);	
		}
		return;
	}
	
	public Resource readTCInfo(ObjectId tcId){
		TCInfo findedinfo = this.repository.findByTcId(tcId);
		ObjectId apkId = findedinfo.getApkId();
		if(apkId != null) {
			String fileName = findedinfo.getTCFileName();
			String version = findedinfo.getVersionNum();
			String tcFullPath = tcFullPath(apkId, version, fileName);
			return loadFileAsResource(tcFullPath);
		}
		return null;
	}
	
	
	public boolean updateTCInfo(ObjectId _tcId, String _version, MultipartFile _tcfile){
		TCInfo findedinfo = this.repository.findByTcId(_tcId);
		
		if(findedinfo != null){
			String fileName = _tcfile.getOriginalFilename();
			ObjectId apkId = findedinfo.getApkId();
			ObjectId tcId = null;
			try {
				tcId = saveFiles(_tcfile, fileName, GridFSOperations.TC_FILE);
				copyTcFile(apkId, _version, _tcfile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(tcId != null){
				deleteFile(_tcId);
				deleteTcFile(apkId, findedinfo.getVersionNum(), findedinfo.getTCFileName());
				TCInfo tcInfo = new TCInfo();
				tcInfo.setTCFileName(fileName);
				tcInfo.setTCId(tcId);
				tcInfo.setApkId(apkId);
				tcInfo.setVersionNum(_version);
				this.repository.save(tcInfo);	
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
	
	public void deleteTCInfo(ObjectId _tcId){
		TCInfo findedinfo = this.repository.findByTcId(_tcId);
		
		if(findedinfo != null){
			ObjectId tcId = findedinfo.getTCId();
			if(tcId != null) {
				deleteTcFile(findedinfo.getApkId(), findedinfo.getVersionNum(), findedinfo.getTCFileName());
				deleteFile(tcId);
			}
			this.repository.deleteByTcId(_tcId);
		}
		
		return ;
	}
	
}
