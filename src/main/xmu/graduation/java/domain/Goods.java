package graduation.java.domain;

/**
 * FileName: Goods
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-19 上午11:15
 * Description:
 * 测试可视化环境所用的Goods类
 *
 */
public class Goods {
    private  int id;
    private  String name;
    private int price;
    private int sales;
    private int stock;
    private String detail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", sales=" + sales +
                ", stock=" + stock +
                ", detail='" + detail + '\'' +
                '}';
    }
}
