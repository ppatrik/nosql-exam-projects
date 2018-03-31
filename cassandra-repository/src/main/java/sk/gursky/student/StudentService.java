package sk.gursky.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.mapping.BasicMapId;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.stereotype.Service;
import sk.gursky.simplestudent.nosql.aislike.DaoFactory;
import sk.gursky.simplestudent.nosql.aislike.StudentDao;
import sk.gursky.simplestudent.nosql.aislike.entity.Student;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;


    private StudentDao studentDao = DaoFactory.INSTANCE.getStudentDao();

    public void vlozData() {
        List<Student> students = studentDao.getAll();
        List<CassandraStudent> css = students.stream().map(s -> new CassandraStudent(s)).collect(Collectors.toList());
        repository.saveAll(css);
    }

    public void najdiPodlaPriezviska() {
        String podla = "Rojlulas";
        List<CassandraStudent> list = repository.findByPriezvisko(podla);
        System.out.println(list);
    }

    public void najdiPodlaId() {
        MapId mapId = BasicMapId.id("id", 1006357).with("priezvisko", "Rojlulas");
        Optional<CassandraStudent> byId = repository.findById(mapId);
        System.out.println(byId);
    }

    public void vypisMena() {
        System.out.println(repository.findAllBy());
    }

    public void vypisPodlaAkadTitul() {
        Collection<MenoAPriezvisko> menoAPriezvisko = repository.findBySkratkaakadtitul("Mgr.");
        System.out.println(menoAPriezvisko);
    }
}
