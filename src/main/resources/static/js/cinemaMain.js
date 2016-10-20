function MainController($scope, $http) {
	var self = this;

	self.ping = function(){
		$http.get('health/').then(function(response) {
			self.health = response.data;
		})
	};

	self.ping();
}