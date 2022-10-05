package models;

public class Product {

    private String name;
    private double quantityPrice;
    private int quantity;
    private double totalPrice;

    public Product(String name, double quantityPrice, int quantity, double totalPrice) {
        this.name = name;
        this.quantityPrice = quantityPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", quantityPrice=" + quantityPrice +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public static class ProductBuilder {
        private String name;
        private double quantityPrice;
        private int quantity;
        private double totalPrice;

        public ProductBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder setQuantityPrice(double quantityPrice) {
            this.quantityPrice = quantityPrice;
            return this;
        }

        public ProductBuilder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public ProductBuilder setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Product build() {
            return new Product(name, quantityPrice, quantity, totalPrice);
        }
    }
}
