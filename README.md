# table_view_x
TableView X is an attempt to improve TableView to be able to handle custom user-defined types as data.  By using reflection, it will read the data structure and create columns for all properties.


Sample code:
```java

@FXML
TableViewX<Sample> tableViewX;
        
List<Sample> samples;
        
...

  tableViewX.setContent(samples);
