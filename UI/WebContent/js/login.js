var checkLogin = CONTEXT_PATH+ "account/checkLogin";

var app = angular.module("ngApp", []);
app.controller("ngCtrl", function($scope,$http,$window) {
    $scope.submit = function () {        
        $http.post(checkLogin, $scope.account).success(function (response) {
			if (response == true) {
				$window.location.href = '/dashboard.html';
	 		}
			else{
				alert("Wrong username or password");
			}
		});
    }
});