package sk.gursky.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
@ComponentScan(basePackages = "sk.gursky.mongo")
public class MongoConfig extends AbstractMongoConfiguration {

    private static final String HOSTNAME = "localhost";
    private static final int PORT = 32768;
    private static final String DATABASE_NAME = "nosql-mongo";

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(HOSTNAME, PORT);
    }

    @Override
    protected String getDatabaseName() {
        return DATABASE_NAME;
    }

}
