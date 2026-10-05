package lw03.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Map<String, Integer> enrollment = new LinkedHashMap<>();
        List<String> checkResults = new ArrayList<>();
        int rejectedOperations = 0;

        File file = new File("src/lw03/unguided/enrollment.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(" ");
            String command = parts[0];

            if (command.equals("REGISTER")) {
                String courseCode = parts[1];
                int count = Integer.parseInt(parts[2]);

                if (count <= 0) {
                    rejectedOperations++;
                } else {
                    if (enrollment.containsKey(courseCode)) {
                        enrollment.put(courseCode, enrollment.get(courseCode) + count);
                    } else {
                        enrollment.put(courseCode, count);
                    }
                }

            } else if (command.equals("WITHDRAW")) {
                String courseCode = parts[1];
                int count = Integer.parseInt(parts[2]);

                if (count <= 0) {
                    rejectedOperations++;
                } else if (!enrollment.containsKey(courseCode)) {
                    rejectedOperations++;
                } else {
                    int currentStock = enrollment.get(courseCode);
                    if (currentStock >= count) {
                        enrollment.put(courseCode, currentStock - count);
                    } else {
                        rejectedOperations++;
                    }
                }

            } else if (command.equals("CHECK")) {
                String courseCode = parts[1];
                if (enrollment.containsKey(courseCode)) {
                    checkResults.add(courseCode + ": " + enrollment.get(courseCode) + " students");
                } else {
                    checkResults.add(courseCode + ": Not found");
                }
            }
        }
        scanner.close();

        System.out.println("===== Enrollment Checks =====");
        for (String result : checkResults) {
            System.out.println(result);
        }

        System.out.println("===== Final Enrollment =====");
        for (Map.Entry<String, Integer> entry : enrollment.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " students");
        }
        System.out.println("Rejected operations: " + rejectedOperations);
    }
}