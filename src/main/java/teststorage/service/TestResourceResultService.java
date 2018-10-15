package teststorage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teststorage.model.TestResourceResult;
import teststorage.repository.TestResourceResultRepository;

@Service
public class TestResourceResultService {
	@Autowired
	private TestResourceResultRepository resultRespository;
	
	public void insertTestResourceResult(TestResourceResult result){
		this.resultRespository.save(result);	
		return;
	}
	
	public TestResourceResult readTestResourceResult(String testId){
		TestResourceResult findedresult = this.resultRespository.findByTestId(testId);
    	return findedresult;
	}
	
	public boolean updateTestResourceResult(TestResourceResult result){
		
		String objectId = result.getTestId();
		
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
	
	public void deleteTestResourceResult(String testId){
		this.resultRespository.deleteByTestId(testId);
	}
	
}
