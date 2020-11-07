package examples.pubhub.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.TagDAO;
import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;
import examples.pubhub.utilities.TagUtilities;

/**
 * Servlet implementation class UpdateBookServlet
 */
@WebServlet("/UpdateBook")
public class UpdateBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean isSuccess = false;
		String isbn13 = request.getParameter("isbn13");

		BookDAO dao = DAOUtilities.getBookDAO();
		Book book = dao.getBookByISBN(isbn13);

		if (book != null) {
			// The only fields we want to be updatable are title, author and price. A new
			// ISBN has to be applied for
			// And a new edition of a book needs to be re-published.
			book.setTitle(request.getParameter("title"));
			book.setAuthor(request.getParameter("author"));
			book.setPrice(Double.parseDouble(request.getParameter("price")));
			request.setAttribute("book", book);

			isSuccess = dao.updateBook(book) && this.updateTags(request, response);
		} else {
			// ASSERT: couldn't find book with isbn. Update failed.
			System.out.println("UPDATE FAILED");
			isSuccess = false;
		}

		if (isSuccess) {
			request.getSession().setAttribute("message", "Book successfully updated");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("ViewBookDetails?isbn13=" + isbn13);
		} else {
			request.getSession().setAttribute("message", "There was a problem updating this book");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
		}
	}


	private boolean updateTags(HttpServletRequest request, HttpServletResponse response) {
		// The client provides a comma separated list of tags that reflects the
		// new tag state, e.g.,"fantasy, romance, sci-fi".
		// Here we add and and remove tags from the database to match this
		// string of tags.

		boolean isSuccess = true;

		String isbn13 = request.getParameter("isbn13");
		TagDAO tagDAO = DAOUtilities.getTagDAO();

		List<String> currentTags = tagDAO.getTagNamesForBook(isbn13);
		List<String> nextTags  = TagUtilities.parseCommaSeparatedTags(request.getParameter("tags"));

		// Find and delete old tags that have been removed by the client
		List<String> toDelete = this.findRemovedTags(currentTags, nextTags);
		for (String tag : toDelete) {
			boolean deleteSuccess = tagDAO.deleteTagByISBNAndName(isbn13, tag);

			if (!deleteSuccess)
				isSuccess = false;
		}

		// Find and add new tags that aren't already in the database
		List<String> toAdd = this.findNewTags(currentTags, nextTags);
		for (String tag : toAdd) {
			Tag t = new Tag(isbn13, tag);
			boolean addSuccess = tagDAO.addTag(t);

			if (!addSuccess )
				isSuccess = false;
		}

		request.setAttribute("tags", tagDAO.getAllTagsForBook(isbn13));

		return isSuccess;
	}
	
	private List<String> findRemovedTags(List<String> currentTags, List<String> nextTags) {
		return currentTags.stream().filter(t -> !nextTags.contains(t)).collect(Collectors.toList());
	}

	private List<String> findNewTags(List<String> currentTags, List<String> nextTags) { 
		return nextTags.stream().filter(t -> !currentTags.contains(t)).collect(Collectors.toList());
	} 
}
