import java.util.Scanner;

/**
 * @author: Bruce
 * @description: 系统入口
 * @date: 2024/4/3 11:30
 */
public class Main {
    public static void main(String[] args) {
        showMainMenu();
    }

    public static void showMainMenu() {
        // 欢迎界面
        System.out.println("欢迎使用二手交易系统，请选择进入的角色：");
        System.out.println("1. 管理员");
        System.out.println("2. 卖家");
        System.out.println("3. 买家");
        System.out.print("请输入选择的角色编号：");

        // 获取用户输入进入相应界面
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        // 根据用户选择呈现不同的选项
        switch (choice) {
            case 1:
                // 进入管理员系统
                System.out.println("您选择了管理员角色，可以查看已上架的商品");
                AdminSystem.start();
                // 管理员功能...
                break;
            case 2:
                // 进入卖家系统
                SellerSystem.start();
            case 3:
                // 进入买家系统
                BuyerSystem.start();
                // 买家功能...
                break;
            default:
                System.out.println("输入错误，请重新选择。");
        }
    }
}
