package lw03.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        solveProblem1();
        solveProblem2();
        solveProblem3();
    }

    // Problem 1

    public static void solveProblem1() throws FileNotFoundException {
        List<String> playlist = new ArrayList<>();
        File file = new File("src/lw03/prelab/playlist.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(" ", 2);
            String command = parts[0];

            if (command.equals("ADD")) {
                String song = parts[1];
                playlist.add(song);
            } else if (command.equals("INSERT")) {
                String[] insertParts = parts[1].split(" ", 2);
                int index = Integer.parseInt(insertParts[0]);
                String song = insertParts[1];
                playlist.add(index, song);
            } else if (command.equals("REMOVE")) {
                String song = parts[1];
                playlist.remove(song);
            }
        }
        scanner.close();

        System.out.println("===== Problem 1 =====");
        System.out.println("Total songs: " + playlist.size());
        for (int i = 0; i < playlist.size(); i++) {
            System.out.println((i + 1) + ": " + playlist.get(i));
        }
    }

// problem 2

    public static void solveProblem2() throws FileNotFoundException {
        Set<String> participants = new LinkedHashSet<>();
        int duplicateCount = 0;

        File file = new File("src/lw03/prelab/participants.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) continue;

            if (participants.contains(name)) {
                duplicateCount++;
            } else {
                participants.add(name);
            }
        }
        scanner.close();

        System.out.println("Problem 2 =====");
        System.out.println("Unique participants: " + participants.size());
        int index = 1;
        for (String name : participants) {
            System.out.println(index + ". " + name);
            index++;
        }
        System.out.println("Duplicate registrations: " + duplicateCount);
    }

    // problem 3

    public static void solveProblem3() throws FileNotFoundException {
        Map<String, Integer> inventory = new LinkedHashMap<>();
        int failedSales = 0;

        File file = new File("src/lw03/prelab/inventory.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(" ");
            String type = parts[0];
            String product = parts[1];
            int quantity = Integer.parseInt(parts[2]);

            if (type.equals("ADD")) {
                int currentStock = inventory.getOrDefault(product, 0);
                inventory.put(product, currentStock + quantity);
            } else if (type.equals("SELL")) {
                if (inventory.containsKey(product)) {
                    int currentStock = inventory.get(product);
                    if (currentStock >= quantity) {
                        inventory.put(product, currentStock - quantity);
                    } else {
                        failedSales++;
                    }
                } else {
                    failedSales++;
                }
            }
        }
        scanner.close();

        System.out.println("===== Problem 3 =====");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Failed sales: " + failedSales);
    }
}