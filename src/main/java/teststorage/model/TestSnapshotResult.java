package teststorage.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

import lombok.Data;


@Data
public class TestSnapshotResult {
	
	@Id
	private String _id;
	private String testId;
	
	private ArrayList<SnapshotData> images;
	
	public String getTestId() {
		return testId;
	}

	public void setTestId(String id) {
		this.testId = id;
	}
	
	public void setImages(ArrayList<SnapshotData> images){
		this.images = images;
	}
	
	public ArrayList<SnapshotData> getImages(){
		return images;
	}
	
	public void addImage(String id, String fileName){
		if(images == null)
			images = new ArrayList<SnapshotData>();
		SnapshotData snapshot = new SnapshotData();
		snapshot.setFileName(fileName);
		snapshot.setObjectId(id);
		images.add(snapshot);
		
	}
	
	
	@Override
	public String toString() {
		return "Result [ testid = " + testId + "]";	
	}
	


}