package application;

import javafx.stage.Stage;
import marchetti.leon.vista.MiAplicacion;


public class Main extends MiAplicacion {

    @Override
    public void start(Stage primaryStage) {
        setLocation("/vista/Vista.fxml");
        super.start(primaryStage);
    }

	public static void main(String[] args) {
		launch(args);
	}
}
