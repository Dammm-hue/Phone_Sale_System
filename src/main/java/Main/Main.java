package Main;

import Utility.Window_Manage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Window_Manage.isOpen("/FXML/user_login_form.fxml","login_stage",false,true);
    }
}
