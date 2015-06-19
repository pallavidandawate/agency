(function(){
	

	var branchController = function($scope,$http,Excel,$timeout)
	{

	 
		  $scope.fetchData = function() {
			  
			  $scope.loading = true;
			  
			 /* $scope.names=[{"bglAccountNumber":"3199485407822","commulativeDifference":0.0,"dayDefference":0.0,"totalAdminDecrease":0.0,"totalAdminIncrease":0.0,"totalCreditInBgl3198":0.0,"totalCreditInIntermediateAccount":0.0,"totalDebitInBgl3198":0.0,"totalDebitInIntermediateAccount":0.0,"txnDate":"21/11/14"},{"bglAccountNumber":"3199467407824","commulativeDifference":-8100000.0,"dayDefference":-8100000.0,"totalAdminDecrease":0.0,"totalAdminIncrease":5400000.0,"totalCreditInBgl3198":0.0,"totalCreditInIntermediateAccount":0.0,"totalDebitInBgl3198":0.0,"totalDebitInIntermediateAccount":-8100000.0,"txnDate":"21/11/14"}];
			  $scope.bglLists = [{"bglAccountNumber":"3199485407822","selected":true},{"bglAccountNumber":"3199467407824","selected":true}];*/
			  
			  
			 $http.get("/getData?branchCode=" + $scope.branchCode)
			    .success(function(response) {$scope.names = response;$scope.loading = false; });
			  
			  $http.get("/getBgl3197List?branchCode=" + $scope.branchCode)
			    .success(function(response) {$scope.bglLists = response;$scope.loading = true;});
			  
			  $http.get("/getBranchName?branchCode=" + $scope.branchCode)
			    .success(function(response) {$scope.branchName = response;$scope.loading = true;});
			  
		  };
		  
		    $scope.exportToExcel=function(tableId){ // ex: '#my-table'
	            var exportHref=Excel.tableToExcel(tableId,'sheet name');
	            $timeout(function(){location.href=exportHref;},100); // trigger download
	        }
		  
		  
		  $scope.isShown = function(bglAccountNumber) {
			  
			  var value;
			  var breakout = false;
			
			
					  angular.forEach($scope.bglLists, function(filterObj) {
							  
						if(!breakout)
						{
						  if(filterObj.bglAccountNumber == bglAccountNumber)
								  {
								  	
								  	value =filterObj.selected;
								  	breakout = true;
								  
								  	
								  }
						       
						    }})
				 return value;
		
			  }
		  
		 
		  
		
		
	};
	
	
	var mainApp = angular.module("mainApp");
	var homeController = mainApp.controller("branchController",branchController);

	
	

	
	
}());