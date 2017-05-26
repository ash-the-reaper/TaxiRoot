var urlGetAllClients = CONTEXT_PATH+ "client/getAllClients";
var urlDisableClients = CONTEXT_PATH+ "client/disableClient";
var urlEnableClients = CONTEXT_PATH+ "client/enableClient";
var urlDeleteClients = CONTEXT_PATH+ "client/deleteClient";
var urlClientByStatus = CONTEXT_PATH+ "client/getAllClientByStatus";


var app = angular.module("ngApp", []);
app.controller("ngCtrl", function($scope,$http) {
	
	$scope.getData = function(userRole){
		$http.post(urlGetAllClients, angular.toJson(userRole)).success(function (response) {
	    	$scope.userDetails = response;
		});
	}	
	
	$scope.getAllClient = function (userStatus, userRole) { 
		var url;
		var data;
		
		if(userStatus == 'ALL'){
			url = urlGetAllClients;
			data = userRole;
		}
		else{
			url = urlClientByStatus;
			data = {'userStatus': userStatus, 'userRole': userRole};
		}
	
		$http.post(url,  angular.toJson(data)).success(function (response) {
	    	$scope.userDetails = response;
		});
    }
    
    $scope.disable = function (userRole) { 
    	$scope.listId = [];
    	
    	for (var k = 0; k < $scope.userDetails.length; k++){
    	     if($scope.userDetails[k].desactive == true)    	 
    	    	 $scope.listId.push($scope.userDetails[k].id);
    	}
    	
    	$http.post((userRole == 'client')?urlDisableClients: null, $scope.listId).success(function (response) {    		

    		$scope.getData = function(userRole){
    			$http.post(urlGetAllClients, angular.toJson(userRole)).success(function (response) {
    		    	$scope.userDetails = response;
    			});
    		}
    		
    	});
    }
    
    $scope.enable = function (userRole) { 
    	$scope.listId = [];
    	
    	for (var k = 0; k < $scope.userDetails.length; k++){
    	     if($scope.userDetails[k].active == true)    	 
    	    	 $scope.listId.push($scope.userDetails[k].id);
    	}
    	
    	$http.post((userRole == 'client')?urlEnableClients: null, $scope.listId).success(function (response) {
    		$scope.getData = function(userRole){
    			$http.post(urlGetAllClients, angular.toJson(userRole)).success(function (response) {
    		    	$scope.userDetails = response;
    			});
    		}
    	});
    }

	
    $scope.remove = function (userRole) { 
    	
    	$scope.listId = [];
    	
    	for (var k = 0; k < $scope.userDetails.length; k++){
    	     if($scope.userDetails[k].remove == true)    	 
    	    	 $scope.listId.push($scope.userDetails[k].id);
    	}
    	
    	$http.post((userRole == 'client')?urlDeleteClients: null, $scope.listId).success(function (response) {
    		$scope.getData = function(userRole){
    			$http.post(urlGetAllClients, angular.toJson(userRole)).success(function (response) {
    		    	$scope.userDetails = response;
    			});
    		}
    	});
    }
        
    
    $scope.updateSelection = function(position, userDetails) {
	  angular.forEach(userDetails, function(c, index) {
	    if (position != index) 
	      c.checked = false;
	  });
    }
    
});

function reloadPage(userRole){
	if(userRole == 'client'){
		window.location.reload();
	}
}