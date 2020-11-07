package pubhub.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pubhub.model.Tag;
import pubhub.utilities.DAOUtilities;

public class TagDAOImpl implements TagDAO {
	Connection connection = null;
	PreparedStatement stmt = null;

	/*------------------------------------------------------------------------------------------------*/

	// @Override
	public List<Tag> getAllTagsForBook(String isbn) {
		List<Tag> tags = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM Book_Tags WHERE isbn_13=?";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, isbn);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Tag tag = new Tag();

				tag.setIsbn13(rs.getString("isbn_13"));
				tag.setName(rs.getString("tag_name"));

				tags.add(tag);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}

		return tags;
	}

	/*------------------------------------------------------------------------------------------------*/

	// @Override
	public List<String> getTagNamesForBook(String isbn) {
		List<String> tags = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT tag_name FROM Book_Tags WHERE isbn_13=?";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, isbn);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				tags.add(rs.getString("tag_name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}

		return tags;
	}

	/*------------------------------------------------------------------------------------------------*/

	// @Override
	public boolean addTag(Tag tag) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "INSERT INTO Book_Tags VALUES (?, ?)";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, tag.getIsbn13());
			stmt.setString(2, tag.getName());

			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	/*------------------------------------------------------------------------------------------------*/

	// @Override
	public boolean deleteTagByISBNAndName(String isbn, String name) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "DELETE FROM Book_Tags WHERE isbn_13=? AND tag_name=?";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, isbn);
			stmt.setString(2, name);

			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		} finally {
			closeResources();
		}
	}

	/*------------------------------------------------------------------------------------------------*/

	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}

		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}
}
