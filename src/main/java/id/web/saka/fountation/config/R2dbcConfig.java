package id.web.saka.fountation.config;

import id.web.saka.fountation.util.converter.RoleNameReadConverter;
import id.web.saka.fountation.util.converter.RoleNameWriteConverter;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import java.util.List;

@Configuration
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    private final ConnectionFactory connectionFactory;

    public R2dbcConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public ConnectionFactory connectionFactory() {
        return connectionFactory;
    }

    @Override
    protected List<Object> getCustomConverters() {
        return List.of(
                new RoleNameReadConverter(),
                new RoleNameWriteConverter()
        );
    }

}
