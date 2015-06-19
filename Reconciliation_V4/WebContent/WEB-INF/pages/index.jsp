<!DOCTYPE html>
<html  ng-app="myApp">

  <head>
   
   
   	<link rel="stylesheet" href="/resources/css/style.css" />
    <script src="/resources/js/angular.js"></script>
    <script src="/resources/js/script.js"></script>
     
     
   
  </head>

<div ng-controller="myCtrl"  >
<body background="w3s.png">
<form name="myForm" ng-submit="fetchData()">

    <h1 align="center" >OUTSOURCED ATMs</h1>
    <h2 align="center" style="font: italic;font-family: sans-serif">RECONCILIATION OF AGENCY ACCOUNTS(BGL 3197/3199) AND BGL 3198</h2>
    <h4 align="center">
    BRANCH CODE :
    <input type="number" name="branchCode" ng-model="branchCode" placeholder="Please enter branch code" ng-minlength="5" ng-maxlength="5" ng-pattern="/4[0-9][0-9][0-9][0-9]/" required />
	<input type="submit" ng-disabled="myForm.branchCode.$dirty && myForm.branchCode.$invalid" />
    </h4>
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
    
     
    <h4 align="center">AGENCY ACCOUNTS FOR THE BRANCH :
   <div ng-repeat="bgl in bglLists">
        <input type="checkbox" id="checkbox-{{$index}}" value="{{bgl.bglAccountNumber}}" ng-model="bgl.selected"/>
        <label for="checkbox-{{$index}}">{{bgl.bglAccountNumber}}</label>
   </div>
   </h4>
   <br>
   

<div id="wrapper">
        <table>
          <thead>
            <tr>
              <th rowspan=2>DATE</th>
              <th rowspan=2>AGENCY A/C NO.</th>
              <th colspan='2'>AGENCY A/C</th>
              <th colspan='2'>AGENCY A/C</th>
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
            <!-- <tr ng-repeat="i in names" ng-show="isShown('{{i.bglAccountNumber}}')"  ng-class="{even: $even, odd: $odd}"> -->
             <tr ng-repeat="i in names" ng-show="isShown('{{i.bglAccountNumber}}')" >
              <td align='center' class="bgl">{{i.txnDate}}</td>
              <td align='center' class="bgl">{{i.bglAccountNumber}}</td>
              <td align='right' class='agency'>{{i.totalDebitInIntermediateAccount}}</td>
              <td align='right' class='agency'>{{i.totalCreditInIntermediateAccount}}</td>
           
              <td align='right' class="difference">{{i.dayDefference}}</td>
              <td align='right' class="difference">{{i.commulativeDifference}}</td>
               
              <td align='right' class="bgl"><a ng-href="/get3198TxnDetails?bglAccountNumber={{i.bglAccountNumber}}&txnDate={{i.txnDate}}&TotalDebit={{i.totalDebitInIntermediateAccount}}" target="_blank">{{i.net3198}}</a></td>
           
        
            
              <td align='right' class="difference">{{i.days3198}}</td>
              
              <td align='right' class="difference">{{i.commulative3198}}</td>
               <td align='right' class="admin"> {{i.totalAdminIncrease}}</td>
              <td align='right' class="admin">{{i.totalAdminDecrease}}</td>

            </tr>
          </tbody>
        </table>
        </div>
  </form>
</body>
</div>

</html>
