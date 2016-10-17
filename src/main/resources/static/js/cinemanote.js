
angular.module('cinemaApp', [])
.controller('HeaderController', ['$scope', '$http', function($scope, $http) {
	$http.get('information/jsondoc/').then(function(response) {
		$scope.info = response.data;
	})
}])
.directive('headerComponent', function() {
	return {
		restrict: 'A',
		templateUrl: "../view/header.html"
	};
})
.controller('AsideController', ['$scope', '$http', function($scope, $http) {
	
}])
.directive('asideComponent', function() {
	return {
		restrict: 'A',
		templateUrl: "../view/aside.html"
	};
})
.controller('MenuController', ['$scope', '$http', function($scope, $http) {
	
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
