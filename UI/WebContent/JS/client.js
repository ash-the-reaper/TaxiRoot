var urlGetAllClients = CONTEXT_PATH+ "client/getAllClients";
var urlDisableClients = CONTEXT_PATH+ "client/disableClient";

var app = angular.module("ngApp", []);
app.controller("ngCtrl", function($scope,$http) {
	
    $http.post(urlGetAllClients).success(function (response) {
    	$scope.userDetails = response;
	});
    
    
    $scope.disable = function () { 
    	
    	$scope.listId = [];
    	
    	for (var k = 0; k < $scope.userDetails.length; k++){
    	     if($scope.userDetails[k].selected == true)    	 
    	    	 $scope.listId.push($scope.userDetails[k].id);
    	}

    	$http.post(urlDisableClients, $scope.listId).success(function (response) {
    		window.location.reload();
    	});
      
    }
    
    $scope.updateSelection = function(position, userDetails) {
		  angular.forEach(userDetails, function(c, index) {
		    if (position != index) 
		      c.checked = false;
		  });
    }
       
});