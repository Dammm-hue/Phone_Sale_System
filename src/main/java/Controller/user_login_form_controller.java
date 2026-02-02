package Controller;

import Database.Dynamic_Method;
import Utility.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.*;

public class user_login_form_controller {

    @FXML
    private Button btn_change;

    @FXML
    private Button btn_exit;

    @FXML
    private Button btn_login;

    @FXML
    private Button btn_next;

    @FXML
    private VBox fg_change_password_pane;

    @FXML
    private StackPane fg_confirm_error_container;

    @FXML
    private Label fg_confirm_error_msg;

    @FXML
    private Button fg_confirm_eye_icon;

    @FXML
    private Button fg_confirm_icon;

    @FXML
    private StackPane fg_email_error_container;

    @FXML
    private Label fg_email_error_msg;

    @FXML
    private Button fg_email_icon;

    @FXML
    private VBox fg_email_request_pane;

    @FXML
    private Button fg_first_page_exit;

    @FXML
    private StackPane fg_otp_error_container;

    @FXML
    private Label fg_otp_error_msg;

    @FXML
    private Button fg_otp_icon;

    @FXML
    private VBox fg_otp_request_pane;

    @FXML
    private StackPane fg_password_error_container;

    @FXML
    private Label fg_password_error_msg;

    @FXML
    private Button fg_password_eye_icon;

    @FXML
    private Button fg_password_icon;

    @FXML
    private Button fg_second_page_exit;

    @FXML
    private Button fg_third_page_exit;

    @FXML
    private Button fg_to_change_password;

    @FXML
    private Button fg_to_otp;

    @FXML
    private Label forgot_password;

    @FXML
    private Button login_eye_icon;

    @FXML
    private HBox login_password_error_container;

    @FXML
    private Label login_password_error_msg;

    @FXML
    private Label login_title;

    @FXML
    private HBox login_user_name_error_container;

    @FXML
    private Label login_user_name_error_msg;

    @FXML
    private TextField otp_no_1;

    @FXML
    private TextField otp_no_2;

    @FXML
    private TextField otp_no_3;

    @FXML
    private TextField otp_no_4;

    @FXML
    private TextField otp_no_5;

    @FXML
    private TextField otp_no_6;

    @FXML
    private Label otp_timer;

    @FXML
    private Button password_check_icon;

    @FXML
    private HBox password_check_pane;

    @FXML
    private Button password_icon;

    @FXML
    private PasswordField pwd_fg_confirm;

    @FXML
    private PasswordField pwd_fg_password;

    @FXML
    private PasswordField pwd_password;

    @FXML
    private Label resend_it;

    @FXML
    private TextField txt_fg_confirm;

    @FXML
    private TextField txt_fg_email;

    @FXML
    private TextField txt_fg_password;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_user_email;

    @FXML
    private VBox user_login_pane;

    @FXML
    private Button user_email_check_icon;

    @FXML
    private HBox user_email_check_pane;

    @FXML
    private Button user_email_icon;

    @FXML
    private Circle user_profile;

    boolean check_user_role = false;

