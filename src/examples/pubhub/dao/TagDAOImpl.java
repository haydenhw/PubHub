package examples.pubhub.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;


public class TagDAOImpl implements TagDAO {
	Connection connection = null;
	PreparedStatement stmt = null;

  /*------------------------------------------------------------------------------------------------*/


  //@Override
  public List<Tag> getAllTagsForBook(String isbn){
	  List<Tag> tags = new ArrayList<>();
	  
	  
	  try {
		 connection = DAOUtilities.getConnection(); 
		 String sql = "SELECT * FROM book_tags WHERE isbn_13=?";
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