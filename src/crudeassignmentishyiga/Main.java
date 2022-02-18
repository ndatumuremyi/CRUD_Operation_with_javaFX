/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crudeassignmentishyiga;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

/**
 *
 * @author pater
 */
public class Main extends Application{
    Db db;

    static int spacing = 23;
    private HBox hBox;
    private VBox insertNewData;
    private VBox displayData;
    private TableView<Data> dataTableView;

    @Override
    public void start(Stage stage) {
        db = new Db();

        HBox name = new HBox(spacing);
        HBox department = new HBox(spacing);
        HBox sex = new HBox(spacing);
        HBox favouriteLanguage = new HBox(spacing);

        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-color:red");
        StackPane buttonPane = new StackPane(saveButton);
        buttonPane.setAlignment(Pos.BOTTOM_CENTER);

        Button deleteButton = new Button("Delete selected");
        deleteButton.setStyle("-fx-color:red");
        StackPane deletePane = new StackPane(deleteButton);
//        buttonPane.setAlignment(Pos.BOTTOM_CENTER);


        Label labelForName = new Label("Name: ");
        TextField nameField = new TextField();
        name.getChildren().addAll(labelForName, nameField);
        HBox.setHgrow(nameField, Priority.ALWAYS);

        Label labelForDepartment = new Label("Department: ");
        TextField departmentField = new TextField();
        department.getChildren().addAll(labelForDepartment,departmentField);
        HBox.setHgrow(departmentField, Priority.ALWAYS);

        ObservableList<String> cbSex = FXCollections.observableArrayList("Female", "Male");

        Label labelForSex = new Label("Sex: ");
        ComboBox<String> sexCombo = new ComboBox<>();
        sexCombo.getItems().addAll(cbSex);
        sexCombo.setStyle("-fx-color: red");
        sexCombo.setValue("Male");
        sex.getChildren().addAll(labelForSex, sexCombo);


        ObservableList<String> languages = FXCollections.observableArrayList("java","python","nodejs","dart");
        Label labelForFavouriteLanguage = new Label("Choose your favourite");
        ComboBox<String> favouriteCombo = new ComboBox<>();
        favouriteCombo.getItems().addAll(languages);
        favouriteCombo.setStyle("-fx-color: red");
        favouriteCombo.setValue(languages.get(1));
        favouriteLanguage.getChildren().addAll(labelForFavouriteLanguage, favouriteCombo);


        dataTableView = new TableView<>();
        dataTableView.setEditable(true); // make it editable
        ObservableList<Data> dataObservableList = FXCollections.observableArrayList(db.getData());
        dataTableView.setItems(dataObservableList);

        TableColumn namesColumn = new TableColumn("Full name");
        namesColumn.setCellValueFactory(new PropertyValueFactory<Data,String>("fullName"));
        namesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        namesColumn.setOnEditCommit(event -> {fullName_onEditCommit(event);});

        TableColumn departmentColumn = new TableColumn("Department");
        departmentColumn.setCellValueFactory(new PropertyValueFactory<Data,String>("department"));
        departmentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        departmentColumn.setOnEditCommit(event -> {department_onEditCommit(event);});

        TableColumn sexColumn = new TableColumn("Sex");
        sexColumn.setCellValueFactory(new PropertyValueFactory<Data, String>("sex"));
        sexColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(),cbSex));
        sexColumn.setOnEditCommit(event -> sex_onEditCommit(event));

        TableColumn favouriteLanguageColumn = new TableColumn("Favourite");
        favouriteLanguageColumn.setCellValueFactory(new PropertyValueFactory<Data,String>("favouriteLanguage"));
        favouriteLanguageColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(),languages));
        favouriteLanguageColumn.setOnEditCommit(event -> favourite_onEditCommit(event));

        dataTableView.getColumns().addAll(namesColumn, departmentColumn, sexColumn, favouriteLanguageColumn);



        hBox = new HBox(spacing);
        insertNewData = new VBox(name, department, sex, favouriteLanguage, buttonPane);
        VBox.setMargin(buttonPane, new Insets(100,0,0,0));
        insertNewData.setSpacing(spacing);
        displayData = new VBox(dataTableView,deletePane);
        displayData.setSpacing(spacing);
        hBox.getChildren().addAll(insertNewData,displayData);
        
        Scene scene = new Scene(hBox);
        
        stage.setScene(scene);
        stage.setTitle("testing title");
        stage.show();









        saveButton.setOnAction(e->{
            String fullName = nameField.getText();
            String sexData = sexCombo.getValue();
            String departmentData = departmentField.getText();
            String favouriteL = favouriteCombo.getValue();

            Data data = new Data(sexData,departmentData,favouriteL,fullName);

            db.insertData(data);
            dataObservableList.setAll(db.getData());
//            dataObservableList.addAll(data);

            System.out.println(favouriteL);
        });

        deleteButton.setOnAction(e->{
            ObservableList<Data> sel, items;
            items = dataTableView.getItems();
            sel = dataTableView.getSelectionModel().getSelectedItems();
            for(Data d : sel){
                if(db.deleteData(d.getDataId())){
                    items.remove(d);
                }

            }
        });
    }
    public void fullName_onEditCommit(Event e){
        TableColumn.CellEditEvent<Data, String > ce;
        ce = (TableColumn.CellEditEvent<Data, String>)e;
        Data d = ce.getRowValue();
        d.setFullName(ce.getNewValue());

        db.updateData(d);
    }
    public void sex_onEditCommit(Event e){
        TableColumn.CellEditEvent<Data, String > ce;
        ce = (TableColumn.CellEditEvent<Data, String>)e;
        Data d = ce.getRowValue();
        d.setSex(ce.getNewValue());

        db.updateData(d);
    }
    public void favourite_onEditCommit(Event e){
        TableColumn.CellEditEvent<Data, String > ce;
        ce = (TableColumn.CellEditEvent<Data, String>)e;
        Data d = ce.getRowValue();
        d.setFavouriteLanguage(ce.getNewValue());

        db.updateData(d);
    }
    public void department_onEditCommit(Event e){
        TableColumn.CellEditEvent<Data, String > ce;
        ce = (TableColumn.CellEditEvent<Data, String>)e;
        Data d = ce.getRowValue();
        d.setDepartment(ce.getNewValue());

        db.updateData(d);
    }
    public static void main(String arg[]){
        Application.launch(arg);
    }
    
}
