(function(){
	

	var networkController = function($scope,$http,Excel,$timeout)
	{
		
		
		var onSuccess = function(response)
		{
			$scope.networkNameList = response.data;
		};
		
		var OnError = function(reason)
		{
			$scope.error = "coulde not fetch the data";
		};
		
		$http.get("/getNetworkNames").then(onSuccess,OnError);
		
		 $scope.exportToExcel=function(tableId){ // ex: '#my-table'
	            var exportHref=Excel.tableToExcel(tableId,'sheet name');
	            $timeout(function(){location.href=exportHref;},100); // trigger download
	        }
		
		
			
		
		
		
		
		 $scope.fetchData = function() {
			 
			//Initializing parameter
				var date = new Date( $scope.endDate);
				var finalDate =  date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate();
			 			 
			 var onNetworkData = function(response)
			 {
					$scope.networkData = response.data;
				};
				
				var onNetworkDataError = function(reason)
				{
					$scope.networkData = "coulde not fetch the network  data";
				};
			 
			$http.get("/getNetworkData?networkName=" + $scope.network + "&cutOfDate=" + finalDate)
			    .then(onNetworkData, onNetworkDataError);
			  
		
			      	 
			  
		  };
		
		
		
		
	};
	
	
	var mainApp = angular.module("mainApp");
	var zoneController = mainApp.controller("networkController",networkController);



	
	
}());
