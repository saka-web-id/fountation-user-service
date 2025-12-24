package id.web.saka.fountation.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequestDTO {

    @JsonProperty("userEmail")
    private String email;

    @JsonProperty("userName")
    private String name;

    @JsonProperty("userPhone")
    private String phone;

    @JsonProperty("userStatus")
    private String status;

    @JsonProperty("userIsVerified")
    private boolean isVerified;

    @JsonProperty("companyId")
    private Long companyId;

    @JsonProperty("departmentId")
    private Long departmentId;

    @JsonProperty("userNote")
    private String note;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "UserRequestDTO{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                ", isVerified=" + isVerified +
                ", companyId=" + companyId +
                ", departmentId=" + departmentId +
                ", note='" + note + '\'' +
                '}';
    }
}
