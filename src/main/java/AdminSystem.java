import java.sql.*;
import java.util.Scanner;

/**
 * @author: Bruce
 * @description: 管理员系统
 * @date: 2024/4/8 10:18
 */
public class AdminSystem {
    public static void start(Scanner scanner) {
        while (true) {
            System.out.println("请选择功能：");
            System.out.println("1. 删除库存为0的商品");
            System.out.println("2. 退出系统");
            System.out.println("3. 返回主菜单");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    deleteZeroStockProducts();
                    break;
                case 2:
                    System.out.println("退出系统，谢谢使用！");
                    System.exit(0);
                case 3:
                    return;
                default:
                    System.out.println("请输入正确的选项！");
            }
        }
    }

    private static void deleteZeroStockProducts() {
        try (Connection connection = DriverManager.getConnection(Register.url, Register.DBusername, Register.DBpassword)) {
            String sql = "DELETE FROM products WHERE StockQuantity = 0";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                int deletedRows = statement.executeUpdate();
                if (deletedRows == 0) {
                    System.out.println("暂时没有库存为0的商品！");
                    return;
                }
                System.out.println("已删除 " + deletedRows + " 条库存为0的商品记录。");
            }
        } catch (SQLException e) {
            System.out.println("删除商品失败：" + e.getMessage());
        }
    }
}
