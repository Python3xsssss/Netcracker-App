let app = angular.module("skillsApp", []);

app.controller("AppCtrl", function($scope, $http) {
    $scope.entities = [];
    $http.get('http://localhost:8080/data/entities').then(async function (response) {
        $scope.entities = response.data;
    });

    $scope.users = [];
    $http.get('http://localhost:8080/data/users').then(async function (response) {
        $scope.users = response.data;
    });

    $scope.departs = [];
    $http.get('http://localhost:8080/data/departments').then(async function (response) {
        $scope.departs = response.data;
    });

    $scope.teams = [];
    $http.get('http://localhost:8080/data/teams').then(async function (response) {
        $scope.teams = response.data;
    });
});