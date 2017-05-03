package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by macbook on 01.05.2017.
 */
public class EquipmentList {

    static ObservableList<Equipment> equipments = FXCollections.observableArrayList();

    public EquipmentList(){

        }


    public static ObservableList<Equipment> getAllEquipments(){

        equipments.add(new Equipment(1,"10101011","ci+"));
        equipments.add(new Equipment(2,"10101012","Router"));
        equipments.add(new Equipment(3,"10101013","Modem"));

        return equipments;
    }

    public static ObservableList<Equipment> addEquipment(int id, String sn, String type){

        equipments.add(new Equipment(id,sn,type));
        return equipments;
    }

    public static int getLastId(){

        int i = equipments.size();

        return equipments.get(i-1).getId();

    }

}
