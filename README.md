# test-storage
[Application Information](https://github.com/Profiling-Based-UI-Test-Automation/test-storage/blob/master/src/main/java/teststorage/model/ApplicationInfo.java), [Apk File](https://github.com/Profiling-Based-UI-Test-Automation/test-storage/blob/master/src/main/java/teststorage/model/ApkInfo.java), [Test script Information](https://github.com/Profiling-Based-UI-Test-Automation/test-storage/blob/master/src/main/java/teststorage/model/TCInfo.java), [Test Result](https://github.com/Profiling-Based-UI-Test-Automation/test-storage/blob/master/src/main/java/teststorage/model/TestResult.java), [Device Log](https://github.com/Profiling-Based-UI-Test-Automation/test-storage/blob/master/src/main/java/teststorage/model/TestDeviceLogResult.java), [Resource 사용 추이](https://github.com/Profiling-Based-UI-Test-Automation/test-storage/blob/master/src/main/java/teststorage/model/TestResourceResult.java), Test Snapshot File 등의 정보를 읽기/저장/삭제/업데이트하기 위한 API이다.<br>

Spring Boot 1.5.7.RELEASE 기반 구현<br>

####개발 툴 정보
Spring Tool Suite 4 <br>
Version: 4.0.0.RELEASE<br>
Build Id: 201809220817<br>

####[프로젝트 정보](https://github.com/Profiling-Based-UI-Test-Automation/test-storage/blob/development/pom.xml)
Maven project<br>
spring 4.3.10.RELEASE 기반<br>
> repository : https://github.com/Profiling-Based-UI-Test-Automation/test-storage<br>
> host : http://127.0.0.1:8090<br>
> swagger : http://127.0.0.1:8090/swagger-ui.html<br>

#### 실행 전 요구사항
MongoDB 셋업 및 데몬 실행<br>

####[몽고 디비 설정 정보](https://github.com/Profiling-Based-UI-Test-Automation/test-storage/blob/development/src/main/resources/application.properties)

## [Application](https://github.com/Profiling-Based-UI-Test-Automation/test-storage/blob/development/src/main/java/teststorage/controller/ApplicationController.java) <br>
어플리케이션에 대한 정보를 저장/수정/삭제/읽을 수 있다.<br>

> endpoints<br>
   * GET /application/{appId} appId를 갖는 어플리케이션 정보 조회<br>
   * GET /application 모든 어플리케이션 정보 조회<br>
   * DELETE /application/{appId} 어플리케이션 정보 삭제<br>
   * PUT /application?{"appId":"xxx","appName":"yyy","companyName":"zzz"} appId를 갖는 어플리케이션 정보 변경<br>
   * POST /application?{"appName":"app1","companyName":"com1"} 어플리케이션 정보 저장<br>

## [APK file](https://github.com/Profiling-Based-UI-Test-Automation/test-storage/blob/development/src/main/java/teststorage/controller/ApkController.java) <br>
특정 어플리케이션의 apk file을 업로드, 수정, 삭제, 다운로드할 수 있다.

> endpoints<br>
   * GET /apk/allversion/{appId} 앱에 등록된 모든 apk file의 버전 정보 조회<br>
   * GET /apk/lastversion/{appId} 앱의 최신 버전 apk file 다운로드<br>
   * GET /apk/{apkId} apkId를 갖는 apk file 다운로드 <br>
   * DELETE /apk/{apkId} apkId를 갖는 apk file 삭제<br>
   * POST /apk/{appId}/{versionId}?{"appId":"xxx", "versionId":"yyy", apkfile:{formData}} apk file 업로드<br>
   * PUT /apk/{appId}/{versionId}?{"appId":"xxx", "versionId":"yyy", apkfile:{formData}} appId의 versionId를 갖는 apk file 업데이트<br>
   * PUT /apk/{apkId}?{"apkId":"xxx", apkfile:{formData}} apkId를 갖는 apk file 업데이트

## [TC(test script) file](https://github.com/Profiling-Based-UI-Test-Automation/test-storage/blob/development/src/main/java/teststorage/controller/TCController.java) <br>
특정 apk file을 테스트하기 위한 test script를 업로드, 수정, 삭제, 다운로드할 수 있다.

> endpoints<br>
   * GET /tc/allversion/{apkId} apk file에 등록된 모든 tc file의 버전 정보 조회<br>
   * GET /tc/lastversion/{apkId} apk file의 최신 버전 tc file 다운로드<br>
   * GET /tc/{tcId} tcId를 갖는 tc file 다운로드 <br>
   * DELETE /tc/{tcId} tcId를 갖는 tc file 삭제<br>
   * POST /tc/{apkId}/{versionId}?{"apkId":"xxx", "versionId":"yyy", tcfile:{formData}} tc file 업로드<br>
   * PUT /tc/{apkId}/{versionId}?{"apkId":"xxx", "versionId":"yyy", tcfile:{formData}} apk file의 versionId를 갖는 tc file 업데이트<br>
   * PUT /tc/{tcId}?{"apkId":"xxx", apkfile:{formData}} tcId를 갖는 tc file 업데이트

## [Test Result(테스트 결과 요약 정보)](https://github.com/Profiling-Based-UI-Test-Automation/test-storage/blob/development/src/main/java/teststorage/controller/TestResultController.java)
테스트 실행 결과(디바이스 로그)정보를 서버에 전송한다. 실행 결과 정보를 저장, 수정, 삭제, 읽을 수 있다.

> endpoints<br>
   * GET /result/{testId} testId를 갖는 테스트 실행결과 정보 조회<br>
   * DELETE /result/{testId} testId를 갖는 테스트 실행결과 정보 삭제<br>
   * PUT /result?{"totalTestCount": 0, "failCount": 0, "passCount": 0,
          "log": [ "string" ], "testId": "string", "testTime": [{}], "testcaseResult":["string"]}
          testId를 갖는 테스트 실행결과 정보 변경<br>
   * POST /result?{"totalTestCount": 0, "failCount": 0, "passCount": 0,
          "log": [ "string" ], "testId": "string", "testTime": [{}], "testcaseResult":["string"]} 
          testId를 갖는 테스트 실행결과 정보 저장<br>

## [Test Result(Device log)](https://github.com/Profiling-Based-UI-Test-Automation/test-storage/blob/development/src/main/java/teststorage/controller/TestDeviceLogResultController.java)
테스트 실행 결과(디바이스 로그)정보를 서버에 전송한다. 실행 결과 정보를 저장, 수정, 삭제, 읽을 수 있다.

> endpoints<br>
   * GET /devicelog/{testId} testId를 갖는 테스트 실행결과(디바이스 로그) 정보 조회<br>
   * DELETE /devicelog/{testId} testId를 갖는 테스트 실행결과(디바이스 로그) 정보 삭제<br>
   * PUT /devicelog?{"log": ["string"],"testId": "string"} testId를 갖는 테스트 실행결과(디바이스 로그) 정보 변경<br>
   * POST /devicelog?{"log": ["string"],"testId": "string"} testId를 갖는 테스트 실행결과(디바이스 로그) 정보 저장<br>

## [Test Result(리소스 사용량 변경 추이)](https://github.com/Profiling-Based-UI-Test-Automation/test-storage/blob/development/src/main/java/teststorage/controller/TestResourceResultController.java)
테스트 실행 결과(리소스 사용량 변경 추이)정보를 서버에 전송한다. 실행 결과 정보를 저장, 수정, 삭제, 읽을 수 있다.

> endpoints<br>
   * GET /resource/{testId} testId를 갖는 테스트 실행결과(리소스 사용량 변경 추이) 정보 조회<br>
   * DELETE /resource/{testId} testId를 갖는 테스트 실행결과(리소스 사용량 변경 추이) 정보 삭제<br>
   * PUT /resource?{"resources": [{}], "testId": "string"} testId를 갖는 테스트 실행결과(리소스 사용량 변경 추이) 정보 변경<br>
   * POST /resource?{"resources": [{}], "testId": "string"} testId를 갖는 테스트 실행결과(리소스 사용량 변경 추이) 정보 저장<br>

## [Test Result(스크린 샷)](https://github.com/Profiling-Based-UI-Test-Automation/test-storage/blob/development/src/main/java/teststorage/controller/TestResultSnapshotController.java)
테스트 실행 결과(스크린 샷)을 서버에 전송한다. 실행 결과 정보를 저장, 수정, 삭제, 읽을 수 있다.

> endpoints<br>
   * GET /snapshot/{testId} testId를 갖는 테스트 실행결과(스크린 샷) 다운로드<br>
   * DELETE /snapshot/{testId} testId를 갖는 테스트 실행결과(스크린 샷) 삭제<br>
   * PUT /snapshot?{"testId": "string", "images": [{formData}]} testId를 갖는 테스트 실행결과(스크린 샷) 변경<br>
   * POST /snapshot?{"testId": "string", "images": [{formData}]} testId를 갖는 테스트 실행결과(스크린 샷) 업로드<br>













