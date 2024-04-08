import java.util.Scanner;

/**
 * @author: Bruce
 * @description: 卖家系统
 * @date: 2024/4/6 11:08
 */
public class SellerSystem {
    //卖家系统入口
    public static void start(Scanner scanner,String username){
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
                    //上架商品
                    uploadProduct.start(scanner,username);
                    //跳出switch语句回到卖家界面
                    break;
                case 2:
                    // 查看订单...
                    SellerOrderViewer.viewOrders(username);
                    break;
                case 3:
                    System.out.println("退出系统，谢谢使用！");
                    System.exit(0); // 退出系统
                    break;//为了习惯break留着
                case 4:
                    //结束方法返回到调用处
                    return;
                default:
                    System.out.println("输入错误，请重新选择。");
            }
        }
    }
}