    public void initialize() {
        user_login_pane.setVisible(true);
        user_login_pane.setManaged(true);

        login_title.setText("User Login");

        forgot_password.setDisable(false);

        FontIcon exit = new FontIcon(FontAwesomeSolid.TIMES);
        exit.setIconSize(15);
        exit.setIconColor(Color.rgb(0, 0, 0, 0.5));
        btn_exit.setGraphic(exit);

        FontIcon user_email = new FontIcon(FontAwesomeSolid.ENVELOPE);
        user_email.setIconSize(15);
        user_email.setIconColor(Color.rgb(0, 0, 0, 0.5));
        user_email_icon.setGraphic(user_email);

        FontIcon password = new FontIcon(FontAwesomeSolid.LOCK);
        password.setIconSize(15);
        password.setIconColor(Color.rgb(0, 0, 0, 0.5));
        password_icon.setGraphic(password);

        FontIcon login_closed_eye = new FontIcon(FontAwesomeSolid.EYE_SLASH);
        login_closed_eye.setIconSize(13);
        login_closed_eye.setIconColor(Color.rgb(0, 0, 0, 0.5));
        login_eye_icon.setGraphic(login_closed_eye);

        FontIcon fg_email = new FontIcon(FontAwesomeSolid.ENVELOPE);
        fg_email.setIconSize(15);
        fg_email.setIconColor(Color.rgb(0, 0, 0, 0.5));
        fg_email_icon.setGraphic(fg_email);

        FontIcon fg_otp = new FontIcon(FontAwesomeSolid.PHONE_ALT);
        fg_otp.setIconSize(15);
        fg_otp.setIconColor(Color.rgb(0, 0, 0, 0.5));
        fg_otp_icon.setGraphic(fg_otp);

        FontIcon fg_password = new FontIcon(FontAwesomeSolid.LOCK);
        fg_password.setIconSize(15);
        fg_password.setIconColor(Color.rgb(0, 0, 0, 0.5));
        fg_password_icon.setGraphic(fg_password);

        FontIcon fg_closed_eye = new FontIcon(FontAwesomeSolid.EYE_SLASH);
        fg_closed_eye.setIconSize(13);
        fg_closed_eye.setIconColor(Color.rgb(0, 0, 0, 0.5));
        fg_password_eye_icon.setGraphic(fg_closed_eye);

        FontIcon fg_confirm = new FontIcon(FontAwesomeSolid.LOCK);
        fg_confirm.setIconSize(15);
        fg_confirm.setIconColor(Color.rgb(0, 0, 0, 0.5));
        fg_confirm_icon.setGraphic(fg_confirm);

        FontIcon confirm_closed_eye = new FontIcon(FontAwesomeSolid.EYE_SLASH);
        confirm_closed_eye.setIconSize(13);
        confirm_closed_eye.setIconColor(Color.rgb(0, 0, 0, 0.5));
        fg_confirm_eye_icon.setGraphic(confirm_closed_eye);

        FontIcon fg_first_exit = new FontIcon(FontAwesomeSolid.TIMES);
        fg_first_exit.setIconSize(15);
        fg_first_exit.setIconColor(Color.rgb(0, 0, 0, 0.5));
        fg_first_page_exit.setGraphic(fg_first_exit);

        FontIcon fg_second_exit = new FontIcon(FontAwesomeSolid.TIMES);
        fg_second_exit.setIconSize(15);
        fg_second_exit.setIconColor(Color.rgb(0, 0, 0, 0.5));
        fg_second_page_exit.setGraphic(fg_second_exit);

        FontIcon fg_third_exit = new FontIcon(FontAwesomeSolid.TIMES);
        fg_third_exit.setIconSize(15);
        fg_third_exit.setIconColor(Color.rgb(0, 0, 0, 0.5));
        fg_third_page_exit.setGraphic(fg_third_exit);

        FontIcon fg_to_otp_pane = new FontIcon(FontAwesomeSolid.ARROW_RIGHT);
        fg_to_otp_pane.setIconSize(15);
        fg_to_otp_pane.setIconColor(Color.rgb(0, 0, 0, 0.5));
        fg_to_otp.setGraphic(fg_to_otp_pane);

        FontIcon fg_to_change_password_pane = new FontIcon(FontAwesomeSolid.ARROW_RIGHT);
        fg_to_change_password_pane.setIconSize(15);
        fg_to_change_password_pane.setIconColor(Color.rgb(0, 0, 0, 0.5));
        fg_to_change_password.setGraphic(fg_to_change_password_pane);

        FontIcon user_email_check = new FontIcon(FontAwesomeSolid.CHECK_CIRCLE);
        user_email_check.setIconSize(15);
        user_email_check.setIconColor(Color.GREEN);
        user_email_check_icon.setGraphic(user_email_check);

        FontIcon password_check = new FontIcon(FontAwesomeSolid.CHECK_CIRCLE);
        password_check.setIconSize(15);
        password_check.setIconColor(Color.GREEN);
        password_check_icon.setGraphic(password_check);

        user_email_check_pane.setVisible(false);
        user_email_check_pane.setManaged(false);

        password_check_pane.setVisible(false);
        password_check_pane.setManaged(false);

        btn_login.setVisible(true);
        btn_login.setManaged(true);

        btn_next.setVisible(false);
        btn_next.setManaged(false);

        login_user_name_error_container.setVisible(false);
        login_user_name_error_container.setManaged(false);

        login_password_error_container.setVisible(false);
        login_password_error_container.setManaged(false);

        user_profile.setVisible(false);
        user_profile.setManaged(false);

        txt_password.setVisible(false);
        txt_password.setManaged(true);

        fg_email_error_container.setVisible(false);
        fg_email_error_container.setManaged(false);

        fg_otp_error_container.setVisible(false);
        fg_otp_error_container.setManaged(false);

        fg_password_error_container.setVisible(false);
        fg_password_error_container.setManaged(false);

        fg_confirm_error_container.setVisible(false);
        fg_confirm_error_container.setManaged(false);

        txt_password.textProperty().bindBidirectional(pwd_password.textProperty());

        txt_fg_password.textProperty().bindBidirectional(pwd_fg_password.textProperty());

        txt_fg_confirm.textProperty().bindBidirectional(pwd_fg_confirm.textProperty());

        List<TextField> otp_fields = List.of(otp_no_1, otp_no_2, otp_no_3, otp_no_4, otp_no_5, otp_no_6);

        for (int i = 0; i < otp_fields.size(); i++) {
            TextField current = otp_fields.get(i);
            TextField next = (i < otp_fields.size() - 1) ? otp_fields.get(i + 1) : null;
            TextField previous = (i > 0) ? otp_fields.get(i - 1) : null;
            Function.setup_otp_field(current, next, previous);
        }

        otp_timer.setText("3:00");
    }

