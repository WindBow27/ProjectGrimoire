package org.graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class App extends Application {
    static int width = 640;
    static int length = 1280;
    static BorderPane root = new BorderPane();
    static Scene scene = new Scene(root, length, width);


    public void start(Stage primaryStage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-screen.fxml"));
        Parent root = FXMLLoader.load(App.class.getResource("main-screen.fxml"));
//        Group root = new Group();
//        Scene scene = new Scene(root, Color.rgb(10, 9, 45));
//        stage.setTitle("Dictionary v1.0");
//        stage.setScene(scene);
        InputStream stream = new FileInputStream("src/main/icon.png");
        Image icon = new Image(stream);
        ImageView imageView = new ImageView();

        imageView.setImage(icon);
        primaryStage.setScene(new Scene(root, 1280,640 ));

        if (!icon.isBackgroundLoading()) primaryStage.getIcons().add(icon);
        else System.out.println("Failed to load image");
        setResizable(primaryStage, false);

        primaryStage.show();
    }

    public static void runApplication() {
        launch();
    }

    public static void init(Stage primaryStage) {
//        root.setBackground(Background.fill(Color.rgb(10, 9, 45)));
//
//        Util menu = new Util();
//
//        root.setTop(menu.getMenuBar());

        //primaryStage.getIcons().add(icon);

        primaryStage.setTitle("Dictionary v1.0");

        primaryStage.setScene(scene);
    }

    public void setResizable(Stage primaryStage, boolean bool) { primaryStage.setResizable(bool); }

}
