package Controller;

import Database.DB_Connection;
import Database.Dynamic_Method;
import Utility.Window_Manage;
import com.example.phone_sale.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class user_controller {

    @FXML
    private TextField txtsearch;

    @FXML
    private Button btnadd;

    @FXML
    private TilePane tilepane;

    public static String search="";

    private static TilePane statilePane;

    public void initialize() throws SQLException, IOException, ClassNotFoundException {
        statilePane=tilepane;
        tilepanecard();

        txtsearch.setOnKeyReleased(e->{
            try {
            search=txtsearch.getText().trim();
                tilepanecard();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @FXML
    void clickadd(ActionEvent event) throws IOException {
        FXMLLoader addloader=new FXMLLoader(getClass().getResource("/FXML/add.fxml"));
        Parent addroot=addloader.load();
        add_controller addControl=addloader.getController();
        addControl.newcard();
        Stage stage=new Stage();
        stage.setScene(new Scene(addroot));
        stage.show();
    }
    public static void tilepanecard() throws IOException, SQLException, ClassNotFoundException {
        statilePane.getChildren().clear();
        List<Map<String, Object>> userlist;
        if(search==null || search.isEmpty()) {
            userlist = Dynamic_Method.select("users", null, null, null, null, null);
        }else {
            userlist = Dynamic_Method.select("users", null, "lower(user_name) like ? or cast(user_id as char) like ? or lower(user_email) like ?", java.util.Arrays.asList("%"+search.toLowerCase()+"%","%"+search.toLowerCase()+"%","%"+search.toLowerCase()+"%"), null, null);
        }
        for (Map<String, Object> userdata : userlist) {
            FXMLLoader aloader = new FXMLLoader(user_controller.class.getResource("/FXML/add.fxml"));
            Parent usercard = aloader.load();
            add_controller Controller = aloader.getController();
            Controller.setdata(userdata);
            Controller.seteditablemode(false);
            Controller.buttonaction();
            statilePane.getChildren().add(usercard);


            //edit button action
            final Map<String,Object>current=userdata;
            Controller.getBtnedit().setOnAction(e -> {
                try {
                FXMLLoader eloader = new FXMLLoader(user_controller.class.getResource("/FXML/add.fxml"));
                    Parent root = eloader.load();
                    add_controller editcard = eloader.getController();
                    //editcard.newcard();
//                    editcard.buttonaction();
                    editcard.setdata(current);
                    editcard.seteditablemode(true);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });


            //detail button action
            Controller.getBtndetail().setOnAction(e->{
                try {
                FXMLLoader detailloader=new FXMLLoader(user_controller.class.getResource("/FXML/detail.fxml"));
                    Parent root=detailloader.load();

                detail_controller detail=detailloader.getController();
                detail.setdetail(current);
                Stage stage=new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                ((Stage)((Node)e.getSource()).getScene().getWindow()).close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            }
        }
    }
















