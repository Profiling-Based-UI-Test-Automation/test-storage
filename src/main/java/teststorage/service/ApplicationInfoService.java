package teststorage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teststorage.model.ApplicationInfo;
import teststorage.repository.ApplicationInfoRepository;

@Service
public class ApplicationInfoService {
	@Autowired
	private ApplicationInfoRepository repository;
	
	public void insertApplicationInfo(ApplicationInfo info){
		this.repository.save(info);		
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
	
}
