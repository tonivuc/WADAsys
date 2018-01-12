package server.restklasser;

import java.util.ArrayList;

/**
 * Created by BrageHalse on 10.01.2018.
 */
public class Husholdning {
    private int husholdningId;
    private String navn;
    private ArrayList<Handleliste> handlelister = new ArrayList<>();
    private ArrayList<Gjøremål> gjøremål = new ArrayList<>();
    private ArrayList<Nyhetsinnlegg> nyhetsinnlegg = new ArrayList<>();
    private ArrayList<Bruker> medlemmer = new ArrayList<>();

    public ArrayList<Handleliste> getHandlelister() {
        return handlelister;
    }

    public void setHandlelister(ArrayList<Handleliste> handlelister) {
        this.handlelister = handlelister;
    }

    public ArrayList<Gjøremål> getGjøremål() {
        return gjøremål;
    }

    public void setGjøremål(ArrayList<Gjøremål> gjøremål) {
        this.gjøremål = gjøremål;
    }

    public ArrayList<Nyhetsinnlegg> getNyhetsinnlegg() {
        return nyhetsinnlegg;
    }

    public void setNyhetsinnlegg(ArrayList<Nyhetsinnlegg> nyhetsinnlegg) {
        this.nyhetsinnlegg = nyhetsinnlegg;
    }

    public ArrayList<Bruker> getMedlemmer() {
        return medlemmer;
    }

    public void setMedlemmer(ArrayList<Bruker> medlemmer) {
        this.medlemmer = medlemmer;
    }

    public int getHusholdningsId() {
        return husholdningId;
    }

    public void setHusholdningsId(int husholdningId) {
        this.husholdningId = husholdningId;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }
}
