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

			// TODO figure out how to set attribute for Tag
			isSuccess = dao.updateBook(book) && this.updateTags(request, response);
		} else {
			// ASSERT: couldn't find book with isbn. Update failed.
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
		boolean deletionError = false;
		boolean creationError = false;
		String isbn13 = request.getParameter("isbn13");
		
		TagDAO tagDAO = DAOUtilities.getTagDAO();

		//TODO refactor string splitter into util function
		String[] newTagsArr = request.getParameter("tags").split("\\s*,\\s*");
		List<String> newTags = Arrays.asList(newTagsArr);

		List<String> oldTags = tagDAO.getAllTagsForBook(isbn13).stream().map(t -> t.getName())
				.collect(Collectors.toList());

		// Remove tags in the database that have been deleted by the client
		List<String> toDelete = oldTags.stream().filter(t -> !newTags.contains(t)).collect(Collectors.toList());

		for (String tag : toDelete) {
			boolean isSuccess = tagDAO.deleteTagByISBNAndName(isbn13, tag);

			if (!isSuccess)
				deletionError = true;

		}

		// Add new tags that do not already exist in the database
		List<String> toCreate = newTags.stream().filter(t -> !oldTags.contains(t)).collect(Collectors.toList());

		for (String tagName : toCreate) {
			Tag tag = new Tag(isbn13, tagName);
			boolean isSuccess = tagDAO.addTag(tag);

			if (!isSuccess)
				creationError = true;
		}

		return !deletionError && !creationError;
	}
}
