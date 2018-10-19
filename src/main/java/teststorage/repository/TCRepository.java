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

import teststorage.model.Tc;
import teststorage.model.TcVersion;

public interface TCRepository extends MongoRepository<Tc, String> {

	Tc findByTcId(String tcId);
	void deleteByTcId(@Param(value = "tcId") String tcId);
	
	@Query("{'apkId': ?0}, {'versionNum':1, 'tcId': 1}")
	List<TcVersion> findAllByApkId(@Param(value = "apkId") String apkId);
	
	Tc findTopByOrderByVersionNumDesc(String apkId);
	
	
	Tc findByApkIdAndVersionNum(String apkId, String versionNum);
	
}
