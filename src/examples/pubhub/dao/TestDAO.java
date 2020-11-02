package examples.pubhub.dao;

import examples.pubhub.model.Tag;

import java.util.List;

public class TestDAO {
    public static void main(String[] args) {
        TagDAOImpl tagDAO = new TagDAOImpl();
        List<Tag> tags = tagDAO.getAllTagsForBook("1111111111111");

        for (Tag tag : tags) {
            System.out.println(tag.getName());
        }
    }
}
