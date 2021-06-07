let app = angular.module("skillsApp", []);

app.controller("AppCtrl", function($scope, $http) {
    // $scope.people = [{
    //     id: 1,
    //     firstName: "Name",
    //     lastName: "Surname"
    // }];
    $scope.people = [];
    $http.get('http://localhost:8080/data/people').then(function (response) {
        $scope.people = response.data;
    });
});