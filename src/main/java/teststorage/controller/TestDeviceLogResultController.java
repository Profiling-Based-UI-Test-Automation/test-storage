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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import teststorage.model.TestDeviceLogResult;
import teststorage.service.TestDeviceLogResultService;


@RepositoryRestController
@RestController
@RequestMapping(value = "/devicelog")
@Api(value="/TestDeviceLogResultController", description="Test Runner가 실행완료 할때 실행 결과(디바이스 로그)정보를 몽고 디비에 전송한다. 실행 결과 정보를 저장, 수정, 삭제, 읽을 수 있다.")
public class TestDeviceLogResultController {
		
	@Autowired
	private TestDeviceLogResultService deviceLogResultService;
	
    @ApiOperation(value="테스트 실행 디바이스 로그 결과 정보를 읽기위한 인터페이스이다.")
    @RequestMapping(method = RequestMethod.GET, value = "/{testId}")
	public @ResponseBody ResponseEntity<?> readTestDeviceLogResult(@PathVariable("testId") ObjectId testId) {
    	
    	TestDeviceLogResult result = null;
    	
    	try{
    		result = deviceLogResultService.readTestDeviceLogResult(testId);
    	}catch(Exception e){
    		return ResponseEntity
    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
    	            .body("Exception happened : " + e.getMessage());
    	}
    	
    	return ResponseEntity.ok(result);

	}

    @ApiOperation(value="테스트 실행 디바이스 로그 결과 정보를 저장하기위한 인터페이스이다.")
    @RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> saveTestDeviceLogResult(@RequestBody TestDeviceLogResult result) {
    	try {
    		deviceLogResultService.insertTestDeviceLogResult(result);
    	}catch(Exception e){
    		return ResponseEntity
    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
    	            .body("Exception happened : " + e.getMessage());
    	}
    	
    	return ResponseEntity.ok("saveTestResult");
	}  
    
    @ApiOperation(value="테스트 실행 디바이스 로그 결과 정보를 삭제하기위한 인터페이스이다.")
    @RequestMapping(method = RequestMethod.DELETE, value = "{testId}")
	public @ResponseBody ResponseEntity<?>  removeTestDeviceLogResult(@PathVariable("testId") ObjectId testId) {
    	try {
    		deviceLogResultService.deleteTestDeviceLogResult(testId);
    	}catch(Exception e){
    		return ResponseEntity
    	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
    	            .body("Exception happened : " + e.getMessage());
    	}
    	    	
    	return ResponseEntity.ok("removeTestResult");
	} 
    
    @ApiOperation(value="테스트 실행 디바이스 로그 결과 정보를 수정하기위한 인터페이스이다.")
    @RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateTestDeviceLogResult(@RequestBody TestDeviceLogResult result) {
		// find the test result
    	try {
    		boolean ret = deviceLogResultService.updateTestDeviceLogResult(result);

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
