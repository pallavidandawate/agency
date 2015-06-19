<!DOCTYPE html>
<html  ng-app="myApp">

  <head>
   
   
   	<link rel="stylesheet" href="/resources/css/style.css" />
    <script src="/resources/js/angular.js"></script>
    <script src="/resources/js/script.js"></script>
     
     
   
  </head>

<body ng-controller="myCtrl">
<form name="myForm" ng-submit="fetchData()">

    
    <h1>Reconciliation Of Agency Accounts (BGL 3197/3199) and BGL 3198</h1> Enter Branch Code :
    <input type="number" name="branchCode" ng-model="branchCode" placeholder="Please enter branch code" ng-minlength="5" ng-maxlength="5" ng-pattern="/4[0-9][0-9][0-9][0-9]/" required />


    <input type="submit" ng-disabled="myForm.branchCode.$dirty && myForm.branchCode.$invalid" />
    <br/>

    <span style="color:red" ng-show="myForm.branchCode.$dirty && myForm.branchCode.$invalid">
  			<span ng-show="myForm.branchCode.$error.required">Branch Code is required</span>
    </span>
    <!-- <span style="color:red" class="error" ng-show="myForm.branchCode.$error.minlength">Branch Code can't be less than 5 digit</span>
      <span style="color:red" class="error" ng-show="myForm.branchCode.$error.maxlength">Branch code cant't be more than 5 digit</span> -->
    <span style="color:red" class="error" ng-show="myForm.branchCode.$error.pattern">Branch code should be 5 digit number, start with 4</span>

    <br/>
    <div ng-show="loading" class="loading">
      <img src="resources/img/ajaxLoading.GIF">
    </div>
    <br/>
    <br/>

	<h4>Agency accounts for the branch :</h4>
   <div ng-repeat="bgl in bglLists">
        <input type="checkbox" id="checkbox-{{$index}}" value="{{bgl.bglAccountNumber}}" ng-model="bgl.selected"/>
        <label for="checkbox-{{$index}}">{{bgl.bglAccountNumber}}</label>
   </div>


<div id="wrapper">
        <table>
          <thead>
            <tr>
              <th rowspan=2>DATE</th>
              <th rowspan=2>AGENCY A/C NO.</th>
              <th colspan='2'>AGENCY A/C</th>
              <th colspan='2'>AGENCY A/C - BGL 3198 DIFF</th>
              <th >BGL 3198</th> 
              <th colspan='2'>AGENCY A/C - BGL 3198 DIFF</th>
              <th colspan='2'>ADMIN TXNS</th>
              </tr>
              <tr>
              <th>DEBIT</th>
              <th>CREDIT</th>
              <th>DAY'S DIFF</th>
                       
              <th>CUMM. DIFF</th>
              <th>NET TXNS</th>
             
              <th>DAY'S DIFF.</th>
              <th>CUMM. DIFF.</th>
              <th>INCREASE</th>
               <th>DECREASE</th>
              

            </tr>
            <!-- <tr ng-repeat="i in names | orderBy:['bglAccountNumber','txnDate']"> -->
            <tr ng-repeat="i in names" ng-show="isShown('{{i.bglAccountNumber}}')"  ng-class="{even: $even, odd: $odd}">
              <td align='center'>{{i.txnDate}}</td>
              <td align='center'>{{i.bglAccountNumber}}</td>
              <td align='right'>{{i.totalDebitInIntermediateAccount}}</td>
              <td align='right'>{{i.totalCreditInIntermediateAccount}}</td>
           
              <td align='right'>{{i.dayDefference}}</td>
              <td align='right'>{{i.commulativeDifference}}</td>
              
              <td align='right'><a ng-href="/get3198TxnDetails?bglAccountNumber={{i.bglAccountNumber}}&txnDate={{i.txnDate}}&TotalDebit={{i.totalDebitInIntermediateAccount}}" target="_blank">{{i.net3198}}</a></td>
           
        
            
              <td align='right'>{{i.days3198}}</td>
              
              <td align='right'>{{i.commulative3198}}</td>
               <td align='right'>{{i.totalAdminIncrease}}</td>
              <td align='right'>{{i.totalAdminDecrease}}</td>

            </tr>
          </tbody>
        </table>
        </div>
  </form>
</body>

</html>
