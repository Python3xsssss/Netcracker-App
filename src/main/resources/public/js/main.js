let app = angular.module("skillsApp", []);

app.controller("AppCtrl", function($scope, $http) {
    $scope.entities = [];
    $http.get('http://localhost:8080/data/entities').then(async function (entResponse) {
        $scope.entities = entResponse.data;
    });

    $scope.eavObjects = [];
    $http.get('http://localhost:8080/data/eavObjects').then(async function (eavResponse) {
        $scope.eavObjects = eavResponse.data;
    });

    $scope.attributes = [];
    $http.get('http://localhost:8080/data/metadata').then(async function (mdResponse) {
        $scope.attributes = mdResponse.data;
    });
});