# table_view_x
TableViewX is an attempt to improve TableView to be able to handle custom user-defined types as data.  By using reflection, it will read the data structure and create columns for all properties.


Sample code:
```java

@FXML
TableViewX<Sample> tableViewX;
        
List<Sample> samples;
        
...

        // [OPTIONAL] Enables item-counting column
        tableViewX.setRowCounting(true);
        // [OPTIONAL] Sets the base index of the item-counting column to One-based
        tableViewX.setBaseIndex(1);
        // [OPTIONAL] Sets the title mode to "Camel Case With Spaces"
        tableViewX.setTitleStyle(TableViewX.TitleStyle.CAPITALIZE_SPACING);
        // Sets the contents
        tableViewX.setContent(samples);
```
