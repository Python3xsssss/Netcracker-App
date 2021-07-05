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
    // {"id":1,"entType":{"name":"user"},"entName":"User1"},
    // {"id":2,"entType":{"name":"user"},"entName":"User2"},
    // {"id":3,"entType":{"name":"user"},"entName":"User3"},
    $scope.entities = [];
    $http.get('http://localhost:8080/data/entities').then(async function (response) {
        $scope.entities = response.data;
    });
});