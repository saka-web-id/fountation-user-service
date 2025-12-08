package id.web.saka.fountation.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public class UserAccountDTO {

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

    @JsonProperty("role")
    private String role;

    @JsonProperty("locale")
    private String locale;

    @JsonProperty("department")
    private String department;

    @JsonProperty("organization")
    private String organization;

    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;

    @JsonProperty("membershipStartDate")
    private OffsetDateTime membershipStartDate;

    @JsonProperty("membershipEndDate")
    private OffsetDateTime membershipEndDate;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
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
}
