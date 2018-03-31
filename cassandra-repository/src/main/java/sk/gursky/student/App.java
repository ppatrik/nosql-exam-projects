package sk.gursky.student;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.datastax.driver.core.Session;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        // inicializácia springu
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.WARN);
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(CassandraConfig.class);

        // načítanie beanov
        Session session = applicationContext.getBean(Session.class);
        StudentService studentService = applicationContext.getBean(StudentService.class);

        studentService.vlozData();
        //studentService.najdiPodlaPriezviska();
        //studentService.najdiPodlaId();
        //studentService.vypisMena();
        studentService.vypisPodlaAkadTitul();



        // ukončenie aplikácie
        session.getCluster().close();
        applicationContext.close();
    }
}
