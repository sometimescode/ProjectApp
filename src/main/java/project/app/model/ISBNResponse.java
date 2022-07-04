package project.app.model;

import java.util.Arrays;

public class ISBNResponse {
    private String title;
    private ISBNResponseAuthors[] authors;
    private ISBNResponsePublishers[] publishers;
    private String publish_date;
    private ISBNResponseCover cover;
    private int number_of_pages;
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public ISBNResponseAuthors[] getAuthors() {
        return authors;
    }
    public void setAuthors(ISBNResponseAuthors[] authors) {
        this.authors = authors;
    }
    public ISBNResponsePublishers[] getPublishers() {
        return publishers;
    }
    public void setPublishers(ISBNResponsePublishers[] publishers) {
        this.publishers = publishers;
    }
    public String getPublish_date() {
        return publish_date;
    }
    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }
    public ISBNResponseCover getCover() {
        return cover;
    }
    public void setCover(ISBNResponseCover cover) {
        this.cover = cover;
    }
    public int getNumber_of_pages() {
        return number_of_pages;
    }
    public void setNumber_of_pages(int number_of_pages) {
        this.number_of_pages = number_of_pages;
    }
    @Override
    public String toString() {
        return "ISBNResponse [authors=" + Arrays.toString(authors) + ", cover=" + cover + ", number_of_pages="
                + number_of_pages + ", publish_date=" + publish_date + ", publishers=" + Arrays.toString(publishers)
                + ", title=" + title + "]";
    }

    
}