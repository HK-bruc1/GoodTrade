/**
 * @author: Bruce
 * @description: 订单类
 * @date: 2024/4/3 14:34
 */
public class Order {
    //商品对象，按照需求这个对象只有商品名称没有卖家联系方式
    private Product product;

    //买家的联系方式
    private String buyerContact;

    //提供一个有参数的构造方法，用来创建订单对象，因为会有很多个不同的订单所以不用单例
    public Order(Product product, String buyer) {
        this.product = product;
        this.buyerContact = buyer;
    }

    //product为私有，想要从其他类中访问订单类的商品对象，对外提供方法，不能静态。因为product对象不共用
    public Product getProduct() {
        return product;
    }

    // 只要用private修饰，都可以使用idea的自动setter和getter方法
    public String getBuyerContact() {
        return buyerContact;
    }
}
