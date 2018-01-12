package server.restklasser;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by BrageHalse on 10.01.2018.
 */
public class Handleliste {

    private String tittel;
    private int handlelisteId;
    private int husholdningId;
    private int skaperId;
    private boolean offentlig;
    private Date frist;
    private Vare[] varer;

    public Handleliste() {
        //tom konstruktør
    }

    public Handleliste(int handlelisteId, int husholdningId, Date frist, boolean offentlig, String navn, int skaperId) {
        this.handlelisteId = handlelisteId;
        setHusholdningId(husholdningId);
        setFrist(frist);
        setOffentlig(offentlig);
        setTittel(navn);
        setSkaperId(skaperId);
    }

    public Handleliste(int handlelisteId) {
        this.handlelisteId = handlelisteId;
    }

    public int getHandlelisteId(){
        return handlelisteId;
    }

    public int getHusholdningsId(){
        return husholdningId;
    }

    public void setHusholdningId(int id) {
        husholdningId = id;
    }

    public int getSkaperId(){
        return skaperId;
    }

    public String getTittel() {
        return tittel;
    }

    public void setTittel(String tittel) {
        this.tittel = tittel;
    }

    public boolean isOffentlig() {
        return offentlig;
    }

    public void setOffentlig(boolean offentlig) {
        this.offentlig = offentlig;
    }

    public void setSkaperId(int id) {
        this.skaperId = id;
    }

    public Date getFrist() {
        return frist;
    }

    public void setFrist(Date frist) {
        this.frist = frist;
    }

    public Vare[] getVarer() {
        return varer;
    }

    public void setVarer(Vare[] varer) {
        this.varer = varer;
    }

}

