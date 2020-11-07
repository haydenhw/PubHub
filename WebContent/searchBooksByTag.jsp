<!--
  ____                 _                  
 |  _ \ _____   ____ _| |_ _   _ _ __ ___ 
 | |_) / _ \ \ / / _` | __| | | | '__/ _ \
 |  _ <  __/\ V / (_| | |_| |_| | | |  __/
 |_| \_\___| \_/ \__,_|\__|\__,_|_|  \___|
 
-->

<!-- Header -->
<jsp:include page="header.jsp" />

<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<header>
	<div class="container">
		<%
			//display message if present
		if (request.getAttribute("message") != null) {
			out.println("<p class=\"alert alert-danger\">" + request.getAttribute("message") + "</p>");
		}
		%>
		<h1>
			PUBHUB <small>Search By Tag</small>
		</h1>
		<hr class="book-primary">


		<form action="SearchBooksByTag" method="get"
			class="form-inline form-group text-left">
			<input type="search" class="tagSearchInput form-control" id="tag"
				name="tag" required="required">
			<button type="submit" class="btn btn-info form-control">Search</button>
		</form>

		<div class="tagSearchMessage text-left">
			<strong>${tagSearchMessage}</strong>
		</div>
		
		<jsp:include page="bookSimpleTable.jsp" />
		
	</div>
</header>



<!-- Footer -->
<jsp:include page="footer.jsp" />