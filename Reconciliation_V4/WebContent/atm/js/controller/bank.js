(function(){
	

	var bank = function($scope,$http,Excel,$timeout)
	{
		
		
		
		 $scope.fetchData = function() {
			 
			//Initializing parameter
				var date = new Date( $scope.endDate);
				var finalDate =  date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate();
			 			 
			 var onNetworkData = function(response)
			 {
					$scope.bankData = response.data;
				};
				
				var onNetworkDataError = function(reason)
				{
					$scope.bankData = "coulde not fetch the bank  data";
				};
			 
			$http.get("/getBankData?cutOfDate=" + finalDate)
			    .then(onNetworkData, onNetworkDataError);
			  
		
			      	 
			  
		  };
		  
		  $scope.exportToExcel=function(tableId){ // ex: '#my-table'
	            var exportHref=Excel.tableToExcel(tableId,'sheet name');
	            $timeout(function(){location.href=exportHref;},100); // trigger download
	        }
		
		
		
		
	};
	
	
	var mainApp = angular.module("mainApp");
	var bank = mainApp.controller("bank",bank);



	
	
}());