    @FXML
    void click_change(ActionEvent event) {

        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        if (!pwd_fg_password.getText().isEmpty()) {
            if (!pwd_fg_confirm.getText().isEmpty()) {
                if (pwd_fg_password.getText().matches(regex)) {
                    if (pwd_fg_password.getText().equals(pwd_fg_confirm.getText())) {

                        String hash_password = BCrypt.hashpw(pwd_fg_password.getText(), BCrypt.gensalt());
                        Map<String, Object> update_map = new HashMap<>();
                        update_map.put("user_password", hash_password);
                        Dynamic_Method.update("users", update_map, "user_email=?", List.of(txt_fg_email.getText()));

                        Function.showCase("Successfully", "Now,please login again.");

                        fg_password_error_container.setVisible(false);
                        fg_password_error_container.setManaged(false);

                        fg_confirm_error_container.setVisible(false);
                        fg_confirm_error_container.setManaged(false);

                        fg_email_request_pane.setVisible(false);
                        fg_email_request_pane.setManaged(false);

                        Animation.Slide_left_to_right(fg_email_request_pane, Duration.millis(350), 450, null);

                        fg_otp_request_pane.setVisible(false);
                        fg_otp_request_pane.setManaged(false);

                        Animation.Slide_left_to_right(fg_otp_request_pane, Duration.millis(350), 450, null);

                        Animation.Popup_Reverse(fg_change_password_pane, Duration.millis(300), () -> {
                            Animation.Slide_right_to_left(user_login_pane, Duration.millis(370), 470, null);
                            user_login_pane.setDisable(false);
                        });
                    } else {
                        fg_confirm_error_msg.setText("Password does not match!!!");
                        fg_confirm_error_container.setVisible(true);
                        fg_confirm_error_container.setManaged(true);
                    }
                } else {
                    fg_password_error_msg.setText("Password must be at least 8 characters including at least one letter, at least one digit, and at least one Special Character.");
                    fg_password_error_container.setVisible(true);
                    fg_password_error_container.setManaged(true);
                }
            } else {
                fg_confirm_error_msg.setText("Empty Field!!!");
                fg_confirm_error_container.setVisible(true);
                fg_confirm_error_container.setManaged(true);
            }
        } else {
            fg_password_error_msg.setText("Empty Field!!!");
            fg_password_error_container.setVisible(true);
            fg_password_error_container.setManaged(true);
        }

    }

    @FXML
    void click_exit(ActionEvent event) {

        Window_Manage.getStage("login_stage").close();
    }

    @FXML
    void click_fg_confirm_eye_icon(ActionEvent event) {

        Function.Show_Hide(pwd_fg_confirm, txt_fg_confirm, fg_confirm_eye_icon);
    }

    @FXML
    void click_fg_password_eye_icon(ActionEvent event) {

        Function.Show_Hide(pwd_fg_password, txt_fg_password, fg_password_eye_icon);
    }

    @FXML
    void click_fg_third_page_exit(ActionEvent event) {

        fg_email_request_pane.setVisible(false);
        fg_email_request_pane.setManaged(false);

        Animation.Slide_left_to_right(fg_email_request_pane, Duration.millis(350), 450, null);

        fg_otp_request_pane.setVisible(false);
        fg_otp_request_pane.setManaged(false);

        Animation.Slide_left_to_right(fg_otp_request_pane, Duration.millis(350), 450, null);

        Animation.Popup_Reverse(fg_change_password_pane, Duration.millis(300), () -> {
            Animation.Slide_right_to_left(user_login_pane, Duration.millis(370), 470, null);
            user_login_pane.setDisable(false);
        });
    }

