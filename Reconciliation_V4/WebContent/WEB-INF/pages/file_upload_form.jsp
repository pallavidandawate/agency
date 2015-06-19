<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<link href="css/myCss.css" rel="stylesheet">
<!-- Bootstrap -->
<link href="atm/css/bootstrap.min.css" rel="stylesheet">

<h1 align="center">ATM BGL 3197/3199 and 3198 Reconciliation</h1>
<h3 align="center">File Uploading</h3>
 
<br><br><br>
 
<div class="container">
	<form:form method="post" action="save.html" modelattribute="uploadForm" enctype="multipart/form-data">
	
		<table id="fileTable" class="table table-bordered table-striped table-hover">
		        <tbody>
			        <tr>
			            <th><label>BGL 3197/3199 Transactions File </label></th><td><input name="files[0]" type="file"></td>
			        </tr>
			        <tr>
			            <th><label>Admin Transactions File </label></th><td><input name="files[1]" type="file"></td>
			        </tr>
			        <tr>
			            <th><label>BGL 3198 Transactions File </label></th><td><input name="files[2]" type="file"></td>
			        </tr>
			        <tr>
			            <th><label>ATM Mapping File </label></th><td><input name="files[3]" type="file"></td>
			        </tr>
			        <tr>
			            <th><label>Branch Data File </label></th><td><input name="files[4]" type="file"></td>
			        </tr>
		    	</tbody>
		 </table>
		   
	     <br>
	     
	     <input type="submit" value="Upload">
	   
	</form:form>
</div>