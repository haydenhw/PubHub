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

<!-- 	Just some stuff you need -->
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


		<form action="SearchBooksByTag" method="post" class="form-inline form-group text-left m-b-md">
			<input type="search" class="form-control w-100 mr-sm-6" id="query" name="query"
				required="required">
			<button type="submit" class="btn btn-info my-2 my-sm-0">Submit</button>
		</form>

		<table class="table table-striped table-hover table-responsive">
			<thead>
				<tr>
					<td>ISBN-13:</td>
					<td>Title:</td>
					<td>Author:</td>
					<td>Publish Date:</td>
					<td>Price:</td>
					<td></td>
					<td></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="book" items="${books}">
					<tr>
						<td><c:out value="${book.isbn13}" /></td>
						<td><c:out value="${book.title}" /></td>
						<td><c:out value="${book.author}" /></td>
						<td><c:out value="${book.publishDate}" /></td>
						<td><fmt:formatNumber value="${book.price }" type="CURRENCY" /></td>
						<td><form action="DownloadBook" method="get">
								<input type="hidden" name="isbn13" value="${book.isbn13}">
								<button class="btn btn-success">Download</button>
							</form></td>
						<td><form action="ViewBookDetails?isbn=${book.isbn13}"
								method="get">
								<input type="hidden" name="isbn13" value="${book.isbn13}">
								<button class="btn btn-primary">Details</button>
							</form></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>


	</div>
</header>



<!-- Footer -->
<jsp:include page="footer.jsp" />