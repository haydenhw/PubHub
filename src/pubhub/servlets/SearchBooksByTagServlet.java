package pubhub.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pubhub.dao.BookDAO;
import pubhub.model.Book;
import pubhub.utilities.DAOUtilities;

/*
 * This servlet will take you to the homepage for the Book Publishing module (level 100)
 */
@WebServlet("/SearchBooksByTag")
public class SearchBooksByTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BookDAO dao = DAOUtilities.getBookDAO();
		List<Book> bookList;

		String tag = request.getParameter("tag");
		if (tag == null) {
			bookList = dao.getAllBooks();
			request.getSession().setAttribute("tagSearchMessage", "Search for a tag to filter results"); 
		} else {
			tag = tag.trim();
			bookList = dao.getBooksByTag(tag);

			if (bookList.size() == 0) {
				request.getSession().setAttribute("tagSearchMessage", "No books found for tag '" + tag + "'");
			} else {
				request.getSession().setAttribute("tagSearchMessage", "Showing books that include tag: '" + tag + "'");
			}
		}

		request.getSession().setAttribute("books", bookList);
		request.getRequestDispatcher("searchBooksByTag.jsp").forward(request, response);
	}
}
