package server.restklasser;

//Husholdningsmedlem

public class HHMedlem {

    int hhBrukerId;
    String husholdningId;
    boolean admin;

    public int getHhBrukerId() {
        return hhBrukerId;
    }

    public void setHhBrukerId(int hhBrukerId) {
        this.hhBrukerId = hhBrukerId;
    }

    public String getHusholdningsId() {
        return husholdningId;
    }

    public void setHusholdningsId(String husholdningId) {
        this.husholdningId = husholdningId;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }


}
