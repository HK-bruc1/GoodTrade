import java.util.Scanner;

/**
 * @author: Bruce
 * @description: 买家系统
 * @date: 2024/4/3 16:36
 */
public class BuyerSystem {
    public static void start(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("您选择了买家角色，可以购买商品和查看热门商品。");
        System.out.println("请您输入联系方式，方便接收通知。");
        String BuyerContact = scanner.next();
        // 买家选择对应的功能
        while (true){
            System.out.println("您可以选择以下操作：");
            System.out.println("1. 查看所有商品");
            System.out.println("2. 下订单");
            System.out.println("3. 退出系统");
            System.out.println("4. 返回主菜单");
            System.out.print("请输入选项编号：");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    AdminSystem.start();
                    break;
                case 2:
                    System.out.println("请输入想要购买的商品名称:");
                    String productName = scanner.next();
                    //上传订单信息
                    Buyer buyer = new Buyer();
                    buyer.addOrder(new Product(productName), BuyerContact);
                    System.out.println("下单成功！" + productName + "-" + BuyerContact);
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
