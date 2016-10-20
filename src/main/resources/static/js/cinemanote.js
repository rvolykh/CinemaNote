(function(angular) {
	'use strict';
//	BEGIN

	angular.module('cinemaApp', [])
	.directive('headerComponent', [function($scope, $http){
		return {
				restrict : "A",
				templateUrl: "../view/header.html",
				controller: HeaderController,
				controllerAs: 'header'
			};
	}])
	.directive('asideComponent', [function($scope, $http){
		return {
				restrict : "A",
				templateUrl: "../view/aside.html",
				controller: AsideController,
				controllerAs: 'aside'
			};
	}])
	.directive('menuComponent', [function($scope, $http){
		return {
			restrict : "A",
			templateUrl: "../view/menu.html",
			controller: MenuController,
			controllerAs: 'menu'
		};
	}])
	.directive('mainComponent', [function($scope, $http){
		return {
			restrict : "A",
			templateUrl: "../view/dashboard01.html",
			controller: MainController,
			controllerAs: 'main'
		};
	}]);

//	END
})(window.angular);