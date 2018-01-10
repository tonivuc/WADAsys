package server.REST;

/**
 * Created by BrageHalse on 10.01.2018.
 */
public class Husholdning {
    private int husholdningsId;
    private String navn;

    public int getHusholdningsId() {
        return husholdningsId;
    }

    public void setHusholdningsId(int husholdningsId) {
        this.husholdningsId = husholdningsId;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }
}