    @FXML
    void click_first_page_exit(ActionEvent event) {

        Animation.Popup_Reverse(fg_email_request_pane, Duration.millis(300), () -> {
            Animation.Slide_right_to_left(user_login_pane, Duration.millis(370), 470, null);
            user_login_pane.setDisable(false);
        });
    }

    @FXML
    void click_forgot_password(MouseEvent event) {

        Animation.Slide_left_to_right(user_login_pane, Duration.millis(350), 485, () -> {
            user_login_pane.setDisable(true);
            Animation.Popup(fg_email_request_pane, Duration.millis(300), null);
        });
    }

    int attempt = 5;

    @FXML
    void click_login(ActionEvent event) throws IOException {

        if (!txt_user_email.getText().isEmpty() || !pwd_password.getText().isEmpty()) {

            login_user_name_error_msg.setText("Error");
            login_user_name_error_container.setVisible(false);
            login_user_name_error_container.setManaged(false);

            login_password_error_msg.setText("Error");
            login_password_error_container.setVisible(false);
            login_password_error_container.setManaged(false);

            if (!txt_user_email.getText().isEmpty()) {

                login_user_name_error_msg.setText("Error");
                login_user_name_error_container.setVisible(false);
                login_user_name_error_container.setManaged(false);

                if (!pwd_password.getText().isEmpty()) {

                    login_password_error_msg.setText("Error");
                    login_password_error_container.setVisible(false);
                    login_password_error_container.setManaged(false);

                    if (attempt > 0) {

                        List<Map<String, Object>> user_list = Dynamic_Method.select("users", null, "user_email=?", List.of(txt_user_email.getText()), null, null, null);

                        if (!user_list.isEmpty()) {

                            login_user_name_error_msg.setText("Error");
                            login_user_name_error_container.setVisible(false);
                            login_user_name_error_container.setManaged(false);

                            for (Map<String, Object> user : user_list) {

                                if (user.get("user_status").equals("Active")) {

                                    if (BCrypt.checkpw(pwd_password.getText(), (String) user.get("user_password"))) {

                                        login_password_error_msg.setText("Error");
                                        login_password_error_container.setVisible(false);
                                        login_password_error_container.setManaged(false);

                                        if ((int) user.get("user_role_id") == 1) {
                                            check_user_role = true;
                                        }

                                        if (check_user_role) {
                                            Function.showCase("Successfully", "You Successfully Login with Admin.");
                                            Function.setUser_Info(user.get("user_profile").toString(), user.get("user_name").toString(), "ADMIN");
                                        } else {
                                            Function.showCase("Successfully", "You Successfully Login with Staff.");
                                            Function.setUser_Info((String) user.get("user_profile"), (String) user.get("user_name"), "STAFF");
                                        }

                                        Image user_image = new Image("File:" + user.get("user_profile").toString());
                                        user_profile.setFill(new ImagePattern(user_image));
                                        user_profile.setVisible(true);
                                        user_profile.setManaged(true);

                                        login_title.setText("Welcome");

                                        user_email_check_pane.setVisible(true);
                                        user_email_check_pane.setManaged(true);

                                        password_check_pane.setVisible(true);
                                        password_check_pane.setManaged(true);

                                        forgot_password.setDisable(true);

                                        btn_login.setVisible(false);
                                        btn_login.setManaged(false);

                                        btn_next.setVisible(true);
                                        btn_next.setManaged(true);

                                    } else {
                                        attempt--;
                                        login_password_error_msg.setText("User Password is Wrong. " + attempt + " attempts left!!!");
                                        login_password_error_container.setVisible(true);
                                        login_password_error_container.setManaged(true);
                                    }
                                } else {
                                    Function.error("Failed", "User Status is Inactive.");
                                }
                            }
                        } else {
                            login_user_name_error_msg.setText("User Email is Wrong!!!");
                            login_user_name_error_container.setVisible(true);
                            login_user_name_error_container.setManaged(true);
                        }
                    } else {
                        Map<String, Object> update_map = new HashMap<>();
                        update_map.put("user_status", "Inactive");
                        if (Dynamic_Method.update("users", update_map, "user_email=?", List.of(txt_user_email.getText())) > 0) {
                            Function.error("Locked", "Your ACC is Now Locked!!!");
                        }
                    }
                }
                else {
                    login_password_error_msg.setText("Field is Empty!!!");
                    login_password_error_container.setVisible(true);
                    login_password_error_container.setManaged(true);
                }
            }
            else {
                login_user_name_error_msg.setText("Field is Empty!!!");
                login_user_name_error_container.setVisible(true);
                login_user_name_error_container.setManaged(true);
            }
        }
        else {
            login_user_name_error_msg.setText("Field is Empty!!!");
            login_user_name_error_container.setVisible(true);
            login_user_name_error_container.setManaged(true);

            login_password_error_msg.setText("Field is Empty!!!");
            login_password_error_container.setVisible(true);
            login_password_error_container.setManaged(true);
        }
    }

