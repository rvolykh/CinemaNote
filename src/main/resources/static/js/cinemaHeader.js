function HeaderController($scope, $http) {
	var self = this;

	self.getApiPath = function(){
		$http.get('information/jsondoc/').then(function(response) {
			self.info = response.data;
		})
	};

	self.getApiPath();
}
