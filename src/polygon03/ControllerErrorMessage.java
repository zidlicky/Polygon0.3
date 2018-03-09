package polygon03;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class ControllerErrorMessage implements Initializable {
    @FXML
    public Button btnOk;

    public void errorMessage () {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ErrorMessage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 300, 100);
            Stage stage = new Stage();
            stage.setTitle("Polygon0.3");
            stage.setScene(scene);
            stage.show();
            stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(!newValue){
                        stage.close();
                    }
                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
     @FXML
     private void error(ActionEvent actionEvent){
         Stage stage = (Stage) btnOk.getScene().getWindow();
         stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


}