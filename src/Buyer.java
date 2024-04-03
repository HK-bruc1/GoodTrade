import java.util.List;
import java.util.ArrayList;

/**
 * @author: Bruce
 * @description: 买家类
 * @date: 2024/4/3 16:32
 */
public class Buyer {
    private static List<Order> orders;
    public Buyer() {
        orders = new ArrayList<>();
    }
    // 添加订单
    public  void addOrder(Product product, String BuyerContact) {
        Order order = new Order(product, BuyerContact);
        orders.add(order);
    }
    // 获取订单列表
    public List<Order> getOrders() {
        return orders;
    }

}
