package teststorage.model;

import org.bson.types.ObjectId;

public class SnapshotData {
	private String fileName;
	private ObjectId objectId;
	
	public SnapshotData(String name, ObjectId id) {
		fileName = name;
		objectId = id;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public ObjectId getObjectId() {
		return objectId;
	}
	
}
