/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package teststorage.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import teststorage.model.Apk;
import teststorage.model.ApkVersion;
//value = "{ 'userName' : ?0 }", fields = "{ 'requests': 1, '_id': 0 }"
public interface ApkRepository extends MongoRepository<Apk, String> {

	//@Query("{}, { sort: { '_id': -1 }}")
	Apk findTopByOrderByVersionNumDesc(String appId);
	
	Apk findByApkId(String apkId);
	
	Apk findByAppIdAndVersionNum(String appId, String versionNum);
	
	@Query("{'apkId': ?0}, {'versionNum':1, 'apkId': 1}")
	List<ApkVersion> findAllByAppId(@Param(value = "appId") String appId);
	
	//@Query("{}, {}")
	void deleteByApkId(@Param(value = "apkId") String apkId);
	
	
	
}
