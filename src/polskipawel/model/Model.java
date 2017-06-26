package polskipawel.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by macbook on 01.05.2017.
 */
public class Model {

    /**
     * Equipments list which can be displayed in JavaFX's TableView
     */
    private ObservableList<Equipment> equipments = FXCollections.observableArrayList();

    /**
     * Fildered equipments l
     * ist which can be displayed in JavaFX's TableView when needed.
     */
    private ObservableList<Equipment> filteredEquipments = FXCollections.observableArrayList();

    /**
     * Stores equipments types in array list
     */
    private ArrayList<String> equipmentsTypes = new ArrayList<>();

    public Model() throws IOException {

        addTypesOfEquipmentsToList();
        getDataFromFileAndAddToTableList();
    }

    public void getDataFromFileAndAddToTableList() throws IOException {
        BufferedReader csvFile = new BufferedReader(new FileReader("Equipments.csv"));
        String mainRow = csvFile.readLine();
        Object[] tempList = csvFile.lines().toArray();
        for (int i = 0; i < tempList.length; i++) {
            String row = tempList[i].toString();
            String[] splitedRow = row.split(",");
            addEquipment(Integer.parseInt(splitedRow[0]), splitedRow[1], splitedRow[2], splitedRow[3]);

        }
    }

    /**
     * add fixed types of equipments to array list
     */
    public void addTypesOfEquipmentsToList() {
        equipmentsTypes.add("Pace DCR7111");
        equipmentsTypes.add("Cisco HD8485");
        equipmentsTypes.add("Cisco HD8685");
        equipmentsTypes.add("Horizon HD High");
        equipmentsTypes.add("Horizon DVR High");
        equipmentsTypes.add("Horizon DVR Low");
        equipmentsTypes.add("Mediamudul CI+");
        equipmentsTypes.add("Modem");
        equipmentsTypes.add("Router");
    }

    /**
     * Method alows to add new Equipment to list
     *
     * @param id     Equipment id
     * @param sn     Equipment serialNumber
     * @param type   Equipment type
     * @param status Equipment status
     * @return returns updated list of equipments
     */
    public ObservableList<Equipment> addEquipment(int id, String sn, String type, String status) {
        equipments.add(new Equipment(id, sn, type, status));
        return equipments;
    }


    /**
     * Method finds Equipments list size
     *
     * @return returns last row from equipment list and it's id.
     */
    public int getLastId() {
        int i = equipments.size();
        return equipments.get(i - 1).getId();
    }

    /**
     * Method removes row from Equipments list
     *
     * @param index index of equipment which is going to be removed
     * @return returns updeted list, without removed row
     */
    public ObservableList<Equipment> removeEquipment(int index) {
        equipments.remove(index);
        return equipments;
    }

    /**
     * This is method which lets me export Table View list into Equipments.csv
     * This file is "dataBase-like" for temprary usage until we get to know about data base connections.
     */
    public void writeExcel() throws Exception {
        Writer writer = null;
        try {
            File file = new File("Equipments.csv");
            writer = new BufferedWriter(new FileWriter(file));
            String columns = "ID,Serial Number,Type,Status\n";
            writer.write(columns);
            for (Equipment equipment : equipments) {
                String text = equipment.getId() + "," + equipment.getSerialNumber() + "," + equipment.getType() + "," + equipment.getStatus() + "\n";
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }


    /**
     * Getters and Setters for ObservableLists
     */
    public ObservableList<Equipment> getFilteredEquipments() {
        return filteredEquipments;
    }

    public void setFilteredEquipments(ObservableList<Equipment> filteredEquipments) {
        this.filteredEquipments = filteredEquipments;
    }

    public ObservableList<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(ObservableList<Equipment> equipments) {
        this.equipments = equipments;
    }

    public ArrayList<String> getEquipmentsTypes() {
        return equipmentsTypes;
    }

    public void setEquipmentsTypes(ArrayList<String> equipmentsTypes) {
        this.equipmentsTypes = equipmentsTypes;
    }
}
