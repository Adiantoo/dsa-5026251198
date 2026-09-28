package lw02.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        LinkedList<String[]> requestList = new LinkedList<>();
        LinkedList<String[]> bookList = new LinkedList<>();
        LinkedList<String[]> memberList = new LinkedList<>();
        LinkedList<String[]> successList = new LinkedList<>();
        Queue<String[]> requestQueue = new LinkedList<>();
        Stack<String[]> failedStack = new Stack<>();

        bookList.add(new String[]{"Kalkulus", "2"});
        bookList.add(new String[]{"Fisika", "1"});
        bookList.add(new String[]{"Statistika", "2"});

        File file = new File("src/lw02/unguided/borrowing.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            if (parts.length == 2) {
                String memberName = parts[0];
                String bookTitle = parts[1];

                requestList.add(new String[]{memberName, bookTitle});

                if (!isMemberExist(memberList, memberName)) {
                    memberList.add(new String[]{memberName, "0"});
                }
            }
        }

        for (String[] req : requestList) {
            requestQueue.add(req);
        }

        while (!requestQueue.isEmpty()) {
            String[] req = requestQueue.poll();
            String memberName = req[0];
            String bookTitle = req[1];

            String[] book = getBook(bookList, bookTitle);
            String[] member = getMember(memberList, memberName);

            if (book != null && member != null) {
                int stock = Integer.parseInt(book[1]);
                int borrowed = Integer.parseInt(member[1]);

                if (stock > 0 && borrowed < 2) {
                    book[1] = String.valueOf(stock - 1);
                    member[1] = String.valueOf(borrowed + 1);
                    successList.add(req);
                } else {
                    failedStack.push(req);
                }
            }
        }

        System.out.println("=== Successfully Processed Requests ===");
        for (String[] req : successList) {
            System.out.println(req[0] + " " + req[1]);
        }

        System.out.println("=== Remaining Book Stock ===");
        for (String[] book : bookList) {
            System.out.println(book[0] + ": " + book[1]);
        }

        System.out.println("=== Failed Requests ===");
        while (!failedStack.isEmpty()) {
            String[] failedReq = failedStack.pop();
            System.out.println(failedReq[0] + " " + failedReq[1]);
        }
    }

    private static boolean isMemberExist(LinkedList<String[]> memberList, String name) {
        for (String[] member : memberList) {
            if (member[0].equals(name)) return true;
        }
        return false;
    }

    private static String[] getBook(LinkedList<String[]> bookList, String title) {
        for (String[] book : bookList) {
            if (book[0].equals(title)) return book;
        }
        return null;
    }

    private static String[] getMember(LinkedList<String[]> memberList, String name) {
        for (String[] member : memberList) {
            if (member[0].equals(name)) return member;
        }
        return null;
    }
}