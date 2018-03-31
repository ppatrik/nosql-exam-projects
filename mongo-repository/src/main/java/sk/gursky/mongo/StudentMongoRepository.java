package sk.gursky.mongo;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentMongoRepository extends CrudRepository<MongoStudent, Long> {

    List<MongoStudent> findAllByPriezvisko(String priezvisko);

    List<MenoAPriezvisko> findAllBySkratkaakadtitul(String Skratkaakadtitul);

    @Query(value="{  \"studia\": {    \"$elemMatch\": {      \"zaciatok_studia_number\": {        $lte: ?0     },    \"koniec_studia_number\": {        $gte: ?0     },    \"studijnyProgram.skratka\": ?1    }  }}")
    List<MongoStudent> findAllByRokuAStudijnehoProgramu(int rok, String studijny_program_skratka);
}
