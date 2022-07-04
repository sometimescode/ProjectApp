package project.app.model;

public class ISBNResponseCover {
    private String small;
    private String medium;
    private String large;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }
    
    public void setLarge(String large) {
        this.large = large;
    }

    @Override
    public String toString() {
        return "ISBNResponseCover [large=" + large + ", medium=" + medium + ", small=" + small + "]";
    }
}
