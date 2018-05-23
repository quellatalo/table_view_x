# table_view_x
TableViewX is an attempt to improve TableView to be able to handle custom user-defined types as data.  By using reflection, it will read the data structure and create columns for all properties.


Sample code:

Sample Class
```java
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
```

FXML
```xml
<TableViewX fx:id="tableViewX" displayHashCode="true" titleStyle="CAPITALIZE_SPACING"/>
```

Controller
```java
@FXML
TableViewX<Sample> tableViewX;
        
...

        List<Sample> samples = new ArrayList<>();
        samples.add(new Sample(1, "one"));
        samples.add(new Sample(2, "two"));
        samples.add(new Sample(3, "three"));
        samples.add(new Sample(10, "ten"));

        // [OPTIONAL] Enables item-counting column
        tableViewX.setRowCounting(true);
        // [OPTIONAL] Sets row-counting column title
        tableViewX.setRowCounterTitle("Count");
        // [OPTIONAL] Sets the base index of the item-counting column to One-based
        tableViewX.setBaseIndex(1);
        // [OPTIONAL] Sets the title mode to "Camel Case With Spaces"
        tableViewX.setTitleStyle(TableViewX.TitleStyle.CAPITALIZE_SPACING);
        // [OPTIONAL] Sets the base index of the item-counting column to One-based
        tableViewX.setOnlyStringAndPrimitives(true);
        // [OPTIONAL] Display item type
        tableViewX.setDisplayClass(true);
        // [OPTIONAL] Display HashCode
        tableViewX.setDisplayHashCode(true);

        // Sets the contents
        tableViewX.setContent(samples);
```
