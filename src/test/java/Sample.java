import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Sample {
    private int id;
    private String name;
    private LocalDateTime time;
    private String[] args;
    private List<String> list;

    public Sample(int id, String name) {
        this.id = id;
        this.name = name;
        time = LocalDateTime.now();
        list = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "id -> " + id + ", name -> " + name;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}