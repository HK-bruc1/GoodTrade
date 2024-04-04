# 二手交易系统

## V1.0实现

- 纯Java实现，控制台交互
- 系统角色：管理员，买家，卖家
- 管理员功能：查看上架商品和成交商品
- 卖家功能：上架商品，查看订单
- 买家功能：根据名称购买商品，查看所有商品
- 有欢迎简单的欢迎界面，由欢迎界面可以切换为设定的三个角色。
- 商品类只有名称和卖家的联系方式。

## 问题

- 不会运用单例模式
- 方法的调用不熟悉（实例方法，静态方法）

### **单例对象的创建，适合全局单例使用，直接使用类名去拿对象的属性和方法**

```java
public class Seller {
    //创建一个静态的对象，并创建静态的方法对外提供单例对象
    private static Seller seller = new Seller();
    public static Seller getSingle() {
        return seller;
    }
}
```

### 将对象存储在集合中的理解

`private static List<Product> products = new ArrayList<>();`

```java
public void addProduct(Product product) {
    products.add(product);
}
```

**这个集合中的数据是怎么样存储的？**

在 Java 中，ArrayList 是一个动态数组，它可以存储任意类型的对象。在您提供的代码中，products 变量是一个 List 类型，它被初始化为一个新的 ArrayList 实例。这意味着 products 集合可以存储任何类型的对象，并且它的大小可以根据需要动态增长。

- **`products` 集合的本质**

`private static List<Product> products = new ArrayList<>();` 声明了一个`ArrayList`，专门用来存储 `Product` 类型的对象。

- **`products.add(product)` 具体作用**？

- **创建对象引用:** 当你执行 `products.add(product)`， `ArrayList` 并不会把整个 `product` 对象直接复制进去。它做的是创建一个引用（可以理解为一个指针或地址），指向你创建的 `product`对象在内存中的实际位置。
- **存储对象属性:** 由于 `ArrayList` 存储的是这个引用，所以任何对于 `Product` 对象属性（`productName` 和 `sellerContact`）的修改都会同时反映在 `products` 集合中的元素上，因为它们本质上指向的是同一个对象（指向你创建的 `product`对象在内存中的实际位置）。

- 结合理解：

```java
//全局共用一个商品列表所以直接定义为静态，直接使用类名调用，对外提供单例对象
    private static List<Product> products = new ArrayList<>();
    public static List<Product> getProductsSingle() {
        return products;
    }

// 卖家上架商品信息，将商品对象存储到集合中（即使不是静态方法，但我new了对象，可以直接seller.addProduct调用）
    public void addProduct(Product product) {
        products.add(product);
    }

//通过对外的单例对象方法，拿到商品集合（在其它类中直接调用对外的单例对象方法）
        List<Product> Products = Seller.getProductsSingle();
        for (Product product : Products) {
            System.out.println("商品名称: " + product.getProductName() + ", 卖家联系方式: " + product.getSellerContact());
        }
```

**1. 获取商品集合:**

`Seller.getProductsSingle()` 方法是一个**对外暴露的单例方法**，用于获取所有商品的集合。该方法通常会从数据库或其他数据源中获取数据，并返回一个 `List<Product>` 类型的值。

**2. 遍历商品集合:**

`for` 循环会遍历 `Products` 集合中的每个元素（每一个元素都是一个对象，可以访问属性和方法），并将其赋值给循环变量 `product`。

**3. 打印商品信息:**

在每次循环迭代中，代码会使用 `product` 对象的 `getProductName()` 和 `getSellerContact()` 方法获取商品名称和卖家联系方式，并将其打印输出。

### 为什么使用单例方法

```Java
public class Seller {
    //创建一个静态的对象，并创建静态的方法对外提供单例方法
    private static Seller seller = new Seller();
    public static Seller getSingle() {
        return seller;
    }
//全局共用一个商品列表所以直接定义为静态，对外提供单例方法
	private static List<Product> products = new ArrayList<>();
	public static List<Product> getProductsSingle() {
    return products;
	}
}
```
我在其他类为什么不可以直接用类名. products直接获取这个对象？
		反而还要类名. getProductsSingle()去返回来获取这个对象？

**原因如下：**

- **封装性**: `products` 是一个私有变量，这意味着它只能在 `Seller` 类内部访问其他类要访问就必须对外提供方法（类似于私有属性对外提供相应的getter和setter方法来访问一样）。

### get和set方法的使用

- 纯粹是给private修饰的变量和方法和对象对外提供访问的作用。
- 都可以用idea的自动生成搞定