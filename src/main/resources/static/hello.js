function Hello($scope, $http) {
    $http.get('health', {data: {name: name}}).
        success(function(data) {
            $scope.greeting = data;
        });

    $scope.update = function() {
        $http.get('health', {params: {name: $scope.name}}).
	        success(function(data) {
	            $scope.greeting = data;
	        });
    }
   
}
