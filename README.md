# Library Management System

A Java-based Library Management System that allows **Admins** to manage books and users, and **Members** to borrow and return books. Built using Object-Oriented Programming principles.

---

## Features

### User Roles
- **Admin**: Add/remove/view books and manage users
- **Member**: View/search books, borrow and return books

### Book Management
- Add, delete, update book details (title, author, ISBN, availability)
- Search books by title, author, or ISBN
- Display all available books

### User Management
- Register and login functionality
- Each user has a unique ID, name, and role
- Admin can view all registered members

### Borrowing System
- Members can borrow available books
- Return books and update availability
- Records borrowing and due dates using `LocalDate`

---

## How to Run

1. Ensure **Java JDK 17+** is installed.
2. Open a terminal in the project root directory.
3. Compile all Java files:

    ```bash
    javac -d out src/**/*.java
    ```

4. Run the main class:

    ```bash
    java -cp out Main
    ```

   This will start the Library Management System from the console.

---

## Default Admin Credentials
- **User ID**: admin1
- **Password**: admin123

---

## Data Persistence
The system saves data to files and loads them at startup:
- `books.dat` – Stores book data
- `users.dat` – Stores admin/member data
- `records.dat` – Stores borrow records

---

## Class Structure

### Model Classes
- **Book**: Contains title, author, ISBN, and availability
- **User**: Abstract base class for all users
- **Admin**: Inherits from `User`, has admin privileges
- **Member**: Inherits from `User`, can borrow/return books
- **BorrowRecord**: Stores borrowing info (user, book, dates)

### Service Classes
- **Library**: Handles all business logic
- **FileService**: Reads/writes data from/to files

### Main Class
- **Main**: Entry point, handles the menu-based console UI

---

## OOP Principles Demonstrated

- **Encapsulation**: Private fields with public getters/setters
- **Inheritance**: Admin and Member extend `User`
- **Polymorphism**: Shared interface, different behavior for user roles
- **Abstraction**: `User` class defines common user behavior

---

## Usage Instructions

### First-Time Setup:
1. Login as admin using default credentials
2. Add books to the system
3. Register new members

### Member Actions:
- Search and borrow books
- Return borrowed books
- View borrow history

### Admin Actions:
- Manage books (add/update/delete)
- View all members
- View all borrow records

---

## Conclusion
This project demonstrates the proper use of Java OOP concepts in a modular, real-world application. It features clear structure, file persistence, and a functional CLI for managing a small library system.

---

## Want to contribute or improve it?
Fork the repo and submit a PR! We'd love to have your contributions.
