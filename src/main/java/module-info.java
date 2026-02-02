module com.example.phone_sale {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires jakarta.mail;
    requires javafx.base;
    requires javafx.graphics;
    requires jbcrypt;

    opens com.example.phone_sale to javafx.fxml;
    opens Controller to javafx.fxml;
    exports com.example.phone_sale;
    exports Controller;
    exports Model;
    exports Utility;
    exports Database;
    exports Main;
}