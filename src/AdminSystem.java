import java.util.List;
import java.util.Scanner;

/**
 * @author: Bruce
 * @description: 管理员系统
 * @date: 2024/4/3 15:29
 */
public class AdminSystem {
    public static void start(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("您选择了管理员角色，可以查看上架商品和查看成交商品。");
        //管理员选择功能
        while (true){
            System.out.println("您可以选择以下操作：");
            System.out.println("1. 查看上架商品");
            System.out.println("2. 查看成交订单");
            System.out.println("3. 退出系统");
            System.out.println("4. 返回主菜单");
            System.out.print("请输入选项编号：");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    //通过对外的单例对象方法，拿到商品集合
                    List<Product> Products = Seller.getProductsSingle();
                    for (Product product : Products) {
                        System.out.println("商品名称: " + product.getProductName() + ", 卖家联系方式: " + product.getSellerContact());
                    }
                    //跳出switch语句回到循环开始
                    break;
                case 2:
                    // 查看订单...
                    // 获取对外的单例订单列表
                    List<Order> ordersSingle = Buyer.getOrdersSingle();
                    Seller.displayOrders(ordersSingle);
                    break;
                case 3:
                    System.out.println("退出系统，谢谢使用！");
                    System.exit(0); // 退出系统
                    break;
                case 4:
                    Main.showMainMenu(); // 返回主菜单
                    break;
                default:
                    System.out.println("输入错误，请重新选择。");
            }
        }
    }
}
