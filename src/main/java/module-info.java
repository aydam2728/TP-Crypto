module com.example.tpcrypto {
    requires org.apache.commons.lang3;
    requires org.junit.jupiter.api;
    requires java.desktop;
    requires java.logging;
    opens com.example.tpcrypto;
    exports com.example.tpcrypto;
}