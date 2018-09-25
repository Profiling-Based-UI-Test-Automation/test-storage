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
import teststorage.model.TCInfo;
import teststorage.service.ApkInfoService;
import teststorage.service.ApplicationInfoService;
import teststorage.service.TCInfoService;

@RestController
@RepositoryRestController
@Api(value="/ApkController", description="testcases 정보를 저장, 수정, 삭제, 읽을 수 있다.")
public class TCfilesController {
		
	@Autowired
	private TCInfoService tcInfoService;

    @ApiOperation(value="testcases 정보를 읽기위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.GET, value = "/tc/{tcId}", produces = "application/json")
	public @ResponseBody ResponseEntity<?> readTC(@PathVariable("tcId") ObjectId tcId) {
    		TCInfo tcInfo = null;
	    	try{ 
	    		tcInfo = tcInfoService.readTCInfo(tcId);
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
    			
	    	return ResponseEntity.ok(tcInfo);

	}
    

    @ApiOperation(value="testcases 정보를 저장하기위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.POST, value = "/tc/{apkId}")
	public @ResponseBody ResponseEntity<?> saveTCInfo( @PathVariable("apkId") ObjectId apkId,
			@RequestParam(value="tcfile", required=true) MultipartFile tcfile) {
	    	try {
	    		tcInfoService.insertTCInfo(apkId, tcfile);
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
	    		
	    	return ResponseEntity.ok("");
	}    
    @ApiOperation(value="testcases 정보를 삭제하기위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.DELETE, value = "/tc/{tcId}")
	public @ResponseBody ResponseEntity<?>  removeTCInfo(@PathVariable("tcId") ObjectId tcId) {
	    	try {
	    		tcInfoService.deleteTCInfo(tcId);
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
	    	
	    	return ResponseEntity.ok("");
	} 
    
    @ApiOperation(value="testcases 정보를 수정하기위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.PUT, value = "/tc/{tcId}")
	public @ResponseBody ResponseEntity<?> updateTCInfo(@PathVariable("tcId") ObjectId tcId,
			@RequestParam(value="tcfile", required=true) MultipartFile tcfile) {
		// find the test result//String _appId, ObjectId _apkId, MultipartFile _apkfile
	    	try {
	    		boolean ret = tcInfoService.updateTCInfo(tcId, tcfile);
	
	        	if(ret){
	    			return ResponseEntity.ok("");
	    		}
	        	else {
	        		return ResponseEntity
	        	            .status(HttpStatus.NO_CONTENT)
	        	            .body("There is no testcases or invalid tcfile");
	        	}
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
    	
	}    

}
