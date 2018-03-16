package sk.htsys;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class SubscribberService extends Service {

    private RedisConnection connection;
    private ObservableList<String> spravy;

    public SubscribberService(RedisConnection connection, ObservableList<String> spravy) {
        this.connection = connection;
        this.spravy = spravy;
    }

    @Override
    protected Task createTask() {

        return new Task() {

            @Override
            protected Void call() throws Exception {
                StringRedisSerializer serializer = new StringRedisSerializer();
                byte[] channels = serializer.serialize(MainSceneController.CHANNEL);
                connection.subscribe((message, bytes) -> {
                    String received = serializer.deserialize(message.getBody());
                    Platform.runLater(() -> spravy.add(received));
                }, channels);
                return null;
            }
        };
    }
}
