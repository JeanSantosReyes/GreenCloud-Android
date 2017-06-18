package ReciclerMinimosMaximos;

/**
 * Created by MAND on 16/06/2017.
 */
public class MinimosMaximos {
    private String Tmaxima,Tminima;
    private String HRmaxima,HRminima;
    private String HSmaxima,HSminima;
    private String variedad;
    public MinimosMaximos(){}
    public MinimosMaximos(String variedad,String Tmaxima,String Tminima,String HRmaxima,String HRminima,String HSmaxima,String HSminima){

        setVariedad(variedad);

        setTminima(Tminima);
        setTmaxima(Tmaxima);

        setHRminima(HRminima);
        setHRmaxima(HRmaxima);

        setHSminima(HSminima);
        setHSmaxima(HSmaxima);

    }

    public String getTmaxima() {
        return Tmaxima;
    }

    public void setTmaxima(String tmaxima) {
        Tmaxima = tmaxima;
    }

    public String getTminima() {
        return Tminima;
    }

    public void setTminima(String tminima) {
        Tminima = tminima;
    }

    public String getHRmaxima() {
        return HRmaxima;
    }

    public void setHRmaxima(String HRmaxima) {
        this.HRmaxima = HRmaxima;
    }

    public String getHRminima() {
        return HRminima;
    }

    public void setHRminima(String HRminima) {
        this.HRminima = HRminima;
    }

    public String getHSmaxima() {
        return HSmaxima;
    }

    public void setHSmaxima(String HSmaxima) {
        this.HSmaxima = HSmaxima;
    }

    public String getHSminima() {
        return HSminima;
    }

    public void setHSminima(String HSminima) {
        this.HSminima = HSminima;
    }

    public String getVariedad() {
        return variedad;
    }

    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }
}
