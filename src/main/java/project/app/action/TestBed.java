package project.app.action;

import java.util.Arrays;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

public class TestBed extends ActionSupport {
    private String select;
    private String[] names = {"Alexa", "Siri", "Jeeves"};

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
}
