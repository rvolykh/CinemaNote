
angular.module('cinemaApp', [])
.controller('MainController', ['$scope', '$http', function($scope, $http) {
	$http.get('health/').then(function(response) {
		$scope.health = response.data;
	})
}])
.directive('mainComponent', function() {
	return {
		restrict: 'A',
		template: 'Name: {{health.id}} Address: {{health.time}}'
	};
});
