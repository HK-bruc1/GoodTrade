import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * @author: Bruce
 * @description: 注册类
 * @date: 2024/4/5 17:32
 */
public class Register {
    // MySQL数据库连接信息，用public修饰，常量全局使用
    public static final String url = "jdbc:mysql://localhost:3306/goodtradedb";
    public static final String DBusername = "root";
    public static final String DBpassword = "123";
    public static final int MAX_RETRY = 3; // 最大重试次数

    //传入主程序new的Scanner对象，改为静态方法，简单明了
    public static void registerUser(Scanner scanner,int retryCount) {
        //递归出口
        if (retryCount >= MAX_RETRY) {
            System.out.println("注册失败！达到最大重试次数。返回主菜单");
            return;//达到最大次数之后会返回到主菜单的循环中
        }
        System.out.println("欢迎注册交易系统！");
        System.out.println("1. 管理员");
        System.out.println("2. 卖家");
        System.out.println("3. 买家");
        System.out.println("请选择角色：");
        int roleChoice = scanner.nextInt();

        // 在这里处理用户注册逻辑，将用户信息保存到本地数据库中
        System.out.println("请输入用户名:");
        String username = scanner.next();
        System.out.println("请输入密码:");
        String password = scanner.next();
        System.out.println("请输入联系方式:");
        String contact = scanner.next();

        // 连接数据库并插入用户信息
        //连接信息由url、username和password指定。try语句块中的代码在执行完毕后会自动关闭数据库连接。
        try (Connection connection = DriverManager.getConnection(url, DBusername, DBpassword)) {
            //定义了一个SQL语句，用于向名为users的表中插入一条记录。VALUES (?, ?, ?)部分使用了占位符，后面会用实际的值替换这些占位符。
            String sql = "INSERT INTO users (username, password, role, contact) VALUES (?, ?, ?, ?)";
            //执行带有参数的SQL语句，try语句块中的代码在执行完毕后会自动关闭PreparedStatement对象。
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                //设置了SQL语句中的第一个、第二个和第三个参数的值
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setInt(3, roleChoice);
                statement.setString(4, contact);
                //执行了一次插入操作，并返回插入的行数
                int rowsInserted = statement.executeUpdate();
                //根据执行SQL语句后受影响的行数，判断注册是否成功
                if (rowsInserted > 0) {
                    //正常结束整个方法回到调用Register处，再执行一个break返回主界面
                    System.out.println("注册成功！返回主界面登录！");
                } else {
                    //注册失败，用户肯定要继续注册，
                    System.out.println("注册失败！没有使表插入成功！");
                    //不管什么问题，让他最多再注册三次，不成功就返回主界面
                    registerUser(scanner, retryCount+1);
                }
            }//捕获了SQLException类型的异常
        } catch (SQLException e) {
            //调用getErrorCode()方法，我们可以获取到数据库引擎返回的错误码。如果错误码为1062，表示违反了唯一性约束，即用户名已存在
            if (e.getErrorCode() == 1062) {
                System.out.println("用户名已存在，请选择一个不同的用户名重新注册！");
                //直接退出registerUser（）方法，进入到主菜单的循环当中即登录注册页面
                //return;
                // 用递归结构直接到选择角色界面，即选择注册的下一步
                registerUser(scanner, retryCount+1); // 用户名已存在，重新调用注册方法
            } else {
                //其他的错误，直接打印错误信息
                //连接数据库出了问题，只能重新启动程序试试。
                System.out.println("注册失败！错误信息：" + e.getMessage());
                System.out.println("将退出系统，请重新启动程序");
                System.exit(0); // 退出系统
            }
        }
    }
}
