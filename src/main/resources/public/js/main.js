let app = angular.module("skillsApp", []);

function addColumn(columnName) {
    [...document.querySelectorAll('#table tr')].forEach((row, i) => {
        const input = document.createElement("input")
        input.setAttribute('type', 'text')
        const cell = document.createElement(i ? "td" : "th")
        cell.appendChild(input)
        row.appendChild(cell)
    });
}

app.controller("AppCtrl", function($scope, $http) {
    $scope.entities = [];
    $http.get('http://localhost:8080/data/entities').then(async function (entResponse) {
        $scope.entities = entResponse.data;
    });

    $scope.max = 0;
    for (let i = 0; i < $scope.entities.length; i++) {
        if ($scope.entities[i].parameters.length > $scope.max) {
            $scope.max = $scope.entities[i].parameters.length;
        }
    }

    $scope.users = jQuery.grep($scope.entities = [], function(item) {
        return ( item.id === 1 );
    });


});