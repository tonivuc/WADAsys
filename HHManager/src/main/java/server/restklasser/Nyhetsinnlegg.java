package server.restklasser;

/**
 * Created by BrageHalse on 10.01.2018.
 */
public class Nyhetsinnlegg {
    private int nyhetsinnleggId;
    private int hhBrukerId;
    private int husholdningId;
    private String tekst;

    public int getNyhetsinnleggId() {
        return nyhetsinnleggId;
    }

    public int getHhBrukerId() {
        return hhBrukerId;
    }

    public int getHusholdningsId() {
        return husholdningId;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String nyTekst){
        this.tekst = nyTekst;
    }
}
