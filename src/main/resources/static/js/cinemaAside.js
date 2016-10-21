function AsideController($rootScope, $http, $location) {
	var self = this;
	
	self.todo = "TODO: implement of course ;)";
	
	self.out = function() {
		$http.post('logout', {}).finally(function() {
			$rootScope.authenticated = false;
			$location.path("/");
		});
	}
}