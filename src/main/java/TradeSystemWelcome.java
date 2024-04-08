import java.util.Scanner;

/**
 * @author: Bruce
 * @description: 程序入口
 * @date: 2024/4/5 17:24
 */
public class TradeSystemWelcome {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 死循环可以实现注册之后自动返回欢迎界面
        while (true) {
            System.out.println("欢迎使用交易系统！");
            System.out.println("1. 注册");
            System.out.println("2. 登录");
            System.out.println("3. 退出系统");
            System.out.println("请选择操作：");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    //注册
                    //确保在每次注册时，retryCount都被正确初始化为0，然后在递归调用时逐渐递增。
                    int retryCount = 0; // 初始化重试注册次数
                    Register.registerUser(scanner,retryCount);
                    break;
                case 2:
                    //登录
                    int retryLoginCount = 0; // 初始化重试登录次数
                    Login.login(scanner,retryLoginCount);
                    break;//里面用了return返回出来再执行break就会回到主菜单
                case 3:
                    System.out.println("退出交易系统，再见！");
                    return;
                default:
                    System.out.println("无效的选择，请重新输入！");
            }
        }
    }
}
