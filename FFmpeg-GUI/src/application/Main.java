package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Main.fxml"));
            Parent root = loader.load();
            Controlador controlador = loader.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/application/application.css");

            primaryStage.setResizable(false);
            primaryStage.setTitle("FFmpeg");
            primaryStage.setScene(scene);

            primaryStage.getIcons().add(
                new Image(this.getClass().getResourceAsStream("ffmpeg.png")));

            primaryStage.setOnHidden(evento -> {
                try {
                    controlador.guardarDatos();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
	}

	public static void main(String[] args) {
		launch(args);
	}
}
