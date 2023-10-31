module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;
    requires java.logging;
    requires java.sql;
    requires jlayer;

    opens org.graphic to javafx.fxml;
    exports org.graphic;
    exports org.graphic.controller;
    opens org.graphic.controller to javafx.fxml;
    exports org.graphic.app;
    opens org.graphic.app to javafx.fxml;
    exports org.graphic.dictionary;
    opens org.graphic.dictionary to javafx.fxml;
}