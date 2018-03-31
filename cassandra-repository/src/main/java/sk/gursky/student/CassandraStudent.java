package sk.gursky.student;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import sk.gursky.simplestudent.nosql.aislike.entity.Student;
import sk.gursky.simplestudent.nosql.aislike.entity.Studium;

import java.util.HashSet;
import java.util.Set;

/**
 * Potrebujeme vytvoriť create table:
 * ```
 * create table simple_student (
 * id bigint primary key,
 * meno varchar,
 * priezvisko varchar,
 * titul varchar,
 * id_studii set<bigint>
 * );
 * create index priezviskoIndex on simple_student (priezvisko);
 * ```
 */
@Table(value = "student")
public class CassandraStudent {

    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Long id;
    @Column
    private String meno;
    @PrimaryKeyColumn(name = "priezvisko", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private String priezvisko;
    @Transient
    private char kodpohlavie;
    @Column("titul")
    private String skratkaakadtitul;
    @Column
    private Set<CassandraStudium> studia = new HashSet<>();

    public CassandraStudent() {

    }

    public CassandraStudent(Student student) {
        id = student.getId();
        meno = student.getMeno().trim();
        priezvisko = student.getPriezvisko().trim();
        kodpohlavie = student.getKodpohlavie();
        skratkaakadtitul = student.getSkratkaakadtitul().trim();
        for (Studium studium : student.getStudium()) {
            studia.add(new CassandraStudium(studium));
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
        return "CassandraStudent{" +
                "id=" + id +
                ", meno='" + meno.trim() + '\'' +
                ", priezvisko='" + priezvisko.trim() + '\'' +
                ", kodpohlavie=" + kodpohlavie +
                ", skratkaakadtitul='" + skratkaakadtitul.trim() + '\'' +
                ", studia=" + studia +
                '}';
    }
}
