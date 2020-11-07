package pubhub.dao;

import java.util.List;

import pubhub.model.Tag;

public interface TagDAO {
	public List<Tag> getAllTagsForBook(String isbn13);
	public List<String> getTagNamesForBook(String isbn13);

	public boolean addTag(Tag tag);
	public boolean deleteTagByISBNAndName(String isbn, String name);
}
