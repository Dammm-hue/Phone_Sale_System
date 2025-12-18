module com.example.phone_sale {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.phone_sale to javafx.fxml;
    exports com.example.phone_sale;
}