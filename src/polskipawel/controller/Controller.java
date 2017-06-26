package polskipawel.controller;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import polskipawel.model.Equipment;
import polskipawel.model.Model;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Optional;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

public class Controller {

    public Model model;

    public Controller() throws IOException {
        model = new Model();

    }

    PrintWriter writer;
    Socket socket;
    BufferedWriter os = null;
    BufferedReader is = null;
    ObjectOutputStream out = null;
    ObjectInputStream in = null;



    private ArrayList<String> equipmentsTypes = new ArrayList<>();

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


    /**
     * This button listnes for action and Initialize data to table view, or adds new equipment
     */
    public void initializeAndAddButton(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        equipmentsTypes.add("Pace DCR7111");
        equipmentsTypes.add("Cisco HD8485");
        equipmentsTypes.add("Cisco HD8685");
        equipmentsTypes.add("Horizon HD High");
        equipmentsTypes.add("Horizon DVR High");
        equipmentsTypes.add("Horizon DVR Low");
        equipmentsTypes.add("Mediamudul CI+");
        equipmentsTypes.add("Modem");
        equipmentsTypes.add("Router");


        if (initializeAndAddButton.getText().equals("Initialize data")) {

            setsPropertyValueOnTable();
            justConnect();

            System.out.println("Zainicjalizowano dane");


            //os.write("test");
            //   os.newLine();
            //  os.flush();


            // Read data sent from the server.
            // By reading the input stream of the Client Socket.
//                String responseLine;
//                while ((responseLine = is.readLine()) != null) {
//                    System.out.println("Server: " + responseLine);
//                    if (responseLine.indexOf("OK") != -1) {
//                        break;


            initializeTableDataFromList();

            filterField.setDisable(false);

        } else { //Button has "Add equipment" value

            addNewEquipment();

            String lineFromServer = is.readLine();
            System.out.println(lineFromServer + "z przycisku");
        }

    }

