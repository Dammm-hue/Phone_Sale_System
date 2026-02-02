package Controller;

import Database.DB_Connection;
import Database.Dynamic_Method;
import Utility.Window_Manage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class add_controller {

    @FXML
    private Label lblid;

    @FXML
    private Label lblroleid;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnedit;

    @FXML
    private Button btndetail;

    @FXML
    private TextField txtuser;

    @FXML
    private Button btnlocation;

    @FXML
    private TextField txtaddress;

    @FXML
    private Button btnphone;

    @FXML
    private TextField txtphone;

    @FXML
    private Button btnemail;

    @FXML
    private TextField txtemail;

    @FXML
    private Rectangle rectimage;

    @FXML
    private HBox buttonbox;

    @FXML
    private PasswordField pwdpassword;

    private int id=0;
    private String imagepath="";


    public void initialize() {
        FontIcon location = new FontIcon(FontAwesomeSolid.MAP_MARKER_ALT);
        location.setIconColor(Color.BLACK);
        location.setIconSize(16);
        btnlocation.setGraphic(location);

        FontIcon phone = new FontIcon(FontAwesomeSolid.PHONE_SQUARE_ALT);
        phone.setIconColor(Color.BLACK);
        phone.setIconSize(8);
        btnphone.setGraphic(phone);

        FontIcon email = new FontIcon(FontAwesomeSolid.ENVELOPE);
        email.setIconColor(Color.BLACK);
        email.setIconSize(8);
        btnemail.setGraphic(email);

        FontIcon edit = new FontIcon(FontAwesomeSolid.PEN_ALT);
        edit.setIconColor(Color.BLACK);
        edit.setIconSize(8);
        btnedit.setGraphic(edit);

        FontIcon detail = new FontIcon(FontAwesomeSolid.INFO_CIRCLE);
        detail.setIconColor(Color.BLACK);
        detail.setIconSize(8);
        btndetail.setGraphic(detail);

        FontIcon save = new FontIcon(FontAwesomeSolid.SAVE);
        save.setIconColor(Color.BLACK);
        save.setIconSize(8);
        btnsave.setGraphic(save);


        btnsave.setVisible(true);
        btnsave.setManaged(true);

        buttonbox.setVisible(false);
        buttonbox.setManaged(false);

    }

    public void buttonaction() {
        btnsave.setVisible(false);
        btnsave.setManaged(false);

        buttonbox.setVisible(true);
        buttonbox.setManaged(true);
    }

    @FXML
    void clickdetail(ActionEvent event) throws IOException {
        FXMLLoader detailloader=new FXMLLoader(getClass().getResource("/FXML/detail.fxml"));
        Parent root=detailloader.load();
        add_controller addControl=detailloader.getController();
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void clickedit(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        Map<String, Object> update_data = new HashMap<>();
        update_data.put("user_name", txtuser.getText());
        update_data.put("user_address", txtaddress.getText());
        update_data.put("user_email", txtemail.getText());
        update_data.put("user_phno", txtphone.getText());
        update_data.put("user_profile", image);
        update_data.put("user_status", "Active");
        if (!pwdpassword.getText().isEmpty()) {
            update_data.put("user_password", pwdpassword.getText());
        }
        int update = Dynamic_Method.update("users", update_data, "user_id=?", java.util.Arrays.asList(lblid.getText()));
        if (update > 0) {
            System.out.println("Update Successful!");
            user_controller.tilepanecard();
            Stage stage = (Stage) txtaddress.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Update Failed!");
        }

    }

    @FXML
    void clicksave(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        if (txtuser.getText().isEmpty() || txtphone.getText().isEmpty() || txtemail.getText().isEmpty() || txtaddress.getText().isEmpty()) {
            showalert("Validation Error","PLEASE FILL DATA COMPLETELY!");
            return;
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("validation Error");
//            alert.setHeaderText(null);
//            alert.setContentText("PLEASE FILL THE DATA COMPLETELY!");
//            alert.showAndWait();
//            return;
        }
        Map<String, Object> user_map = new HashMap<>();
        user_map.put("user_name", txtuser.getText());
        user_map.put("user_password", pwdpassword.getText());
        user_map.put("user_role_id", "2");
        user_map.put("user_address", txtaddress.getText());
        user_map.put("user_email", txtemail.getText());
        user_map.put("user_phno", txtphone.getText());
        user_map.put("user_profile", image);
        user_map.put("user_status", "Active");

        String email=txtemail.getText().trim();
        if(!email.contains("@")){
           showalert("Data Error:","Please complete the format!You miss '@'!!");
            return;
        } else if (!email.contains("gmail.com")) {
            showalert("Data Error:","Please complete the format!You miss '.'!!");
        }

        String phone=txtphone.getText().trim();
        if(phone.length()!=11){
            showalert("Data Error:","Please enter your phone number exactly 11 digits!");
            return;
        }
        if(!phone.matches("\\d+")){
            showalert("Data Error:","Phone Number must contain only digits(0-9)!");
            return;
        }

        String pass=pwdpassword.getText();
        boolean upper=false;
        boolean lower=false;
        boolean digit=false;
        boolean spec=false;
        String special="!@#$%^&*+";
        if(pass.length()<8){
           showalert("Security Error:","Please input at least 8 characters!");
            return;
        }
        for (char ch:pass.toCharArray()){
            if(Character.isUpperCase(ch)){
                upper=true;
            } else if (Character.isLowerCase(ch)) {
                lower=true;
            } else if (Character.isDigit(ch)) {
                digit=true;
            } else if (special.contains(String.valueOf(ch))) {
                spec=true;
            }
        }
            if(!upper || !lower || !digit || !spec){
                showalert("Security Error:","Please input 8 characters with CAPITAL LETTERS,small letters,Numbers,Special Characters!");
                return;
            }


        if(id==0){
            int insert=Dynamic_Method.insert("users",user_map);
            if(insert>0){
                System.out.println("Insert Successfully!");
                user_controller.tilepanecard();
                ((Stage)txtuser.getScene().getWindow()).close();
            }
        }else{
            int update=Dynamic_Method.update("users",user_map,"user_id=?",java.util.Arrays.asList(lblid.getText()));
            if(update>0){
                System.out.println("Update Successfully!");
                user_controller.tilepanecard();
                ((Stage)txtuser.getScene().getWindow()).close();
            }
        }
    }

    File rimage;
    String image = "";

    @FXML
    void uploadimage(MouseEvent event) {
        FileChooser img = new FileChooser();
        img.setTitle("Choose Photo");
        img.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.png", "*.jpg", "*.jfif", "*.gif", "*.jpeg"));
        rimage = img.showOpenDialog(null);
        if (rimage != null) {
            image = rimage.toURI().toString();
            Image rec = new Image(image);
            rectimage.setFill(new ImagePattern(rec));
        }
    }

    public Button getBtnedit() {
        return btnedit;
    }

    public Button getBtndetail(){
        return btndetail;
    }


    public void newcard() {
        txtuser.clear();
        txtaddress.clear();
        txtemail.clear();
        txtphone.clear();
        image = "";
        rectimage.setFill(Color.LIGHTGRAY);
    }

    public void setdata(Map<String, Object> data) {
        this.id=Integer.parseInt(data.get("user_id").toString());
        txtuser.setText(data.get("user_name").toString());
        txtaddress.setText(data.get("user_address").toString());
        txtemail.setText(data.get("user_email").toString());
        if (data.get("user_password") != null) {
            pwdpassword.setText(data.get("user_password").toString());
        }
        txtphone.setText(data.get("user_phno").toString());
        lblid.setText(data.get("user_id").toString());
        if (data.get("user_profile") != null && !data.get("user_profile").toString().isEmpty()) {
            image = data.get("user_profile").toString();
            Image profile = new Image(image);
            rectimage.setFill(new ImagePattern(profile));
        } else {
            rectimage.setFill(Color.GRAY);
        }
    }

    public void detailmode(Map<String, Object> userdata) {
        setdata(userdata);
        txtuser.setEditable(false);
        txtaddress.setEditable(false);
        txtemail.setEditable(false);
        txtphone.setEditable(false);
        pwdpassword.setEditable(false);
    }

    public void seteditablemode(boolean mode){
        txtuser.setEditable(mode);
        txtemail.setEditable(mode);
        txtaddress.setEditable(mode);
        txtphone.setEditable(mode);
        pwdpassword.setEditable(mode);
    }

    public void showalert(String title,String message){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
