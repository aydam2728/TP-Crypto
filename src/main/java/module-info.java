module com.example.tpcrypto {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    opens com.example.tpcrypto to javafx.fxml;
    exports com.example.tpcrypto;
}