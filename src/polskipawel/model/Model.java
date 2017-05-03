package polskipawel.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

/**
 * Created by macbook on 01.05.2017.
 */
public class Model {

    private ObservableList<Equipment> equipments = FXCollections.observableArrayList();

    public Model() {

    }

    public void addStartingEquipments() {
        equipments.add(new Equipment(1, "179267128194", "Horizon", "in warehouse"));
        equipments.add(new Equipment(2, "28R281224003", "Router", "in warehouse"));
        equipments.add(new Equipment(3, "SAAP50421933", "Modem", "in warehouse"));
    }

    public ObservableList<Equipment> addEquipment(int id, String sn, String type, String status) {
        equipments.add(new Equipment(id, sn, type, status));
        return equipments;
    }

    public int getLastId() {
        int i = equipments.size();
        return equipments.get(i - 1).getId();
    }

    public ObservableList<Equipment> removeEquipment(int index) {
        equipments.remove(index);
        return equipments;
    }

    public ObservableList<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(ObservableList<Equipment> equipments) {
        this.equipments = equipments;

    }
    public void writeExcel() throws Exception {
        Writer writer = null;
        try {
            File file = new File("Equipments.csv");
            writer = new BufferedWriter(new FileWriter(file));
            String columns = "ID,Serial Number,Type,Status\n";
            writer.write(columns);
            for (Equipment equipment : equipments) {

                String text = equipment.getId() + "," + equipment.getSerialNumber() + ","+equipment.getType()+","+equipment.getStatus()  + "\n";

                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {

            writer.flush();
            writer.close();
        }
    }
}
