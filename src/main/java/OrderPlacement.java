import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

/**
 * @author: Bruce
 * @description: 下订单
 * @date: 2024/4/7 10:06
 */
public class OrderPlacement {
    public static void start(Scanner scanner, String username) {
        System.out.print("请输入商品ID：");
        int productID = scanner.nextInt();
        //在用户输入购买数量之前，先查询商品表中对应商品的库存数量，名称，价格，卖家id（涉及到return，查询的东西太多，不好封装）
        int availableQuantity = 0; // 可用库存数量，默认为0
        String productName = "";//商品名称，默认为空
        BigDecimal price = BigDecimal.ZERO; // 价格，默认为0
        int sellerID = 0;//卖家id，默认为0
        try (Connection connection = DriverManager.getConnection(Register.url, Register.DBusername, Register.DBpassword)) {
            String sql = "SELECT ProductName, StockQuantity, Price, SellerID FROM products WHERE ProductID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, productID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        availableQuantity = resultSet.getInt("StockQuantity");
                        productName = resultSet.getString("ProductName");
                        price = resultSet.getBigDecimal("Price");
                        sellerID = resultSet.getInt("SellerID");
                    } else {
                        System.out.println("未找到对应的商品，将返回买家界面，请重新确认商品ID");
                        return;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("查询库存数量失败：" + e.getMessage());
            System.out.println("将返回买家界面请重新下单！");
            return;
        }

        // 显示可用库存数量给用户
        System.out.println("商品名称为 " + productName + " 的可用库存数量为：" + availableQuantity);

        // 如果可用库存数量为0，则提示用户无法购买
        //卖家上架的商品数量一定是大于0的，库存为零只能说明卖完了
        if (availableQuantity == 0) {
            System.out.println("抱歉，商品库存不足，无法购买。请下单其他商品！");
            return;//返回到买家界面重新下单其他商品
        }

        // 检查用户输入的购买数量是否超过可用库存数量，如果超过或不合法则提示用户重新输入
        int quantity = 0;//初始化一下
        while (true) {
            System.out.print("请输入购买数量（最多可购买 " + availableQuantity + " 件）：");
            // 检查用户输入是否为整数
            if (scanner.hasNextInt()) {
                quantity = scanner.nextInt();
                // 检查购买数量是否合法
                if (quantity > 0 && quantity <= availableQuantity) {
                    break; // 跳出循环，购买数量合法
                } else {
                    //就会进入循环
                    System.out.println("购买数量超过可用库存数量或不合法，请重新输入。");
                }
            } else {
                // 清除输入流中的非法输入
                System.out.println("购买数量格式错误，请输入一个有效的整数。");
                scanner.next(); // 清除输入流中的非法输入
                //进入循环
            }
        }

        // 通过用户名查询买家用户ID
        int buyerID = getUserIdByUsername(username);

        // 将商品ID，卖家ID（谁的商品）、购买数量和用户ID（谁买的）保存到订单表中
        try(Connection connection = DriverManager.getConnection(Register.url, Register.DBusername, Register.DBpassword)) {
            if (buyerID != -1) {
                //查到买家用户id的情况下，共用一个数据库连接，方便回滚事务
                if (placeOrder(productID, buyerID, sellerID, quantity, connection) && updateStockQuantity(productID, quantity, connection)) {
                    // 启动事务
                    connection.setAutoCommit(false);
                    System.out.println("已成功下单！");
                    System.out.println("商品名称:" + productName);
                    System.out.println("购买数量:" + quantity);
                    // 计算订单总价
                    BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));
                    System.out.println("订单总价为:" + totalPrice);
                } else {
                    // 如果任何一个方法执行失败，则回滚事务（避免数据冲突和不一致）
                    connection.rollback();
                    //可能数据库连接有问题可以重新下单一次
                    System.out.println("下单失败！请重新下单一次！");
                    return;
                }
            } else {
                System.out.println("用户名不存在！将结束系统，请重启程序");
                System.exit(0); // 退出系统
            }
        }catch (SQLException e) {
            // 处理 SQL 异常
            e.printStackTrace();
            System.out.println("发生了 SQL 异常：" + e.getMessage());
            System.out.println("将结束系统，请重启程序");
            System.exit(0); // 退出系统
        }
    }

    // 通过用户名查询用户的id
    public static int getUserIdByUsername(String username) {
        int userID = -1;//没查到
        try (Connection connection = DriverManager.getConnection(Register.url, Register.DBusername, Register.DBpassword)) {
            String sql = "SELECT id FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        //从查询结果中拿到用户id
                        userID = resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("查询用户ID失败：" + e.getMessage());
        }
        return userID;//返回-1就是没查到
    }

    // 将商品ID、卖家id,购买数量和买家ID保存到订单表中
    private static boolean placeOrder(int productID, int buyerID,int sellerID, int quantity, Connection connection) throws SQLException {
        try {
            String sql = "INSERT INTO orders (ProductID, BuyerID, SellerID, Quantity) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, productID);
                statement.setInt(2, buyerID);
                statement.setInt(3, sellerID);
                statement.setInt(4, quantity);
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;//返回一个影响行数的结果
            }
        } catch (SQLException e) {
            System.out.println("下单失败：" + e.getMessage());
            return false;
        }
    }

    // 更新商品表中的库存数量
    private static boolean updateStockQuantity(int productID, int quantity,Connection connection) {
        try {
        // 创建 SQL 查询语句，更新商品表中的库存数量
        String sql = "UPDATE products SET StockQuantity = StockQuantity - ? WHERE ProductID = ?";
            // 创建 PreparedStatement 对象
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // 设置参数：购买数量和商品ID
                statement.setInt(1, quantity);
                statement.setInt(2, productID);
                // 执行 SQL 更新操作
                int rowsAffected = statement.executeUpdate();
                // 返回是否更新成功（更新成功则返回true）
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            // 处理 SQL 异常
            System.out.println("更新商品库存数量失败：" + e.getMessage());
            return false;
        }
    }
}
