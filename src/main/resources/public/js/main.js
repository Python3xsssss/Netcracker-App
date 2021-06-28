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
    $http.get('http://localhost:8080/data/entities').then(async function (response) {
        $scope.entities = response.data;
    });

    $scope.attributes = [
        {"id":1,"name":"first_name","type":"text","descr":null},
        {"id":2,"name":"last_name","type":"text","descr":null},
        {"id":14,"name":"role","type":"text","descr":null},
        {"id":15,"name":"picture","type":"text","descr":null},
        {"id":16,"name":"email","type":"text","descr":null},
        {"id":24,"name":"age","type":"integer","descr":null}
    ];
    // $http.get('http://localhost:8080/data/entities/1/attributes').then(async function (response) {
    //     $scope.attributes = response.data;
    // });


});