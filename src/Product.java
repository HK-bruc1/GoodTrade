/**
 * @author: Bruce
 * @description: 商品类
 * @date: 2024/4/3 14:19
 */
public class Product {
    private String productName;
    private String sellerContact;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSellerContact() {
        return sellerContact;
    }

    public void setSellerContact(String sellerContact) {
        this.sellerContact = sellerContact;
    }

    //方便商品实例化时可以初始化
    public Product(String productName, String sellerContact) {
        this.productName = productName;
        this.sellerContact = sellerContact;
    }
    //只有商品名字也可以，为了下订单时使用
    public Product(String productName) {
        this.productName = productName;
    }
}