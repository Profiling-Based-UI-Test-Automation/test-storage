package teststorage.model;


public class SnapshotData {
	
	private String fileName;
	private String objectId;
	
	public void setFileName(String name) {
		fileName = name;
	}
	
	public void setObjectId(String id) {
		objectId = id;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getObjectId() {
		return objectId;
	}
	
}
