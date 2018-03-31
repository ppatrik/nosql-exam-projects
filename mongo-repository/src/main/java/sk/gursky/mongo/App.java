package sk.gursky.mongo;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

public class App {
    public static void main(String[] args) {
        // inicializácia springu
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.WARN);
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MongoConfig.class);

        // načítanie beanov
        StudentMongoService studentService = applicationContext.getBean(StudentMongoService.class);
        MongoTemplate mongoTemplate = applicationContext.getBean(MongoTemplate.class);
        MongoDatabase mongoDatabase = mongoTemplate.getDb();

        ////// ULOHA 1 - vlozenie udajov a otestovanie crud find operacii
        //studentService.zmazVsetko();
        //studentService.vlozData();
        if (false) {
            studentService.najdiVsetko();
            studentService.najdiPodlaPriezviska("Elkjuhovu");
            // projekcia
            studentService.najdiPodlaSkratkaakadtitul("RNDr.");
        }


        ////// ULOHA 2 - vnoreny find
        if (false) {
            studentService.najdiPodlaRokuAStudijnehoProgramu(2000, "MCH");
        }


        ////// ULOHA 3 - pridanie indexu
        if (false) {
            studentService.zmazVsetko();
            studentService.vlozData();

            // Vlozenie indexu, s nim je query pod 10 millis, bez indexu to bolo 70 a viac
            MongoCollection<Document> collection = mongoDatabase.getCollection("student");
            collection.createIndex(Indexes.ascending("studia", "studijnyProgram"));
            studentService.najdiPodlaRokuAStudijnehoProgramu(2000, "MCH");
        }


        ////// ULOHA 4
        if (true) {
            studentService.najdiPodlaRokuAStudijnehoProgramu(2000, "MCH");
            studentService.agregujStudijnyProgramARok(mongoTemplate);
        }

        //studentService.zmazVsetko();

        // ukončenie aplikácie
        applicationContext.close();
    }
}
