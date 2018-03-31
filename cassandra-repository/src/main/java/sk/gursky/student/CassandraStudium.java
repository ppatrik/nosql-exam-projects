package sk.gursky.student;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;
import sk.gursky.simplestudent.nosql.aislike.entity.SimpleStudent;
import sk.gursky.simplestudent.nosql.aislike.entity.Studium;

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
@UserDefinedType(value = "studium")
public class CassandraStudium {

    @PrimaryKeyColumn(name="id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Long id;
    @Column
    private String zaciatok_studia;
    @Column
    private String koniec_studia;
    @Column
    private Long id_studijny_program;
    @Column
    private String skratka;
    @Column
    private String popis;

    public CassandraStudium() {

    }

    public CassandraStudium(Studium studium) {
        this.id = studium.getId();
        this.zaciatok_studia = studium.getZaciatokStudia().trim();
        this.koniec_studia = studium.getKoniecStudia().trim();
        this.id_studijny_program = studium.getStudijnyProgram().getId();
        this.skratka = studium.getStudijnyProgram().getSkratka().trim();
        this.popis = studium.getStudijnyProgram().getPopis().trim();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZaciatok_studia() {
        return zaciatok_studia;
    }

    public void setZaciatok_studia(String zaciatok_studia) {
        this.zaciatok_studia = zaciatok_studia;
    }

    public String getKoniec_studia() {
        return koniec_studia;
    }

    public void setKoniec_studia(String koniec_studia) {
        this.koniec_studia = koniec_studia;
    }

    public Long getId_studijny_program() {
        return id_studijny_program;
    }

    public void setId_studijny_program(Long id_studijny_program) {
        this.id_studijny_program = id_studijny_program;
    }

    public String getSkratka() {
        return skratka;
    }

    public void setSkratka(String skratka) {
        this.skratka = skratka;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    @Override
    public String toString() {
        return "CassandraStudium{" +
                "id=" + id +
                ", zaciatok_studia='" + zaciatok_studia.trim() + '\'' +
                ", koniec_studia='" + koniec_studia.trim() + '\'' +
                ", id_studijny_program=" + id_studijny_program +
                ", skratka='" + skratka.trim() + '\'' +
                ", popis='" + popis.trim() + '\'' +
                '}';
    }
}
