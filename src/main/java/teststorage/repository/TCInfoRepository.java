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


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import teststorage.model.ApkInfo;
import teststorage.model.ApplicationInfo;
import teststorage.model.TCInfo;

public interface TCInfoRepository extends MongoRepository<TCInfo, ObjectId> {

	TCInfo findByTcId(ObjectId tcId);
	void deleteByTcId(@Param(value = "tcId") ObjectId tcId);
	
}
