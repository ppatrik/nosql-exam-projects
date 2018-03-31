package sk.gursky.student;

import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends CrudRepository<CassandraStudent, MapId> {

    @AllowFiltering
    List<CassandraStudent> findByPriezvisko(String priezvisko);

    List<MenoAPriezvisko> findAllBy();

    @AllowFiltering
    Collection<MenoAPriezvisko> findBySkratkaakadtitul(String skratka);
}
