<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<thead>
	<tr>
		<td>ISBN-13:</td>
		<td>Title:</td>
		<td>Author:</td>
		<td>Publish Date:</td>
		<td>Price:</td>
		<td>Tags:</td>
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
			<td><fmt:formatNumber value="${book.price}" type="CURRENCY" /></td>
			<td><c:out value="${book.tags}" /></td>
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