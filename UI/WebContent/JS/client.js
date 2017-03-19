var urlGetAllClients = CONTEXT_PATH+ "client/getAllClients";

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
    	console.log(response);
	});
    
});