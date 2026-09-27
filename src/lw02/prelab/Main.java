package lw02.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        LinkedList<String[]> transactionList = new LinkedList<>();
        LinkedList<String[]> customerList = new LinkedList<>();
        Queue<String[]> transactionQueue = new LinkedList<>();
        Stack<String[]> failedStack = new Stack<>();

        File file = new File("src/lw02/prelab/transaction.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            if (parts.length == 3) {
                String name = parts[0];
                String type = parts[1];
                String amount = parts[2];

                transactionList.add(new String[]{name, type, amount});

                if (!isCustomerExist(customerList, name)) {
                    customerList.add(new String[]{name, "0"});
                }
            }
        }
        scanner.close();

        for (String[] tx : transactionList) {
            transactionQueue.add(tx);
        }

        while (!transactionQueue.isEmpty()) {
            String[] tx = transactionQueue.poll();
            String name = tx[0];
            String type = tx[1];
            int amount = Integer.parseInt(tx[2]);

            String[] customer = getCustomer(customerList, name);
            if (customer != null) {
                int currentBalance = Integer.parseInt(customer[1]);

                if (type.equalsIgnoreCase("DEPOSIT")) {
                    currentBalance += amount;
                    customer[1] = String.valueOf(currentBalance);
                } else if (type.equalsIgnoreCase("WITHDRAW")) {
                    if (amount > currentBalance) {
                        failedStack.push(tx);
                    } else {
                        currentBalance -= amount;
                        customer[1] = String.valueOf(currentBalance);
                    }
                }
            }
        }

        System.out.println("=== Final Balances ===");
        for (String[] cust : customerList) {
            System.out.println(cust[0] + ": " + cust[1]);
        }

        System.out.println("=== Failed Transactions ===");
        while (!failedStack.isEmpty()) {
            String[] failedTx = failedStack.pop();
            System.out.println(failedTx[0] + " " + failedTx[1] + " " + failedTx[2]);
        }
    }

    private static boolean isCustomerExist(LinkedList<String[]> customerList, String name) {
        for (String[] cust : customerList) {
            if (cust[0].equals(name)) return true;
        }
        return false;
    }

    private static String[] getCustomer(LinkedList<String[]> customerList, String name) {
        for (String[] cust : customerList) {
            if (cust[0].equals(name)) return cust;
        }
        return null;
    }
}