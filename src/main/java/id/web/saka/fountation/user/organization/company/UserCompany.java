package id.web.saka.fountation.user.organization.company;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "user_company", schema = "users")
public class UserCompany {

    @Column("id")
    private Long id;

    @Column("user_id")
    private Long userId;

    @Column("company_id")
    private Long companyId;

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
}
