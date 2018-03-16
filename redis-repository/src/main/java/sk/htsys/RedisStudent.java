package sk.htsys;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import sk.gursky.nosql.aislike.entity.Student;
import sk.gursky.nosql.aislike.entity.Studium;

import java.util.ArrayList;
import java.util.List;

@RedisHash("redisStudentRepository")
public class RedisStudent {

    @Id
    private Long id;
    private String meno;
    private String priezvisko;
    private Character kodpohlavie;
    private String skratkaakadtitul;
    private List<Studium> studium = new ArrayList<Studium>();

    public RedisStudent() {

    }

    public RedisStudent(Student student) {
        id = student.getId();
        meno = student.getMeno();
        priezvisko = student.getPriezvisko();
        kodpohlavie = student.getKodpohlavie();
        skratkaakadtitul = student.getSkratkaakadtitul();
        studium = student.getStudium();
    }

    public Student getStudent() {
        Student s = new Student();
        s.setId(id);
        s.setMeno(meno);
        s.setPriezvisko(priezvisko);
        s.setKodpohlavie(kodpohlavie);
        s.setSkratkaakadtitul(skratkaakadtitul);
        s.setStudium(studium);
        return s;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getPriezvisko() {
        return priezvisko;
    }

    public void setPriezvisko(String priezvisko) {
        this.priezvisko = priezvisko;
    }

    public Character getKodpohlavie() {
        return kodpohlavie;
    }

    public void setKodpohlavie(Character kodpohlavie) {
        this.kodpohlavie = kodpohlavie;
    }

    public String getSkratkaakadtitul() {
        return skratkaakadtitul;
    }

    public void setSkratkaakadtitul(String skratkaakadtitul) {
        this.skratkaakadtitul = skratkaakadtitul;
    }

    public List<Studium> getStudium() {
        return studium;
    }

    public void setStudium(List<Studium> studium) {
        this.studium = studium;
    }

    @Override
    public String toString() {
        return "RedisStudent{" +
                "id=" + id +
                ", meno='" + meno.trim() + '\'' +
                ", priezvisko='" + priezvisko.trim() + '\'' +
                ", kodpohlavie=" + kodpohlavie +
                ", skratkaakadtitul='" + skratkaakadtitul.trim() + '\'' +
                ", studium=" + studium +
                '}';
    }
}
