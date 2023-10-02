module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires async.http.client;
    requires java.net.http;
    requires java.logging;
    requires java.sql;
    requires jlayer;

    opens org.graphic to javafx.fxml;
    exports org.graphic;
}