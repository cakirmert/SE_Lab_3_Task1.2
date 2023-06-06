package de.haw.hamburg.sel.ex_commerce;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * The Demo class represents the main entry point of the program.
 * It provides a user interface to select and order products from a product catalog.
 * The ProductComponent class acts as the component interface,
 * Product represents the leaf nodes, and ProductGroup represents the composite nodes.
 * The ProductGroup class can contain both Product objects and other ProductGroup objects.
 *
 * <p>
 * Author: Mert Cakir
 * </p>
 * <p>
 * Date: 06.06.2023
 * </p>
 * <p>
 * The code was modified based on feedback received in the lab. Some classes were removed to simplify the project.
 * </p>
 */
public class Demo {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));    // BufferedReader to read user input
    private static final Order order = new Order();   // Order instance to manage the order
    private static ProductComponent productCatalog;    // Root of the product catalog

    public static void main(String[] args) throws IOException {
        initializeProductCatalog();
        runUserInterface();
    }
    /**
     * Runs the user interface for selecting and ordering products.
     * The user can choose to select from product groups or display all products.
     * The user can add products to the order and proceed to payment.
     */
    private static void runUserInterface() {
        try {
            while (!order.isClosed()) {
                String continueChoice = "";

                do {
                    System.out.println("Please select an option:");
                    System.out.println("1. Select from product groups");
                    System.out.println("2. Display all products");

                    int option;
                    while (true) {
                        try {
                            option = Integer.parseInt(reader.readLine());
                            if (option < 1 || option > 2) {
                                System.out.println("Invalid option. Please select a valid option.");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                    }

                    if (option == 1) {
                        selectProduct((ProductGroup) productCatalog);
                    } else {
                        selectFromAllProducts();
                    }

                    System.out.print("Do you wish to continue selecting products? Y/N: ");
                    continueChoice = reader.readLine();
                } while (continueChoice.equalsIgnoreCase("Y"));

                order.processOrder();

                System.out.print("Pay " + order.getTotalCost() + " € or Continue shopping? P/C: ");
                String proceed = reader.readLine();
                if (proceed.equalsIgnoreCase("P")) {
                    if (order.payWithPaypal(order.getTotalCost())) {
                        System.out.println("Payment has been successful.");
                    } else {
                        System.out.println("Payment failed. Please check your data.");
                    }
                    order.setClosed();
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading input. Please try again.");
        }

        System.out.println("Thank you. Goodbye!");
    }
    /**
     * Allows the user to select a product or product group from the given ProductGroup.
     * If a product is selected, it is added to the order.
     * If a product group is selected, the user is prompted to select a product from that group.
     * @param productGroup the product group to select from
     */
    private static void selectProduct(ProductComponent productGroup) throws IOException {
        while (true) {
            List<ProductComponent> children = productGroup.getChildren();
            if (children.isEmpty()) {
                System.out.println("No products available in this group.");
                break;
            }

            // Check if all children are product groups
            boolean allGroups = true;
            for (ProductComponent child : children) {
                if (!(child instanceof ProductGroup)) {
                    allGroups = false;
                    break;
                }
            }

            // Check if all children are products
            boolean allProducts = true;
            for (ProductComponent child : children) {
                if (!(child instanceof Product)) {
                    allProducts = false;
                    break;
                }
            }

            if (allGroups) {
                System.out.println("Please select a product group:");
            } else if (allProducts) {
                System.out.println("Please select a product:");
            } else {
                System.out.println("Please select a product group or a product:");
            }

            displayProductGroupsAndProducts(children);

            int choice;
            while (true) {
                try {
                    choice = Integer.parseInt(reader.readLine());
                    if (choice < 1 || choice > children.size()) {
                        System.out.println("Invalid option. Please select a valid option.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            ProductComponent selectedProduct = children.get(choice - 1);
            if (selectedProduct instanceof Product) {
                addProductToOrder(selectedProduct);
                break;
            } else if (selectedProduct instanceof ProductGroup) {
                productGroup = selectedProduct;
            } else {
                System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }

    /**
     * Displays the list of product groups and products to the user.
     * @param children the list of product components to display
     */
    private static void displayProductGroupsAndProducts(List<ProductComponent> children) {
        int index = 1;
        for (ProductComponent child : children) {
            if (child instanceof ProductGroup) {
                System.out.println(index + ". " + child.getName());
            } else if (child instanceof Product) {
                System.out.println(index + ". " + child.getName() + " - " + child.getPrice() + " €");
            }
            index++;
        }
    }

    /**
     * Adds a product to the order with the specified quantity.
     * @param selectedProduct the product to add to the order
     */
    private static void addProductToOrder(ProductComponent selectedProduct) throws IOException {
        System.out.print("Count: ");
        int count;
        while (true) {
            try {
                count = Integer.parseInt(reader.readLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        order.setTotalCost(selectedProduct.getPrice() * count);
        System.out.println("Product added to order.");
    }
    /**
     * Allows the user to select a product or product group from the entire product catalog.
     * If a product is selected, it is added to the order.
     */
    private static void selectFromAllProducts() throws IOException {
        System.out.println("Displaying all products:");
        List<ProductComponent> allProducts = new ArrayList<>();
        collectAllProducts(productCatalog, allProducts);
        displayProductGroupsAndProducts(allProducts);

        int productChoice;
        while (true) {
            try {
                productChoice = Integer.parseInt(reader.readLine());
                if (productChoice < 1 || productChoice > allProducts.size()) {
                    System.out.println("Invalid product. Please select a valid option.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        ProductComponent selectedProduct = allProducts.get(productChoice - 1);
        if (selectedProduct instanceof Product) {
            addProductToOrder((Product) selectedProduct);
        } else {
            System.out.println("Invalid product. Please select a valid option.");
        }
    }


    /**
     * Recursively collects all products from the given ProductComponent and adds them to the allProducts list.
     * @param productComponent the product component to collect products from
     * @param allProducts the list to store all the collected products
     */
    private static void collectAllProducts(ProductComponent productComponent, List<ProductComponent> allProducts) {
        if (productComponent instanceof Product) {
            allProducts.add(productComponent);
        } else if (productComponent instanceof ProductGroup) {
            List<ProductComponent> subComponents = productComponent.getChildren();
            for (ProductComponent subComponent : subComponents) {
                collectAllProducts(subComponent, allProducts);
            }
        }
    }
    /**
     * Initializes the product catalog with pre-defined product groups and products.
     */
    private static void initializeProductCatalog() {
        productCatalog = new ProductGroup("Product Catalog");

        ProductComponent motherboardGroup = new ProductGroup("Motherboards");
        ProductComponent cpuGroup = new ProductGroup("CPUs");
        ProductComponent hddGroup = new ProductGroup("HDDs");
        ProductComponent ramGroup = new ProductGroup("RAMs");

        ProductComponent amdMotherboards = new ProductGroup("AMD Motherboards");
        amdMotherboards.add(new Product("AMD AM3+ Biostar A960D+ V3 MATX DDR3, USB 2.0", 59.90));
        amdMotherboards.add(new Product("AMD AM3+ Asrock 970 Pro3 R2.0 ATX DDR3, USB 3.0", 99.90));

        ProductComponent intelMotherboards = new ProductGroup("Intel Motherboards");
        intelMotherboards.add(new Product("Intel 1151 Asrock Z390 Pro4, ATX, DDR4, USB3.0", 129.90));
        intelMotherboards.add(new Product("Intel 1200 Asrock Z490 Phantom Gaming 4, DDR4", 169.90));

        ProductComponent intelCPUs = new ProductGroup("Intel CPUs");
        intelCPUs.add(new Product("Intel Pentium G4560 3,5GHz Sockel 1151, Box", 79.90));
        intelCPUs.add(new Product("Intel Core i5-8400, 6x 2,8 GHz, Sockel 1151 V2 Box", 219.90));
        intelCPUs.add(new Product("Intel Core i7-9700, 8x 3,0 GHz, Sockel 1151 V2, Box", 299.90));
        intelCPUs.add(new Product("Intel Core i9-10900K BOX 10x3,7GHz, Sockel 1200 Box", 669.90));

        ProductComponent amdCPUs = new ProductGroup("AMD CPUs");
        amdCPUs.add(new Product("AM4 AMD Ryzen 5 3400G 4x 3,7GHz Box", 179.90));
        amdCPUs.add(new Product("AM4 AMD Ryzen 7 3700X 8x 3,6GHz, Box", 319.90));
        amdCPUs.add(new Product("AM4 AMD Ryzen 9 5900X 12x 3,70GHz Box", 699.90));

        ProductComponent pcHDDs = new ProductGroup("PC HDDs");
        pcHDDs.add(new Product("2 TB Seagate ST2000DM008, 256MB, 7200 U./Min.", 65.90));
        pcHDDs.add(new Product("4 TB Western Digital WD40EFRX 'Red' SATA3", 119.90));

        ProductComponent laptopHDDs = new ProductGroup("Laptop HDDs");
        laptopHDDs.add(new Product("2 TB Seagate ST2000LM015, 128MB, 5400 U/Min.", 75.90));

        ProductComponent pcRAMs = new ProductGroup("PC RAMs");
        pcRAMs.add(new Product("Corsair Vengeance LPX 16GB DDR4 3200MHz", 69.90));
        pcRAMs.add(new Product("G.SKILL Trident Z Neo 32GB DDR4 3600MHz", 99.90));

        ProductComponent laptopRAMs = new ProductGroup("Laptop RAMs");
        laptopRAMs.add(new Product("Crucial Ballistix Sport LT 8GB DDR4 2666MHz", 49.90));

        motherboardGroup.add(amdMotherboards);
        motherboardGroup.add(intelMotherboards);

        cpuGroup.add(intelCPUs);
        cpuGroup.add(amdCPUs);

        hddGroup.add(pcHDDs);
        hddGroup.add(laptopHDDs);

        ramGroup.add(pcRAMs);
        ramGroup.add(laptopRAMs);

        productCatalog.add(motherboardGroup);
        productCatalog.add(cpuGroup);
        productCatalog.add(hddGroup);
        productCatalog.add(ramGroup);
    }
}
