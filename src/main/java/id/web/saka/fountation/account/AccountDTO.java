package id.web.saka.fountation.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public class AccountDTO {

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("accountStatus")
    private String accountStatus;

    @JsonProperty("membershipType")
    private String membershipType;

    @JsonProperty("membershipStatus")
    private String membershipStatus;

    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;

    @JsonProperty("membershipStartDate")
    private OffsetDateTime membershipStartDate;

    @JsonProperty("membershipEndDate")
    private OffsetDateTime membershipEndDate;


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
}
