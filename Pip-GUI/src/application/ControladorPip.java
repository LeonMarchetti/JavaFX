package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import marchetti.leon.vista.MiControlador;
import modelo.Pip;


public class ControladorPip extends MiControlador
{
    private class HiloListaPip extends Thread
    {

        private ControladorPip controlador;

        public HiloListaPip(ControladorPip controlador)
        {
            this.controlador = controlador;
        }

        @Override
        public void run()
        {
            try
            {
                List<String> paquetes = Pip.listaActualizables();

                // Actualizar barra de estado con n° de paquetes encontrados
                Platform.runLater(() ->
                {
                    lblPaquetes.setText((paquetes.size() == 1)
                        ? "Un paquete encontrado"
                        : paquetes.size() + " paquetes encontrados");
                });

                filasPip.clear();
                if (!paquetes.isEmpty())
                {
                    try
                    {
                        for (String fila : paquetes)
                        {
                            filasPip.add(new FilaPip(fila, controlador));
                        }
                    } catch (RuntimeException e)
                    {
                        e.printStackTrace();
                    }
                }

            } catch (InterruptedException | IOException e)
            {
                e.printStackTrace();
            }
            desactivarControles(false);
        }
    }

    class HiloActualizarPip
        extends Thread
    {
        private String paquete;

        public HiloActualizarPip(String paquete)
        {
            this.paquete = paquete;
        }

        @Override
        public void run()
        {
            try
            {
                Platform.runLater(() ->
                {
                    lblActualizado.setText("Actualizando \"" + this.paquete + "\"");
                });

                desactivarControles(true);
                String salida = Pip.actualizar(this.paquete);
                String mensaje = "";

                if (!salida.equals(""))
                {
                    mensaje = "Error actualizando";
                    alertaError("Actualizar", salida);

                } else
                {
                    mensaje = "Paquete actualizado";
                    mostrar();
                }

                final String estadoInstalado = new String(mensaje + " \"" + this.paquete + "\"");
                Platform.runLater(() ->
                {
                    lblActualizado.setText(estadoInstalado);
                });

            } catch (InterruptedException | IOException e)
            {
                e.printStackTrace();
            }
            desactivarControles(false);
        }
    }

    class HiloDesinstalarPip
        extends Thread
    {
        private String paquete;

        public HiloDesinstalarPip(String paquete)
        {
            this.paquete = paquete;
        }

        @Override
        public void run()
        {
            try
            {
                Platform.runLater(() ->
                {
                    lblActualizado.setText("Desinstalando \"" + this.paquete + "\"");
                });

                desactivarControles(true);
                String salida = Pip.desinstalar(this.paquete);
                String mensaje = "";

                if (!salida.equals(""))
                {
                    mensaje = "Error desinstalando";
                    alertaError("Desinstalar", salida);

                } else
                {
                    mensaje = "Paquete desinstalado";
                    mostrar();
                }

                final String estadoInstalado = new String(mensaje + " \"" + this.paquete + "\"");
                Platform.runLater(() ->
                {
                    lblActualizado.setText(estadoInstalado);
                });

            } catch (InterruptedException | IOException e)
            {
                e.printStackTrace();
            }
            desactivarControles(false);
        }
    }

    @FXML
    private Button                       btnMostrar;
    @FXML
    private Label                        lblActualizado;
    @FXML
    private Label                        lblPaquetes;
    @FXML
    private TableView<FilaPip>           tblPip;
    @FXML
    private TableColumn<FilaPip, String> colPackage;
    @FXML
    private TableColumn<FilaPip, String> colVersion;
    @FXML
    private TableColumn<FilaPip, String> colLatest;
    @FXML
    private TableColumn<FilaPip, Button> colUpdate;
    @FXML
    private TableColumn<FilaPip, Button> colUninstall;
    private ObservableList<FilaPip>      filasPip = FXCollections.observableArrayList();

    public ControladorPip()
    {
        TITULO = "Pip GUI";
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        tblPip.setItems(filasPip);

        colPackage.setCellValueFactory(new PropertyValueFactory<FilaPip, String>("package"));
        colVersion.setCellValueFactory(new PropertyValueFactory<FilaPip, String>("version"));
        colLatest.setCellValueFactory(new PropertyValueFactory<FilaPip, String>("latest"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<FilaPip, Button>("actualizar"));
        colUninstall.setCellValueFactory(new PropertyValueFactory<FilaPip, Button>("desinstalar"));

        mostrar();

        tblPip.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) ->
        {
            if (newV != null)
            {
                mostrarInfoPaquete(newV.getPackage());
                Platform.runLater(() ->
                {
                    /* Limpiar la selección para volver a poder seleccionar la
                     * fila. Tuve que ponerlo dentro del Platform.runLater(),
                     * porque si no no me dejaba. */
                    tblPip.getSelectionModel().clearSelection();
                });

            }
        });
    }

    private void mostrarInfoPaquete(String paquete)
    {
        Platform.runLater(() ->
        {
            try
            {
                String salida = Pip.mostrar(paquete);
                alertaInformacion("Información de paquete " + paquete, salida);

            } catch (InterruptedException | IOException e)
            {
                alertaError("Información de paquete " + paquete, "No se pudo conseguir información del paquete.");
            }
        });
    }

    @FXML
    private void mostrar()
    {
        desactivarControles(true);
        (new HiloListaPip(this)).start();
    }

    @Override
    protected void desactivarControles(boolean desactivar)
    {
        btnMostrar.setDisable(desactivar);
        for (FilaPip fila : filasPip)
        {
            fila.getActualizar().setDisable(desactivar);
            fila.getDesinstalar().setDisable(desactivar);
        }
    }

    @Override
    public void guardarDatos() throws IOException
    {

    }
}
