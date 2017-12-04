package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.io.File;

public class Main extends Application {
    private static final String MEDIA_URL =

            new File("/home/chinthaka/Downloads/wow/giphy.mp4").toURI().toString();

    @Override
    public void start(Stage primaryStage)  {

        primaryStage.setTitle("Embedded Media Player");
        Group root = new Group();
        Scene scene = new Scene(root, 540, 210);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

        // create media player
        Media media = new Media(MEDIA_URL);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);


        // create mediaView and add media player to the viewer
        MediaView mediaView = new MediaView(mediaPlayer);
        ((Group)scene.getRoot()).getChildren().add(mediaView);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
