package polygon03;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ui.fxml"));
            loader.setControllerFactory((Class<?> type) -> {
                try {
                    Object controller = type.newInstance();
                    if (controller instanceof PrimaryStageAware) {
                        ((PrimaryStageAware) controller).setPrimaryStage(primaryStage);
                    }
                    return controller ;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            StackPane root = loader.load();
            Scene scene = new Scene(root,800,600);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
