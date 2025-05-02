import service.*;
import model.*;
import java.util.*;
import java.time.LocalDate;
import java.io.*;

public class Main {
    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Load existing data
        FileService.loadData(library);

        // Main menu
        while (true) {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput(1, 3);

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    FileService.saveData(library);
                    System.out.println("Thank you for using the Library Management System!");
                    System.exit(0);
            }
        }
    }

    private static void login() {
        System.out.print("\nEnter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (library.login(userId, password)) {
            User user = library.getCurrentUser();
            System.out.println("\nWelcome, " + user.getName() + " (" + user.getRole() + ")!");

            if (user.getRole().equals("Admin")) {
                showAdminMenu();
            } else {
                showMemberMenu();
            }
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private static void register() {
        System.out.println("\n=== User Registration ===");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.println("Select Role:");
        System.out.println("1. Admin");
        System.out.println("2. Member");
        System.out.print("Enter choice: ");
        int roleChoice = getIntInput(1, 2);

        String role = roleChoice == 1 ? "Admin" : "Member";

        if (library.registerUser(userId, name, password, role)) {
            System.out.println("Registration successful! Please login.");
        } else {
            System.out.println("User ID already exists. Please try again.");
        }
    }

    private static void showAdminMenu() {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Book Management");
            System.out.println("2. User Management");
            System.out.println("3. View All Borrow Records");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            int choice = getIntInput(1, 4);

            switch (choice) {
                case 1:
                    showBookManagementMenu();
                    break;
                case 2:
                    showUserManagementMenu();
                    break;
                case 3:
                    viewAllBorrowRecords();
                    break;
                case 4:
                    library.logout();
                    return;
            }
        }
    }

    private static void showBookManagementMenu() {
        while (true) {
            System.out.println("\n=== Book Management ===");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Update Book");
            System.out.println("4. Search Books");
            System.out.println("5. View All Books");
            System.out.println("6. Back to Admin Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput(1, 6);

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    searchBooks();
                    break;
                case 5:
                    viewAllBooks();
                    break;
                case 6:
                    return;
            }
        }
    }

    private static void showUserManagementMenu() {
        while (true) {
            System.out.println("\n=== User Management ===");
            System.out.println("1. View All Members");
            System.out.println("2. Back to Admin Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput(1, 2);

            switch (choice) {
                case 1:
                    viewAllMembers();
                    break;
                case 2:
                    return;
            }
        }
    }

    private static void showMemberMenu() {
        while (true) {
            System.out.println("\n=== Member Menu ===");
            System.out.println("1. Search Books");
            System.out.println("2. View Available Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. View My Borrowing History");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            int choice = getIntInput(1, 6);

            switch (choice) {
                case 1:
                    searchBooks();
                    break;
                case 2:
                    viewAvailableBooks();
                    break;
                case 3:
                    borrowBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    viewMemberBorrowingHistory();
                    break;
                case 6:
                    library.logout();
                    return;
            }
        }
    }

    // Book Management Operations
    private static void addBook() {
        System.out.println("\n=== Add New Book ===");
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        if (library.addBook(title, author, isbn)) {
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Book with this ISBN already exists.");
        }
    }

    private static void removeBook() {
        System.out.println("\n=== Remove Book ===");
        System.out.print("Enter ISBN of the book to remove: ");
        String isbn = scanner.nextLine();

        if (library.removeBook(isbn)) {
            System.out.println("Book removed successfully!");
        } else {
            System.out.println("Book not found or couldn't be removed.");
        }
    }

    private static void updateBook() {
        System.out.println("\n=== Update Book ===");
        System.out.print("Enter ISBN of the book to update: ");
        String isbn = scanner.nextLine();

        Book book = library.getBooks().get(isbn);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        System.out.println("Current Details: " + book);
        System.out.print("Enter new Title (leave blank to keep current): ");
        String title = scanner.nextLine();
        System.out.print("Enter new Author (leave blank to keep current): ");
        String author = scanner.nextLine();

        if (!title.isEmpty()) book.setTitle(title);
        if (!author.isEmpty()) book.setAuthor(author);

        System.out.println("Book updated successfully!");
    }

    private static void searchBooks() {
        System.out.println("\n=== Search Books ===");
        System.out.print("Enter search query (title, author, or ISBN): ");
        String query = scanner.nextLine();

        List<Book> results = library.searchBooks(query);
        if (results.isEmpty()) {
            System.out.println("No books found matching your query.");
        } else {
            System.out.println("\nSearch Results:");
            for (Book book : results) {
                System.out.println(book);
            }
        }
    }

    private static void viewAllBooks() {
        List<Book> books = library.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("\nNo books in the library.");
        } else {
            System.out.println("\nAll Books in Library:");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private static void viewAvailableBooks() {
        List<Book> books = library.getAllBooks();
        List<Book> availableBooks = new ArrayList<>();

        for (Book book : books) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }

        if (availableBooks.isEmpty()) {
            System.out.println("\nNo available books at the moment.");
        } else {
            System.out.println("\nAvailable Books:");
            for (Book book : availableBooks) {
                System.out.println(book);
            }
        }
    }

    // User Management Operations
    private static void viewAllMembers() {
        List<User> members = library.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("\nNo members registered.");
        } else {
            System.out.println("\nAll Members:");
            for (User member : members) {
                System.out.println(member);
            }
        }
    }

    // Borrowing System Operations
    private static void borrowBook() {
        System.out.println("\n=== Borrow Book ===");
        System.out.print("Enter ISBN of the book to borrow: ");
        String isbn = scanner.nextLine();

        if (library.borrowBook(isbn)) {
            System.out.println("Book borrowed successfully! Due date: " +
                    library.getBooks().get(isbn).getDueDate());
        } else {
            System.out.println("Book not available or you're not eligible to borrow.");
        }
    }

    private static void returnBook() {
        System.out.println("\n=== Return Book ===");
        System.out.print("Enter ISBN of the book to return: ");
        String isbn = scanner.nextLine();

        if (library.returnBook(isbn)) {
            System.out.println("Book returned successfully!");
        } else {
            System.out.println("Book not found or wasn't borrowed by you.");
        }
    }

    private static void viewMemberBorrowingHistory() {
        String memberId = library.getCurrentUser().getUserId();
        List<BorrowRecord> history = library.getBorrowingHistory(memberId);

        if (history.isEmpty()) {
            System.out.println("\nYou have no borrowing history.");
        } else {
            System.out.println("\nYour Borrowing History:");
            for (BorrowRecord record : history) {
                System.out.println(record);
            }
        }
    }

    private static void viewAllBorrowRecords() {
        List<BorrowRecord> records = library.getBorrowRecords();
        if (records.isEmpty()) {
            System.out.println("\nNo borrowing records found.");
        } else {
            System.out.println("\nAll Borrowing Records:");
            for (BorrowRecord record : records) {
                System.out.println(record);
            }
        }
    }

    // Utility method for getting valid integer input
    private static int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}