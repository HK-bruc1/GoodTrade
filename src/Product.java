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

    public String getSellerContact() {
        return sellerContact;
    }

    //方便商品实例化时可以初始化，创建商品对象时，必须满足这两个条件
    public Product(String productName, String sellerContact) {
        this.productName = productName;
        this.sellerContact = sellerContact;
    }
//    //只有商品名字也可以，为了下订单时使用？？？？
//    public Product(String productName) {
//        this.productName = productName;
//    }
}
