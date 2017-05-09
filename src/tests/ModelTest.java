package tests;

import polskipawel.model.Model;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by macbook on 07.05.2017.
 */
class ModelTest {

    @org.junit.jupiter.api.Test
    void addTypesOfEquipmentsToList() {

        Model model = new Model();
        ArrayList<String> equipmentsTypesTest = new ArrayList<>();
        equipmentsTypesTest.add("Pace DCR7111");
        equipmentsTypesTest.add("Cisco HD8485");
        equipmentsTypesTest.add("Cisco HD8685");
        equipmentsTypesTest.add("Horizon HD High");
        equipmentsTypesTest.add("Horizon DVR High");
        equipmentsTypesTest.add("Horizon DVR Low");
        equipmentsTypesTest.add("Mediamudul CI+");
        equipmentsTypesTest.add("Modem");
        //equipmentsTypesTest.add("Router");   //commented jus for test
        assertEquals(equipmentsTypesTest, model.getEquipmentsTypes());
    }

    @org.junit.jupiter.api.Test
    void addEquipment() {
        Model model = new Model();
        model.addEquipment(1, "", "", "");
        assertEquals(1, model.getEquipments().size());

    }


    @org.junit.jupiter.api.Test
    void getLastId() {
        Model model = new Model();
        int testing = 5;
        model.addEquipment(1, "", "", "");
        model.addEquipment(2, "", "", "");
        model.addEquipment(5, "", "", "");
        assertEquals(testing, model.getLastId());
    }

    @org.junit.jupiter.api.Test
    void removeEquipment() {
        Model model = new Model();

        model.addEquipment(5, "", "", "");
        model.addEquipment(6, "", "", "");
        model.addEquipment(7, "", "", "");
        model.removeEquipment(0);

        assertEquals(2, model.getEquipments().size());
    }

    @org.junit.jupiter.api.Test
    void writeExcel() {
        // no idea how to test
    }

}