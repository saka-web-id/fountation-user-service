package id.web.saka.fountation.organization.department;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DepartmentRequestDTO {

    @JsonProperty("companyId")
    private Long companyId;

    @JsonProperty("departmentName")
    private String name;

    @JsonProperty("departmentStatus")
    private String status;

    @JsonProperty("departmentDescription")
    private String description;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
