package id.web.saka.fountation.user.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.web.saka.fountation.account.AccountDTO;
import id.web.saka.fountation.authority.RolePermissionDTO;
import id.web.saka.fountation.organization.company.CompanyDTO;
import id.web.saka.fountation.organization.department.DepartmentDTO;
import id.web.saka.fountation.user.User;

import java.time.ZonedDateTime;

public class UserAccountDTO {

    public UserAccountDTO() {
        // empty constructor for Jackson
    }

    public UserAccountDTO(User user, AccountDTO account, RolePermissionDTO authority, CompanyDTO companyDTO, DepartmentDTO departmentDTO) {
        this.id = user.getId();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.note = user.getNote();
        this.isVerified = user.isVerified();
        this.accountNumber = account.accountNumber();
        this.accountStatus = account.accountStatus();
        this.membershipType = account.membershipType();
        this.membershipStatus =  account.membershipStatus();
        this.authority = authority;
        this.company = companyDTO;
        this.department = departmentDTO;
        this.createdAt = account.createdAt();
        this.membershipStartDate = account.membershipStartDate();
        this.membershipEndDate = account.membershipEndDate();
    }

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("note")
    private String note;

    @JsonProperty("isVerified")
    private boolean isVerified;

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("accountStatus")
    private String accountStatus;

    @JsonProperty("membershipType")
    private String membershipType;

    @JsonProperty("membershipStatus")
    private String membershipStatus;

    @JsonProperty("authority")
    private RolePermissionDTO authority;

    @JsonProperty("company")
    private CompanyDTO company;

    @JsonProperty("department")
    private DepartmentDTO department;

    @JsonProperty("createdAt")
    private ZonedDateTime createdAt;

    @JsonProperty("membershipStartDate")
    private ZonedDateTime membershipStartDate;

    @JsonProperty("membershipEndDate")
    private ZonedDateTime membershipEndDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(String membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    public RolePermissionDTO getAuthority() {
        return authority;
    }

    public void setAuthority(RolePermissionDTO authority) {
        this.authority = authority;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getMembershipStartDate() {
        return membershipStartDate;
    }

    public void setMembershipStartDate(ZonedDateTime membershipStartDate) {
        this.membershipStartDate = membershipStartDate;
    }

    public ZonedDateTime getMembershipEndDate() {
        return membershipEndDate;
    }

    public void setMembershipEndDate(ZonedDateTime membershipEndDate) {
        this.membershipEndDate = membershipEndDate;
    }

    @Override
    public String toString() {
        return "UserAccountDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", note='" + note + '\'' +
                ", isVerified=" + isVerified +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                ", membershipType='" + membershipType + '\'' +
                ", membershipStatus='" + membershipStatus + '\'' +
                ", authority=" + authority +
                ", company=" + company +
                ", department=" + department +
                ", createdAt=" + createdAt +
                ", membershipStartDate=" + membershipStartDate +
                ", membershipEndDate=" + membershipEndDate +
                '}';
    }
}
