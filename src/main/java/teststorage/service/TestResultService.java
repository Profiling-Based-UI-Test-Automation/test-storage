package teststorage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teststorage.model.TestResult;
import teststorage.repository.TestResultRepository;

@Service
public class TestResultService {
	@Autowired
	private TestResultRepository resultRespository;
	
	public void insertTestResult(TestResult result){
		this.resultRespository.save(result);	
		return;
	}
	
	public TestResult readTestResult(String testId){
		TestResult findedresult = this.resultRespository.findByTestId(testId);
    	return findedresult;
	}
	
	public boolean updateTestResult(TestResult result){
		
		String objectId = result.getTestId();
		
		if(objectId != null) {
			TestResult findedresult = this.resultRespository.findByTestId(objectId);
			
	    	if(findedresult != null){
	    		findedresult.setFailCount(result.getFailCount());
	    		findedresult.setLog(result.getLog());
	    		findedresult.setPassCount(result.getPassCount());
	    		findedresult.setTestcaseResult(result.getTestcaseResult());
	    		findedresult.setTestTime(result.getTestTime());
	    		findedresult.setTotalTestCount(result.getTotalTestCount());
				this.resultRespository.save(findedresult);
				return true;
			}
		}
		return false;
	}
	
	public void deleteTestResult(String testId){
		this.resultRespository.deleteByTestId(testId);
	}
	
}