    @FXML
    void click_login_eye_icon(ActionEvent event) {

        Function.Show_Hide(pwd_password, txt_password, login_eye_icon);
    }

    @FXML
    void click_next(ActionEvent event) throws IOException {

        if (check_user_role) {
            Window_Manage.isOpen("/FXML/main_panel.fxml", "admin_stage", true, false);
            Main_Panel_Manage.panel_selection(true, false);
            Window_Manage.getStage("login_stage").close();
        } else {
            Window_Manage.isOpen("/FXML/main_panel.fxml", "staff_stage", true, false);
            Main_Panel_Manage.panel_selection(false, true);
            Window_Manage.getStage("login_stage").close();
        }
    }

    @FXML
    void click_resend_it(MouseEvent event) {
        OTP_Service.resend_otp(txt_fg_email.getText(), otp_timer);
        fg_otp_error_msg.setText("Error");
        fg_otp_error_container.setVisible(false);
        fg_otp_error_container.setManaged(false);
    }

    @FXML
    void click_second_page_exit(ActionEvent event) {

        fg_email_request_pane.setVisible(false);
        fg_email_request_pane.setManaged(false);

        Animation.Slide_left_to_right(fg_email_request_pane, Duration.millis(350), 450, null);

        Animation.Popup_Reverse(fg_otp_request_pane, Duration.millis(300), () -> {
            Animation.Slide_right_to_left(user_login_pane, Duration.millis(370), 470, () -> {
                user_login_pane.setDisable(false);
            });
        });
    }

    @FXML
    void click_to_change_password(ActionEvent event) {

        if (!Function.isComplete(otp_no_1, otp_no_2, otp_no_3, otp_no_4, otp_no_5, otp_no_6)) {
            fg_otp_error_msg.setText("Please, enter complete OTP!!!");
            fg_otp_error_container.setVisible(true);
            fg_otp_error_container.setManaged(true);
            return;
        }

        String otp = otp_no_1.getText() + otp_no_2.getText() + otp_no_3.getText() + otp_no_4.getText() + otp_no_5.getText() + otp_no_6.getText();

        if (OTP_Service.verify_otp(txt_fg_email.getText(), otp)) {

            Function.showCase("Correct", "OTP is Valid. Now,You can change Password.");
            Animation.Slide_right_to_left(fg_otp_request_pane, Duration.millis(350), 450, () -> {
                Animation.Popup(fg_change_password_pane, Duration.millis(300), null);
            });

            fg_otp_error_container.setVisible(false);
            fg_otp_error_container.setManaged(false);
        } else {
            fg_otp_error_msg.setText("OTP is Invalid!!!");
            fg_otp_error_container.setVisible(true);
            fg_otp_error_container.setManaged(true);
        }
    }

    @FXML
    void click_to_otp(ActionEvent event) {

        String email = txt_fg_email.getText();

        if (!email.isBlank()) {

            List<Map<String, Object>> check_list = Dynamic_Method.select("users", null, "user_email=?", List.of(email), null, null,null);

            if (!check_list.isEmpty()) {

                OTP_Service.generate_and_send_otp(email, otp_timer);

                Function.showCase("Successfully",
                        "Please Wait Your OTP." +
                                "\n" +
                                "If OTP is expired or You don't get OTP, please Press [Resend It].");

                Animation.Slide_right_to_left(fg_email_request_pane, Duration.millis(350), 450, () -> {
                    Animation.Popup(fg_otp_request_pane, Duration.millis(300), null);
                });

                fg_email_error_container.setVisible(false);
                fg_email_error_container.setManaged(false);
            } else {
                fg_email_error_msg.setText(email + " doesn't have in database.");
                fg_email_error_container.setVisible(true);
                fg_email_error_container.setManaged(true);
            }
        } else {
            fg_email_error_msg.setText("Empty Email!!!");
            fg_email_error_container.setVisible(true);
            fg_email_error_container.setManaged(true);
        }
    }
}