package id.web.saka.fountation.user;

import id.web.saka.fountation.account.Account;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.List;

@Table("users")
public class User {

    @Id
    private Long id;

    private String email;

    private String passwordHash;

    private String fullName;

    private String status;

    private boolean isVerified;

    private Timestamp lastLoginAt;

    private Timestamp createdAt;

    private Timestamp updateAt;

    private Long accountId;

    private String note;

    List<Account> accounts;

    public <T> User(String userId, String mail, String joe, String bloggs, List<Account> ts) {
    }
}
