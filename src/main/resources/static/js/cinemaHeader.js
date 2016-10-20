function HeaderController($scope, $http) {
	var self = this;

	// Set Api Documentation on Page loading
	$http.get('information/jsondoc/').then(function(response) {
		self.apiDocumentation = response.data.jsonDocPath;
	})

}
