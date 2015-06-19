(function(){
	

	var regionController = function($scope,$http,Excel,$timeout)
	{
		
				
		var onSuccess = function(response)
		{
			$scope.regionList = response.data;
		};
		
		var OnError = function(reason)
		{
			$scope.error = "coulde not fetch the data";
		};
		
		$http.get("/getRegionNames").then(onSuccess,OnError);
		
		 $scope.fetchData = function() {
			 
			
			 //Initializing parameter
			var date = new Date( $scope.endDate);
			var finalDate =  date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate();
			 
			 var regionName = $scope.region;
			 
			 var onRegionData = function(response)
			 {
					$scope.regionData = response.data;
				};
				
				var onRegionDataError = function(reason)
				{
					$scope.regionData = "coulde not fetch the region  data";
				};
			 
			$http.get("/getRegionData?regionName=" + $scope.region + "&cutOfDate=" + finalDate)
			    .then(onRegionData, onRegionDataError);
			  
		
			      	 
			  
		  };
		  
		  $scope.exportToExcel=function(tableId){ // ex: '#my-table'
	            var exportHref=Excel.tableToExcel(tableId,'sheet name');
	            $timeout(function(){location.href=exportHref;},100); // trigger download
	        }
		
		
		
		
	};
	
	
	var mainApp = angular.module("mainApp");
	mainApp.factory('Excel',function($window){
        var uri='data:application/vnd.ms-excel;base64,',
            template='<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
            base64=function(s){return $window.btoa(unescape(encodeURIComponent(s)));},
            format=function(s,c){return s.replace(/{(\w+)}/g,function(m,p){return c[p];})};
        return {
            tableToExcel:function(tableId,worksheetName){
                var table=$(tableId),
                    ctx={worksheet:worksheetName,table:table.html()},
                    href=uri+base64(format(template,ctx));
                return href;
            }
        };
    });
	var regionController = mainApp.controller("regionController",regionController);



	
	
}());
