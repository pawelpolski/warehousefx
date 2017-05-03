package polskipawel.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import polskipawel.model.Model;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static javafx.scene.control.ButtonType.NO;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

public class Controller {

    @FXML
    public Button initializeAndAddButton;

    @FXML
    public Button editAndSaveButton;

    @FXML
    public Button cancelAndRemoveButton;

    @FXML
    public Label informationLabel;

    @FXML
    public ChoiceBox typeChoiseField;

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
    public TableColumn status;


    public Model model;

    public Controller() {
        model = new Model();
    }


    @FXML
    public void initializeAndAddButton(ActionEvent actionEvent) {
        if (initializeAndAddButton.getText().equals("Initialize data")) {
            initializeTableData();
        } else {
            addNewEquipment();
        }
    }

    public void initializeTableData() {
        editAndSaveButton.setDisable(false);
        cancelAndRemoveButton.setDisable(false);
        textField.setDisable(false);
        typeChoiseField.setDisable(false);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        serialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        typeChoiseField.getItems().addAll("Modem", "Horizon", "Router", "CI+");
        model.addStartingEquipments();
        table.setItems(model.getEquipments());
        initializeAndAddButton.setText("Add equipment");
        typeChoiseField.setValue("");
        table.centerShapeProperty();
        type.setStyle("-fx-alignment: CENTER");
        status.setStyle("-fx-alignment: CENTER");
        serialNumber.setStyle("-fx-alignment: CENTER");
        id.setStyle("-fx-alignment: CENTER");
    }

    public void addNewEquipment() {
        informationLabel.setText("");

        if (textField.getText().equals("") || typeChoiseField.getValue().toString().equals("")) {
            informationLabel.setTextFill(RED);
            informationLabel.setText("You have to write serial number and it's type!");
        } else {
            table.setItems(model.addEquipment(model.getLastId() + 1, textField.getText(), typeChoiseField.getValue().toString(), "in warehouse"));
            textField.setText("");
            informationLabel.setTextFill(GREEN);
            informationLabel.setText("Equipment has been added correctly!");
        }
    }

    public void editAndSaveButton(ActionEvent actionEvent) {
        try {
            if(editAndSaveButton.getText().equals("Edit")) {
                informationLabel.setText("");
                int selectionId = table.getSelectionModel().getSelectedIndex();
                textField.setText(model.getEquipments().get(selectionId).getSerialNumber());
                typeChoiseField.setValue(model.getEquipments().get(selectionId).getType());
                editAndSaveButton.setText("Save");
                table.setDisable(true);
                initializeAndAddButton.setDisable(true);
                cancelAndRemoveButton.setText("Cancel");
            }else{ // save pressed
                if (textField.getText().equals("") || typeChoiseField.getValue().toString().equals("")) {
                    informationLabel.setTextFill(RED);
                    informationLabel.setText("You have to write serial number and it's type!");
                } else {
                    informationLabel.setText("");
                    int selectionId = table.getSelectionModel().getSelectedIndex();
                    model.getEquipments().get(selectionId).setSerialNumber(textField.getText());
                    model.getEquipments().get(selectionId).setType((String) typeChoiseField.getValue());
                    table.refresh();
                    table.setDisable(false);
                    editAndSaveButton.setText("Edit");
                    cancelAndRemoveButton.setText("Remove");
                    textField.setText("");
                    initializeAndAddButton.setDisable(false);
                    informationLabel.setTextFill(GREEN);
                    informationLabel.setText("Equipment ID: "+ model.getEquipments().get(selectionId).getId()+" edited correctly.");

                }
            }
        } catch (ArrayIndexOutOfBoundsException e){
            informationLabel.setTextFill(RED);
            informationLabel.setText("If you want to edit, you have to select row!");
        }
    }

    public void cancelAndRemoveButton(ActionEvent actionEvent) {
        if (table.getSelectionModel().getSelectedIndex() < 0){
            informationLabel.setTextFill(RED);
            informationLabel.setText("If you want to remove, select row!");

        } else {
            if (cancelAndRemoveButton.getText().equals("Remove")) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                int selectionId = table.getSelectionModel().getSelectedIndex();
                alert.setTitle("Remove button pressed");
                alert.setHeaderText("Equipment ID: "+model.getEquipments().get(selectionId).getId()+" will be removed");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    model.removeEquipment(selectionId);
                    informationLabel.setTextFill(RED);
                    informationLabel.setText("Equipment has been removed");
                } else {
                    informationLabel.setText("");
                }
            } else { //cancell button is pressed
                table.setDisable(false);
                editAndSaveButton.setText("Edit");
                cancelAndRemoveButton.setText("Remove");
                textField.setText("");
                initializeAndAddButton.setDisable(false);
                informationLabel.setText("");
            }
        }

    }
}
