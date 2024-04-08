import java.sql.*;

/**
 * @author: Bruce
 * @description: 查看所有商品
 * @date: 2024/4/6 17:27
 */
public class ViewProducts {
    public static void strat() {
        try (Connection connection = DriverManager.getConnection(Register.url, Register.DBusername, Register.DBpassword)) {
            String sql = "SELECT ProductID, ProductName, Description, Price, StockQuantity, SellerUserName, SellerContact FROM products";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    System.out.println("所有商品信息:");
                    System.out.println("--------------------------------------");
                    //检查结果集中是否有下一行数据
                    //循环会一直迭代，直到结果集中没有下一行数据为止。
                    while (resultSet.next()) {
                        //通过主键商品id来下单简单又方便。
                        int productID = resultSet.getInt("ProductID");
                        String productName = resultSet.getString("ProductName");
                        String description = resultSet.getString("Description");
                        double price = resultSet.getDouble("Price");
                        int stockQuantity = resultSet.getInt("StockQuantity");
                        String sellerUserName = resultSet.getString("SellerUserName");
                        String sellerContact = resultSet.getString("SellerContact");
                        System.out.println("商品ID: " + productID);
                        System.out.println("商品名称: " + productName);
                        System.out.println("描述: " + description);
                        System.out.println("价格: " + price);
                        System.out.println("库存数量: " + stockQuantity);
                        System.out.println("卖家用户名: " + sellerUserName);
                        System.out.println("卖家联系方式: " + sellerContact);
                        System.out.println("--------------------------------------");
                    }
                    return;
                }
            }
        } catch (SQLException e) {
            //可能数据库连接出错了
            System.out.println("查询商品信息失败：" + e.getMessage());
            System.out.println("连接数据库可能出错将返回买家界面请重新尝试一次......");
            return;//返回买家界面
        }
    }
}
