package modelo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComparadorDirectorios {

    private File dir1;
    private File dir2;

    private List<File> faltantesDir1;
    private List<File> faltantesDir2;

    public ComparadorDirectorios(File dir1, File dir2) {
        this.dir1 = dir1;
        this.dir2 = dir2;

        List<File> archivosDir1 = Arrays.asList(dir1.listFiles());
        List<String> nombresDir1 = new ArrayList<>();
        for (File f : archivosDir1) {
            nombresDir1.add(f.getName());
        }

        List<File> archivosDir2 = Arrays.asList(dir2.listFiles());
        List<String> nombresDir2 = new ArrayList<>();
        for (File f : archivosDir2) {
            nombresDir2.add(f.getName());
        }

        faltantesDir1 = new ArrayList<>();
        faltantesDir2 = new ArrayList<>();

        for (File f : archivosDir2) {
            if (!f.isDirectory() && !nombresDir1.contains(f.getName())) {
                faltantesDir1.add(f);
            }
        }

        for (File f : archivosDir1) {
            if (!f.isDirectory() && !nombresDir2.contains(f.getName())) {
                faltantesDir2.add(f);
            }
        }
    }

    public File getDir1() {
        return dir1;
    }

    public File getDir2() {
        return dir2;
    }

    public List<File> getFaltantesDir1() {
        return faltantesDir1;
    }

    public List<File> getFaltantesDir2() {
        return faltantesDir2;
    }

    public static boolean esDirValido(File dir) {
        return (dir.isDirectory() && dir.exists());
    }
}
