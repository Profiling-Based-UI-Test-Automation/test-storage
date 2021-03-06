package teststorage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import teststorage.model.ApplicationInfo;
import teststorage.service.ApplicationInfoService;
import teststorage.userInput.ApplicationInfoInput;

@RestController
@RepositoryRestController
@RequestMapping(value = "/application")
@Api(value="/application", description="application 정보를 저장, 수정, 삭제, 읽을 수 있다.")
public class ApplicationController {
		
	@Autowired
	private ApplicationInfoService applicationInfoService;

    @ApiOperation(value="application 정보를 읽기위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.GET, value = "/{appId}")
	public @ResponseBody ResponseEntity<?> readApplication(@PathVariable("appId") String appId) {
    		ApplicationInfo applicationInfo = null;
	    	try{
	    		applicationInfo = applicationInfoService.readApplicationInfo(appId);
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
    			
	    	return ResponseEntity.ok(applicationInfo);

	}
    
    @ApiOperation(value="모든 application 정보를 읽기위한 인터페이스이다.")
    @RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<ApplicationInfo> getApplicationInfo() {	
    	return applicationInfoService.findAll();
	}  

    @ApiOperation(value="application 정보를 저장하기위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> saveApplicationInfo(@RequestBody ApplicationInfoInput info) {
	    	try {

	    		applicationInfoService.insertApplicationInfo(info.getAppName(), info.getCompanyName());
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
	    		
	    	return ResponseEntity.ok("");
	}    
    @ApiOperation(value="application 정보를 삭제하기위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.DELETE, value = "/{appId}")
	public @ResponseBody ResponseEntity<?>  removeApplicationInfo(@PathVariable("appId") String appId) {
	    	try {
	    		applicationInfoService.deleteApplicationInfo(appId);
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
	    	
	    	return ResponseEntity.ok("");
	} 
    
    @ApiOperation(value="application 정보를 수정하기위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateApplicationInfo(@RequestBody ApplicationInfo info) {
		// find the test result
    	try {
    		boolean ret = applicationInfoService.updateApplicationInfo(info);

        	if(ret){
    			return ResponseEntity.ok("");
    		}
        	else {
        		return ResponseEntity
        	            .status(HttpStatus.NO_CONTENT)
        	            .body("There is no test info which has the testId and deviceSerial");
        	}
    	}catch(Exception e){
    		return ResponseEntity
    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
    	            .body("Exception happened : " + e.getMessage());
    	}
    	
	}    

}
