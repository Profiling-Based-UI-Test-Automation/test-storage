package teststorage.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import teststorage.model.Test;
import teststorage.repository.TestRegisterRepository;
import teststorage.userInput.TestInput;


@Service
public class TestRegisterService {
	@Autowired
	private TestRegisterRepository repository;

	public Test readTest(String testId) {
		// TODO Auto-generated method stub
		//Test test = null;
		Test test = repository.findByTestId(testId);
		return test;
	}

	public void insertTest(TestInput info) {
		// TODO Auto-generated method stub
		
		if(info != null) {
			Test test = new Test();
			test.setApkId(info.getApkId());
			test.setAppId(info.getAppId());
			test.setTcId(info.getTcId());
			test.setTestDescription(info.getTestDescription());
			test.setTestName(info.getTestName());
			test.setTestDevices(info.getTestDevices());
			repository.save(test);
		}
	}

	public boolean updateTest(String testId, Test info) {
		// TODO Auto-generated method stub
		
		Test test = repository.findByTestId(testId);
		
		if(test != null) {
			Test testUpdated = new Test();
			testUpdated.setApkId(info.getApkId());
			testUpdated.setAppId(info.getAppId());
			testUpdated.setTcId(info.getTcId());
			testUpdated.setTestDescription(info.getTestDescription());
			testUpdated.setTestName(info.getTestName());
			testUpdated.setTestDevices(info.getTestDevices());
			repository.save(testUpdated);
			
			return true;
			
		}
		return false;
	}

	public void deleteTest(String testId) {
		// TODO Auto-generated method stub
		//Test test = null;
		Test test = repository.findByTestId(testId);
		
		if(test != null) {
			repository.deleteByTestId(testId);
		}
		
	}

}
