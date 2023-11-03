package org.graphic.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;

import java.util.Objects;

public class AboutScreenController extends HomeScreenController {
    @FXML
    private WebView github;
    @FXML
    private Button exit;
    @FXML
    private ImageView loading;

    public void initialize() {
        loading.setVisible(true);
        github.getEngine().load("https://github.com/windbow27/ProjectGrimoire#readme");
        github.getEngine().setUserStyleSheetLocation(Objects.requireNonNull(getClass().getResource("/org/graphic/css/web.css")).toString());
        waitLoading(loading);
    }

    public void exit() throws Exception {
        loadScreen("home", exit);
    }
}
