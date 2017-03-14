var checkLogin = CONTEXT_PATH+ "account/checkLogin";

var app = angular.module("ngApp", []);
app.controller("ngCtrl", function($scope,$http) {
    $scope.changeView = function (val) {    
    	if(val == 'home'){
    		$("#content").load('/home.html');
    	}
    	
    	else if(val == 'taxi'){
    		$("#content").load('/taxiclients.html');
    	}
    	
    	else if(val == 'clients'){
    		$("#content").load('/taxiclients.html');
    	}
    }
});