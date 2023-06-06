package de.haw.hamburg.sel.ex_commerce;

import java.util.List;

/**
 * Abstract class representing a component in the product catalog.
 * Both Product and ProductGroup extend this class.
 */
abstract class ProductComponent {
    /**
     * Returns the name of the component.
     * This method is abstract and should be implemented by subclasses.
     * @return the name of the component
     */
    public abstract String getName();

    /**
     * Returns the price of the component.
     * This method is abstract and should be implemented by subclasses.
     * @return the price of the component
     */
    public abstract double getPrice();

    /**
     * Throws an UnsupportedOperationException.
     * This method is provided for compatibility and should not be called.
     * Subclasses override this method to provide their specific implementation for adding a child component.
     *
     * @param component the product component to remove
     * @throws UnsupportedOperationException when called
     */
    public void remove(ProductComponent component) {
        throw new UnsupportedOperationException("Cannot remove child component.");
    }

    /**
     * Throws an UnsupportedOperationException.
     * This method is provided for compatibility and should not be called.,
     * Subclasses override this method to provide their specific implementation for adding a child component.
     *
     * @param component the product component to add
     * @throws UnsupportedOperationException when called
     */
    public void add(ProductComponent component) {
        throw new UnsupportedOperationException("Cannot add child component.");
    }
    /**
     * Returns the list of child components.
     *
     * @return the list of child components
     */
    public abstract List<ProductComponent> getChildren();

}