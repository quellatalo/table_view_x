# table_view_x
TableViewX is an attempt to improve TableView to be able to handle custom user-defined types as data.  By using reflection, it will read the data structure and create columns for all properties.
### Usage
##### Maven
```xml
<dependency>
    <groupId>io.github.quellatalo.fx</groupId>
    <artifactId>table-view-x</artifactId>
    <version>1.7.4.1</version>
</dependency>
```
### Sample code:

##### Sample Class
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

##### FXML
```xml
<TableViewX fx:id="tableViewX" displayHashCode="true" titleStyle="CAPITALIZE_SPACING"/>
```

##### Controller
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
        // [OPTIONAL] Narrow down display properties to String and Primitive types only
        tableViewX.setStringAndPrimitivesOnly(true);
        // [OPTIONAL] Display item type
        tableViewX.setDisplayClass(true);
        // [OPTIONAL] Display HashCode
        tableViewX.setDisplayHashCode(true);
        //// [OPTIONAL] Forced Display Types
        //// In case you want to display some specific properties which return a custom type
        //// It will display the result of toString()
        //tableViewX.getForcedDisplayTypes().add(SomeType.class);
        // [OPTIONAL] Display Map and Collection properties also
        tableViewX.setDisplayMapsAndCollections(true);

        // Sets the contents
        tableViewX.setContent(samples);
        
        // force open filter
        tableViewX.openFilter();
```
