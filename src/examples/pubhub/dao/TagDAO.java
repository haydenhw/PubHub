package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.Tag;

public interface TagDAO {
	public List<Tag> getAllTagsForBook(String isbn13);

	public boolean addTag(Tag tag);

	public boolean deleteTagByISBNAndName(String isbn, String name);
}
