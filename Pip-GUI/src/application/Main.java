package application;

import javafx.stage.Stage;
import marchetti.leon.vista.MiAplicacion;


public class Main extends MiAplicacion {

    @Override
    public void start(Stage primaryStage) {
        setLocation("/vista/Main.fxml");
        addIcon(primaryStage, "/vista/python.png");
        super.start(primaryStage);
    }

	public static void main(String[] args) {
		launch(args);
	}
}
