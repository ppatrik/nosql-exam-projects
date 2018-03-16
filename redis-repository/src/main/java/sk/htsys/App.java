package sk.htsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import sk.gursky.nosql.aislike.DaoFactory;
import sk.gursky.nosql.aislike.StudentDao;
import sk.gursky.nosql.aislike.entity.Student;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(App.class, args);
        StudentRepository repository = applicationContext.getBean(StudentRepository.class);

        StudentDao studentDao = DaoFactory.INSTANCE.getStudentDao();
        List<Student> studentList = studentDao.getAll();
        List<RedisStudent> redisStudentList = studentList.stream().map(RedisStudent::new).collect(Collectors.toList());

        System.out.println("Ukladam");
        repository.save(redisStudentList.get(0));

        System.out.println("nacitavam");
        Iterable<RedisStudent> all = repository.findAll();
        for (RedisStudent redisStudent : all) {
            System.out.println(redisStudent);
        }

        repository.deleteAll();

        repository.saveAll(redisStudentList);


        all = repository.findAll();
        for (RedisStudent redisStudent : all) {
            System.out.println(redisStudent);
        }

        applicationContext.close();

    }
}
