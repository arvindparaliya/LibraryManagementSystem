package model;

public class Member extends User {
    public Member(String userId, String name, String password) {
        super(userId, name, password, "Member");
    }
}