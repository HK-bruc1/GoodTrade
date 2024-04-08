import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

/**
 * @author: Bruce
 * @description: 封装上架商品功能
 * @date: 2024/4/6 15:30
 */
public class uploadProduct {
    public static void start(Scanner scanner,String username) {
        System.out.print("请输入商品名称:");
        String productName = scanner.next();
        System.out.print("请输入商品描述:");
        String description = scanner.next();
        //商品价格
        BigDecimal price =null;
        while (price == null) {
            //表中将价格设计为decimal类型
            //首先接收字符串类型的输入，然后将其转换为合适的数据类型，即BigDecimal。
            System.out.print("请输入商品价格:");
            String priceStr = scanner.next();
            try {
                price = new BigDecimal(priceStr);
            } catch (NumberFormatException e) {
                System.out.println("无效的价格格式，请输入有效的数字。");
                // 继续循环，提示用户重新输入价格
            }
        }
        int stockQuantity = 0; // 初始化库存数量为0
        // 循环直到用户输入了一个有效的库存数量
        while (stockQuantity <= 0) {
            System.out.print("请输入上架数量:");
            if (scanner.hasNextInt()) {
                stockQuantity = scanner.nextInt();
                if (stockQuantity <= 0) {
                    System.out.println("请输入一个正确的库存数量！");
                }
            } else {
                //如果用户输入一个字符而不是一个整数，scanner.nextInt() 方法会抛出 InputMismatchException 异常，
                // 而且输入流中的字符不会被消耗掉，因此会导致程序陷入无限循环。为了解决这个问题，（进入循环但是还读取到上一次的字符）
                // 我们需要在 else 分支中添加代码来清除输入流中的无效输入，使得程序能够继续执行下一次循环。
                System.out.println("请输入一个有效的数字！");
                scanner.next(); // 清除输入流中的无效输入
            }
        }

        // 查询数据库，获取卖家对应的用户ID和联系方式
        int sellerID = -1; // 默认为 -1，表示未找到对应的卖家ID（不可能因为能登录进来必定有id）
        String sellerContact = null; // 默认为 null，表示未找到对应的卖家联系方式（不可能因为能登录进来必定有联系方式）
        try (Connection connection = DriverManager.getConnection(Register.url, Register.DBusername, Register.DBpassword)) {
            String sql = "SELECT id, contact FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        //从查询结果中拿id和卖家联系方式
                        sellerID = resultSet.getInt("id");
                        sellerContact = resultSet.getString("contact");
                    } else {
                        //查询结果中没有内容
                        System.out.println("未找到您的用户信息，已经登录进来了，不可能找不到id和contact");
                        System.out.println("将返回卖家页面！再上传一次商品......");
                        return;
                    }
                }
            }
        } catch (SQLException e) {
            //如果 catch 语句捕获到了异常，后面的代码就不会执行了
            //程序会立即转到 catch 语句块中执行异常处理代码
            //如果异常处理完成后，没有抛出新的异常或使用 return 等语句退出方法，则程序会继续执行 catch
            // 语句块后面的代码。但如果在异常处理代码中使用了 return、throw 等语句导致方法提前退出，
            // 或者在 catch 语句块内部抛出了新的异常，那么后面的代码就不会执行。
            //连接数据库出了问题
            System.out.println("查询卖家信息失败：" + e.getMessage());
            System.out.println("可能数据库连接出了问题，将返回卖家界面，请再一次上架商品......");
            return;
        }
        //拿到了商品的所有字段值，可以连接数据库将信息保存到products表中
        try (Connection connection = DriverManager.getConnection(Register.url, Register.DBusername, Register.DBpassword)) {
            // 准备 SQL 语句，使用占位符代替实际的值
            String sql = "INSERT INTO products (ProductName, Description, Price, StockQuantity, SellerID, SellerUserName, SellerContact) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // 设置占位符的值
                statement.setString(1, productName);
                statement.setString(2, description);
                statement.setBigDecimal(3, price);
                statement.setInt(4, stockQuantity);
                statement.setInt(5, sellerID);
                statement.setString(6, username);
                statement.setString(7, sellerContact);

                // 执行 SQL 语句，插入新的商品信息
                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    //影响的行数大于0，为成功
                    System.out.println("商品上架成功！");
                    System.out.println("请核对信息:");
                    System.out.println("商品名称:  " + productName);
                    System.out.println("商品描述:  " + description);
                    System.out.println("价格:  " + price);
                    System.out.println("上架数量:  " + stockQuantity);
                    System.out.println("卖家联系方式:  " + sellerContact);
                    System.out.println("上传者:  " + username);
                    return;//跳出方法，返回到卖家界面
                } else {
                    System.out.println("商品信息插入失败！请重新尝试一遍！");
                    return;//跳出方法，返回到卖家界面
                }
            }
        } catch (SQLException e) {
            System.out.println("插入商品信息时出现异常：" + e.getMessage());
            System.out.println("可能数据库连接出了问题，将返回卖家界面，请再一次上架商品......");
            return;
        }
    }
}
