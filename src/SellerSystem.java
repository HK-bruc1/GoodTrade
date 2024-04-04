import java.util.List;
import java.util.Scanner;

/**
 * @author: Bruce
 * @description: 卖家系统
 * @date: 2024/4/3 14:59
 */
public class SellerSystem {
    public static void start(){
        //获取键盘输入对象
        Scanner scanner = new Scanner(System.in);
        //拿到对外提供的单例对象
        Seller seller = Seller.getSingle();
        System.out.println("您选择了卖家角色，可以上架商品和查看订单。");
        System.out.println("请您输入联系方式，方便买家联系。");
        String sellerContact = scanner.next();
        // 卖家选择对应的功能
        while (true){
            System.out.println("您可以选择以下操作：");
            System.out.println("1. 上架商品");
            System.out.println("2. 查看订单");
            System.out.println("3. 退出系统");
            System.out.println("4. 返回主菜单");
            System.out.print("请输入选项编号：");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.print("请输入商品名称：");
                    String productName = scanner.next();
                    //有了商品的两个条件我直接实例化一个商品对象，作为存储的实参
                    Product product = new Product(productName, sellerContact);
                    //上传商品信息，把新实例化的对象存进集合去了
                    seller.addProduct(product);
                    System.out.println("上架商品成功！" + product.getProductName() + "  上传者:  " + product.getSellerContact());
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
