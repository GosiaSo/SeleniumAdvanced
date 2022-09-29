package models;

import pages.cart.CartPopupPage;

public class ProductFactory {
    public Product getProductInfo(CartPopupPage cartPopupPage) {

        String name = cartPopupPage.getNameOfProduct();
        double quantityPrice = cartPopupPage.getPriceOfProduct();
        int quantity = cartPopupPage.getQuantityOfProduct();
        double totalPrice = cartPopupPage.getTotalCartValue();

        return new Product.ProductBuilder()
                .setName(name)
                .setQuantityPrice(quantityPrice)
                .setQuantity(quantity)
                .setTotalPrice(totalPrice)
                .build();
    }
}
