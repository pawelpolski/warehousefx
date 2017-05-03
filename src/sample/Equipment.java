package sample;

/**
 * Created by macbook on 01.05.2017.
 */
public class Equipment {

    private int id;
    private String serialNumber;
    private String type;


    public Equipment(int id, String serialNumber, String type){
        this.id = id;
        this.serialNumber = serialNumber;
        this.type = type;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
