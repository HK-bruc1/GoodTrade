import java.util.Scanner;

/**
 * @author: Bruce
 * @description: 卖家系统
 * @date: 2024/4/3 14:59
 */
public class SellerSystem {
    public static void start(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("您选择了卖家角色，可以上架商品和查看订单。");
        System.out.println("请您输入联系方式，方便买家联系。");
        String sellerContact = scanner.next();
        // 卖家选择对应的功能
        while (true){
            System.out.println("您可以选择以下操作：");
            System.out.println("1. 上架商品");
            System.out.println("2. 查看订单");
            System.out.println("3. 退出系统");
            System.out.println("4. 返回主菜单");
            System.out.print("请输入选项编号：");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.print("请输入商品名称：");
                    String productName = scanner.next();
                    //上传商品信息
                    Seller seller = new Seller();
                    seller.addProduct(new Product(productName, sellerContact));
                    System.out.println("上架商品成功！" + productName + "-" + sellerContact  );
                    break;
                case 2:
                    // 查看订单...
                    Seller.displayOrders(new Buyer());
                    break;
                case 3:
                    System.out.println("退出系统，谢谢使用！");
                    System.exit(0); // 退出系统
                    break;
                case 4:
                    Main.showMainMenu(); // 返回主菜单
                    break;
                default:
                    System.out.println("输入错误，请重新选择。");
            }
        }
    }
}
