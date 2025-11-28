package id.web.saka.fountation.user;

import id.web.saka.fountation.account.Account;

import java.util.List;

public class User {

    String id;
    String email;
    String firstName;
    String lastName;
    List<Account> accounts;

    public User(String userId, String mail, String joe, String bloggs, List<Account> accounts) {
        this.id = userId;
        this.email = mail;
        this.firstName = joe;
        this.lastName = bloggs;
        this.accounts = (List<Account>) accounts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
