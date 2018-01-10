package server.REST;

/**
 * Created by BrageHalse on 10.01.2018.
 */
public class Gjøremål {
    private int gjøremålId;
    private int husholdningsId;
    private int hhBrukerId;
    private boolean fullført;
    private String beskrivelse;

    public int getGjøremålId(){
        return gjøremålId;
    }

    public int getHusholdningsId(){
        return husholdningsId;
    }

    public int getHhBrukerId(){
        return hhBrukerId;
    }

    public boolean getFullført(){
        return fullført;
    }

    public void setFullført(boolean nyVariabel){
        this.fullført = nyVariabel;
    }

    public String getBeskrivelse(){
        return beskrivelse;
    }

    public void setBeskrivelse(String nyBeskrivelse){
        this.beskrivelse = nyBeskrivelse;
    }
}
