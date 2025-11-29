package id.web.saka.fountation.user;

import id.web.saka.fountation.account.Account;

import java.util.List;

public record User(String id, String name, String account, String note, List<Account> accounts) {
}
