package REST;

/**
 * Created by BrageHalse on 10.01.2018.
 */
public class Nyhetsinnlegg {
    private int nyhetsinnleggId;
    private int hhBrukerId;
    private int husholdningsId;
    private String tekst;

    public int getNyhetsinnleggId() {
        return nyhetsinnleggId;
    }

    public int getHhBrukerId() {
        return hhBrukerId;
    }

    public int getHusholdningsId() {
        return husholdningsId;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String nyTekst){
        this.tekst = nyTekst;
    }
}
