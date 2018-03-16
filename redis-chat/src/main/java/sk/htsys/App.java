package sk.htsys;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{

	@Override
	public void start(Stage primaryStage) throws Exception {
        MainSceneController controller = new MainSceneController();
        FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        fXMLLoader.setController(controller);
        
        Scene scene = new Scene(fXMLLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Users management");
        primaryStage.show();
		
	}

    public static void main(String[] args) {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.INFO);
        launch(args);
    }
}
