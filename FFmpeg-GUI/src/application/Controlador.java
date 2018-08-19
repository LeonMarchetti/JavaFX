package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import ffmpeg.ConversionGIF;
import ffmpeg.RecortarImagen;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;


public class Controlador implements Initializable {

    private final String ARCHIVO = getClass().getClassLoader().getResource("").getPath()
        + "ffmpeg-gui.properties";

    private Properties props;
    private ValorDirectorio dir = new ValorDirectorio();

    private class ValorDirectorio {
        private String dir = "";

        public String getDir() {
            return this.dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
            btnDir.setText(this.dir);
        }
    }

    private class HiloConvertir extends Thread {
        @Override public void run() {
            try {
                ConversionGIF.convertir(
                    dir.getDir(),
                    txtConvertirVideo.getText(),
                    txtConvertirSalida.getText(),
                    txtConvertirInicio.getText(),
                    txtConvertirFin.getText(),
                    txtConvertirEscala.getText()
                );
                desactivarBotones(false);

            } catch (IOException e) {
                alertaError("Error de E/S", e.getMessage());

            } catch (InterruptedException e) {
                alertaError("Proceso interrumpido", e.getMessage());
            }
        }
    }

    private class HiloRecortar extends Thread {
        @Override public void run() {
            try {
                RecortarImagen.recortar(
                    dir.getDir(),
                    txtRecortarImagen.getText(),
                    txtRecortarSalida.getText(),
                    Integer.parseInt(txtRecortarAncho.getText()),
                    Integer.parseInt(txtRecortarAlto.getText()),
                    Integer.parseInt(txtRecortarX.getText()),
                    Integer.parseInt(txtRecortarY.getText())
                );
                desactivarBotones(false);

            } catch (NumberFormatException e) {
                alertaError("Error de conversión de texto a número", e.getMessage());

            } catch (IOException e) {
                alertaError("Error de E/S", e.getMessage());

            } catch (InterruptedException e) {
                alertaError("Proceso interrumpido", e.getMessage());
            }
        }
    }

    @FXML private Button btnDir;

    // Pestaña "Convertir a GIF":
    @FXML private TextField txtConvertirVideo;
    @FXML private TextField txtConvertirSalida;
    @FXML private TextField txtConvertirInicio;
    @FXML private TextField txtConvertirFin;
    @FXML private TextField txtConvertirEscala;
    @FXML private Button btnConvertir;

    // Pestaña "Recortar Imagen":
    @FXML private TextField txtRecortarImagen;
    @FXML private TextField txtRecortarSalida;
    @FXML private TextField txtRecortarAncho;
    @FXML private TextField txtRecortarAlto;
    @FXML private TextField txtRecortarX;
    @FXML private TextField txtRecortarY;
    @FXML private Button btnRecortar;

    public Controlador() {

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            props = new Properties();
            props.load(new FileReader(ARCHIVO));

            txtConvertirVideo.setText(props.getProperty("convertir.video"));
            txtConvertirSalida.setText(props.getProperty("convertir.salida"));
            txtConvertirInicio.setText(props.getProperty("convertir.inicio"));
            txtConvertirFin.setText(props.getProperty("convertir.fin"));
            txtConvertirEscala.setText(props.getProperty("convertir.escala"));

            txtRecortarImagen.setText(props.getProperty("recortar.imagen"));
            txtRecortarSalida.setText(props.getProperty("recortar.salida"));
            txtRecortarAncho.setText(props.getProperty("recortar.ancho"));
            txtRecortarAlto.setText(props.getProperty("recortar.alto"));
            txtRecortarX.setText(props.getProperty("recortar.X"));
            txtRecortarY.setText(props.getProperty("recortar.Y"));

            dir.setDir(props.getProperty("directorio"));

            txtConvertirSalida.textProperty().addListener(evento -> {
                txtRecortarImagen.setText(txtConvertirSalida.getText());
                txtRecortarSalida.setText(txtConvertirSalida.getText());

                try {
                    BufferedImage bImg = ImageIO.read(new File(
                        dir.getDir() + "\\" +
                        txtConvertirSalida.getText()));
                    txtRecortarAncho.setText(String.valueOf(bImg.getWidth()));
                    txtRecortarAlto.setText(String.valueOf(bImg.getHeight()));

                } catch (IOException e) { /* No hacer nada */}
            });

        } catch (FileNotFoundException e) {
            System.out.printf("FileNotFoundException\n%s\n", e.getMessage());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void actSeleccionarDir(ActionEvent evento) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("FFmpeg GUI");

        // Indicar directorio actual como directorio inicial de búsqueda:
        File directorioInicial = new File(dir.getDir());
        if (directorioInicial.isDirectory()) {
            dc.setInitialDirectory(directorioInicial);
        }

        File directorio = dc.showDialog(((Node) evento.getTarget()).getScene().getWindow());
        if (directorio != null) {
            dir.setDir(directorio.getAbsolutePath());
        }
    }

    @FXML private void actConvertir() {
        desactivarBotones(true);
        (new HiloConvertir()).start();
    }

    @FXML private void actRecortar() {
        desactivarBotones(true);
        (new HiloRecortar()).start();
    }

    public void guardarDatos() throws IOException {
        props.setProperty("directorio", dir.getDir());

        props.setProperty("convertir.video", txtConvertirVideo.getText());
        props.setProperty("convertir.salida", txtConvertirSalida.getText());
        props.setProperty("convertir.inicio", txtConvertirInicio.getText());
        props.setProperty("convertir.fin", txtConvertirFin.getText());
        props.setProperty("convertir.escala", txtConvertirEscala.getText());

        props.setProperty("recortar.imagen", txtRecortarImagen.getText());
        props.setProperty("recortar.salida", txtRecortarSalida.getText());
        props.setProperty("recortar.ancho", txtRecortarAncho.getText());
        props.setProperty("recortar.alto", txtRecortarAlto.getText());
        props.setProperty("recortar.X", txtRecortarX.getText());
        props.setProperty("recortar.Y", txtRecortarY.getText());

        props.store(new FileOutputStream(ARCHIVO), null);
    }

    private void alertaError(String encabezado, String contenido) {
        Platform.runLater(new Thread() {
            @Override public void run() {
                Alert alerta = new Alert(AlertType.ERROR);
                alerta.setTitle("FFmpeg GUI");
                alerta.setHeaderText(encabezado);
                alerta.setContentText(contenido);
                alerta.show();
            }
        });
    }

    private void desactivarBotones(boolean desactivar) {
        btnConvertir.setDisable(desactivar);
        btnDir.setDisable(desactivar);
        btnRecortar.setDisable(desactivar);
    }
}
