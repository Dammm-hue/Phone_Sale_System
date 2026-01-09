package Utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Window_Manage {

    private static Map<String,Stage> stageMap = new HashMap<>();

    public static void isOpen(String fxml,String key,boolean maximize,boolean topbar) throws IOException {

        Parent root = FXMLLoader.load(Window_Manage.class.getResource(fxml));
        Stage stage = new Stage();
        stageMap.put(key,stage);
        stage.setScene(new Scene(root));
        stage.setMaximized(maximize);
        if(topbar){
            stage.initStyle(StageStyle.UNDECORATED);
        }
        stage.show();
        stage.centerOnScreen();
    }

    public static Stage getStage(String key) {
        return stageMap.get(key);
    }
}
