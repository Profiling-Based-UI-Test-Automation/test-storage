package teststorage.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.web.multipart.MultipartFile;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;


public class GridFSOperations {
	
	public final String resourceDirectory = File.separator + "data1" + File.separator + "public" ;
	public final String appDirectory  = resourceDirectory + File.separator + "app";
	public final String tcDirectory  = resourceDirectory + File.separator + "tc";
	public final String snapshotDirectory  = resourceDirectory + File.separator + "snapshot";
	public static final String APK_FILE = "apkfile"; 
	public static final String TC_FILE = "tcfile"; 
	public static final String IMAGE_FILE = "imagefile";
	
	public GridFSOperations(){
		createFolder(File.separator + "data1");
		createFolder(resourceDirectory);
		createFolder(appDirectory);
		createFolder(tcDirectory);
		createFolder(snapshotDirectory);
	}
	
	@Autowired
	GridFsOperations gridOperations;
	
	
	public String saveFiles(MultipartFile file, String name, String fileType) throws FileNotFoundException {
		// Define metaData
		DBObject metaData = new BasicDBObject();
		/**
		 * 1. save an image file to MongoDB
		 */
		// Get input file
		InputStream fileStream = null;
		try {
			fileStream = file.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		metaData.put("type", fileType);
		// Store file to MongoDB
		GridFSFile fileId = gridOperations.store(fileStream, name, "application/octet-stream", metaData);
		
		System.out.println("fileId = " + fileId);
		
		if(fileId != null) {
			Object id = fileId.getId();
			if(id != null)
				return id.toString();
			else
				return null;
		}
		else 
			return null;
	}
	
//	public String readFile(ObjectId id) {
//		
//		
//		GridFSDBFile result = (GridFSDBFile) gridOperations.find(
//	               new Query().addCriteria(Criteria.where("_id").is(id)));
//		
//		String tempFile = resourceDirectory + UUID.randomUUID().toString(); 
//		
//		
//		createFolder(tempFile);
//		
//		if(result != null) {
//			
//			String fileName = tempFile + File.separator + result.getFilename();
//			
//			try {
//				result.writeTo( fileName );
//				return fileName;
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
//		return null;
//
//	}
	
	public boolean deletSnapshot(String testId){
		// delete image via id
		gridOperations.delete(new Query(Criteria.where("_id").is(testId)));
		
		return true;
	}
	
	public boolean deleteFile(String id){
		// delete image via id
		gridOperations.delete(new Query(Criteria.where("_id").is(id)));
		
		return true;
	}
	
	public String makeDirectory(String fileTypeCategory, String id) {
		String tempFile = resourceDirectory + fileTypeCategory + File.separator + id; 
		createFolder(tempFile);
		return tempFile;
	}
	
	public String makeAppDirectory(String appId) {
		String tempFile = resourceDirectory + "app" + File.separator + appId; 
		createFolder(tempFile);
		return tempFile;
	}
	
	public String makeTcDirectory(String apkId) {
		String tempFile = resourceDirectory + "tc" + File.separator + apkId; 
		createFolder(tempFile);
		return tempFile;
	}
	
	public String getAppDirectory(String appId) {
		return appDirectory + File.separator + appId;
	}
	
	public String getTcDirectory(String _apkId) {
		return tcDirectory + File.separator + _apkId;
	}
	
	public String getSnapshotDirectory(String testId) {
		return snapshotDirectory + File.separator + testId;
	}
	
	public void copySnapshotFile(String _testId, MultipartFile file) {
		try{
			String fileName = file.getName();
			String testIdPath = makeDirectory("snapshot", _testId);
			if(testIdPath != null) {
				String testImagePath = testIdPath + File.separator + fileName;
				Path path = Paths.get(testImagePath);
				InputStream inputStream = file.getInputStream();
				Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

			}
		}catch(Exception e) {
			
		}
	}
	
	public void copyAppFile(String _appId, String version, MultipartFile file) {
		try{
			String fileName = file.getName();
			String applicationPath = makeDirectory("app", _appId); //makeAppDirectory(_appId);
			if(applicationPath != null) {
				String versionPath = createFolder(applicationPath + File.separator + version);
				
				if(versionPath != null) {
					String appPath = versionPath + File.separator + fileName;
					Path path = Paths.get(appPath);
					InputStream inputStream = file.getInputStream();
					Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
				}
			}
		}catch(Exception e) {
			
		}
	}
	
	public void copyTcFile(String _apkId, String version, MultipartFile file) {
		try{
			String fileName = file.getName();
			String apkPath = makeDirectory("tc", _apkId.toString()); //makeTcDirectory(_apkId.toString());
			if(apkPath != null) {
				String versionPath = createFolder(apkPath + File.separator + version);
				
				if(versionPath != null) {
					String tcPath = versionPath + File.separator + fileName;
					Path path = Paths.get(tcPath);
					InputStream inputStream = file.getInputStream();
					Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
				}
			}
		}catch(Exception e) {
			
		}
	}
	
	public void deleteSnapshotFile(String _testId) {
		try {
			String snapshotPath = getSnapshotDirectory(_testId);
			Path spath = Paths.get(snapshotPath);
			File path = spath.toFile();
			
			if(!path.exists()) { 
				return ; 
			} 
			File[] files = path.listFiles(); 
			for (File file : files) { 
				if (! file.isDirectory()) { 
					file.delete(); 
				} 
			} 
			
			path.delete();

		}catch(Exception e) {
			
		}
	}
	
	public void deleteAppFile(String _appId, String version, String fileName) {
		try {
			String appPath = getAppDirectory(_appId) + File.separator + version + File.separator + fileName;
			Path path = Paths.get(appPath);
			Files.deleteIfExists(path);
		}catch(Exception e) {
			
		}
	}
	
	public void deleteTcFile(String _apkId, String version, String fileName) {
		try {
			String appPath = getTcDirectory(_apkId) + File.separator + version + File.separator + fileName;
			Path path = Paths.get(appPath);
			Files.deleteIfExists(path);
		}catch(Exception e) {
			
		}
	}
	
	public String makeSnapshotDirPath(String testId) {
		
		String appPath = getSnapshotDirectory(testId) ;
		File appDir = new File(appPath);
		if(testId != null && !appDir.exists()) {
			appDir.mkdir();
		}
		
		return appPath;
	}
	
	public String makeApkDirPath(String _appId, String version) {
		String appDir = getAppDirectory(_appId);
		File appDirectory = new File(appDir);
		String versionDir = null;
		if(appDir != null && ! appDirectory.exists()) {
			appDirectory.mkdir();
		}
		
		versionDir = appDir + File.separator + version;
		File verDirectory = new File(versionDir);
		if(versionDir != null && ! verDirectory.exists()) {
			verDirectory.mkdir();
		}
		
		return versionDir;
	}
	
	public String makeTcDirPath(String _apkId, String version) {
		
		String tcDir = getTcDirectory(_apkId);
		File tcDirectory = new File(tcDir);
		String versionDir = null;
		
		if(tcDir != null && ! tcDirectory.exists()) {
			tcDirectory.mkdir();
		}
		
		versionDir = tcDir + File.separator + version;
		File verDirectory = new File(versionDir);
		if(versionDir != null && ! verDirectory.exists()) {
			verDirectory.mkdir();
		}
		
		return versionDir;
	}
	
	public Resource loadFileAsResource(String id, String dirPath) {
	
//		GridFSDBFile imageFile = gridOperations.findOne(new Query(Criteria.where("_id").is(imageFileId)));
//		
//		// Save file back to local disk
//		imageFile.writeTo("D:\\JSA\\retrieve\\jsa-logo.png");
		GridFSDBFile result = gridOperations.findOne(
	               new Query().addCriteria(Criteria.where("_id").is(id)));

		System.out.println("result = " + result);
		if(result != null) {
			
			String fileName = dirPath + File.separator + result.getFilename();
			try {
				result.writeTo( fileName );
				
				Path filePath = Paths.get(fileName);
				
	            Resource resource = new UrlResource(filePath.toUri());
	            if(resource.exists()) {
	                return resource;
	            } else {
	                return null;
	            }
	            
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return null;
        
    }
	
	public String createFolder(String folderName){
		// Create folder under project with name "screenshots" provided to destDir. 
		File file = new File(folderName);
		if(!file.exists())
			file.mkdirs(); 
		try {
			return file.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Set file name using current date time. 
		return null;
    }
    
    public void deleteFolder(String folderPath){
    	try {
			FileUtils.deleteDirectory(new File(folderPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	
	
}
