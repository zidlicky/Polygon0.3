package polygon03;


import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class Save {

    private SecretKeySpec getKeySpec() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(" ".getBytes(), "AES");
        try {
            String keyStr = "Z23H`{MX`^98~>*?rfm`@gr";
            byte[] key = (keyStr).getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKeySpec = new SecretKeySpec(key, "AES");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return secretKeySpec;
    }

    public String encode(String text) {
        String secret = " ";
        try {
            SecretKeySpec secretKeySpec = getKeySpec();

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(text.getBytes());


            byte[] encodedBytes = Base64.getEncoder().encode(encrypted);
            String content = new String(encodedBytes);

            secret = content;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return secret;
    }

    public File saveDataAs(String data) {
        data = "PLGFORMAT....FORMATPOLYGON0.3..........." + data + "PLGFILE";


        FileChooser fileChooser = new FileChooser();
        try {
            String currentDir = System.getProperty("user.home") + File.separator;
            File fileD = new File(currentDir);
            fileChooser.setInitialDirectory(fileD);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        fileChooser.setTitle("Speichern unter");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Polygonformat (*.plg)", "*.plg"));

        File file = fileChooser.showSaveDialog(new Stage());



        if (file != null) {
            if (!(file.getPath().substring(file.getPath().length() - 4).equals(".plg"))) {
                file = new File(file.toString() + ".plg");
            }
            try {
                FileWriter fw = new FileWriter(file);
                fw.write(data);
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return file;
    }

    public File openData() {
        FileChooser fileChooser = new FileChooser();

        try {
            String currentDir = System.getProperty("user.home") + File.separator;
            File fileD = new File(currentDir);
            fileChooser.setInitialDirectory(fileD);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        fileChooser.setTitle("Öffnen");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Polygonformat (*.plg)", "*.plg"));

        Stage error = new Stage();
        File file = fileChooser.showOpenDialog(error);

        return file;
    }

    public String[] encryptOpenData(File file) {
        boolean failed = false;
        String content = null;
        try {
            content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            failed=true;
        }
        if(!failed && (content.length()>=48)) {
            content = content.substring(40, content.length() - 7);
        }

        String erg = " ";
        try {
            byte[] crypted2;
            crypted2 = Base64.getDecoder().decode(content.getBytes(StandardCharsets.UTF_8));
            Cipher cipher2 = Cipher.getInstance("AES");
            cipher2.init(Cipher.DECRYPT_MODE, getKeySpec());

            byte[] cipherData2 = cipher2.doFinal(crypted2);
            erg = new String(cipherData2);
        } catch (Exception e) {
            failed=true;
        }



        if(failed){
            String[] fail = {"failed"};
            return fail;
        }else {
            String delims = "[;]";
            String[] tokens = erg.split(delims);
            return tokens;
        }
    }


    public void exportAsXML(String data,int[] rgb,int[] sizeProperty){

        data="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<svg xmlns=\"http://www.w3.org/2000/svg\""
                +" style=\"background: rgb("
                +String.valueOf(rgb[0])+","
                +String.valueOf(rgb[1]) +","
                +String.valueOf(rgb[2]) +")\" "
                +"width=\""
                +String.valueOf(sizeProperty[0])+"\" height=\""
                +String.valueOf(sizeProperty[1])+"\""+">\n<!-- Created with Polygon0.3 -->\n\n"
                +data+"\n</svg>";

        FileChooser fileChooser = new FileChooser();

        try {
            String currentDir = System.getProperty("user.home") + File.separator;
            File fileD = new File(currentDir);
            fileChooser.setInitialDirectory(fileD);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        fileChooser.setTitle("Exportieren als XML");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML-Format (*.xml)", "*.xml"));

        File file = fileChooser.showSaveDialog(new Stage());


        if (file != null) {
            if (!(file.getPath().substring(file.getPath().length() - 4).equals(".xml"))) {
                file = new File(file.toString() + ".xml");
            }
            try {
                FileWriter fw = new FileWriter(file);
                fw.write(data);
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}





