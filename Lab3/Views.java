public class Views {

    private final String productId;
    private final Integer productPrice;

    Views(String productId, Integer productPrice){
        this.productId = productId;
        this.productPrice = productPrice;
    }

    public String getProductId(){return this.productId;}
    public Integer getProductPrice(){return this.productPrice;}
}
