import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * This program
 * uses an url connection to
 * read a file on the web
 * and write the file 
 * to into folder
 */

public class Main {

    private static final String kittenURL = "https://images.pexels.com/photos/1056252/pexels-photo-1056252.jpeg";
    private static final String destinationFile = "Destination Folder\\kitten.jpeg";

    public static void main(String[] args) throws IOException {

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {

            URL url = new URI(kittenURL).toURL();
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            // Opens a URL Connection using a URL
            httpCon.addRequestProperty("User-Agent", "Mozilla/4.0");
            /**
             * addRequestProperty adds a User-Agent Header
             * with the Mozilla/4.0 value
             */

            inputStream = httpCon.getInputStream();
            /**
             * returns an input stream that reads
             * from the httpCon open connection
             */

            outputStream = new FileOutputStream(destinationFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if(inputStream != null){
                inputStream.close();
                outputStream.close();
            }
        }

    }

}
