package sk.htsys;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainSceneController {

    @FXML
    private ListView<String> chatAreaListView;
    @FXML
    private Button sendButton;
    @FXML
    private TextField textToSendTextField;
    @FXML
    private TextField menoTextField;

    private ObservableList<String> spravy;

    private RedisTemplate redisTemplate;

    private RedisConnection redisConnection;

    public static String CHANNEL = "redisChatChannel";

    public MainSceneController() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(RedisConfiguration.class);
        redisTemplate = ac.getBean(RedisTemplate.class);
        redisConnection = ac.getBean(RedisConnection.class);
    }

    @FXML
    void initialize() {
        spravy = FXCollections.observableArrayList(new ArrayList<String>());
        chatAreaListView.setItems(spravy);
        String meno = menoTextField.textProperty().getValue();
        if (meno == null || meno.trim().length() == 0) {
            meno = "user" + (int) (100000 * Math.random());
            menoTextField.textProperty().setValue(meno);
        }
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String meno = menoTextField.textProperty().getValue();
                String text = textToSendTextField.textProperty().getValue();
                if (text != null && text.trim().length() > 0) {
                    // publish

                    LocalTime now = LocalTime.now();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
                    String message = "[" + now.format(dtf) + ", " + meno + "] " + text;
                    redisTemplate.convertAndSend(CHANNEL, message);
                    textToSendTextField.textProperty().setValue("");
                    textToSendTextField.requestFocus();
                }
            }
        });
        textToSendTextField.setOnAction(event -> {
            sendButton.fire();
        });
        textToSendTextField.requestFocus();

        SubscribberService subscribberService = new SubscribberService(redisConnection, spravy);
        subscribberService.start();
    }
}
