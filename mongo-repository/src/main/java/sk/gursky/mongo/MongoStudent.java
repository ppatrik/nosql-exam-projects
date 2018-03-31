package sk.gursky.mongo;

import org.springframework.data.mongodb.core.mapping.Document;
import sk.gursky.simplestudent.nosql.aislike.entity.Student;
import sk.gursky.simplestudent.nosql.aislike.entity.Studium;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "student")
public class MongoStudent {

    private Long id;
    private String meno;
    private String priezvisko;
    private char kodpohlavie;
    private String skratkaakadtitul;
    private List<MongoStudium> studia = new ArrayList<>();

    public MongoStudent() {

    }

    public MongoStudent(Student student) {
        id = student.getId();
        meno = student.getMeno().trim();
        priezvisko = student.getPriezvisko().trim();
        kodpohlavie = student.getKodpohlavie();
        skratkaakadtitul = student.getSkratkaakadtitul().trim();
        for (Studium studium : student.getStudium()) {
            studia.add(new MongoStudium(studium));
        }
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

    public char getKodpohlavie() {
        return kodpohlavie;
    }

    public void setKodpohlavie(char kodpohlavie) {
        this.kodpohlavie = kodpohlavie;
    }

    public String getSkratkaakadtitul() {
        return skratkaakadtitul;
    }

    public void setSkratkaakadtitul(String skratkaakadtitul) {
        this.skratkaakadtitul = skratkaakadtitul;
    }

    @Override
    public String toString() {
        return "MongoStudent{" +
                "id=" + id +
                ", meno='" + meno + '\'' +
                ", priezvisko='" + priezvisko + '\'' +
                ", kodpohlavie=" + kodpohlavie +
                ", skratkaakadtitul='" + skratkaakadtitul + '\'' +
                ", studia=" + studia +
                '}';
    }
}
