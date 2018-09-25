package teststorage.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;



/**
 * 
 */
@Controller
@RequestMapping(value = "/archive")
public class ArchiveController {


	private Logger logger = LoggerFactory.getLogger(ArchiveController.class);
	

	
    /**
     * zip file 업로드 
     */
    @RequestMapping(value = "/uploadApp", method = RequestMethod.POST)
    public @ResponseBody boolean handleAppFileUpload(
            @RequestParam(value="apkfile", required=true) MultipartFile apkfile ,
            @RequestParam(value="tcfile", required=true) MultipartFile tcfile) {  
    	
	    	System.out.println("/uploadApp");
	    	System.out.println("apkfile = " + apkfile);
	    	System.out.println("tcfile = " + tcfile);
			return false;
    
    }
    
   
}
