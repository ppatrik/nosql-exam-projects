package sk.gursky.mongo;

import org.springframework.data.mongodb.core.mapping.Document;
import sk.gursky.simplestudent.nosql.aislike.entity.StudijnyProgram;
import sk.gursky.simplestudent.nosql.aislike.entity.Studium;

@Document(collection = "studijny_program")
public class MongoStudijnyProgram {


    private Long id;
    private String skratka;
    private String popis;

    public MongoStudijnyProgram() {

    }

    public MongoStudijnyProgram(StudijnyProgram studijnyProgram) {
        this.id = studijnyProgram.getId();
        this.popis = studijnyProgram.getPopis().trim();
        this.skratka = studijnyProgram.getSkratka().trim();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "MongoStudijnyProgram{" +
                "id=" + id +
                ", skratka='" + skratka + '\'' +
                ", popis='" + popis + '\'' +
                '}';
    }
}
