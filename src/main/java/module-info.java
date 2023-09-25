module com.example.tpcrypto {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.apache.commons.lang3;
    requires org.junit.jupiter.api;
    opens com.example.tpcrypto to javafx.fxml;
    exports com.example.tpcrypto;
}