module com.example.dictionaryapplicaton {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dictionaryapplicaton to javafx.fxml;
    exports com.example.dictionaryapplicaton;
}