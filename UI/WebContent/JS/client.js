var urlGetAllClients = CONTEXT_PATH+ "client/getAllClients";
var urlDisableClients = CONTEXT_PATH+ "client/disableClient";

var app = angular.module("ngApp", []);
app.controller("ngCtrl", function($scope,$http,$window) {
       
	$scope.changeView = function (val) {    
    	if(val == 'home'){
    		$window.location.href = '/home.html';
    	}
    	
    	else if(val == 'taxi'){
    		$window.location.href = '/taxiclients.html';
    	}
    	
    	else if(val == 'clients'){
    		$window.location.href = '/taxiclients.html';
    	}
    }
	
	
    $http.post(urlGetAllClients).success(function (response) {
		
    	$scope.userDetails = response;
	});
    
    
    $scope.disable = function () { 
    	
    	$scope.id = [];
    	
    	for (var k = 0; k < $scope.userDetails.length; k++){
    	     if($scope.userDetails[k].selected==true) {  	    	 
    	    	 $scope.id.push($scope.userDetails[k].id);
       	      }
    	 }
    	
    	console.log(JSON.stringify($scope.id));
    	
    	$http.post(urlDisableClients, {params:{ "id": JSON.stringify($scope.id)}}).success(function (response) {
			
    	});
      
    }
    
});