import java.sql.*;
/**
 * @author: Bruce
 * @description: 卖家查看订单
 * @date: 2024/4/7 17:22
 */
public class SellerOrderViewer {
    // 查看卖家的所有订单
    public static void viewOrders(String username) {
        // 根据卖家的username查usrs中的用户id
        int sellerID = OrderPlacement.getUserIdByUsername(username);
        if (sellerID == -1) {
            System.out.println("未找到对应的卖家，请确认用户名是否正确。");
            return;//返回卖家界面重新查看订单
        }

        // 根据卖家ID查询订单，查到对应卖家的订单
        try (Connection connection = DriverManager.getConnection(Register.url, Register.DBusername, Register.DBpassword)) {
            String sql = "SELECT * FROM orders WHERE SellerID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, sellerID); // 使用卖家的ID作为查询条件
                try (ResultSet resultSet = statement.executeQuery()) {
                    //显示订单之前看一下是否有数据（订单）
                    //如果 isBeforeFirst() 返回 true，表示光标当前位于结果集的第一行之前，说明结果集中没有数据；
                    // 如果返回 false，表示光标已经在第一行或之后，说明结果集中有数据。
                    if (!resultSet.isBeforeFirst()) {
                        System.out.println("您暂时没有订单。");
                        return;//返回卖家页面
                    }
                    displayOrders(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("查询订单失败：" + e.getMessage());
            return;//可能连接数据库失败，返回卖家界面重新查看订单
        }
    }

    // 显示订单信息
    private static void displayOrders(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int orderID = resultSet.getInt("OrderID");
            int productID = resultSet.getInt("ProductID");
            int buyerID = resultSet.getInt("BuyerID");
            int sellerID = resultSet.getInt("SellerID");
            int quantity = resultSet.getInt("Quantity");
            Timestamp orderDate = resultSet.getTimestamp("OrderDate");

            // 获取买家信息（用户名和联系方式）
            String[] buyerInfo = getBuyerInfo(buyerID);
            if (buyerInfo == null) {
                // 如果未找到对应的买家信息，则跳过当前订单显示
                //确保在显示订单信息时，不会因为买家信息不存在而导致程序出现异常，同时保证其他订单信息的正常显示。
                continue;
            }
            String buyerUsername = buyerInfo[0];
            String buyerContact = buyerInfo[1];

            // 在这里你可以根据需要显示更多订单信息
            System.out.println("订单ID: " + orderID);
            System.out.println("商品ID: " + productID);
            System.out.println("买家用户名: " + buyerUsername);
            System.out.println("买家联系方式: " + buyerContact);
            System.out.println("购买数量: " + quantity);
            System.out.println("下单时间: " + orderDate);
            System.out.println("--------------------------------------");
        }
    }

    // 根据买家ID获取买家的用户名和联系方式
    private static String[] getBuyerInfo(int buyerID) {
        String[] buyerInfo = new String[2]; // 存储买家的用户名和联系方式
        try(Connection connection = DriverManager.getConnection(Register.url, Register.DBusername, Register.DBpassword)) {
            String sql = "SELECT username, contact FROM users WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, buyerID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        buyerInfo[0] = resultSet.getString("username");
                        buyerInfo[1] = resultSet.getString("contact");
                    }else {
                        // 如果未找到对应的买家信息，则返回 null
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("查询买家信息失败：" + e.getMessage());
        }
        return buyerInfo;
    }
}
