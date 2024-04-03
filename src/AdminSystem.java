import java.util.List;
/**
 * @author: Bruce
 * @description: 管理员系统
 * @date: 2024/4/3 15:29
 */
public class AdminSystem {
    public static void start(){
        //拿到商品列表
        List<Product> sellerProducts = Seller.getProducts();
        for (Product product : sellerProducts) {
            System.out.println("商品名称: " + product.getProductName() + ", 卖家联系方式: " + product.getSellerContact());
        }
    }
}
