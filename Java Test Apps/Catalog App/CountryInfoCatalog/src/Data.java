import java.io.*;
import java.util.ArrayList;
public class Data {
    private static File dataFile = new File("Catalog.dat");

    public static ArrayList<Country> load() throws IOException {
        ArrayList<Country> countryListData = new ArrayList<>();
        try {
            if(!dataFile.exists()){
                save(countryListData);
            }
            FileInputStream fis = new FileInputStream(dataFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            countryListData = (ArrayList<Country>) ois.readObject();
            ois.close();

        }catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return countryListData;
    }

    public static void save(ArrayList<Country> countryListData) throws IOException {
        FileOutputStream fos = new FileOutputStream(dataFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(countryListData);
        oos.flush();
        oos.close();
    }
}
