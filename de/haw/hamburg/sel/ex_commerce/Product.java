package de.haw.hamburg.sel.ex_commerce;

import java.util.List;

/**
 * Class representing a single product.
 * It extends the abstract class ProductComponent.
 */
class Product extends ProductComponent {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Returns the name of the product.
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the product.
     * @return the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Throws an UnsupportedOperationException.
     * Leaf nodes, such as individual products, do not have children.
     *
     * @return a UnsupportedOperationException indicating that leaf nodes do not have children
     * @throws UnsupportedOperationException when called
     */
    @Override
    public List<ProductComponent> getChildren() {
        throw new UnsupportedOperationException("Leaf nodes do not have children.");
    }
}