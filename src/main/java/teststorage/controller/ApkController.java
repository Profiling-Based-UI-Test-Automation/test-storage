package teststorage.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import teststorage.model.ApkInfo;
import teststorage.model.ApplicationInfo;
import teststorage.service.ApkInfoService;
import teststorage.service.ApplicationInfoService;

@RestController
@RepositoryRestController
@Api(value="/ApkController", description="apk file 정보를 저장, 수정, 삭제, 읽을 수 있다.")
public class ApkController {
		
	@Autowired
	private ApkInfoService apkInfoService;

    @ApiOperation(value="apk file 정보를 읽기위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.GET, value = "/apk/{apkId}", produces = "application/json")
	public @ResponseBody ResponseEntity<?> readApk(@PathVariable("apkId") ObjectId apkId) {
    		ApkInfo apkInfo = null;
	    	try{ 
	    		apkInfo = apkInfoService.readApkInfo(apkId);
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
    			
	    	return ResponseEntity.ok(apkInfo);

	}
    

    @ApiOperation(value="apk file 정보를 저장하기위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.POST, value = "/apk/{appId}")
	public @ResponseBody ResponseEntity<?> saveApkInfo( @PathVariable("appId") String appId,
			@RequestParam(value="apkfile", required=true) MultipartFile apkfile) {
	    	try {
	    		apkInfoService.insertApkInfo(appId, apkfile);
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
	    		
	    	return ResponseEntity.ok("");
	}    
    @ApiOperation(value="apk file 정보를 삭제하기위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.DELETE, value = "/apk/{apkId}")
	public @ResponseBody ResponseEntity<?>  removeApkInfo(@PathVariable("apkId") ObjectId apkId) {
	    	try {
	    		apkInfoService.deleteApkInfo(apkId);
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
	    	
	    	return ResponseEntity.ok("");
	} 
    
    @ApiOperation(value="apk file 정보를 수정하기위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.PUT, value = "/apk/{appId}/{apkId}")
	public @ResponseBody ResponseEntity<?> updateApkInfo(@PathVariable("appId") String appId,
			@PathVariable("apkId") ObjectId apkId,
			@RequestParam(value="apkfile", required=true) MultipartFile apkfile) {
		// find the test result//String _appId, ObjectId _apkId, MultipartFile _apkfile
	    	try {
	    		boolean ret = apkInfoService.updateApkInfo(appId, apkId, apkfile);
	
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
