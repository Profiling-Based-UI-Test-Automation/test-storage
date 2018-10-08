'use strict';
var App = angular.module('uploadTestFiles',[]);

App.factory('docService', ['$http', '$q', 'urls', function ($http, $q, urls) {

            var factory = {
                saveAppTestFiles: saveAppTestFiles
            };

            return factory;

            function saveAppTestFiles(apkfile, tcfile) {
              
              var deferred = $q.defer();
              var formData = new FormData();
              formData.append('appName', "app1");
              formData.append('companyName', "com1");
              
              $http.post(urls.DOC_URL+'application', formData,{
                  transformRequest : angular.identity,
                  headers : {
                      'Content-Type' : undefined
                  }})
                  .then(
                      function (response) {
                          deferred.resolve(response.data);
                          alert(response.data);
                      },
                      function (errResponse) {
                          alert(errResponse.data.errorMessage);
                          deferred.reject(errResponse);
                      }
                  );
              
              
              //formData.append('file', apkfile);
              //formData.append('tcfile', tcfile);
              
              
//              $http.post(urls.DOC_URL+'upload', formData,{
//                  transformRequest : angular.identity,
//                  headers : {
//                      'Content-Type' : undefined
//                  }})
//                  .then(
//                      function (response) {
//                          deferred.resolve(response.data);
//                          alert(response.data);
//                      },
//                      function (errResponse) {
//                          alert(errResponse.data.errorMessage);
//                          deferred.reject(errResponse);
//                      }
//                  );
              return deferred.promise;
              
              
              //console.log("apkfile = " + apkfile);
              //console.log("tcfile = " + tcfile);
            };

        }
    ]);
App.controller('uploadController',
    ['$scope', '$rootScope','docService','$http', function($scope, $rootScope, docService, $http) {

        $scope.apkfile = '';
        $scope.tcfile = '';

        $scope.upload = function(){
            var apkfile = $scope.apkfile;
            var tcfile = $scope.tcfile;
            //console.log('apkfile = ' + apkfile);
            //console.log('tcfile = ' + tcfile);
            docService.saveAppTestFiles(apkfile, tcfile);
            
        }
    }
    ]);

App.constant('urls', {
    DOC_URL: 'http://localhost:8090/'
});

App.directive('fileModel', [ '$parse', function($parse) {
    return {
        restrict : 'A',
        link : function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function() {
                scope.$apply(function() {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
} ]);

App.run(function($rootScope, $http) {
});
