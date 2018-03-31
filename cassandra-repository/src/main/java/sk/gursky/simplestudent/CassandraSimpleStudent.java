package sk.gursky.simplestudent;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import sk.gursky.simplestudent.nosql.aislike.entity.SimpleStudent;

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
@Table(value = "simple_student")
public class CassandraSimpleStudent {

    @PrimaryKey
    private Long id;
    @Column
    private String meno;
    @Column
    private String priezvisko;
    @Transient
    private char kodpohlavie;
    @Column("titul")
    private String skratkaakadtitul;
    @Column("id_studii")
    private Set<Long> idStudii;

    public CassandraSimpleStudent() {

    }

    public CassandraSimpleStudent(SimpleStudent simpleStudent) {
        id = simpleStudent.getId();
        meno = simpleStudent.getMeno();
        priezvisko = simpleStudent.getPriezvisko();
        kodpohlavie = simpleStudent.getKodpohlavie();
        skratkaakadtitul = simpleStudent.getSkratkaakadtitul();
        idStudii = new HashSet<Long>(simpleStudent.getIdStudii());
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

    public Set<Long> getIdStudii() {
        return idStudii;
    }

    public void setIdStudii(Set<Long> idStudii) {
        this.idStudii = idStudii;
    }

    @Override
    public String toString() {
        return "CassandraSimpleStudent{" +
                "id=" + id +
                ", meno='" + meno + '\'' +
                ", priezvisko='" + priezvisko + '\'' +
                ", kodpohlavie=" + kodpohlavie +
                ", skratkaakadtitul='" + skratkaakadtitul + '\'' +
                ", idStudii=" + idStudii +
                '}';
    }
}
