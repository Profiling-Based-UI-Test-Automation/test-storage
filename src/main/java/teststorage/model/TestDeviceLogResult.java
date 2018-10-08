package teststorage.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import org.bson.types.ObjectId;


public class TestDeviceLogResult {
		
	private ObjectId testId;
	
	private ArrayList<String> log;
	
	public ObjectId getTestId() {
		return testId;
	}

	public void setTestId(ObjectId id) {
		this.testId = id;
	}
	
	public void setLog(ArrayList<String> log){
		this.log = log;
	}
	
	public ArrayList<String> getLog(){
		return log;
	}
	
	@Override
	public String toString() {
		return "Result [ testid = " + testId + "]";	
	}
	


}