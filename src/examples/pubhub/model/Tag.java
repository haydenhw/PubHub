package examples.pubhub.model;

public class Tag {
	private String isbn13;
	private String tagName;
	
	public Tag(String isbn, String name) {
		this.isbn13 = isbn;
		this.tagName = name;		
	}

	
    public Tag() {
    	this.isbn13 = null;
    	this.tagName = null;
    }
    
    public void setIsbn13(String isbn) {
    	this.isbn13 = isbn;
    }
    
    public String getIsbn13() {
    	return this.isbn13;
    	
    }

    public void setName(String name) {
        this.tagName = name;
    }
    
    public String getName() {
    	return this.tagName;
    }

}
