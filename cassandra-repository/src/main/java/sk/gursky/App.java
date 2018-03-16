package sk.gursky;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.datastax.driver.core.*;
import com.datastax.driver.core.exceptions.DriverException;
import com.datastax.driver.core.querybuilder.Batch;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.data.cassandra.core.cql.RowMapper;
import org.springframework.data.cassandra.core.cql.generator.CreateTableCqlGenerator;
import org.springframework.data.cassandra.core.cql.generator.DropTableCqlGenerator;
import org.springframework.data.cassandra.core.cql.keyspace.CreateTableSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropTableSpecification;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class App {
    public static void main(String[] args) {
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.WARN);

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(CassandraConfig.class);

        // Ziskanie beanov
        Session session = applicationContext.getBean(Session.class);
        CqlTemplate cqlTemplate = applicationContext.getBean(CqlTemplate.class);

        System.out.println("Vytváram tabuľku");
        cqlTemplate.execute("create table if not exists test_table (" +
                "id uuid primary key, " +
                "hodnota text " +
                ");");

        System.out.println("Vkladám dáta");

        for (int i = 0; i < 10; i++) {
            cqlTemplate.execute("insert into test_table (id, hodnota) values (?, ?);",
                    UUID.randomUUID(),
                    "Test " + i + " " + (int) (1000 * Math.random()));
        }

        System.out.println("Načítavam dáta");
        cqlTemplate.query("select id, hodnota from test_table;", new RowMapper<Object>() {
            public Object mapRow(Row row, int i) throws DriverException {
                System.out.println(row.getUUID("id") + " " + row.getString("hodnota"));
                return null;
            }
        });

        System.out.println("Mažem tabuľku");
        cqlTemplate.execute("drop table if exists test_table");

        // ---------------------------------------------------------------------------

        System.out.println("Ukážka cez builder");
        CreateTableSpecification creator = CreateTableSpecification
                .createTable("test_table_2")
                .ifNotExists()
                .partitionKeyColumn("id_oddelenia", DataType.uuid())
                .clusteredKeyColumn("meno", DataType.varchar())
                .column("plat", DataType.decimal());
        cqlTemplate.execute(CreateTableCqlGenerator.toCql(creator));


        System.out.println("Vloženie údajov do databázy za pomoci pripraveného výrazu");
        PreparedStatement preparedStatement = session.prepare("insert into test_table_2 (id_oddelenia, meno, plat) values (?, ?, ?);");
        for (int i = 0; i < 10; i++) {
            BoundStatement boundStatement = new BoundStatement(preparedStatement);
            boundStatement.bind(UUID.randomUUID(), "User " + i, new BigDecimal(1000 * Math.random()).setScale(2, RoundingMode.HALF_EVEN));
            cqlTemplate.execute(boundStatement);
        }

        BatchStatement batchStatement = new BatchStatement();
        for (int i = 0; i < 10; i++) {
            BoundStatement boundStatement = new BoundStatement(preparedStatement);
            boundStatement.bind(UUID.randomUUID(), "User " + i, new BigDecimal(1000 * Math.random()).setScale(2, RoundingMode.HALF_EVEN));
            batchStatement.add(boundStatement);
        }
        cqlTemplate.execute(batchStatement);

        System.out.println("Načítavam dáta");
        cqlTemplate.query("select * from test_table_2;", new RowMapper<Object>() {
            public Object mapRow(Row row, int i) throws DriverException {
                System.out.println(row.getUUID("id_oddelenia") + " " + row.getString("meno") + " " + row.getDecimal("plat"));
                return null;
            }
        });

        System.out.println("Zmazanie tabuľky");
        DropTableSpecification droper = DropTableSpecification.dropTable("test_table_2");
        cqlTemplate.execute(DropTableCqlGenerator.toCql(droper));


        session.getCluster().close();

        applicationContext.close();
    }
}
