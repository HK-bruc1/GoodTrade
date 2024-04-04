import java.util.ArrayList;
import java.util.List;

/**
 * @author: Bruce
 * @description: 卖家类
 * @date: 2024/4/3 14:18
 */
public class Seller {
    //创建一个私有静态的对象，并创建静态的方法对外提供单例方法（单例模式）
    private static Seller seller = new Seller();
    public static Seller getSingle() {
        return seller;
    }

    //全局共用一个商品列表所以直接定义为私有静态，对外提供单例方法
    private static List<Product> products = new ArrayList<>();
    public static List<Product> getProductsSingle() {
        return products;
    }

    // 卖家上架商品信息，将商品对象存储到集合中（即使不是静态方法，但我new了对象，可以直接seller.addProduct调用）
    public void addProduct(Product product) {
        products.add(product);
    }

    //查看订单
    public static void displayOrders(List<Order> ordersSingle) {
        if (ordersSingle.isEmpty()) {
            System.out.println("暂无订单！");
            return;
        }
        System.out.println("所有订单:");
        //订单集合的单个元素是由一个商品对象（名称和卖家联系方式）和一个买家联系方式组成。
        for (Order order : ordersSingle) {
            System.out.println("商品: " + order.getProduct().getProductName() + " - 卖家联系方式: " + order.getProduct().getSellerContact() + " - 买家联系方式: " + order.getBuyerContact());
        }
    }
}
