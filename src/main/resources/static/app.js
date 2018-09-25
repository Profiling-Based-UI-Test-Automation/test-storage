'use strict';
var App = angular.module('uploadTestFiles',[]);

App.factory('docService', ['$http', '$q', 'urls', function ($http, $q, urls) {

            var factory = {
                saveAppTestFiles: saveAppTestFiles
            };

            return factory;

            function saveAppTestFiles(apkfile, tcfile) {
              console.log("apkfile = " + apkfile);
              console.log("tcfile = " + tcfile);
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
            console.log('apkfile = ' + apkfile);
            console.log('tcfile = ' + tcfile);
            docService.saveAppTestFiles(apkfile, tcfile);
            
        }
    }
    ]);

App.constant('urls', {
    DOC_URL: 'http://localhost:8080/doc/'
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
