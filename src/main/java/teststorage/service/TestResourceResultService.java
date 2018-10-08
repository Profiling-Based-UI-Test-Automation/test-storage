package teststorage.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teststorage.model.TestDeviceLogResult;
import teststorage.model.TestResourceResult;
import teststorage.model.TestResult;
import teststorage.repository.TestDeviceLogResultRepository;
import teststorage.repository.TestResourceResultRepository;
import teststorage.repository.TestResultRepository;

@Service
public class TestResourceResultService {
	@Autowired
	private TestResourceResultRepository resultRespository;
	
	public void insertTestResourceResult(TestResourceResult result){
		this.resultRespository.save(result);	
		return;
	}
	
	public TestResourceResult readTestResourceResult(ObjectId testId){
		TestResourceResult findedresult = this.resultRespository.findByTestId(testId);
    	return findedresult;
	}
	
	public boolean updateTestResourceResult(TestResourceResult result){
		
		ObjectId objectId = result.getTestId();
		
		if(objectId != null) {
			TestResourceResult findedresult = this.resultRespository.findByTestId(objectId);
			
	    	if(findedresult != null){
	    		findedresult.setResources(result.getResources());
				this.resultRespository.save(findedresult);
				return true;
			}
		}
		return false;
	}
	
	public void deleteTestResourceResult(ObjectId testId){
		this.resultRespository.deleteByTestId(testId);
	}
	
}
