package server.restklasser;

import java.sql.Date;

public class Vare {
    private int vareId;
    private int hhBrukerId;
    private String varenavn;
    private boolean kjøpt;
    private int kjøperId;
    private Date datoKjøpt;

    public Date getDatoKjøpt() {
        return datoKjøpt;
    }

    public void setDatoKjøpt(Date datoKjøpt) {
        this.datoKjøpt = datoKjøpt;
    }

    public int getKjøperId() {
        return kjøperId;
    }

    public void setKjøperId(int kjøperId) {
        this.kjøperId = kjøperId;
    }

    public int getVareId() {
        return vareId;
    }

    public void setVareId(int vareId) {
        this.vareId = vareId;
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
