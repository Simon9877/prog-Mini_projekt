module com.example.progmini_projekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.progmini_projekt to javafx.fxml;
    exports com.example.progmini_projekt;
}