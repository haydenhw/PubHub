package pubhub.dao;

import java.util.List;

import pubhub.model.Tag;

public class TestDAO {
    public TagDAOImpl tagDAO = new TagDAOImpl();

    public static void main(String[] args) {
    	TestDAO test = new TestDAO();

    	test.addTag();
    }

	public void addTag() {
    	String isbn13 = "12345";
    	String tagName = "C++";

		Tag tag = new Tag(isbn13, tagName);
		this.tagDAO.addTag(tag);
		
		this.printTagsForBook(isbn13);
	}
	
	
	public void printTagsForBook(String isbn13) {
        List<Tag> tags = this.tagDAO.getAllTagsForBook(isbn13);

        for (Tag tag : tags) {
            System.out.println(tag.getName());
        }
	}
}
