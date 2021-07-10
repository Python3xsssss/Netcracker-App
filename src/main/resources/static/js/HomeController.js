'use strict'

let module = angular.module("skillsApp", []);

module.controller("AppCtrl", function($scope, $http) {
    $scope.entities = [];
    $http.get('http://localhost:8080/api/entities').then(async function (response) {
        $scope.entities = response.data;
    });

    $scope.users = [];
    $http.get('http://localhost:8080/api/users').then(async function (response) {
        $scope.users = response.data;
    });

    $scope.departs = [];
    $http.get('http://localhost:8080/api/departments').then(async function (response) {
        $scope.departs = response.data;
    });

    $scope.teams = [];
    $http.get('http://localhost:8080/api/teams').then(async function (response) {
        $scope.teams = response.data;
    });
});