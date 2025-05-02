package service;

import model.*;
import java.io.*;
import java.util.*;

public class FileService {
    private static final String BOOKS_FILE = "books.dat";
    private static final String USERS_FILE = "users.dat";
    private static final String RECORDS_FILE = "records.dat";

    public static void saveData(Library library) {
        try {
            // Save books
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKS_FILE));
            oos.writeObject(library.getBooks());
            oos.close();

            // Save users
            oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE));
            oos.writeObject(library.getUsers());
            oos.close();

            // Save borrow records
            oos = new ObjectOutputStream(new FileOutputStream(RECORDS_FILE));
            oos.writeObject(library.getBorrowRecords());
            oos.close();
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadData(Library library) {
        try {
            // Load books
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BOOKS_FILE));
            library.setBooks((Map<String, Book>) ois.readObject());
            ois.close();

            // Load users
            ois = new ObjectInputStream(new FileInputStream(USERS_FILE));
            library.setUsers((Map<String, User>) ois.readObject());
            ois.close();

            // Load borrow records
            ois = new ObjectInputStream(new FileInputStream(RECORDS_FILE));
            library.setBorrowRecords((List<BorrowRecord>) ois.readObject());
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing data found or error loading data. Starting with fresh data.");
        }
    }
}