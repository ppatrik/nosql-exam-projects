package sk.gursky.mongo;

import org.springframework.data.mongodb.core.mapping.Document;
import sk.gursky.simplestudent.nosql.aislike.entity.Studium;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Document(collection = "studium")
public class MongoStudium {


    private Long id;
    private String zaciatok_studia;
    private Integer zaciatok_studia_number;
    private String koniec_studia;
    private Integer koniec_studia_number;
    private MongoStudijnyProgram studijnyProgram;

    public MongoStudium() {

    }

    public MongoStudium(Studium studium) {
        setId(studium.getId());
        setZaciatok_studia(studium.getZaciatokStudia().trim());
        setKoniec_studia(studium.getKoniecStudia().trim());
        setStudijnyProgram(new MongoStudijnyProgram(studium.getStudijnyProgram()));
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
        this.zaciatok_studia_number = parseDateToNumber(zaciatok_studia);
    }

    private Integer parseDateToNumber(String stringDate) {
        if(stringDate.isEmpty()) {
            return null;
        }
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        LocalDate date = LocalDate.parse(stringDate, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy");
        return Integer.valueOf(date.format(outputFormatter));
    }

    public String getKoniec_studia() {
        return koniec_studia;
    }

    public void setKoniec_studia(String koniec_studia) {
        this.koniec_studia = koniec_studia;
        this.koniec_studia_number = parseDateToNumber(koniec_studia);
    }

    public MongoStudijnyProgram getStudijnyProgram() {
        return studijnyProgram;
    }

    public void setStudijnyProgram(MongoStudijnyProgram studijnyProgram) {
        this.studijnyProgram = studijnyProgram;
    }

    public Integer getZaciatok_studia_number() {
        return zaciatok_studia_number;
    }

    public void setZaciatok_studia_number(Integer zaciatok_studia_number) {
        this.zaciatok_studia_number = zaciatok_studia_number;
    }

    public Integer getKoniec_studia_number() {
        return koniec_studia_number;
    }

    public void setKoniec_studia_number(Integer koniec_studia_number) {
        this.koniec_studia_number = koniec_studia_number;
    }

    @Override
    public String toString() {
        return "MongoStudium{" +
                "id=" + id +
                ", zaciatok_studia='" + zaciatok_studia + '\'' +
                ", zaciatok_studia_number='" + zaciatok_studia_number + '\'' +
                ", koniec_studia='" + koniec_studia + '\'' +
                ", koniec_studia_number='" + koniec_studia_number + '\'' +
                ", studijnyProgram=" + studijnyProgram +
                '}';
    }
}
