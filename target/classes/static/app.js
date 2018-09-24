var app = angular.module('archive', []);

app.directive('fileModel', [ '$parse', function($parse) {
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


app.service('fileUpload', ['$http','ArchiveService', function($http, ArchiveService) {
	this.uploadFileToUrl = function(uploadUrl, apkfile, tcfile) {
		
		if(apkfile == undefined){
			window.alert("apkfile 를 입력해 주세요~");
			return;
		}
		if(tcfile == undefined){
			window.alert("tcfile  입력해 주세요~");
			return;
		}
		else {
		  if((apkfile.name).search("\apk" ) == -1){
        window.alert("application을 apk file format으로 올려 주세요~");
        return;
      }
		  
			if((tcfile.name).search("\.zip" ) == -1){
				window.alert("tc를 zip file format으로 올려 주세요~");
				return;
			}
		}
		
	}
} ]);

app.controller('UploadCtrl', [ '$scope', 'fileUpload',
		function($scope, fileUpload) {
			console.log("file upload");
			$scope.uploadFile = function() {
				var apkfile = $scope.apkfile;
				var tcfile = $scope.tcfile;
				
				console.log('file is ' + JSON.stringify(file));
				console.log('tcfile is ' + JSON.stringify(tcfile));
				
				var uploadUrl = "/archive/upload";
				fileUpload.uploadFileToUrl(uploadUrl, apkfile, tcfile);
			};
		} 
]);


app.run(function($rootScope, ArchiveService) {
	console.log("app.run");

});
