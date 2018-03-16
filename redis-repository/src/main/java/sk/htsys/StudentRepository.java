package sk.htsys;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<RedisStudent, Long> {
}
