import java.util.ArrayList;
import java.util.List;

/**
 * @author: Bruce
 * @description: 卖家类
 * @date: 2024/4/3 14:18
 */
public class Seller {
    private static List<Product> products;
    public Seller() {
        products = new ArrayList<>();
    }
    // 添加商品
    public void addProduct(Product product) {
        products.add(product);
    }

    // 返回商品列表，便于其他类访问
    public static List<Product> getProducts() {
        return products;
    }
    // 查看订单
    public static void displayOrders(Buyer buyer) {
        List<Order> orders = buyer.getOrders();
        if (orders.isEmpty()) {
            System.out.println("暂无订单！");
            return;
        }
        System.out.println("所有订单:");
        for (Order order : orders) {
            System.out.println("商品: " + order.getProduct().getProductName() + " - 买家: " + order.getBuyerContact());
        }
    }

}
