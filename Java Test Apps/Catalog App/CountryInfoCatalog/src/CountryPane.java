import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
public class CountryPane extends BorderPane {
    private TextField textName = new TextField();
    private TextArea textInfo = new TextArea();
    public CountryPane(){
        textName.setMinHeight(90);
        textName.setEditable(false);

        textInfo.setPrefHeight(220);
        textInfo.setEditable(false);

        setTop(textName);
        setCenter(textInfo);
    }
    public String getTextName(){
        return textName.getText();
    }
    public String getTextInfo(){
        return textInfo.getText();
    }

    public void clearText(){
        textName.clear();
        textInfo.clear();
    }
    public void setEditable(boolean bool){
        textName.setEditable(bool);
        textInfo.setEditable(bool);
    }
    public void setCountry(Country country){
        textName.setText(country.getName());
        textInfo.setText(country.getInfo());
    }
}
