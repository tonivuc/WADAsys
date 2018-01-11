package server.restklasser;

/**
 * Created by BrageHalse on 10.01.2018.
 */
public class Handleliste {

    private String tittel;
    private int handlelisteId;
    private int husholdningsId;
    private int skaperId;

    public int getHandlelisteId(){
        return handlelisteId;
    }

    public int getHusholdningsId(){
        return husholdningsId;
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
}
