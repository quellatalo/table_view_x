public class Sample {
    private int id;
    private String name;
    private boolean isFine;
    private boolean good;

    public Sample() {
    }

    public Sample(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean isFine() {
        return isFine;
    }

    public void setFine(boolean fine) {
        isFine = fine;
    }

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
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