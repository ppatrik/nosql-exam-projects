package sk.gursky.simplestudent;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {

    private static final String HOSTNAME = "nosql.gursky.sk";
    private static final String KEY_SPACE = "sk/gursky/student";

    public Session getSession() {
        Cluster cluster = Cluster.builder().addContactPoint(HOSTNAME).build();
        return cluster.connect(KEY_SPACE);
    }

    @Bean
    public CqlTemplate getCqlTemplate(Session session) {
        return new CqlTemplate(session);
    }

    @Override
    protected String getKeyspaceName() {
        return KEY_SPACE;
    }

    @Override
    protected String getContactPoints() {
        return HOSTNAME;
    }

}
