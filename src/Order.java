/**
 * @author: Bruce
 * @description: 订单类
 * @date: 2024/4/3 14:34
 */
public class Order {
    private Product product;
    private String buyerContact;
    public Order(Product product, String buyer) {
        this.product = product;
        this.buyerContact = buyer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getBuyerContact() {
        return buyerContact;
    }

    public void setBuyerContact(String buyer) {
        this.buyerContact = buyer;
    }
}
