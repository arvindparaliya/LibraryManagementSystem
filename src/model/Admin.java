package model;

public class Admin extends User {
    public Admin(String userId, String name, String password) {
        super(userId, name, password, "Admin");
    }
}