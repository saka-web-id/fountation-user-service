package id.web.saka.fountation.user.authority.permission;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table(value = "permission_manager", schema = "users")
public class PermissionManager {

    @Id
    @Column("id")
    private Long id;

    @Column("user_id")
    private Long userId;

    @Column("permission_id")
    private Long permissionId;

    @Column("source")
    private String source;

    @Column("granted_by")
    private Long grantedBy;

    @Column("granded_at")
    private OffsetDateTime grandedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getGrantedBy() {
        return grantedBy;
    }

    public void setGrantedBy(Long grantedBy) {
        this.grantedBy = grantedBy;
    }

    public OffsetDateTime getGrandedAt() {
        return grandedAt;
    }

    public void setGrandedAt(OffsetDateTime grandedAt) {
        this.grandedAt = grandedAt;
    }

    @Override
    public String toString() {
        return "PermissionManager{" +
                "id=" + id +
                ", userId=" + userId +
                ", permissionId=" + permissionId +
                ", source='" + source + '\'' +
                ", grantedBy=" + grantedBy +
                ", grandedAt=" + grandedAt +
                '}';
    }
}
