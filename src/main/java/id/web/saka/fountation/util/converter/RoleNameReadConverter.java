package id.web.saka.fountation.util.converter;

import id.web.saka.fountation.authority.role.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class RoleNameReadConverter implements Converter<String, Role.RoleName> {

    @Override
    public Role.RoleName convert(String source) {
        return Role.RoleName.valueOf(source);
    }
}
