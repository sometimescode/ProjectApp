package project.app.model;

import java.util.Arrays;

public class BookEntry {
    private String title;
    private String[] authors;
    private String cover;
    private String ISBN;
    private int pageCount;
    private String publisher;
    private String publishedDate;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }
    public String getISBN() {
        return ISBN;
    }
    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }
    public int getPageCount() {
        return pageCount;
    }
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getPublishedDate() {
        return publishedDate;
    }
    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }
    public String[] getAuthors() {
        return authors;
    }
    public void setAuthors(String[] authors) {
        this.authors = authors;
    }
    @Override
    public String toString() {
        return "BookEntry [ISBN=" + ISBN + ", authors=" + Arrays.toString(authors) + ", cover=" + cover + ", pageCount="
                + pageCount + ", publishedDate=" + publishedDate + ", publisher=" + publisher + ", title=" + title
                + "]";
    }
    
}
