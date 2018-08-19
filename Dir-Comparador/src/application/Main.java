package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Vista.fxml"));
            Parent root = loader.load();
            Controlador controlador = loader.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/application/application.css");

            primaryStage.setResizable(false);
            primaryStage.setTitle("Comparador de Directorios");
            primaryStage.setScene(scene);

            primaryStage.setOnHidden(evento -> {
                controlador.guardarDatos();
            });

            primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
