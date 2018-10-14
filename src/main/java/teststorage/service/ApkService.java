package teststorage.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import teststorage.model.Apk;
import teststorage.model.ApkVersion;
import teststorage.repository.ApkRepository;
import org.springframework.core.io.Resource;

@Service
public class ApkService extends GridFSOperations{
	@Autowired
	private ApkRepository repository;
	
	public void insertApk(String _appId, String _version, MultipartFile apkfile){
		
		String fileName = apkfile.getName();
		String _apkId = null;
		try {
			_apkId = saveFiles(apkfile, fileName, GridFSOperations.APK_FILE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		copyAppFile(_appId, _version, apkfile);
		
		if(_apkId != null){
			Apk apkInfo = new Apk();
			apkInfo.setApkFileName(fileName);
			apkInfo.setAppId(_appId);
			apkInfo.setApkId(_apkId);
			apkInfo.setVersionNum(_version);
			this.repository.save(apkInfo);	
		}
		

		return;
	}

	public Resource readApk(String apkId){
		Apk findedinfo = this.repository.findByApkId(apkId);
		System.out.println("findedinfo = " + findedinfo);
		String appId = findedinfo.getAppId();		
		String version = findedinfo.getVersionNum();
		String apkDir = makeApkDirPath(appId, version);		
		return loadFileAsResource(apkId, apkDir);

	}
	
	public Resource readLastApk(String appId){
		System.out.println("appId = " + appId);
		
		Apk findedinfo = this.repository.findTopByOrderByVersionNumDesc(appId);
		System.out.println("findedinfo = " + findedinfo);
		
		if(findedinfo != null) {
			String apkId = findedinfo.getApkId();		
			String version = findedinfo.getVersionNum();
			String apkDir = makeApkDirPath(appId, version);	
			System.out.println("apkId = " + apkId + ", version = " + version + ", apkDir = " + apkDir);
			return loadFileAsResource(apkId, apkDir);
		}
		else 
			return null;

	}
	
	public List<ApkVersion> readAllApkVersionInfo(String appId){
		System.out.println("appId = " + appId);
		
		List<ApkVersion> findedinfo = this.repository.findAllByAppId(appId);
		System.out.println("findedinfo = " + findedinfo);
		
//		if(findedinfo != null) {
//			String apkId = findedinfo.getApkId();		
//			String version = findedinfo.getVersionNum();
//			String apkDir = makeApkDirPath(appId, version);	
//			System.out.println("apkId = " + apkId + ", version = " + version + ", apkDir = " + apkDir);
//			return loadFileAsResource(apkId, apkDir);
//		}
//		else 
			return findedinfo;

	}
	
	public boolean updateApkOfApkId(String _apkId, MultipartFile _apkfile){
		Apk findedinfo = this.repository.findByApkId(_apkId);
		System.out.println("findedinfo = " + findedinfo);
		if(findedinfo != null){
			String appId = findedinfo.getAppId();
			String version = findedinfo.getVersionNum();

			String apkId = null;
			try {
				deleteFile(_apkId);
				deleteAppFile(appId, version, findedinfo.getApkFileName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String fileName = _apkfile.getName();
			try {
				apkId = saveFiles(_apkfile, fileName, GridFSOperations.APK_FILE);
				System.out.println("apkId = " + apkId);
				copyAppFile(appId, version, _apkfile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(apkId != null){
				findedinfo.setApkFileName(fileName);
				findedinfo.setVersionNum(version);
				findedinfo.setApkId(apkId);
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
	
	public boolean updateApkOfAppId(String _appId, String _version, MultipartFile _apkfile){
		Apk findedinfo = this.repository.findByAppIdAndVersionNum(_appId, _version);
		System.out.println("findedinfo = " + findedinfo);
		if(findedinfo != null){
			
			String apkId = findedinfo.getApkId();
			try {
				deleteFile(apkId);
				deleteAppFile(_appId, findedinfo.getVersionNum(), findedinfo.getApkFileName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String fileName = _apkfile.getName();
			
			try {
				apkId = saveFiles(_apkfile, fileName, GridFSOperations.APK_FILE);
				System.out.println("apkId = " + apkId);
				copyAppFile(_appId, _version, _apkfile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(apkId != null){
				findedinfo.setApkFileName(fileName);
				findedinfo.setVersionNum(_version);
				findedinfo.setApkId(apkId);
				this.repository.save(findedinfo);	
				return true;
			}
			else {
				deleteFile(apkId);
				deleteAppFile(_appId, findedinfo.getVersionNum(), findedinfo.getApkFileName());
	    		return false;
	    	}
		}
		else {
			return false;
		}
	}
	
	public void deleteApk(String _apkId){
		Apk findedinfo = this.repository.findByApkId(_apkId);
		
		if(findedinfo != null){
			String apkId = findedinfo.getApkId();
			if(apkId != null) {
				deleteAppFile(findedinfo.getAppId(), findedinfo.getVersionNum(), findedinfo.getApkFileName());
				deleteFile(apkId);
			}
			this.repository.deleteByApkId(_apkId);
		}
		
		return ;
	}
	
}
