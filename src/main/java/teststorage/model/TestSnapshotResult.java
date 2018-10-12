package teststorage.model;

import java.util.ArrayList;

import org.bson.types.ObjectId;


public class TestSnapshotResult {
	
	private ObjectId testId;
	
	private ArrayList<SnapshotData> images;
	
	public ObjectId getTestId() {
		return testId;
	}

	public void setTestId(ObjectId id) {
		this.testId = id;
	}
	
	public void setImages(ArrayList<SnapshotData> images){
		this.images = images;
	}
	
	public ArrayList<SnapshotData> getImages(){
		return images;
	}
	
	public void addImage(ObjectId id, String fileName){
		if(images == null)
			images = new ArrayList<SnapshotData>();
		SnapshotData snapshot = new SnapshotData(fileName, id);
		images.add(snapshot);
		
	}
	
	
	@Override
	public String toString() {
		return "Result [ testid = " + testId + "]";	
	}
	


}