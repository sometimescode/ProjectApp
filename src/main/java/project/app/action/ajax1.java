package project.app.action;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

class test {
    public String x;
    public int y;

    public test(String x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "test [x=" + x + ", y=" + y + "]";
    }

    
}

public class ajax1 extends ActionSupport {
    private List<test> testing;
    private String results;
    // https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/Struts-2-jQuery-Plugin-Ajax-Template-Example-Tutorial-Apache-v-25
    public String execute() {
        return SUCCESS;
    }

    public String loader() {
        testing = new ArrayList<test>();
        testing.add(new test("Test1", 1));
        testing.add(new test("Test2", 2));
        testing.add(new test("Test3", 3));
        System.out.println("this is test "+ testing.toString());
        return SUCCESS;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public List<test> getTesting() {
        return testing;
    }

    public void setTesting(List<test> testing) {
        this.testing = testing;
    }
}
