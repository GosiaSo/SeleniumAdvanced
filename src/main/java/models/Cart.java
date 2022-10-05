package models;

import java.util.List;

public class Cart {

    private List<Product> products;
    private double totalCost;

    public Cart(List<Product> products, double totalCost) {
        this.products = products;
        this.totalCost = totalCost;
    }

    public void addToList(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "products=" + products +
                ", totalCost=" + totalCost +
                '}';
    }
}
