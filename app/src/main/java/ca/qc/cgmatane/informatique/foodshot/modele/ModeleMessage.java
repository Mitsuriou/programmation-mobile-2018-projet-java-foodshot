package ca.qc.cgmatane.informatique.foodshot.modele;

public class ModeleMessage {

    private int code;
    private String type;
    private String message;

    public ModeleMessage(int code, String type, String message) {
        this.code = code;
        this.type = type;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
