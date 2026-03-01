package id.web.saka.fountation.util.mapper;

import id.web.saka.fountation.account.AccountStatus;
import id.web.saka.fountation.account.AccountType;
import id.web.saka.fountation.account.MembershipStatus;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class EnumMapper {

    @Named("enumToString")
    public String enumToString(Enum<?> e) {
        return e == null ? null : e.name();
    }

    @Named("stringToAccountStatus")
    public AccountStatus stringToAccountStatus(String status) {
        if (status == null) return AccountStatus.AS_ACTIVE;
        try { return AccountStatus.valueOf("AS_" + status.toUpperCase()); } catch (Exception e) { return AccountStatus.AS_ACTIVE; }
    }

    @Named("stringToAccountType")
    public AccountType stringToAccountType(String type) {
        if (type == null) return AccountType.AT_FREE;
        try { return AccountType.valueOf("AT_" + type.toUpperCase()); } catch (Exception e) { return AccountType.AT_FREE; }
    }

    @Named("stringToMembershipStatus")
    public MembershipStatus stringToMembershipStatus(String status) {
        if (status == null) return MembershipStatus.MS_ACTIVE;
        try { return MembershipStatus.valueOf("MS_" + status.toUpperCase()); } catch (Exception e) { return MembershipStatus.MS_ACTIVE; }
    }
}
