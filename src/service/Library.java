package service;

import model.*;
import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Library {
    private Map<String, Book> books;
    private Map<String, User> users;
    private List<BorrowRecord> borrowRecords;
    private User currentUser;

    public Library() {
        this.books = new HashMap<>();
        this.users = new HashMap<>();
        this.borrowRecords = new ArrayList<>();
        initializeAdmin();
    }

    private void initializeAdmin() {
        // Create a default admin account
        Admin admin = new Admin("admin1", "System Admin", "admin123");
        users.put(admin.getUserId(), admin);
    }

    // User Management Methods
    public boolean registerUser(String userId, String name, String password, String role) {
        if (users.containsKey(userId)) {
            return false;
        }

        User user = role.equals("Admin") ? new Admin(userId, name, password) : new Member(userId, name, password);
        users.put(userId, user);
        return true;
    }

    public boolean login(String userId, String password) {
        User user = users.get(userId);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<User> getAllMembers() {
        List<User> members = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getRole().equals("Member")) {
                members.add(user);
            }
        }
        return members;
    }

    // Book Management Methods
    public boolean addBook(String title, String author, String isbn) {
        if (books.containsKey(isbn)) {
            return false;
        }
        books.put(isbn, new Book(title, author, isbn));
        return true;
    }

    public boolean removeBook(String isbn) {
        if (!books.containsKey(isbn)) {
            return false;
        }
        books.remove(isbn);
        return true;
    }

    public boolean updateBook(String isbn, String title, String author) {
        Book book = books.get(isbn);
        if (book == null) {
            return false;
        }
        book.setTitle(title);
        book.setAuthor(author);
        return true;
    }

    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        query = query.toLowerCase();

        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(query) ||
                    book.getAuthor().toLowerCase().contains(query) ||
                    book.getIsbn().toLowerCase().contains(query)) {
                results.add(book);
            }
        }
        return results;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    // Borrowing System Methods
    public boolean borrowBook(String isbn) {
        if (currentUser == null || !currentUser.getRole().equals("Member")) {
            return false;
        }

        Book book = books.get(isbn);
        if (book == null || !book.isAvailable()) {
            return false;
        }

        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plus(14, ChronoUnit.DAYS); // 2 weeks borrowing period

        book.setAvailable(false);
        book.setDueDate(dueDate);

        BorrowRecord record = new BorrowRecord(currentUser.getUserId(), isbn, today, dueDate);
        borrowRecords.add(record);

        return true;
    }

    public boolean returnBook(String isbn) {
        if (currentUser == null || !currentUser.getRole().equals("Member")) {
            return false;
        }

        Book book = books.get(isbn);
        if (book == null || book.isAvailable()) {
            return false;
        }

        book.setAvailable(true);
        book.setDueDate(null);

        // Find the borrow record and mark it as returned
        for (BorrowRecord record : borrowRecords) {
            if (record.getBookIsbn().equals(isbn) &&
                    record.getMemberId().equals(currentUser.getUserId()) &&
                    record.getReturnDate() == null) {
                record.setReturnDate(LocalDate.now());
                return true;
            }
        }

        return false;
    }

    public List<BorrowRecord> getBorrowingHistory(String memberId) {
        List<BorrowRecord> history = new ArrayList<>();
        for (BorrowRecord record : borrowRecords) {
            if (record.getMemberId().equals(memberId)) {
                history.add(record);
            }
        }
        return history;
    }

    // Data persistence methods (to be used with FileService)
    public Map<String, Book> getBooks() {
        return books;
    }

    public void setBooks(Map<String, Book> books) {
        this.books = books;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    public List<BorrowRecord> getBorrowRecords() {
        return borrowRecords;
    }

    public void setBorrowRecords(List<BorrowRecord> borrowRecords) {
        this.borrowRecords = borrowRecords;
    }
}