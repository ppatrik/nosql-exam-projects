import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import sk.gursky.nosql.aislike.DaoFactory;
import sk.gursky.nosql.aislike.StudentDao;
import sk.gursky.nosql.aislike.entity.SimpleStudent;

import java.util.List;

class App {
    static String ATOMIC_INTEGER = "upjsCounter";

    public static void main(String[] args) {
        System.out.println("Hello world");

        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.INFO);

        RedisAtomicInteger atomicInteger = new RedisAtomicInteger(ATOMIC_INTEGER, RedisFactory.INSTANCE.jedisConnectionFactory());

        System.out.println("Current value (get) " + atomicInteger.get());

        /*ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 2; i++) {
            final String name = Integer.toString(i);
            es.submit(() -> {
                for (int ii = 0; ii < 10; ii++) {
                    System.out.println(name + " Current value (incrementAndGet) " + atomicInteger.incrementAndGet());
                }
            });
        }*/

        SimpleStudentRepository ssr = new SimpleStudentRepository();

        StudentDao sd = DaoFactory.INSTANCE.getStudentDao();
        List<SimpleStudent> sd_ss = sd.getSimpleStudents();

        int limit  = 2;
        for (SimpleStudent student : sd_ss) {
            ssr.save(student);
            if(limit-- < 0) {
                break;
            }
        }

        List<SimpleStudent> ssr_ss = ssr.getAll();
        System.out.println(ssr_ss);


    }
}