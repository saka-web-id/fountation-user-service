package id.web.saka.fountation.user;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table(value = "users", schema = "users")
public class User {

    @Id
    @Column("id")
    private Long id;

    @Column("email")
    private String email;

    @Column("name")
    private String name;

    @Column("phone")
    private String phone;

    @Column("status")
    private UserStatus status;

    @Column("is_verified")
    private boolean verified;

    @Column("last_login_at")
    private Instant lastLoginAt;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt;

    @Column("leader_id")
    private Long leaderId;

    @Column("note")
    private String note;

    @Column("iam_id")
    private String iamId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Instant getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Instant lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getIamId() {
        return iamId;
    }

    public void setIamId(String iamId) {
        this.iamId = iamId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                ", isVerified=" + verified +
                ", lastLoginAt=" + lastLoginAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", leaderId=" + leaderId +
                ", note='" + note + '\'' +
                ", iamId='" + iamId + '\'' +
                '}';
    }
}
