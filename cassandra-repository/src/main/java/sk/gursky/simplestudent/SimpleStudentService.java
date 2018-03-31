package sk.gursky.simplestudent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.gursky.simplestudent.nosql.aislike.DaoFactory;
import sk.gursky.simplestudent.nosql.aislike.StudentDao;
import sk.gursky.simplestudent.nosql.aislike.entity.SimpleStudent;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimpleStudentService {

    @Autowired
    private SimpleStudentRepository repository;


    private StudentDao studentDao = DaoFactory.INSTANCE.getStudentDao();

    public void vlozData() {
        List<SimpleStudent> simpleStudents = studentDao.getSimpleStudents();
        List<CassandraSimpleStudent> css = simpleStudents.stream().map(s -> new CassandraSimpleStudent(s)).collect(Collectors.toList());
        repository.saveAll(css);
    }

}
