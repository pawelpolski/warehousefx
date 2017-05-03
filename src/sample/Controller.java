package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.xml.soap.Text;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

public class Controller {
    @FXML
    public TextField textField2;
    public Button button;
    public Label informationLabel;
    public ChoiceBox typeChoise;

    public Controller(){

    }

    @FXML
    public TextField textField;

    @FXML
    public TableView table;

    @FXML
    public TableColumn id;

    @FXML
    public TableColumn serialNumber;
    @FXML
    public TableColumn type;


    @FXML
    public void buttonMethod(ActionEvent actionEvent) {

        if (button.getText().equals("Initialize data")){
            initializeTableData();
        } else {
            addNewEquipment();
        }
    }

    public void initializeTableData(){
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        serialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));

        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        typeChoise.getItems().addAll("Modem","Horizon","Router","CI+");
        table.setItems(EquipmentList.getAllEquipments());
        button.setText("Add equipment");
        typeChoise.setValue("");
        textField.setVisible(true);
        typeChoise.setVisible(true);
    }

    public void addNewEquipment(){
        informationLabel.setText("");

        if (textField.getText().equals("") || typeChoise.getValue().toString().equals("")){
            informationLabel.setTextFill(RED);

            informationLabel.setText("You have to write serial number and it's type!");
        } else {
            table.setItems(EquipmentList.addEquipment(EquipmentList.getLastId() + 1, textField.getText(), typeChoise.getValue().toString()));
            textField.setText("");
            informationLabel.setTextFill(GREEN);
            informationLabel.setText("Equipment has been added correctly!");
        }
    }
}
