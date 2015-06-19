(function(){
	

	var zoneController = function($scope,$http,Excel,$timeout)
	{
		
		
		var onSuccess = function(response)
		{
			$scope.zoneNameList = response.data;
		};
		
		var OnError = function(reason)
		{
			$scope.error = "coulde not fetch the data";
		};
		
		$http.get("/getZoneNames").then(onSuccess,OnError);
		
		 $scope.exportToExcel=function(tableId){ // ex: '#my-table'
	            var exportHref=Excel.tableToExcel(tableId,'sheet name');
	            $timeout(function(){location.href=exportHref;},100); // trigger download
	        }
		
		
			
		
		
		
		
		 $scope.fetchData = function() {
			 
			//Initializing parameter
				var date = new Date( $scope.endDate);
				var finalDate =  date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate();
			 			 
			 var onZoneData = function(response)
			 {
					$scope.zoneData = response.data;
				};
				
				var onZoneDataError = function(reason)
				{
					$scope.zoneData = "coulde not fetch the region  data";
				};
			 
			$http.get("/getZoneData?zoneName=" + $scope.zone + "&cutOfDate=" + finalDate)
			    .then(onZoneData, onZoneDataError);
			  
		
			      	 
			  
		  };
		
		
		
		
	};
	
	
	var mainApp = angular.module("mainApp");
	var zoneController = mainApp.controller("zoneController",zoneController);



	
	
}());
