package sk.gursky.nosql.aislike;

import java.util.List;

import sk.gursky.nosql.aislike.entity.SimpleStudent;
import sk.gursky.nosql.aislike.entity.Student;

public interface StudentDao {
	List<Student> getAll();
	List<SimpleStudent> getSimpleStudents();
}
