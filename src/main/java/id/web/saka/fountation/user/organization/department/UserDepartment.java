package id.web.saka.fountation.user.organization.department;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "user_department", schema = "users")
public class UserDepartment {

    @Column("id")
    private Long id;

    @Column("user_id")
    private Long userId;

    @Column("company_id")
    private Long companyId;

    @Column("department_id")
    private Long departmentId;

    @Column("is_default")
    private boolean isDefault;

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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        return "UserDepartment{" +
                "id=" + id +
                ", userId=" + userId +
                ", companyId=" + companyId +
                ", departmentId=" + departmentId +
                ", isDefault=" + isDefault +
                '}';
    }
}
