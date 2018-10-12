package teststorage.controller;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import teststorage.service.TestSnapshotResultService;

@RepositoryRestController
@RestController
@RequestMapping(value = "/snapshot")
@Api(value="/TestResultSnapshotController", description="Test Runner가 테스트 실행완료 할때 실행 결과(스크린샷)  정보를 몽고 디비에 전송한다. 실행 결과 정보를 저장, 수정, 삭제, 읽을 수 있다.")
public class TestResultSnapshotController {
		
	@Autowired
	private TestSnapshotResultService resultService;
	
    @ApiOperation(value="테스트 실행 스크린샷 정보를 읽기위한 인터페이스이다.")
    @RequestMapping(method = RequestMethod.GET, value = "/{testId}")
	public @ResponseBody ResponseEntity<?> readTestResult(@PathVariable("testId") ObjectId testId) {
    	
    	ArrayList<Resource>  result = null;
    	
    	try{
    		result = resultService.readSnapshotFiles(testId);
    	}catch(Exception e){
    		return ResponseEntity
    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
    	            .body("Exception happened : " + e.getMessage());
    	}
    	
    	return ResponseEntity.ok(result);

	}

    @ApiOperation(value="테스트 실행 스크린샷 정보를 저장하기위한 인터페이스이다.")
    @RequestMapping(method = RequestMethod.POST, value = "/{testId}")
	public @ResponseBody ResponseEntity<?> saveTestResult(@PathVariable("testId") ObjectId testId, @RequestParam(value="images", required=true) MultipartFile[] images) {
    	try {
    		resultService.insertSnapshotFiles(testId, images);
    	}catch(Exception e){
    		return ResponseEntity
    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
    	            .body("Exception happened : " + e.getMessage());
    	}
    	
    	return ResponseEntity.ok("saveTestResult");
	}  
    
    @ApiOperation(value="테스트 실행 스크린샷 정보를 삭제하기위한 인터페이스이다.")
    @RequestMapping(method = RequestMethod.DELETE, value = "/{testId}")
	public @ResponseBody ResponseEntity<?>  removeTestResult(@PathVariable("testId") ObjectId testId) {
    	try {
    		resultService.deleteSnapshotFile(testId);
    	}catch(Exception e){
    		return ResponseEntity
    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
    	            .body("Exception happened : " + e.getMessage());
    	}
    	    	
    	return ResponseEntity.ok("removeTestResult");
	} 
    
    @ApiOperation(value="테스트 실행 스크린샷 정보를 수정하기위한 인터페이스이다.")
    @RequestMapping(method = RequestMethod.PUT, value = "/{testId}")
	public @ResponseBody ResponseEntity<?> updateTestResult(@PathVariable("testId") ObjectId testId, @RequestParam(value="images", required=true) MultipartFile[] images) {
		// find the test result
    	try {
    		boolean ret = resultService.updateSnapshotFiles(testId, images);

        	if(ret){
    			return ResponseEntity.ok("");
    		}
        	else {
        		return ResponseEntity
        	            .status(HttpStatus.NO_CONTENT)
        	            .body("There is no test info which has the testId");
        	}
    	}catch(Exception e){
    		return ResponseEntity
    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
    	            .body("Exception happened : " + e.getMessage());
    	}
    	
	}    

}
