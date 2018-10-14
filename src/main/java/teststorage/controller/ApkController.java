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
import teststorage.service.ApkService;
import teststorage.service.ApplicationInfoService;

@RestController
@RepositoryRestController
@RequestMapping(value = "/apk")
@Api(value="/apk", description="특정 어플리케이션의 apk file을 저장, 수정, 삭제, 다운로드할 수 있다.")
public class ApkController {
		
	@Autowired
	private ApkService apkInfoService;
	
    @ApiOperation(value="앱의 모든 apk file의 버전 정보를 얻기 위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.GET, value = "/allversion/{appId}", produces = "application/json")
	public @ResponseBody ResponseEntity<?> readAllApkVersionInfo(@PathVariable("appId") String appId) {
    		List<ApkVersion> apkFullPath = null;
	    	try{ 
	    		apkFullPath = apkInfoService.readAllApkVersionInfo(appId);
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
	    	
	    	return ResponseEntity.ok()
	    			.header(HttpHeaders.CONTENT_DISPOSITION, "not ok")
	    			.body(apkFullPath);

	}
	
    @ApiOperation(value="앱의 최신 버전 apk file을 다운로드하기 위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.GET, value = "/lastversion/{appId}", produces = "application/json")
	public @ResponseBody ResponseEntity<?> readLastApk(@PathVariable("appId") String appId) {
    		Resource apkFullPath = null;
	    	try{ 
	    		apkFullPath = apkInfoService.readLastApk(appId);
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
	    	
	    	return ResponseEntity.ok()
	    			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + apkFullPath.getFilename() + "\"")
	    			.body(apkFullPath);

	}

    @ApiOperation(value="apk file을 다운로드하기 위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.GET, value = "/{apkId}", produces = "application/json")
	public @ResponseBody ResponseEntity<?> readApk(@PathVariable("apkId") String apkId) {
    		Resource apkFullPath = null;
	    	try{ 
	    		apkFullPath = apkInfoService.readApk(apkId);
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
	    	
	    	return ResponseEntity.ok()
	    			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + apkFullPath.getFilename() + "\"")
	    			.body(apkFullPath);

	}
    

    @ApiOperation(value="apk file을 저장하기 위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.POST, value = "/{appId}/{versionId}")
	public @ResponseBody ResponseEntity<?> saveApkInfo( @PathVariable("appId") String appId, @PathVariable("versionId") String versionId,
			@RequestParam(value="apkfile", required=true) MultipartFile apkfile) {
	    	try {
	    		System.out.println("saveApkInfo");
	    		apkInfoService.insertApk(appId, versionId, apkfile);
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
	    		
	    	return ResponseEntity.ok("");
	}    
    @ApiOperation(value="apk file을 삭제하기위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.DELETE, value = "/{apkId}")
	public @ResponseBody ResponseEntity<?>  removeApkInfo(@PathVariable("apkId") String apkId) {
	    	try {
	    		apkInfoService.deleteApk(apkId);
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
	    	
	    	return ResponseEntity.ok("");
	} 
    
    @ApiOperation(value="apk file을 업데이트 하기위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.PUT, value = "/{appId}/{versionId}")
	public @ResponseBody ResponseEntity<?> updateApkInfo(@PathVariable("appId") String appId, @PathVariable("versionId") String versionId,
			@RequestParam(value="apkfile", required=true) MultipartFile apkfile) {
	    	try {
	    		System.out.println("update ");
	    		boolean ret = apkInfoService.updateApkOfAppId(appId, versionId, apkfile);
	
	        	if(ret){
	    			return ResponseEntity.ok("");
	    		}
	        	else {
	        		return ResponseEntity
	        	            .status(HttpStatus.NO_CONTENT)
	        	            .body("There is no apk or invalid apkfile");
	        	}
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
    	
	}
    
    @ApiOperation(value="apk file을 업데이트 하기위한 인터페이스이다.")	
    @RequestMapping(method = RequestMethod.PUT, value = "/{apkId}")
	public @ResponseBody ResponseEntity<?> updateApkInfoByApkId(@PathVariable("apkId") String apkId,
			@RequestParam(value="apkfile", required=true) MultipartFile apkfile) {
	    	try {
	    		System.out.println("update ");
	    		boolean ret = apkInfoService.updateApkOfApkId(apkId, apkfile);
	
	        	if(ret){
	    			return ResponseEntity.ok("");
	    		}
	        	else {
	        		return ResponseEntity
	        	            .status(HttpStatus.NO_CONTENT)
	        	            .body("There is no apk or invalid apkfile");
	        	}
	    	}catch(Exception e){
	    		return ResponseEntity
	    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Exception happened : " + e.getMessage());
	    	}
    	
	}

}
