var UserApp = angular.module('UserApp',[]);

UserApp.controller('UserListCtrl',['$scope', function($s){
    $s.users = [
        {userName:"日本鬼子", loginName:'pig'},
        {userName:"中国人民", loginName:'china'}
    ];
}]);
