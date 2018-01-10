package REST;

//Husholdningsmedlem

public class HHMedlem {

    int hhBrukerId;
    String husholdningsId;
    boolean admin;

    public int getHhBrukerId() {
        return hhBrukerId;
    }

    public void setHhBrukerId(int hhBrukerId) {
        this.hhBrukerId = hhBrukerId;
    }

    public String getHusholdningsId() {
        return husholdningsId;
    }

    public void setHusholdningsId(String husholdningsId) {
        this.husholdningsId = husholdningsId;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }


}
