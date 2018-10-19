package teststorage.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import teststorage.model.SnapshotData;
import teststorage.model.TestSnapshotResult;
import teststorage.repository.TestSnapshotResultRepository;

import org.springframework.core.io.Resource;

@Service
public class TestSnapshotResultService extends GridFSOperations{
	@Autowired
	private TestSnapshotResultRepository repository;
	
	public void insertSnapshotFiles(String _testId, MultipartFile[] imgfiles){
		System.out.println("snapshot....." + imgfiles.length);
		TestSnapshotResult snapshotResult = new TestSnapshotResult();
		snapshotResult.setTestId(_testId);
		
		for(int i = 0; i < imgfiles.length ; ++ i) {
			String fileName = imgfiles[i].getName();
			String imageId = null;
			try {
				imageId = saveFiles(imgfiles[i], fileName, GridFSOperations.IMAGE_FILE);
				System.out.println("imageId = " + imageId);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			copySnapshotFile(_testId, imgfiles[i]);
			snapshotResult.addImage(imageId, fileName);
		}
		
		if(snapshotResult != null){
			this.repository.save(snapshotResult);	
		}
		

		return;
	}
	
	public ArrayList<Resource> readSnapshotFiles(String testId){
		TestSnapshotResult findedinfo = this.repository.findByTestId(testId);
		System.out.println("findedinfo = " + findedinfo);
		if(findedinfo != null) {
			ArrayList<SnapshotData> snapshots = findedinfo.getImages();
		
			ArrayList<Resource> ret = new ArrayList<Resource>();
			
			for(int i = 0 ; i < snapshots.size() ; ++ i) {
				String snapshotFullPath = makeSnapshotDirPath(testId);
				SnapshotData snapshot = snapshots.get(i);
				if(snapshot != null) {
					Resource resource = loadFileAsResource(snapshot.getObjectId(), snapshotFullPath, ("image"+i));
					System.out.println("resource = " + resource);
					ret.add(resource);				
				}
				
			}
			
			return ret;
		
		}
		
		return null;

	}
	
	public boolean updateSnapshotFiles(String _testId, MultipartFile[] imgfiles){
		TestSnapshotResult findedinfo = this.repository.findByTestId(_testId);
		
		if(findedinfo != null){
			deleteSnapshotFile(_testId);
			this.repository.delete(findedinfo);
		}
		
		TestSnapshotResult snapshotResult = new TestSnapshotResult();
		snapshotResult.setTestId(_testId);
		
		for(int i = 0; i < imgfiles.length ; ++ i) {
			String fileName = imgfiles[i].getName();
			String imageId = null;
			try {
				imageId = saveFiles(imgfiles[i], fileName, GridFSOperations.IMAGE_FILE);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			copySnapshotFile(_testId, imgfiles[i]);
			snapshotResult.addImage(imageId, fileName);
		}
		
		if(snapshotResult != null){
			this.repository.save(snapshotResult);	
		}
			
		return true;
    	
	}
	
	public void deleteApkInfo(String _testId){
		TestSnapshotResult findedinfo = this.repository.findByTestId(_testId);
		
		if(findedinfo != null){
			deleteSnapshotFile(_testId);
			this.repository.delete(findedinfo);
		}
		
		return ;
	}
	
}
