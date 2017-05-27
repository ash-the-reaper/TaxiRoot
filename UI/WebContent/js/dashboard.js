var checkLogin = CONTEXT_PATH+ "account/checkLogin";

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
});