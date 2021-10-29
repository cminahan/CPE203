public class Buy {

    private final String proudctId;
    private final Integer price;
    private final Integer quanntity;

    Buy(String proudctId, Integer price, Integer quanntity){
        this.proudctId = proudctId;
        this.price = price;
        this.quanntity = quanntity;
    }

    public String getProudctId(){return this.proudctId;}
    public Integer getPrice(){return this.price;}
    public Integer getQuanntity(){return this.quanntity;}
}
