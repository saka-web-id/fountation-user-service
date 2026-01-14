package id.web.saka.fountation.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

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
    private ZonedDateTime createdAt;

    @JsonProperty("membershipStartDate")
    private ZonedDateTime membershipStartDate;

    @JsonProperty("membershipEndDate")
    private ZonedDateTime membershipEndDate;


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
}
