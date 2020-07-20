import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {
        openZip("C:\\Games\\savegames\\data.zip", "C:\\Games\\savegames\\");
        GameProgress gp = openProgress("C:\\Games\\savegames\\data0.save");
        System.out.println(gp.toString());
    }

    public static void openZip(String zipFilePath, String outputFolderPath){
        InputStream stream;
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFilePath))){
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null){
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(outputFolderPath + name);
                for (int c = zin.read(); c != -1; c = zin.read()){
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    static GameProgress openProgress(String filePath){
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(filePath);
        ObjectInputStream ois = new ObjectInputStream(fis)){
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameProgress;
    }
}
