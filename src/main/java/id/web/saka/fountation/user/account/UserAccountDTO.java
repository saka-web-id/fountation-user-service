package id.web.saka.fountation.user.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.web.saka.fountation.account.AccountDTO;
import id.web.saka.fountation.authority.RolePermissionDTO;
import id.web.saka.fountation.organization.company.CompanyDTO;
import id.web.saka.fountation.organization.department.DepartmentDTO;
import id.web.saka.fountation.user.User;

import java.time.OffsetDateTime;

public class UserAccountDTO {

    public UserAccountDTO(User user, AccountDTO account, RolePermissionDTO authority, CompanyDTO companyDTO, DepartmentDTO departmentDTO) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.accountNumber = account.getAccountNumber();
        this.accountStatus = account.getAccountStatus();
        this.membershipType = account.getMembershipType();
        this.membershipStatus =  account.getMembershipStatus();
        this.authority = authority;
        this.company = companyDTO;
        this.department = departmentDTO;
        this.createdAt = account.getCreatedAt();
        this.membershipStartDate = account.getMembershipStartDate();
        this.membershipEndDate = account.getMembershipEndDate();
    }

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

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
    private OffsetDateTime createdAt;

    @JsonProperty("membershipStartDate")
    private OffsetDateTime membershipStartDate;

    @JsonProperty("membershipEndDate")
    private OffsetDateTime membershipEndDate;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getMembershipStartDate() {
        return membershipStartDate;
    }

    public void setMembershipStartDate(OffsetDateTime membershipStartDate) {
        this.membershipStartDate = membershipStartDate;
    }

    public OffsetDateTime getMembershipEndDate() {
        return membershipEndDate;
    }

    public void setMembershipEndDate(OffsetDateTime membershipEndDate) {
        this.membershipEndDate = membershipEndDate;
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
}