    public void justConnect() {
        try {
            socket = new Socket("localhost", 9999);
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("# Connected to server on: " + socket.getPort() + " port!");
            // Create output stream at the client (to send data to the server)
            os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // Input stream at Client (Receive data from the server).
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            BufferedReader stdIn =
                    new BufferedReader(
                            new InputStreamReader(System.in));
            BufferedWriter stdout =
                    new BufferedWriter(
                            new OutputStreamWriter(System.out));


            os.write("Hello");
            os.newLine();
            os.flush();
            String lineFromServer = is.readLine();
            System.out.println(">> Server sends back welcome msg: " + lineFromServer + " # # ");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//
//            // Write data to the output stream of the Client Socket.
//            os.write("HELO");
//
//            // End of line
//            os.newLine();
//
//            // Flush data.
//            os.flush();
//            os.write("I am Tom Cat");
//            os.newLine();
//            os.flush();

//
//            os.write(test);
//            os.newLine();
//            os.flush();
//
//
//
//            // Read data sent from the server.
//            // By reading the input stream of the Client Socket.

//
////            os.close();
////            is.close();
////            socketOfClient.close();
//        } catch (UnknownHostException e) {
//            System.err.println("Trying to connect to unknown host: " + e);
//        } catch (IOException e) {
//            System.err.println("IOException:  " + e);
//        }
    }


    /**
     * Equipment can be edited via this button, you have to select row then type correct values and save/cancel
     */
    public void editAndSaveButton(ActionEvent actionEvent) {
        try {
            if (editAndSaveButton.getText().equals("Edit")) { //edit button is presed
                informationLabel.setText("");
                int selectionId = table.getSelectionModel().getSelectedIndex();
                textField.setText(model.getEquipments().get(selectionId).getSerialNumber());
                typeChoiseField.setValue(model.getEquipments().get(selectionId).getType());
                editAndSaveButton.setText("Save");
                table.setDisable(true);
                initializeAndAddButton.setDisable(true);
                exportToExcel.setDisable(true);
                filterButton.setDisable(true);
                addFromTextArea.setDisable(true);
                cancelAndRemoveButton.setText("Cancel");
            } else { // save button pressed
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
                    fieldsAndButtonsDisabler(false);
                    informationLabel.setTextFill(GREEN);
                    informationLabel.setText("Equipment ID: " + model.getEquipments().get(selectionId).getId() + " edited correctly.");
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            informationLabel.setTextFill(RED);
            informationLabel.setText("If you want to edit, you have to select row!");
        }
    }


    /**
     * if remove/cancel button is pressed, equipment can be removed, after remove button pressed confirmation box will appear
     */
    public void cancelAndRemoveButton(ActionEvent actionEvent) {
        if (table.getSelectionModel().getSelectedIndex() < 0) {
            informationLabel.setTextFill(RED);
            informationLabel.setText("If you want to remove, select row!");

        } else {
            if (cancelAndRemoveButton.getText().equals("Remove")) { //remove button is pressed

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


    /**
     * Creates new list of equipments based on type filter, just to see how many equipments of type we have
     * no need to input excactly the same type, can be lowercase, half word
     */
    public void filterTypes(KeyEvent keyEvent) {

        filterButton.setText("Clear filter");
        model.getFilteredEquipments().clear();

        for (Equipment equipment : model.getEquipments()) {

            String type = equipment.getType().toLowerCase();
            String typeField = filterField.getText().toLowerCase();

            try {
                if (type.contains(typeField))
                    model.getFilteredEquipments().addAll(equipment);
            } catch (StringIndexOutOfBoundsException e) {

            }
        }

        table.setItems(model.getFilteredEquipments());
        int numberEquipmentsOfThisType = model.getFilteredEquipments().size();
        fieldsAndButtonsDisabler(true);
        filterButton.setDisable(false);
        informationLabel.setText("On warehouse there is: " + numberEquipmentsOfThisType + " equipment of this Type");
    }


    /**
     * leave filter mode and change buttons
     */
    public void filterDataButton(ActionEvent actionEvent) {
        table.setItems(model.getEquipments());
        fieldsAndButtonsDisabler(false);
        filterField.setText("");
        filterButton.setText("Filter data");
        clearAllFields();
    }


    /**
     * if you have alot of equipments which has the same type and want to add them quickly you can use this method
     * to add all from text area, return button is spliterator, so excelent for barcode reader
     */
    public void addEquipmentsFromTextArea() {
        try {
            if (textArea.getText().isEmpty() || textArea.getText().length() < 9) {
                informationLabel.setTextFill(RED);
                informationLabel.setText("Please enter a few correct serial numbers!");
            } else {
                String[] rows = textArea.getText().split("\n");

                for (String equipment : rows) {
                    table.setItems(model.addEquipment(model.getLastId() + 1, equipment, typeChoiseField.getValue().toString(), "in warehouse"));
                    clearAllFields();
                    informationLabel.setTextFill(GREEN);
                    informationLabel.setText("Equipment has been added correctly!");
                }
            }
        } catch (NullPointerException e) {
            informationLabel.setTextFill(RED);
            informationLabel.setText("Choose equipment type");
        }
    }


    /**
     * add new equipment to list and table, checks if all inputs are correct - not empty, gives information to user
     */
    public void addNewEquipment() throws IOException {
        informationLabel.setText("");
        try {
            if (textField.getText().equals("") || textField.getText().length() < 9) {
                informationLabel.setTextFill(RED);
                informationLabel.setText("You have to write correct serial number (Minimum 9 chars)!");
            } else {
                table.setItems(model.addEquipment(model.getLastId() + 1, textField.getText(), typeChoiseField.getValue().toString(), "in warehouse"));
                textField.setText("");
                informationLabel.setTextFill(GREEN);
                informationLabel.setText("Equipment has been added correctly!");
            }
        } catch (NullPointerException e) {
            informationLabel.setTextFill(RED);
            informationLabel.setText("Choose equipment type");
        }
    }

    /**
     * Initialioze data from equipments list to table and changes status of buttons, fields
     */
    public void initializeTableDataFromList() throws IOException, ClassNotFoundException {


        fieldsAndButtonsDisabler(false);
        clearAllFields();
        typeChoiseField.getItems().addAll(equipmentsTypes);
        table.setItems(model.getEquipments());
        initializeAndAddButton.setText("Add equipment");
    }

    /**
     * Initialioze data from equipments list to table and changes status of buttons, fields
     */
    public void setsPropertyValueOnTable() throws IOException {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        serialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        makeTableViewCentered();
    }

    /**
     * Sets style for table to be centered
     */
    public void makeTableViewCentered() {
        table.centerShapeProperty();
        type.setStyle("-fx-alignment: CENTER");
        status.setStyle("-fx-alignment: CENTER");
        serialNumber.setStyle("-fx-alignment: CENTER");
        id.setStyle("-fx-alignment: CENTER");
    }


    /**
     * Listner for excel (csv file) button action, if pressed starts method from model which saves table view into file
     *
     * @param actionEvent
     */
    public void exportToExcel(ActionEvent actionEvent) throws Exception {
        model.writeExcel();
        informationLabel.setText("Saved table to CSV file!");
    }


    /**
     * Clears all values in inputs fields label, and choice field
     */
    public void clearAllFields() {
        textArea.setText("");
        textField.setText("");
        filterField.setText("");
        informationLabel.setText("");
    }

    /**
     * Disable or enable fields and buttons depends on ststus
     *
     * @param trueOrFalse if true then fields are disabled
     */
    public void fieldsAndButtonsDisabler(boolean trueOrFalse) {
        textField.setDisable(trueOrFalse);
        typeChoiseField.setDisable(trueOrFalse);
        cancelAndRemoveButton.setDisable(trueOrFalse);
        editAndSaveButton.setDisable(trueOrFalse);
        initializeAndAddButton.setDisable(trueOrFalse);
        exportToExcel.setDisable(trueOrFalse);
        addFromTextArea.setDisable(trueOrFalse);
        filterButton.setDisable(trueOrFalse);
    }


}
