package teststorage.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teststorage.model.TestDeviceLogResult;
import teststorage.model.TestResult;
import teststorage.repository.TestDeviceLogResultRepository;
import teststorage.repository.TestResultRepository;

@Service
public class TestDeviceLogResultService {
	@Autowired
	private TestDeviceLogResultRepository resultRespository;
	
	public void insertTestDeviceLogResult(TestDeviceLogResult result){
		this.resultRespository.save(result);	
		return;
	}
	
	public TestDeviceLogResult readTestDeviceLogResult(ObjectId testId){
		TestDeviceLogResult findedresult = this.resultRespository.findByTestId(testId);
    	return findedresult;
	}
	
	public boolean updateTestDeviceLogResult(TestDeviceLogResult result){
		
		ObjectId objectId = result.getTestId();
		
		if(objectId != null) {
			TestDeviceLogResult findedresult = this.resultRespository.findByTestId(objectId);
			
	    	if(findedresult != null){
	    		findedresult.setLog(result.getLog());
				this.resultRespository.save(findedresult);
				return true;
			}
		}
		return false;
	}
	
	public void deleteTestDeviceLogResult(ObjectId testId){
		this.resultRespository.deleteByTestId(testId);
	}
	
}
