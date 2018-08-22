package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import marchetti.leon.MiControlador;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaEntrada.fxml"));
            Parent root = loader.load();

            MiControlador controlador = loader.getController();
            controlador.setStage(primaryStage);

            Scene scene = new Scene(root);

            primaryStage.setResizable(false);
            primaryStage.setTitle("Comparador de Directorios");
            primaryStage.setScene(scene);

            primaryStage.setOnHidden(e -> {
                try {
                    controlador.guardarDatos();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
