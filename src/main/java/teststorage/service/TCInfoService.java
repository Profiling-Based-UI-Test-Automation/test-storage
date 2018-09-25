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
import teststorage.model.TCInfo;
import teststorage.repository.ApkInfoRepository;
import teststorage.repository.ApplicationInfoRepository;
import teststorage.repository.TCInfoRepository;

@Service
public class TCInfoService {
	@Autowired
	private TCInfoRepository repository;
	
	
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
		
		metaData.put("type", "tcfile");
		// Store file to MongoDB
		ObjectId fileId = gridOperations.store(fileStream, name, "application/octet-stream", metaData);
		
		return fileId;
	}
	
	public boolean deleteFile(ObjectId tcId){
		// delete image via id
		gridOperations.delete(new Query(Criteria.where("_id").is(tcId)));
		
		return true;
	}
	
	public void insertTCInfo(ObjectId _apkId, MultipartFile tcfile){
		
		String fileName = tcfile.getOriginalFilename();
		ObjectId _tcId = null;
		try {
			_tcId = saveFiles(tcfile, fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(_tcId != null){
			TCInfo tcInfo = new TCInfo();
			tcInfo.setTCFileName(fileName);
			tcInfo.setTCId(_tcId);
			tcInfo.setApkId(_apkId);
			this.repository.save(tcInfo);	
		}

		return;
	}
	
	public TCInfo readTCInfo(ObjectId tcId){
		TCInfo findedinfo = this.repository.findByTcId(tcId);
		return findedinfo;
	}
	
	public boolean updateTCInfo(ObjectId _tcId, MultipartFile _tcfile){
		TCInfo findedinfo = this.repository.findByTcId(_tcId);
		
		if(findedinfo != null){
			String fileName = _tcfile.getOriginalFilename();
			ObjectId apkId = findedinfo.getApkId();
			ObjectId tcId = null;
			try {
				tcId = saveFiles(_tcfile, fileName);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(tcId != null){
				deleteFile(_tcId);
				TCInfo tcInfo = new TCInfo();
				tcInfo.setTCFileName(fileName);
				tcInfo.setTCId(tcId);
				tcInfo.setApkId(apkId);
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
			if(tcId != null)
				deleteFile(tcId);
			this.repository.deleteByTcId(_tcId);
		}
		
		return ;
	}
	
}
