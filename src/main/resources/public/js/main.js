let app = angular.module("skillsApp", []);

app.controller("AppCtrl", function($scope, $http) {
    $scope.entities = [];
    $http.get('http://localhost:8080/data/entities').then(function (response) {
        $scope.entities = response.data;
    });
});