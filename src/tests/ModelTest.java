//
//
//
//
//
//package tests;
//
//        import org.junit.jupiter.api.Test;
//        import polskipawel.model.Model;
//
//        import java.io.IOException;
//        import java.util.ArrayList;
//
//        import static org.junit.jupiter.api.Assertions.assertEquals;
//
///**
// * Created by macbook on 07.05.2017.
// */
//class ModelTest {
//
//    /**
//     *
//     *ModelTest
//     * tthis is class which tests methods from model
//     */
//
//
//    @org.junit.jupiter.api.Test
//    void addTypesOfEquipmentsToList() throws IOException {
//
//        Model model = new Model();
//        ArrayList<String> equipmentsTypesTest = new ArrayList<>();
//        equipmentsTypesTest.add("Pace DCR7111");
//        equipmentsTypesTest.add("Cisco HD8485");
//        equipmentsTypesTest.add("Cisco HD8685");
//        equipmentsTypesTest.add("Horizon HD High");
//        equipmentsTypesTest.add("Horizon DVR High");
//        equipmentsTypesTest.add("Horizon DVR Low");
//        equipmentsTypesTest.add("Mediamudul CI+");
//        equipmentsTypesTest.add("Modem");
//        equipmentsTypesTest.add("Router");   //commented jus for test
//        assertEquals(equipmentsTypesTest, model.getEquipmentsTypes());
//    }
//
//    @org.junit.jupiter.api.Test
//    void addEquipment() throws IOException {
//        Model model = new Model();
//        model.addEquipment(1, "", "", "");
//        assertEquals(13, model.getEquipments().size());
//
//    }
//
//
//    @org.junit.jupiter.api.Test
//    void getLastId() throws IOException {
//        Model model = new Model();
//        int testing = 5;
//        model.addEquipment(1, "", "", "");
//        model.addEquipment(2, "", "", "");
//        model.addEquipment(5, "", "", "");
//        assertEquals(testing, model.getLastId());
//    }
//
//    @org.junit.jupiter.api.Test
//    void removeEquipment() throws IOException {
//        Model model = new Model();
//
//        model.addEquipment(5, "", "", "");
//        model.addEquipment(6, "", "", "");
//        model.addEquipment(7, "", "", "");
//        model.removeEquipment(0);
//
//        assertEquals(14, model.getEquipments().size());
//    }
//
//    @org.junit.jupiter.api.Test
//    void writeExcel() {
//        // no idea how to test
//    }
//
//}