var routeApp = angular.module('myApp', ['ngRoute']);
routeApp.config(['$routeProvider',function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'app/ngTemplate/index.html'
        })
        .when('/user/list/:id', {
            templateUrl: 'views/route/detail.html',
            controller: 'RouteDetailCtl'
        })
        .otherwise({
            redirectTo: '/'
        });
}]);