package id.web.saka.fountation.user.authority.permission;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table("permission_manager")
public class PermissionManager {

    @Id
    private long id;

    private long userId;

    private long permissionId;

    private String source;

    private long grantedBy;

    private OffsetDateTime grandedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getGrantedBy() {
        return grantedBy;
    }

    public void setGrantedBy(long grantedBy) {
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
