module com.github.niipi.ohii_harjoitustyo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.niipi.ohii_harjoitustyo to javafx.fxml;
    exports com.github.niipi.ohii_harjoitustyo;
}