package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pip.Pip;


public class Controlador implements Initializable {

    private class HiloListaPip extends Thread {
        @Override public void run() {
            try {
                filasPip.clear();
                List<String> paquetes = Pip.listaActualizables();
                if (paquetes.isEmpty()) {
                    alertaError("Mostrar paquetes", "No hay paquetes para actualizar.");

                } else {
                    try {
                        for (String fila : paquetes) {
                            filasPip.add(new FilaPip(fila));
                        }

                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
            btnMostrar.setDisable(false);
        }
    }

    private class HiloActualizarPip extends Thread {
        private String paquete;

        public HiloActualizarPip(String paquete) {
            this.paquete = paquete;
        }

        @Override public void run() {
            try {
                String salida = Pip.actualizar(this.paquete);
                if (!salida.equals("")) {
                    alertaError("Actualizar", salida);

                } else {
                    mostrar();
                }

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
            btnMostrar.setDisable(false);
        }
    }

    @FXML private Button btnMostrar;
    @FXML private TableView<FilaPip> tblPip;
    @FXML private TableColumn<FilaPip, String> colPackage;
    @FXML private TableColumn<FilaPip, String> colVersion;
    @FXML private TableColumn<FilaPip, String> colLatest;
    private ObservableList<FilaPip> filasPip = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tblPip.setItems(filasPip);

        colPackage.setCellValueFactory(
            new PropertyValueFactory<FilaPip, String>("package"));
        colVersion.setCellValueFactory(
            new PropertyValueFactory<FilaPip, String>("version"));
        colLatest.setCellValueFactory(
            new PropertyValueFactory<FilaPip, String>("latest"));

        mostrar();

        tblPip.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                btnMostrar.setDisable(true);
                (new HiloActualizarPip(newV.getPackage())).start();
            }
        });
    }

    @FXML private void mostrar() {
        btnMostrar.setDisable(true);
        (new HiloListaPip()).start();
    }

    private void alertaError(String encabezado, String contenido) {
        Platform.runLater(new Thread() {
            @Override public void run() {
                Alert alerta = new Alert(AlertType.ERROR);
                alerta.setTitle("Pip GUI");
                alerta.setHeaderText(encabezado);
                alerta.setContentText(contenido);
                alerta.show();
            }
        });
    }

}
