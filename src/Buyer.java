import java.util.List;
import java.util.ArrayList;

/**
 * @author: Bruce
 * @description: 买家类
 * @date: 2024/4/3 16:32
 */
public class Buyer {
    //使用单例模式，全局使用一个buyer对象
    private static Buyer buyer = new Buyer();
    public static Buyer getSingle()  {
        return buyer;
    }

    //全局使用一个订单集合，用静态私有修饰提供对外的单例方法
    private static List<Order> orders = new ArrayList<>();
    public static List<Order>  getOrdersSingle(){
        return orders;
    }

    // 添加订单（实例方法需要new对象，两个参数传入一个没有卖家联系方式的商品对象只有商品名称
    // 传入买家的联系方式
    public void addOrder(Product product, String buyerContact) {
        Order order = new Order(product, buyerContact);
        orders.add(order);
    }
    //
}
