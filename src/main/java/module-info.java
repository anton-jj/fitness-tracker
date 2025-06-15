module com.example.fitnesstracker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.fitnesstracker to javafx.fxml;
    exports com.example.fitnesstracker;
}