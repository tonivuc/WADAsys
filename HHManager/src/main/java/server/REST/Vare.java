package server.REST;

public class Vare {
    int vareId;
    int handlelisteId;
    int hhBrukerId;
    String varenavn;
    boolean kjøpt;

    public int getVareId() {
        return vareId;
    }

    public void setVareId(int vareId) {
        this.vareId = vareId;
    }

    public int getHandlelisteId() {
        return handlelisteId;
    }

    public void setHandlelisteId(int handlelisteId) {
        this.handlelisteId = handlelisteId;
    }

    public int getHhBrukerId() {
        return hhBrukerId;
    }

    public void setHhBrukerId(int hhBrukerId) {
        this.hhBrukerId = hhBrukerId;
    }

    public String getVarenavn() {
        return varenavn;
    }

    public void setVarenavn(String varenavn) {
        this.varenavn = varenavn;
    }

    public boolean isKjøpt() {
        return kjøpt;
    }

    public void setKjøpt(boolean kjøpt) {
        this.kjøpt = kjøpt;
    }



}
