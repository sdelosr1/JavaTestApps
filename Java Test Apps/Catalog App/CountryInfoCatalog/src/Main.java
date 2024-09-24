import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class Main extends Application {

    private static CountryCatalog catalog = new CountryCatalog();
    private CountryPane countryPane = new CountryPane();
    private ComboBox<String> comboBox = new ComboBox<>();
    private ObservableList<String> list = comboBox.getItems();
    private Button addButton = new Button("Add Country");
    private Button editButton = new Button("Edit");
    private Button saveButton = new Button("Save");
    private Button deleteButton = new Button("Delete");
    private Button goBackButton = new Button("Go Back");
    private Boolean home = true;
    private Boolean addOn = false;
    private int countryIndex = 0;

    @Override
    public void start(Stage stage) {

        list.addAll(catalog.getCountryNames());
        if(!catalog.isEmpty()) {
            countryPane.setCountry(catalog.getCountry(countryIndex));
            comboBox.setValue(list.get(countryIndex));
        }

        setComboBox();
        setAddButton();
        setEditButton();
        setDeleteButton();
        setSaveButton();
        setGoBackButton();

        HBox topUI = new HBox(10);
        topUI.getChildren().addAll(addButton, comboBox, editButton, deleteButton);
        topUI.setAlignment(Pos.CENTER);

        HBox bottomUI = new HBox(10);
        bottomUI.getChildren().addAll(goBackButton, saveButton);
        bottomUI.setAlignment(Pos.CENTER_RIGHT);

        VBox mainPane = new VBox(10);
        mainPane.getChildren().addAll(topUI, countryPane, bottomUI);
        mainPane.setPadding(new Insets(40,70,20,70));

        Scene scene = new Scene(mainPane,730,440);
        stage.setTitle("Country Catalog");
        stage.setScene(scene);
        stage.show();
    }

    public void setComboBox() {
        comboBox.setPrefWidth(500);
        comboBox.setOnAction(e ->{
            // Update country index to selected index on comboBox
            if(!catalog.isEmpty()) {
                countryIndex = list.indexOf(comboBox.getValue());
                if(countryIndex == -1){
                    comboBox.setValue(list.getFirst());
                    return;
                }
                countryPane.setCountry(catalog.getCountry(countryIndex));
            }
        });
    }

    public void switchMode(){
        home = !home;
        addButton.setVisible(home);
        comboBox.setVisible(home);
        editButton.setVisible(home);
        deleteButton.setVisible(home);
        saveButton.setVisible(!home);
        goBackButton.setVisible(!home);
        countryPane.setEditable(!home);
    }
    public void setAddButton(){
        addButton.setMinWidth(90);
        addButton.setOnAction(e ->{
            switchMode();
            countryPane.clearText();
            addOn = !addOn;
        });
    }
    public void setEditButton(){
        editButton.setMinWidth(90);
        editButton.setOnAction(e ->{
            if(!catalog.isEmpty()) {
                switchMode();
            }
        });
    }
    public void setDeleteButton() {
        deleteButton.setMinWidth(90);
        deleteButton.setOnAction(e ->{
            if(!catalog.isEmpty()) {
                catalog.remove(countryIndex);
                list.remove(countryIndex);
                if (list.isEmpty()) {
                    countryPane.clearText();
                }
            }
        });
    }
    public void setSaveButton(){
        saveButton.setMinWidth(90);
        saveButton.setVisible(false);
        saveButton.setOnAction(e ->{
            if(addOn){
                catalog.add(new Country(countryPane.getTextName(), countryPane.getTextInfo()));
                list.add(countryPane.getTextName());
                countryIndex = list.size()-1;
                addOn = !addOn;
            }else{
                catalog.editCountry(countryIndex, countryPane.getTextName(), countryPane.getTextInfo());
                list.add(countryIndex, countryPane.getTextName());
                list.remove(countryIndex + 1);
            }
            comboBox.setValue(list.get(countryIndex));
            switchMode();
        });
    }

    public void setGoBackButton(){
        goBackButton.setMinWidth(90);
        goBackButton.setVisible(false);
        goBackButton.setOnAction(e ->{
            switchMode();
            if(addOn){
                countryPane.clearText();
                addOn = !addOn;
            }
            if(!catalog.isEmpty()) {
                comboBox.setValue(list.get(countryIndex));
                countryPane.setCountry(catalog.getCountry(countryIndex));
            }
        });
    }

    private void editPrompt() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Edit? Y or N : ");
        String edit = scan.nextLine();
        if(edit.equals("Y")){
            System.out.print("name: ");
            String name = scan.nextLine();
            System.out.print("info: ");
            String info = scan.nextLine();
            Country userCountry = new Country(name,info);
            catalog.add(userCountry);
            catalog.print();
        }
        scan.close();
    }

    public static void main(String[] args) throws IOException {

        catalog.setCountryList(Data.load());

        catalog.print();

        Application.launch(args);

        // Backdoor editing for Testing
        //editPrompt();

        Data.save(catalog.getCountryList());

    }
}