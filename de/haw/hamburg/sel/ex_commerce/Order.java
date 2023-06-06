package de.haw.hamburg.sel.ex_commerce;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


/**
 * Order class with payment method. Only change is price refactored from int to double.
 */
public class Order {
    private double totalCost = 0;
    private boolean isOrderClosed = false;
    
    private static final Map<String, String> PAYPAL_DATA_BASE = new HashMap<>();
    private boolean signedInPaypal;

    static {
        PAYPAL_DATA_BASE.put("Passw0rd", "studi@haw.de");
        PAYPAL_DATA_BASE.put("qwertz", "prof@haw.de");
        PAYPAL_DATA_BASE.put("1", "1");
    }
    
    public void processOrder() {
        collectPaypalPaymentDetails();
    }

    public void setTotalCost(double cost) {
        this.totalCost += cost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public boolean isClosed() {
        return isOrderClosed;
    }

    public void setClosed() {
        isOrderClosed = true;
    }
    
    public void collectPaypalPaymentDetails() {
    	BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    	String email;
    	String password;
        
    	try {
            while (!signedInPaypal) {
                System.out.print("Enter the user's email for Playpal: ");
                email = READER.readLine();
                System.out.print("Enter the password: ");
                password = READER.readLine();
                if (verifyPlaypalCredentials(email, password)) {
                    System.out.println("Data verification has been successful.");
                } else {
                    System.out.println("Wrong email or password!");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean verifyPlaypalCredentials(String email, String password) {
        setSignedIn(email.equals(PAYPAL_DATA_BASE.get(password)));
        return signedInPaypal;
    }

    public boolean payWithPaypal(double paymentAmount) {
        if (signedInPaypal) {
            System.out.println("Paying " + paymentAmount + " using PayPal.");
            return true;
        } else {
            return false;
        }
    }

    private void setSignedIn(boolean signedIn) {
        this.signedInPaypal = signedIn;
    }
}