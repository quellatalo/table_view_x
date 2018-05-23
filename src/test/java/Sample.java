public class Sample {
    private int id;
    private String name;

    public Sample() {
    }

    public Sample(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void print() {
        System.out.println(getClass().getDeclaredFields().length);
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

    @Override
    public String toString() {
        return "id -> " + id + ", name -> " + name;
    }
}