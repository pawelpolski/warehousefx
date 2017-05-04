package polskipawel.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import polskipawel.model.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

public class Controller {

    public Model model;

    public Controller() {
        model = new Model();

    }


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
    @FXML
    public Button exportToExcel;
    @FXML
    public TextField filterField;
    @FXML
    public Button filterButton;
    @FXML
    public TextArea textArea;
    @FXML
    public Button addFromTextArea;


    @FXML
    public void initializeAndAddButton(ActionEvent actionEvent) throws IOException {
        if (initializeAndAddButton.getText().equals("Initialize data")) {


            String CsvFile = "Equipments.csv";
            BufferedReader CSV = new BufferedReader(new FileReader(CsvFile));
            String dataRow = CSV.readLine();
            Object[] tempList = CSV.lines().toArray();
            for (int i = 0; i < tempList.length; i++) {
                String cos = tempList[i].toString();
                String[] cos2 = cos.split(",");
                model.addEquipment(Integer.parseInt(cos2[0]), cos2[1], cos2[2], cos2[3]);
            }
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

        typeChoiseField.getItems().addAll("Modem", "Router", "Horizon DVR High", "Horizon DVR Low", "Horizon HD High", "Mediamodul CI+", "Pace DCR7111", "Cisco HD8685");
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
            if (editAndSaveButton.getText().equals("Edit")) {
                informationLabel.setText("");
                int selectionId = table.getSelectionModel().getSelectedIndex();
                textField.setText(model.getEquipments().get(selectionId).getSerialNumber());
                typeChoiseField.setValue(model.getEquipments().get(selectionId).getType());
                editAndSaveButton.setText("Save");
                table.setDisable(true);
                initializeAndAddButton.setDisable(true);
                cancelAndRemoveButton.setText("Cancel");
            } else { // save pressed
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
                    informationLabel.setText("Equipment ID: " + model.getEquipments().get(selectionId).getId() + " edited correctly.");

                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            informationLabel.setTextFill(RED);
            informationLabel.setText("If you want to edit, you have to select row!");
        }
    }

    public void cancelAndRemoveButton(ActionEvent actionEvent) {
        if (table.getSelectionModel().getSelectedIndex() < 0) {
            informationLabel.setTextFill(RED);
            informationLabel.setText("If you want to remove, select row!");

        } else {
            if (cancelAndRemoveButton.getText().equals("Remove")) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                int selectionId = table.getSelectionModel().getSelectedIndex();
                alert.setTitle("Remove button pressed");
                alert.setHeaderText("Equipment ID: " + model.getEquipments().get(selectionId).getId() + " will be removed");

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

    public void fieldsDisabler(boolean trueOrFalse) {
        textField.setDisable(trueOrFalse);
        typeChoiseField.setDisable(trueOrFalse);
        cancelAndRemoveButton.setDisable(trueOrFalse);
        editAndSaveButton.setDisable(trueOrFalse);
        initializeAndAddButton.setDisable(trueOrFalse);
        exportToExcel.setDisable(trueOrFalse);
    }

    public void exportToExcel(ActionEvent actionEvent) throws Exception {
        model.writeExcel();
    }

    public void filterDataButton(ActionEvent actionEvent) {
        if (filterButton.getText().equals("Clear filter") || filterField.getText().equals("")) {
            table.setItems(model.getEquipments());
            fieldsDisabler(false);
            filterField.setText("");
            filterButton.setText("Filter data");
        } else {
            if (!filterField.getText().isEmpty()) {
                filterButton.setText("Clear filter");
                model.getFilteredEquipments().clear();
                for (int i = 0; i < model.getEquipments().size(); i++) {
                    int charLength = filterField.getLength();
                    if (filterField.getText().substring(0, charLength).toLowerCase().equals(model.getEquipments().get(i).getType().substring(0, charLength).toLowerCase())) {
                        model.getFilteredEquipments().addAll(model.getEquipments().get(i));
                    }
                }
                table.setItems(model.getFilteredEquipments());
                fieldsDisabler(true);
            }
        }
    }

    public void addEquipmentsFromTextArea() {
        String[] rows = textArea.getText().split("\n");
        int rowsSize = rows.length;
        for (int i = 0; i < rowsSize; i++) {
            table.setItems(model.addEquipment(model.getLastId() + 1, rows[i], typeChoiseField.getValue().toString(), "in warehouse"));
            textArea.setText("");


        }
    }


}
