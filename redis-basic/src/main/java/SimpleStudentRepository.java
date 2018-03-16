
import org.springframework.data.redis.core.HashOperations;
import sk.gursky.nosql.aislike.entity.SimpleStudent;

import java.util.List;

public class SimpleStudentRepository {
    static String REPOSITORY_KEY = "simpleStudentRepository";
    private HashOperations<String, Long, SimpleStudent> hashOperations;

    public SimpleStudentRepository() {
        hashOperations = RedisFactory.INSTANCE.getSimpleStudentTemplate().opsForHash();
    }

    public void save(SimpleStudent student) {
        hashOperations.put(REPOSITORY_KEY, student.getId(), student);
    }

    public SimpleStudent get(Long id) {
        return hashOperations.get(REPOSITORY_KEY, id);
    }

    public List<SimpleStudent> getAll() {
        return hashOperations.values(REPOSITORY_KEY);
    }

}
