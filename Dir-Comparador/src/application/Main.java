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
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/VistaEntrada.fxml"));
            Parent root = loader.load();

            MiControlador controlador = loader.getController();
            controlador.setStage(primaryStage);

            Scene scene = new Scene(root);

            primaryStage.setResizable(false);
            primaryStage.setTitle("Comparador de Directorios");
            primaryStage.setScene(scene);

            primaryStage.setOnHidden(e -> {
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
