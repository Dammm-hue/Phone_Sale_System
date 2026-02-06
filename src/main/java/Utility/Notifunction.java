package Utility;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class Notifunction {
    private static boolean show=false;
    public static void error(String Title, String Text) {

        if (show) return;

        show = true;

        Notifications notifications = Notifications.create();
        notifications.title(Title);
        notifications.text(Text);
        notifications.hideAfter(Duration.seconds(5));
        notifications.position(Pos.TOP_CENTER);
        notifications.darkStyle();

        FontIcon successicon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
        successicon.setIconSize(20);
        successicon.setIconColor(Color.RED);
        notifications.graphic(successicon);

        notifications.show();

        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(e -> show = false);
        delay.play();
    }
    public static void success(String Title, String Text) {

        if (show) return;

        show = true;

        Notifications notifications = Notifications.create();
        notifications.title(Title);
        notifications.text(Text);
        notifications.hideAfter(Duration.seconds(5));
        notifications.position(Pos.TOP_CENTER);
        notifications.darkStyle();

        FontIcon successicon = new FontIcon(FontAwesomeSolid.CHECK_CIRCLE);
        successicon.setIconSize(20);
        successicon.setIconColor(Color.GREEN);
        notifications.graphic(successicon);

        notifications.show();

        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(e -> show = false);
        delay.play();
    }
}
