package sk.gursky;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.cql.CqlTemplate;

@Configuration
public class CassandraConfig {

    private static final String HOSTNAME = "nosql.gursky.sk";
    private static final String KEY_SPACE = "pekarcik";

    @Bean
    public Session getSession() {
        Cluster cluster = Cluster.builder().addContactPoint(HOSTNAME).build();
        return cluster.connect(KEY_SPACE);
    }

    @Bean
    public CqlTemplate getCqlTemplate(Session session) {
        return new CqlTemplate(session);
    }
}
