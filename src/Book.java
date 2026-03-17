import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable {
    public int id;
    public String author;
    public String title;
    public String date;
    public String content;
    public boolean locked;
    public List<String> readers = new ArrayList<>();

    public Book(int id, String author, String title, String date, String content) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.date = date;
        this.content = content;
        this.locked = false;
    }
}