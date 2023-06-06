package de.haw.hamburg.sel.ex_commerce;
import java.util.ArrayList;
import java.util.List;
/**
 * Class representing a group of products or subgroups.
 * It extends the abstract class ProductComponent.
 */
class ProductGroup extends ProductComponent {
    private List<ProductComponent> children = new ArrayList<>();
    private String name;

    public ProductGroup(String name) {
        this.name = name;
    }

    /**
     * Adds a product component (either a Product or ProductGroup) to this group.
     * This method is overridden to enable adding child components to the ProductGroup.
     *
     * @param component the product component to add
     *
     */
    @Override
    public void add(ProductComponent component) {
        children.add(component);
    }

    /**
     * Removes a product component (either a Product or ProductGroup) from this group.
     * This method is overridden to enable removing child components from the ProductGroup.
     *
     * @param component the product component to remove
     */
    @Override
    public void remove(ProductComponent component) {
        children.remove(component);
    }

    /**
     * Returns the list of child components (Product or ProductGroup) contained in this group.
     *
     * @return the list of child components
     */
    @Override
    public List<ProductComponent> getChildren() {
        return children;
    }

    /**
     * Returns the name of the product group.
     *
     * @return the name of the product group
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the product group.
     * Since a product group doesn't have a specific price, this method throws an UnsupportedOperationException.
     *
     * @return the price of the product group
     * @throws UnsupportedOperationException when called on a ProductGroup
     */
    public double getPrice() {
        throw new UnsupportedOperationException("Product group doesn't have a price.");
    }
}
