
angular.module('cinemaApp', [])
.controller('HeaderController', ['$scope', '$http', function($scope, $http) {
	$http.get('health/').then(function(response) {
		$scope.health = response.data;
	})
}])
.directive('headerComponent', function() {
	return {
		restrict: 'A',
		templateUrl: "../view/header.html"
	};
})
.controller('AsideController', ['$scope', '$http', function($scope, $http) {
	$http.get('health/').then(function(response) {
		$scope.health = response.data;
	})
}])
.directive('asideComponent', function() {
	return {
		restrict: 'A',
		templateUrl: "../view/aside.html"
	};
})
.controller('MenuController', ['$scope', '$http', function($scope, $http) {
	$http.get('health/').then(function(response) {
		$scope.health = response.data;
	})
}])
.directive('menuComponent', function() {
	return {
		restrict: 'A',
		templateUrl: "../view/menu.html"
	};
})
.controller('MainController', ['$scope', '$http', function($scope, $http) {
	$http.get('health/').then(function(response) {
		$scope.health = response.data;
	})
}])
.directive('mainComponent', function() {
	return {
		restrict: 'A',
		templateUrl: "../view/dashboard01.html"
	};
});
