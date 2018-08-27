package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import marchetti.leon.vista.MiControlador;
import modelo.Pip;


public class ControladorPip extends MiControlador {

    private class HiloListaPip extends Thread {
        @Override public void run() {
            try {
                List<String> paquetes = Pip.listaActualizables();
                filasPip.clear();
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
            desactivarControles(false);
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
            desactivarControles(false);
        }
    }

    @FXML private Button btnMostrar;
    @FXML private TableView<FilaPip> tblPip;
    @FXML private TableColumn<FilaPip, String> colPackage;
    @FXML private TableColumn<FilaPip, String> colVersion;
    @FXML private TableColumn<FilaPip, String> colLatest;
    private ObservableList<FilaPip> filasPip = FXCollections.observableArrayList();

    public ControladorPip() {
        TITULO = "Pip GUI";
    }

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
                desactivarControles(true);
                (new HiloActualizarPip(newV.getPackage())).start();
            }
        });
    }

    @FXML private void mostrar() {
        desactivarControles(true);
        (new HiloListaPip()).start();
    }

    @Override
    protected void desactivarControles(boolean desactivar) {
        btnMostrar.setDisable(desactivar);
    }

    @Override
    public void guardarDatos() throws IOException {

    }
}
