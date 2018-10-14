package teststorage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpHeaders;
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
import teststorage.model.Apk;
import teststorage.model.ApkVersion;
import teststorage.model.ApplicationInfo;
import teststorage.model.TcVersion;
import teststorage.service.ApkService;
import teststorage.service.ApplicationInfoService;
import teststorage.service.TCService;

@RestController
@RepositoryRestController
@RequestMapping(value = "/tc")
@Api(value="/tc", description="test script file을 저장, 업데이트, 삭제, 다운로드할 수 있다.")
public class TCController {
		
	@Autowired
	private TCService tcInfoService;
	
    @ApiOperation(value="apk file의 모든 test script 의 버전 정보를 얻기 위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.GET, value = "/allversion/{apkId}", produces = "application/json")
	public @ResponseBody ResponseEntity<?> readAllTcVersionInfo(@PathVariable("apkId") String apkId) {
    		List<TcVersion> tcVersionInfo = null;
	    	try{ 
	    		tcVersionInfo = tcInfoService.readAllTcVersionInfo(apkId);
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
	    	
	    	return ResponseEntity.ok()
	    			.header(HttpHeaders.CONTENT_DISPOSITION, "can't get tc list")
	    			.body(tcVersionInfo);

	}
	
    @ApiOperation(value="apk file의 최신 버전 test script을 다운로드하기 위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.GET, value = "/lastversion/{apkId}", produces = "application/json")
	public @ResponseBody ResponseEntity<?> readLastTc(@PathVariable("apkId") String apkId) {
    		Resource tc = null;
	    	try{ 
	    		tc = tcInfoService.readLastTc(apkId);
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
	    	
	    	return ResponseEntity.ok()
	    			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + tc+ "\"")
	    			.body(tc);

	}

    @ApiOperation(value="test script을 다운로드하기 위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.GET, value = "/{tcId}", produces = "application/json")
	public @ResponseBody ResponseEntity<?> readTc(@PathVariable("tcId") String tcId) {
    	Resource tc = null;
	    try{ 
	    	tc = tcInfoService.readTC(tcId);
	    }catch(Exception e){
	    	return ResponseEntity
	    			.status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	        .body("Exception happened : " + e.getMessage());
	    }
	    	
	    return ResponseEntity.ok()
	    			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + tc + "\"")
	    			.body(tc);

	}
    

    @ApiOperation(value="test script을 저장하기 위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.POST, value = "/{apkId}/{versionId}")
	public @ResponseBody ResponseEntity<?> saveTcInfo( @PathVariable("apkId") String apkId, @PathVariable("versionId") String versionId,
			@RequestParam(value="tcfile", required=true) MultipartFile tcfile) {
	    	try {
	    		System.out.println("savetcInfo");
	    		tcInfoService.insertTC(apkId, versionId, tcfile);
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
	    		
	    	return ResponseEntity.ok("");
	}    
    @ApiOperation(value="test script을 삭제하기위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.DELETE, value = "/{tcId}")
	public @ResponseBody ResponseEntity<?>  removeTcInfo(@PathVariable("tcId") String tcId) {
	    	try {
	    		tcInfoService.deleteTC(tcId);
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
	    	
	    	return ResponseEntity.ok("");
	} 
    
    @ApiOperation(value="test script을 업데이트 하기위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.PUT, value = "/{apkId}/{versionId}")
	public @ResponseBody ResponseEntity<?> updateTCInfo(@PathVariable("apkId") String apkId, @PathVariable("versionId") String versionId,
			@RequestParam(value="tcfile", required=true) MultipartFile tcfile) {
	    	try {
	    		System.out.println("update ");
	    		boolean ret = tcInfoService.updateTcOfApkId(apkId, versionId, tcfile);
	
	        	if(ret){
	    			return ResponseEntity.ok("");
	    		}
	        	else {
	        		return ResponseEntity
	        	            .status(HttpStatus.NO_CONTENT)
	        	            .body("There is no apk or invalid tcfile");
	        	}
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
    	
	}
    
    @ApiOperation(value="test script을 업데이트 하기위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.PUT, value = "/{tcId}")
	public @ResponseBody ResponseEntity<?> updateTcInfoByApkId(@PathVariable("tcId") String tcId,
			@RequestParam(value="tcfile", required=true) MultipartFile tcfile) {
	    	try {
	    		System.out.println("update ");
	    		boolean ret = tcInfoService.updateTcOfTcId(tcId, tcfile);
	
	        	if(ret){
	    			return ResponseEntity.ok("");
	    		}
	        	else {
	        		return ResponseEntity
	        	            .status(HttpStatus.NO_CONTENT)
	        	            .body("There is no apk or invalid tcfile");
	        	}
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
    	
	}

}
