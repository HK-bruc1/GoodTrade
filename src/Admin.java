import java.util.List;

/**
 * @author: Bruce
 * @description: 管理员类
 * @date: 2024/4/3 11:15
 */
public class Admin{
    // 查看已上架的商品列表
    public void viewAvailableProducts() {
        // 这里可以添加具体的逻辑来获取并展示已上架的商品信息
        System.out.println("已上架的商品列表：");
        // 打印已上架的商品信息
    }

    // 查看订单
    public void viewCompletedTransactions() {
        // 这里可以添加具体的逻辑来获取并展示已成交的商品信息
        System.out.println("已成交的商品列表：");
        // 打印已成交的商品信息
    }
    // 通过名称拿到卖家联系方式
    public static String getSellerContactByProductName(String productName) {
        //拿到对外的单例商品列表
        List<Product> productsSingle = Seller.getProductsSingle();
        for (Product product : productsSingle) {
            if (product.getProductName().equals(productName)) {
                return product.getSellerContact();
            }
        }
        return "此商品没有卖家联系方式，这不可能因为上传商品必须有卖家联系方式，这只是保险而已...";
    }
}
