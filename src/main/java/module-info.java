module com.cafeconnect.pos.cafeconnect {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires kotlin.stdlib;
    requires java.desktop;


    opens com.cafeconnect.pos.cafeconnect to javafx.fxml;
    exports com.cafeconnect.pos.cafeconnect;
}