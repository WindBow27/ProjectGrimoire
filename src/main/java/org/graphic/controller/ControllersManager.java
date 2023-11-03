package org.graphic.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javazoom.jl.player.Player;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllersManager {
    private static String currentScreen = "home";
    private final Logger logger = Logger.getLogger(ControllersManager.class.getName());
    private final Map<String, Parent> fxmlCache = new HashMap<>();

    public void loadStage(String typeofScreen, Button typeofButton) throws Exception {
        Stage stage = (Stage) typeofButton.getScene().getWindow();
        Parent root = fxmlCache.get(typeofScreen);

        if (root == null) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/graphic/fxml/" + typeofScreen + "-screen.fxml")));
            fxmlCache.put(typeofScreen, root);
        }

        stage.getScene().setRoot(root);
        stage.show();
    }

    public void loadScreen(String typeofScreen, Button typeofButton) throws Exception {
        currentScreen = typeofScreen;
        loadStage(typeofScreen, typeofButton);
    }

    public void loadHomeScreen(String typeofScreen, Button typeofButton) throws Exception {
        currentScreen = "home";
        loadStage(typeofScreen, typeofButton);
    }

    public void loadGameScreen(String typeofScreen, Button typeofButton) throws Exception {
        currentScreen = "game";
        loadStage(typeofScreen, typeofButton);
    }

    public String getCurrentScreen() {
        return currentScreen;
    }

    public void playSoundGoogleTranslate(String text, String tl) {
        if (text == null || text.isEmpty()) return;
        try {
            String soundAPI = "https://translate.google.com/translate_tts?ie=UTF-8&tl=";
            String urlStr = soundAPI + tl + "&client=tw-ob&q="
                    + URLEncoder.encode(text, StandardCharsets.UTF_8);

            URI uri = URI.create(urlStr);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream audio = connection.getInputStream();
            new Player(audio).play();
            connection.disconnect();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
        }
    }

    public void waitLoading(ImageView loading) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
        });
        future.thenRun(() -> loading.setVisible(false));
    }
}


