package sk.gursky.simplestudent;

import org.springframework.data.repository.CrudRepository;

public interface SimpleStudentRepository extends CrudRepository<CassandraSimpleStudent, Long> {

}
