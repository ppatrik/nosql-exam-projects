package sk.gursky.simplestudent.nosql.aislike;

import java.util.List;

import sk.gursky.simplestudent.nosql.aislike.entity.SimpleStudent;
import sk.gursky.simplestudent.nosql.aislike.entity.Student;

public interface StudentDao {
	List<Student> getAll();
	List<SimpleStudent> getSimpleStudents();
}
