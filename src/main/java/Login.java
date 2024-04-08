import java.sql.*;
import java.util.Scanner;

/**
 * @author: Bruce
 * @description: 登录类
 * @date: 2024/4/6 9:52
 */
public class Login {
    public static void login(Scanner scanner,int retryLoginCount) {
        //递归出口，避免递归栈溢出
        if (retryLoginCount >= Register.MAX_RETRY) {
            System.out.println("达到最大重试次数。返回主菜单");
            return;//达到最大次数之后会返回到主界面的循环中
        }
        System.out.println("请输入用户名：");
        String username = scanner.next();
        System.out.println("请输入密码：");
        String password = scanner.next();

        //复用在Register中定义的常量
        //获取数据库连接
        try (Connection connection = DriverManager.getConnection(Register.url, Register.DBusername, Register.DBpassword)) {
            //从数据库中查询用户角色。? 是占位符，表示将在执行时动态填充参数。
            String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
            //传入了前面定义的 SQL 查询语句
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);
                //执行了 SQL 查询，并将结果保存在 ResultSet 对象中。ResultSet 是一个结果集，可以用于遍历查询结果。
                try (ResultSet resultSet = statement.executeQuery()) {
                    //判断语句用于检查查询结果集中是否有数据
                    if (resultSet.next()) {
                        //拿到用户的角色信息
                        int userRole = resultSet.getInt("role");
                        //根据角色信息进入不同的页面
                        switch (userRole) {
                            case 1:
                                System.out.println("欢迎管理员登录！");
                                AdminSystem.start(scanner);
                                break;//跳出switch语句，整个方法就结束了返回到login调用处
                            case 2:
                                System.out.println("欢迎卖家登录！");
                                SellerSystem.start(scanner,username);
                                break;//里面有return，整个方法就结束了返回到login调用处
                            case 3:
                                System.out.println("欢迎买家登录！");
                                BuyerSystem.start(scanner,username);
                                break;
                            default:
                                //role只能有三个值，这个选项不可能有
                                System.out.println("未知用户角色！");
                        }
                    } else {
                        //ResultSet 对象中没有数据，查无此人
                        System.out.println("登录失败！用户名或密码错误。请重新输入！");
                        //return;//登录失败，跳出这个方法回到调用处，调用处执行break后进入主菜单可以重新选择登录或则注册
                        // 用递归结构直接跳到登录的输入界面
                        login(scanner, retryLoginCount+1); // 用户名或密码错误，重新调用登录方法
                    }
                }
            }//连接数据库失败了，只能重新启动程序了
        } catch (SQLException e) {
            System.out.println("登录失败！错误信息：" + e.getMessage());
            System.out.println("将退出系统，请重新启动程序");
            System.exit(0); // 退出系统
        }
    }
}
