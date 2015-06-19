<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
   
    
    <form:form method="post" action="processFile" >
     <p>Following files are uploaded successfully.</p>
 
  
    <table id="fileTable">
        <tbody>
         <c:forEach items="${files}" var="file">
         <tr>
             <td >${file}</td>
        </tr>
        
         </c:forEach>
     
    </tbody></table>
    <br><input type="submit" value="Process">
</form:form>