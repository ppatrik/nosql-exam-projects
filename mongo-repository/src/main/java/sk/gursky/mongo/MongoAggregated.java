package sk.gursky.mongo;

import org.springframework.data.mongodb.core.mapping.Document;
import sk.gursky.simplestudent.nosql.aislike.entity.Student;
import sk.gursky.simplestudent.nosql.aislike.entity.Studium;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "mongo_agregated")
public class MongoAggregated {

    private String id;
    private Integer value;

    public MongoAggregated() {

    }

    @Override
    public String toString() {
        return "MongoAggregated{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
