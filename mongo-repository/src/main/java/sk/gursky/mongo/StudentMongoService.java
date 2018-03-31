package sk.gursky.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.stereotype.Service;
import sk.gursky.simplestudent.nosql.aislike.DaoFactory;
import sk.gursky.simplestudent.nosql.aislike.StudentDao;
import sk.gursky.simplestudent.nosql.aislike.entity.Student;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentMongoService {

    private StudentMongoRepository repository;

    public StudentMongoService(StudentMongoRepository repository) {
        this.repository = repository;
    }

    private StudentDao studentDao = DaoFactory.INSTANCE.getStudentDao();

    public void vlozData() {
        List<Student> students = studentDao.getAll();
        List<MongoStudent> css = students.stream().map(s -> new MongoStudent(s)).collect(Collectors.toList());
        repository.saveAll(css);
    }

    public void najdiVsetko() {
        Iterable<MongoStudent> all = repository.findAll();
        for (MongoStudent student : all) {
            System.out.println(student);
        }
    }

    public void zmazVsetko() {
        repository.deleteAll();
    }

    /**
     * Uloha 1a
     *
     * @param priezvisko Priezvisko študenta
     */
    public void najdiPodlaPriezviska(String priezvisko) {
        List<MongoStudent> all = repository.findAllByPriezvisko(priezvisko);
        for (MongoStudent student : all) {
            System.out.println(student);
        }
    }


    /**
     * Uloha 1b
     *
     * @param skratkaAkadTitul Skratka akademického titulu
     */
    public void najdiPodlaSkratkaakadtitul(String skratkaAkadTitul) {
        List<MenoAPriezvisko> all = repository.findAllBySkratkaakadtitul(skratkaAkadTitul);
        for (MenoAPriezvisko item : all) {
            // INFO: toString sice vypise vsetko, ale hodnoty ktore nie su v interface nie su vlozene do objektu
            // INFO: toto nedokazeme urobit: MongoStudent student = (MongoStudent) item;
            System.out.println(item);
        }
    }

    /**
     * Uloha 2
     *
     * @param rok                      Rok, v ktorom študuje
     * @param studijny_program_skratka Skratka študovaného programu
     */
    public void najdiPodlaRokuAStudijnehoProgramu(int rok, String studijny_program_skratka) {
        long startTime = System.currentTimeMillis();
        List<MongoStudent> all = repository.findAllByRokuAStudijnehoProgramu(rok, studijny_program_skratka);
        System.out.println("Query executed in " + (System.currentTimeMillis() - startTime) + " millis " + all.size() + " rows");
        for (MongoStudent student : all) {
            System.out.println(student);
        }
    }

    public void agregujStudijnyProgramARok(MongoTemplate mongoTemplate) {
        String map = "function () {\n" +
                "\n" +
                "\tthis.studia.forEach(function(item) {\n" +
                "\t  for(var rok = item.zaciatok_studia_number; rok <= item.koniec_studia_number; rok++) {\n" +
                "\t\t  emit(item.studijnyProgram.skratka + \" \" + rok, 1);\n" +
                "\t  }\n" +
                "\t});\n" +
                "\t\n" +
                "}";
        String reduce = "function (key, values) {\n" +
                "  \n" +
                "    var output = 0;\n" +
                "    values.forEach(function(val) {\n" +
                "      output += val;\n" +
                "    });\n" +
                "\n" +
                "    return output;\n" +
                "    \n" +
                "}";
        MapReduceResults<MongoAggregated> results = mongoTemplate.mapReduce("student", map, reduce,
                new MapReduceOptions().outputCollection("mongo_agregated"), MongoAggregated.class);
        for (MongoAggregated row : results) {
            System.out.println(row);
        }
    }
}
