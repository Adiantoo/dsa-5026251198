package lw01.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/lw01/unguided/rental.txt");
        Scanner scanner = new Scanner(file);
    if (!scanner.hasNextLine()) {
       scanner.close();
       return;
    }

    int totalRecords = scanner.nextInt();
    Rental[] rentals = new Rental[totalRecords];

    for (int i = 0; i < totalRecords; i++) {
        String type = scanner.next();
        String id = scanner.next();
        int days = scanner.nextInt();
        int units = scanner.nextInt();

        Rental baseRental = null;
        if (type.equalsIgnoreCase("LAPTOP")) {
            baseRental = new LaptopRental(id, days);
        } else if (type.equalsIgnoreCase("PROJECTOR")) {
            baseRental = new ProjectorRental(id, days);
        }

        if (baseRental != null) {
                final int totalCharge = baseRental.calculateCharge(units);
                final String itemLabel = baseRental.label();
                final String itemId = baseRental.getId();

                // Simpan object yang sudah dihitung ke array
                rentals[i] = new Rental(itemId, days) {
                    @Override
                    public int calculateCharge() {
                        return totalCharge;
                    }

                    @Override
                    public String label() {
                        return itemLabel;
                    }
                };
            }
        }
        scanner.close();

        for (Rental rental : rentals) {
            if (rental != null) {
                System.out.println(rental.summary());
            }
        }
    }
}
