var checkLogin = CONTEXT_PATH+ "account/checkLogin";

var app = angular.module("ngApp", []);
app.controller("ngCtrl", function($scope,$http) {
    $scope.submit = function () {        
        $http.post(checkLogin, $scope.account).success(function (response) {
			if (response != "") {
				console.log(response);
	 		}
			else{
				console.log("Else "+response);
			}
		});
    }
});