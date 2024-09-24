import java.io.Serializable;
import java.util.ArrayList;
public class CountryCatalog implements Serializable {

    private ArrayList<Country> countryList = new ArrayList<>();
    public CountryCatalog(){}
    public void add(Country country){
        countryList.add(country);
    }
    public void remove(int index) { countryList.remove(index); }
    public Country getCountry(int index) {
        return countryList.get(index);
    }
    public void editCountry(int index, String name, String info){
        Country country = getCountry(index);
        country.setName(name);
        country.setInfo(info);
    }
    public void print(){
        System.out.print("\"Countries\": [");
        for (Country country: countryList){
            System.out.print(country.toString()+",");
        }
        System.out.println("\n]");
    }
    public boolean isEmpty(){
        return countryList.isEmpty();
    }
    public String[] getCountryNames(){
        String[] countryNames = new String[countryList.size()];
        for (int i = 0; i < countryList.size(); i++) {
            countryNames[i] = countryList.get(i).getName();
        }
        return countryNames;
    }
    public void setCountryList(ArrayList<Country> countryListData) {
        countryList = countryListData;
    }
    public ArrayList<Country> getCountryList(){
        return countryList;
    }
}
