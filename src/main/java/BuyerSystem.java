import java.util.Scanner;

/**
 * @author: Bruce
 * @description: 买家系统
 * @date: 2024/4/6 17:19
 */
public class BuyerSystem {
    public static void start(Scanner scanner,String username){
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
                    //查看所有商品
                    ViewProducts.strat();
                    break;
                case 2:
                    //下订单通过商品id下单
                    OrderPlacement.start(scanner,username);
                    break;
                case 3:
                    System.out.println("退出系统，谢谢使用！");
                    System.exit(0); // 退出系统
                    break;
                case 4:
                    return;//返回主界面
                default:
                    System.out.println("输入错误，请重新选择。");
            }
        }
    }
}
