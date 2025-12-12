package id.web.saka.fountation.organization.department;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public class DepartmentDTO {

    public DepartmentDTO(Department department) {
        this.departmentId = department.getId();
        this.name = department.getName();
        this.description = department.getDescription();
        this.createdAt = department.getCreatedAt();
    }

    @JsonProperty("id")
    private Long departmentId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
