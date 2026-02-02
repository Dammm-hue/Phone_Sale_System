package Controller;

import Database.Dynamic_Method;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class detail_controller {

    public String userid;

    @FXML
    private Rectangle detailimage;

    @FXML
    private Label lblname;

    @FXML
    private Label lblroleid;

    @FXML
    private Button btnstatus;

    @FXML
    private Button btnsid;

    @FXML
    private Label lblsid;

    @FXML
    private Button btnemail;

    @FXML
    private Label lblemail;

    @FXML
    private Button btnphone;

    @FXML
    private Label lblphone;

    @FXML
    private Button btnaddress1;

    @FXML
    private Label lbladdress;

    @FXML
    private Button btndate;

    @FXML
    private Label lbldate;

    @FXML
    private Label lbltotalsales;

    @FXML
    private Label lbldealclosed;

    @FXML
    private Label lbltarget;

    @FXML
    private Button btnexit;

    @FXML
    private ComboBox<String> combochart;

    @FXML
    private BarChart<?, ?> monthlychart;

    @FXML
    private BarChart<?, ?> dailychart;

    @FXML
    private Label lblthismonth;

    @FXML
    private Label lbllastmonth;

    @FXML
    private Label lblgrowth;

    @FXML
    private NumberAxis yaxisday;

    @FXML
    private NumberAxis yaxismonth;

    @FXML
    private Label lblgrowth1;

    @FXML
    private Button btnup;

    @FXML
    private Button btndown;

    public void setdetail(Map<String, Object> data) {
        userid=data.get("user_id").toString();
        lblname.setText(data.get("user_name").toString());
        lbladdress.setText(data.get("user_address").toString());
        lblemail.setText(data.get("user_email").toString());
        lblphone.setText(data.get("user_phno").toString());
        lblsid.setText(data.get("user_id").toString());
        lbldate.setText(data.get("created_at") != null ? data.get("created_at").toString() : "N/A");
        lblroleid.setText(data.get("user_role_id").toString());
        try {
            if (data.get("user_profile") != null) {
                String path = data.get("user_profile").toString();
                System.out.println(path);
//                File file = new File(path);
//                if (file.exists()) {
//                    Image img = new Image(file.toURI().toString());
//                    detailimage.setFill(new ImagePattern(img));
//                } else {
//                    System.out.println("Image Path is not found!" + path);
//                }

                Image image = new Image(path);
                detailimage.setFill(new ImagePattern(image));
            }
            else {
                System.out.println("Profile is Null");
            }
        } catch (Exception e) {
            System.out.println("Image error: " + e.getMessage());
        }

        //performance action
        String userid = data.get("user_id").toString();
        String table = "product_sale";
        List<Map<String, Object>> result = Dynamic_Method.select("product_sale", List.of("sum(final_amount)as total", "count(product_sale_id)as deals"), "user_id=?", List.of(userid), null, null);
        if (result != null && !result.isEmpty()) {
            Map<String, Object> per = result.get(0);
            lbltotalsales.setText(per.get("total") != null ? per.get("total").toString() : "0");
            lbldealclosed.setText(per.get("deals") != null ? per.get("deals").toString() : "0");

            double totalsales = per.get("total") != null ? Double.parseDouble(per.get("total").toString()) : 0.0;
            double targetamount = 5000.00;
            double targetPercent=0.00;
            if(totalsales<targetamount) {
                targetPercent = (totalsales / targetamount) * 100;
            }else if(totalsales==targetamount || totalsales>targetamount){
                targetPercent=100.00;
            }
            lbltarget.setText(String.format("%.1f", targetPercent) + "%");

        }

        //chart data
        List<Map<String, Object>> daily = Dynamic_Method.select("product_sale", java.util.Arrays.asList("date(created_at)as day", "sum(final_amount)as amount"), "user_id=? and created_at >= date_sub(curdate(),interval 7 day) group by date(created_at)", java.util.Arrays.asList(userid), "day asc", null);
        XYChart.Series dayseries = new XYChart.Series();
        dayseries.setName("Daily Revenue");
        if (daily != null) {
            for (Map<String, Object> row : daily) {
                String label = row.get("day").toString();
                double value = Double.parseDouble(row.get("amount").toString());
                dayseries.getData().add(new XYChart.Data(label, value));
            }

        }
        dailychart.getData().clear();
        dailychart.getData().add(dayseries);

        List<Map<String, Object>> monthly = Dynamic_Method.select("product_sale", java.util.Arrays.asList("monthname(created_at) as month", "sum(final_amount)as amount"), "user_id=? and year(created_at)=year(curdate()) group by monthname(created_at),month(created_at)", java.util.Arrays.asList(userid), "month(created_at) asc", null);
        XYChart.Series monthseries = new XYChart.Series();
        monthseries.setName("Monthly Revenue");
        if (monthly != null) {
            for (Map<String, Object> row : monthly) {
                String label = row.get("month").toString();
                double value = Double.parseDouble(row.get("amount").toString());
                monthseries.getData().add(new XYChart.Data(label, value));
            }
        }
        monthlychart.getData().clear();
        monthlychart.getData().add(monthseries);


        //comparison of months
        List<Map<String,Object>>thisMonth=Dynamic_Method.select("product_sale",java.util.Arrays.asList("sum(final_amount)as total"),"user_id=? and month(created_at)=month(curdate()) and year(created_at)=year(curdate())",java.util.Arrays.asList(userid),null,null);

        List<Map<String,Object>>lastmonth=Dynamic_Method.select("product_sale",java.util.Arrays.asList("sum(final_amount)as total"),"user_id=? and month(created_at)=month(date_sub(curdate(),interval 1 month)) and year(created_at)=year(date_sub(curdate(),interval 1 month))",java.util.Arrays.asList(userid),null,null);

        double thismonthval=0.0;
        double lastmonthval=0.0;
        if(thisMonth != null && !thisMonth.isEmpty() && thisMonth.get(0).get("total") !=null){
            thismonthval=Double.parseDouble(thisMonth.get(0).get("total").toString());
        }
        lblthismonth.setText(String.valueOf(thismonthval));


        if(lastmonth != null && !lastmonth.isEmpty() && lastmonth.get(0).get("total") !=null){
            lastmonthval=Double.parseDouble(lastmonth.get(0).get("total").toString());
        }
        lbllastmonth.setText(String.valueOf(lastmonthval));

        //monthly growth
        double growth=0.0;
        double growthd=0.0;
        if(lastmonthval==thismonthval){
            growth=0.0;
        }else if(lastmonthval==0){
            growth=100.0;
        } else if(thismonthval>lastmonthval){
            growth=((thismonthval-lastmonthval)/lastmonthval)*100;

        }
        lblgrowth.setText(String.format("%.1f",growth)+"%");
        if(thismonthval<lastmonthval) {
            growthd=((thismonthval-lastmonthval)/lastmonthval)*100;
        }else if(thismonthval==lastmonthval){
            growthd=0.0;
        }
        lblgrowth1.setText(String.format("%.1f",growthd)+"%");

        //chart range
        NumberAxis yaxisday=(NumberAxis) dailychart.getYAxis();
        yaxisday.setAutoRanging(true);
        NumberAxis yaxismonth=(NumberAxis) monthlychart.getYAxis();
        yaxismonth.setAutoRanging(true);
    }

    @FXML
    void clickexit(ActionEvent event) throws IOException {
        Stage stage=(Stage) btnexit.getScene().getWindow();
        stage.close();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXML/user.fxml"));
        Parent exit=loader.load();
        Stage mainstage=new Stage();
        mainstage.setScene(new Scene(exit));
        mainstage.show();
    }

    @FXML
    void clickstatus(ActionEvent event) {
        String status = btnstatus.getText();
        String newstatus = status.equalsIgnoreCase("Active") ? "Inactive" : "Active";
            btnstatus.setText(newstatus);
            if (newstatus.equalsIgnoreCase("Inactive")) {
                btnstatus.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white; -fx-background-radius: 5;");
            } else {
                btnstatus.setStyle("-fx-background-color: #7cfc00; -fx-text-fill: white; -fx-background-radius: 5;");
            }
            if(userid !=null) {
                Map<String, Object> data = new HashMap<>();
                data.put("user_status", newstatus);
                Dynamic_Method.update("users", data, "user_id=?", java.util.Arrays.asList(userid));
            }else{
                System.out.println("Error: user_id is still null!");
            }
        }

    public void initialize() {
        FontIcon location = new FontIcon(FontAwesomeSolid.ANGLE_DOUBLE_LEFT);
        location.setIconColor(Color.BLACK);
        location.setIconSize(16);
        btnexit.setGraphic(location);

        FontIcon staff_id = new FontIcon(FontAwesomeSolid.ID_BADGE);
        staff_id.setIconColor(Color.BLACK);
        staff_id.setIconSize(16);
        btnsid.setGraphic(staff_id);

        FontIcon email = new FontIcon(FontAwesomeSolid.ENVELOPE_SQUARE);
        email.setIconColor(Color.BLACK);
        email.setIconSize(16);
        btnemail.setGraphic(email);

        FontIcon phone = new FontIcon(FontAwesomeSolid.PHONE_SQUARE_ALT);
        phone.setIconColor(Color.BLACK);
        phone.setIconSize(16);
        btnphone.setGraphic(phone);

        FontIcon address = new FontIcon(FontAwesomeSolid.ADDRESS_CARD);
        address.setIconColor(Color.BLACK);
        address.setIconSize(16);
        btnaddress1.setGraphic(address);

        FontIcon date = new FontIcon(FontAwesomeSolid.CALENDAR_ALT);
        date.setIconColor(Color.BLACK);
        date.setIconSize(16);
        btndate.setGraphic(date);

        FontIcon up = new FontIcon(FontAwesomeSolid.ARROW_UP);
        up.setIconColor(Color.BLACK);
        up.setIconSize(12);
        btnup.setGraphic(up);

        FontIcon down = new FontIcon(FontAwesomeSolid.ARROW_DOWN);
        down.setIconColor(Color.BLACK);
        down.setIconSize(12);
        btndown.setGraphic(down);

        combochart.getItems().addAll("Daily","Monthly");
        combochart.setValue("Daily");
        dailychart.setVisible(true);
        monthlychart.setVisible(false);
    }
    @FXML
    void clickcombo(ActionEvent event) {
        if(combochart.getValue().equals("Daily")){
            dailychart.setVisible(true);
            monthlychart.setVisible(false);
        }else{
            dailychart.setVisible(false);
            monthlychart.setVisible(true);
        }
    }



}


