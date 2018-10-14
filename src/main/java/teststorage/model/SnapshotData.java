package teststorage.model;


public class SnapshotData {
	private String fileName;
	private String objectId;
	
	public SnapshotData(String name, String id) {
		fileName = name;
		objectId = id;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getObjectId() {
		return objectId;
	}
	
}
