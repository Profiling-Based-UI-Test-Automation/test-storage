package teststorage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teststorage.model.ApplicationInfo;
import teststorage.repository.ApplicationInfoRepository;

@Service
public class ApplicationInfoService {
	@Autowired
	private ApplicationInfoRepository repository;
	
	public void insertApplicationInfo(String _appName, String _companyName){
		
		ApplicationInfo applicationInfo = new ApplicationInfo();
		applicationInfo.setAppName(_appName);
		applicationInfo.setCompanyName(_companyName);
		
		this.repository.save(applicationInfo);		
		return;
	}
	
	public ApplicationInfo readApplicationInfo(String appId){
		ApplicationInfo findedinfo = this.repository.findByAppId(appId);
		return findedinfo;
	}
	
	public boolean updateApplicationInfo(ApplicationInfo info){
		ApplicationInfo findedinfo = this.repository.findByAppId(info.getAppId());
		
		if(findedinfo != null){
			findedinfo.setAppName(info.getAppName());
			findedinfo.setCompanyName(info.getCompanyName());
			this.repository.save(findedinfo);
			return true;
		}
	    	else {
	    		return false;
	    	}	
    	
	}
	
	public void deleteApplicationInfo(String appId){
		this.repository.deleteByAppId(appId);
		return ;
	}

	public List<ApplicationInfo> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
}
