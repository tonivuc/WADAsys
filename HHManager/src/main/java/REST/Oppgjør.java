package REST;

public class Oppgjør {

    int utleggerId;
    int oppgjørId;
    double sum;
    String beskrivelse;
    int[] skylderPengerMedlemmer;
    int[] varer;

    public int getUtleggerId() {
        return utleggerId;
    }

    public void setUtleggerId(int utleggerId) {
        this.utleggerId = utleggerId;
    }

    public int getOppgjørId() {
        return oppgjørId;
    }

    public void setOppgjørId(int oppgjørId) {
        this.oppgjørId = oppgjørId;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public int[] getSkylderPengerMedlemmer() {
        return skylderPengerMedlemmer;
    }

    public void setSkylderPengerMedlemmer(int[] skylderPengerMedlemmer) {
        this.skylderPengerMedlemmer = skylderPengerMedlemmer;
    }

    public int[] getVarer() {
        return varer;
    }

    public void setVarer(int[] varer) {
        this.varer = varer;
    }




}
