package id.web.saka.fountation.util.mapper;

import id.web.saka.fountation.account.AccountType;
import id.web.saka.fountation.membership.MembershipStatus;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class EnumMapper {

    @Named("enumToString")
    public String enumToString(Enum<?> e) {
        return e == null ? null : e.name();
    }


    @Named("stringToAccountStatus")
    public id.web.saka.fountation.account.AccountStatus stringToAccountStatus(String status) {
        if (status == null) return id.web.saka.fountation.account.AccountStatus.AS_INACTIVE;

        return switch (status.toUpperCase()) {
            case "ACTIVE" -> id.web.saka.fountation.account.AccountStatus.AS_ACTIVE;
            case "INACTIVE" -> id.web.saka.fountation.account.AccountStatus.AS_INACTIVE;
            case "DISABLE", "DISABLED" -> id.web.saka.fountation.account.AccountStatus.AS_DISABLED;
            default -> id.web.saka.fountation.account.AccountStatus.UNRECOGNIZED;
        };
    }

    @Named("stringFromAccountStatus")
    public String stringFromAccountStatus(id.web.saka.fountation.account.AccountStatus status) {
        if (status == null || status == id.web.saka.fountation.account.AccountStatus.UNRECOGNIZED) {
            return "INACTIVE"; // Safe default for the DTO/Frontend
        }
        return status.name().replace("AS_", "");
    }


    @Named("stringToAccountType")
    public AccountType stringToAccountType(String type) {
        if (type == null) return AccountType.AT_FREE;


        return switch (type.toUpperCase()) {
            case "FREE" -> AccountType.AT_FREE;
            case "PREMIUM" -> AccountType.AT_PREMIUM;
            case "ENTERPRISE" -> AccountType.AT_ENTERPRISE;
            default -> AccountType.AT_FREE;
        };
    }

    @Named("stringToMembershipStatus")
    public MembershipStatus stringToMembershipStatus(String status) {
        if (status == null) return MembershipStatus.MS_PENDING;

        return switch (status.toUpperCase()) {
            case "PENDING" -> MembershipStatus.MS_PENDING;
            case "INACTIVE" -> MembershipStatus.MS_INACTIVE;
            case "ACTIVE" -> MembershipStatus.MS_ACTIVE;
            default -> MembershipStatus.MS_PENDING;
        };

    }
}
