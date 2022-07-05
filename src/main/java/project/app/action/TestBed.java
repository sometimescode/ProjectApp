package project.app.action;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import com.opensymphony.xwork2.ActionSupport;

import project.app.service.DBService;

public class TestBed extends ActionSupport {
    private String select;
    private String[] names = {"Alexa", "Siri", "Jeeves"};
    private File file;
    private String contentType = "image/jpg";
    private String filename = "testBedFox";
    private String base64Cover;

    private String error;

    public String execute() {
        return SUCCESS;
    }

    public String getImg() {
        try {
            base64Cover = DBService.getBase64CoverImage(5);
            return SUCCESS;
        } catch (SQLException e) {
            error = e.toString();
            e.printStackTrace();
            return ERROR;
        }
    }

    public String imgUpload() {
        try {
            if(DBService.addTestBed(select, file)) {
                System.out.println("IN HERE succ 1");
                return SUCCESS;
            } else {
                System.out.println("IN HERE succ 2");
                return ERROR;
            }
        } catch (SQLException e) {
            System.out.println("IN HERE err 1");
            error = e.toString();
            e.printStackTrace();
            return ERROR;
        }
    }
    
    public void setUpload(File file) {
        this.file = file;
        // try {
        //     URL url = new URL("https://covers.openlibrary.org/b/id/8739161-M.jpg");
        //     BufferedImage image = ImageIO.read(url);
        //     ImageIO.write(image, "jpg", this.file);
        // } catch (IOException e) {
        //     // handle IOException
        // }
    }

    public void setUploadContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setUploadFileName(String filename) {
        this.filename = filename;
    }
    
    public List<String> getNames() {
        return Arrays.asList(names);
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getBase64Cover() {
        return base64Cover;
    }

    public void setBase64Cover(String base64Cover) {
        this.base64Cover = base64Cover;
    }
}
