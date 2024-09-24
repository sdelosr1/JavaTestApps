import java.io.Serializable;
public class Country implements Serializable{
    private String name;
    private String info;

    public Country(String name, String info){
        this.name = name;
        this.info = info;
    }

    @Override
    public String toString() {
        return "\n\t{\n\t\t\"name\": " + "\"" +this.name + "\",\n" +
                "\t\t\"info\": " + "\"" +this.info + "\"\n\t}";
    }

    public String getName() {
        return this.name;
    }

    public String getInfo() {
        return this.info;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setInfo(String info){
        this.info = info;
    }
}
