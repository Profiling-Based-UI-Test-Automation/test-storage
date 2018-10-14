package teststorage.service;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import teststorage.model.Apk;
import teststorage.model.ApkVersion;
import teststorage.model.Tc;
import teststorage.model.TcVersion;
import teststorage.repository.TCRepository;

@Service
public class TCService extends GridFSOperations{
	@Autowired
	private TCRepository repository;
	
	public List<TcVersion> readAllTcVersionInfo(String apkId){
		System.out.println("appId = " + apkId);
		
		List<TcVersion> findedinfo = this.repository.findAllByApkId(apkId);
		System.out.println("findedinfo = " + findedinfo);
		return findedinfo;

	}
	
	public Resource readLastTc(String apkId){
		System.out.println("apkId = " + apkId);
		
		Tc findedinfo = this.repository.findTopByOrderByVersionNumDesc(apkId);
		System.out.println("findedinfo = " + findedinfo);
		
		if(findedinfo != null) {	
			String version = findedinfo.getVersionNum();
			String tcId = findedinfo.getTCId();
			String tcDir = makeTcDirPath(apkId, version);	
			System.out.println("apkId = " + apkId + ", version = " + version + ", apkDir = " + tcDir);
			return loadFileAsResource(tcId, tcDir);
		}
		else 
			return null;

	}
	
	public void insertTC(String _apkId, String _version, MultipartFile tcfile){
		
		String fileName = tcfile.getName();
		String _tcId = null;
		try {
			_tcId = saveFiles(tcfile, fileName, GridFSOperations.TC_FILE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		copyTcFile(_apkId, _version, tcfile);
		
		if(_tcId != null){
			Tc tcInfo = new Tc();
			tcInfo.setTCFileName(fileName);
			tcInfo.setTCId(_tcId);
			tcInfo.setApkId(_apkId);
			tcInfo.setVersionNum(_version);
			this.repository.save(tcInfo);	
		}
		return;
	}
	
	public Resource readTC(String tcId){
		Tc findedinfo = this.repository.findByTcId(tcId);
		System.out.println("findedinfo = " + findedinfo);
		if(findedinfo != null) {
			String version = findedinfo.getVersionNum();
			String apkId = findedinfo.getApkId();
			String tcDir = makeTcDirPath(apkId, version);
			return loadFileAsResource(tcId, tcDir);
		}
		return null;
	}
	
	
	public boolean updateTC(String _tcId, String _version, MultipartFile _tcfile){
		Tc findedinfo = this.repository.findByTcId(_tcId);
		
		if(findedinfo != null){
			String fileName = _tcfile.getOriginalFilename();
			String apkId = findedinfo.getApkId();
			String tcId = null;
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
				Tc tcInfo = new Tc();
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
	
	public boolean updateTcOfApkId(String _apkId, String _version, MultipartFile _tcfile){
		Tc findedinfo = this.repository.findByApkIdAndVersionNum(_apkId, _version);
		System.out.println("findedinfo = " + findedinfo);
		if(findedinfo != null){
			
			String tcId = findedinfo.getTCId();
			try {
				deleteFile(tcId);
				deleteTcFile(_apkId, _version, findedinfo.getTCFileName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String fileName = _tcfile.getName();
			
			try {
				tcId = saveFiles(_tcfile, fileName, GridFSOperations.TC_FILE);
				System.out.println("tcId = " + tcId);
				copyTcFile(_apkId, _version, _tcfile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(tcId != null){
				findedinfo.setTCFileName(fileName);;
				findedinfo.setVersionNum(_version);
				findedinfo.setTCId(tcId);
				this.repository.save(findedinfo);	
				return true;
			}
			else {
				deleteFile(tcId);
				deleteTcFile(_apkId, _version, findedinfo.getTCFileName());
	    		return false;
	    	}
		}
		else {
			return false;
		}
	}
	
	public boolean updateTcOfTcId(String _tcId, MultipartFile _tcfile){
		Tc findedinfo = this.repository.findByTcId(_tcId);
		System.out.println("findedinfo = " + findedinfo);
		if(findedinfo != null){
			
			String apkId = findedinfo.getApkId();
			String _version = findedinfo.getVersionNum();
			
			try {
				deleteFile(_tcId);
				deleteTcFile(apkId, _version, findedinfo.getTCFileName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String fileName = _tcfile.getName();
			String tcId = null;
			
			
			try {
				tcId = saveFiles(_tcfile, fileName, GridFSOperations.TC_FILE);
				System.out.println("tcId = " + tcId);
				copyTcFile(apkId, _version, _tcfile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(tcId != null){
				findedinfo.setTCFileName(fileName);;
				findedinfo.setVersionNum(_version);
				findedinfo.setTCId(tcId);
				this.repository.save(findedinfo);	
				return true;
			}
			else {
				deleteFile(tcId);
				deleteTcFile(apkId, _version, findedinfo.getTCFileName());
	    		return false;
	    	}
		}
		else {
			return false;
		}
    	
	}
	
	public void deleteTC(String _tcId){
		Tc findedinfo = this.repository.findByTcId(_tcId);
		
		if(findedinfo != null){
			String version = findedinfo.getVersionNum();
			String fileName = findedinfo.getTCFileName();
			String apkId = findedinfo.getApkId();
			deleteTcFile(apkId, version, fileName);
			deleteFile(_tcId);
			this.repository.deleteByTcId(_tcId);
		}
		
		return ;
	}
	
}
