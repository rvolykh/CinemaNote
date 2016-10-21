function LoginController($rootScope, $http, $location) {
	var self = this;

	var authenticate = function(credentials, callback) {

		var headers = credentials ? {authorization : "Basic "
			+ btoa(credentials.username + ":" + credentials.password)
		} : {};

		$http.get('login', {headers : headers}).then(function(response) {
			if (response.data.name) {
				$rootScope.authenticated = true;
			} else {
				$rootScope.authenticated = false;
			}
			callback && callback();
		}, function() {
			$rootScope.authenticated = false;
			callback && callback();
		});

	}

	authenticate();
	self.credentials = {};

	self.in = function() {
		authenticate(self.credentials, function() {
			if ($rootScope.authenticated) {
				$location.path("/");
				self.error = false;
			} else {
				$location.path("/login");
				self.error = true;
			}
		});
	};

	self.out = function() {
		$http.post('logout', {}).finally(function() {
			$rootScope.authenticated = false;
			$location.path("/");
		});
	}
}