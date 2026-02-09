package Controller;

import Database.DB_Connection;
import Database.Dynamic_Method;
import Utility.Animation;
import Utility.Notifunction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class newstockcontroller {
//accessories

    @FXML
    public BorderPane border_pane;
    @FXML
    public FlowPane card_pane;

    @FXML
    public Label all_stock, lbl_lowstock, lbl_outstock, lbl_instock, cancel_click_btn, new_item, lbl_name, past_stock_lbl, quick_stock_name, imeicount, addimei_lbl;

    @FXML
    public ScrollPane detail_card_pane, imei_scroll;

    @FXML
    public VBox quick_stock_pane, detail_pane, card_root, imei_card;

    @FXML
    public HBox qty_pane, real_qty_pane, add_hbox;

    @FXML
    public Rectangle product_image;

    @FXML
    public TextField txt_stock, quick_stock_txt, txt_color, txt_storage, txt_search;

    @FXML
    public Button main_quick_restock_btn, restock_all_btn, minus_btn, plus_btn, restock_btn, clear_all, Change_btn, confirm_imei;

    //phone stock
    @FXML
    public HBox phone_border_pane, hboxacc, storage_box;

    @FXML
    public Label phone_all_stock, phone_low_stock, phone_out_stock, phone_in_stock, phone_new_item;

    @FXML
    public Button phone_quick_stock_btn, phone_search_btn;


    private stock_general_controller selectedCard;

    private boolean isSearchOpen = false;

    public void setSelectedCard(stock_general_controller card) {
        this.selectedCard = card;
    }

    @FXML
    public void initialize() throws IOException {
        only_number(txt_stock);
        quick_stock_pane.setVisible(false);
        quick_stock_pane.setManaged(false);
        add_hbox.setManaged(false);
        add_hbox.setVisible(false);

        detail_pane.setVisible(false);
        detail_pane.setManaged(false);
        card_pane.setHgap(15);
        card_pane.setVgap(10);
        card_pane.setAlignment(Pos.TOP_LEFT);
        FontIcon icon = new FontIcon(FontAwesomeSolid.SEARCH);
        phone_search_btn.setStyle("-fx-background-color:transparent");
        icon.setIconSize(20);
        icon.setIconColor(Color.BLACK);
        phone_search_btn.setGraphic(icon);
        txt_search.setTranslateX(300);
        txt_search.textProperty().addListener((observable, oldValue, newValue) -> {
            if (phone_quick_stock_btn.isVisible()) {
                loadAllStock();
            } else {
                phoneloadAllStock();
            }
        });
        if (border_pane.isVisible()) {
            loadAllStock();
        } else {
            phoneloadAllStock();
        }
        imei_card.setVisible(false);
        imei_card.setManaged(false);
        txt_stock.textProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedCard != null && selectedCard.storage_box.isVisible()) {
                handleImeiGeneration(newValue);
            } else {
                imei_card.setVisible(false);
                imei_card.setManaged(false);
            }
        });

    }

    private void handleImeiGeneration(String newValue) {
        if (newValue.isEmpty() || !newValue.matches("\\d+")) {
//            imei_scroll.setContent(null);
            imei_scroll.setVisible(false);
            imei_scroll.setManaged(false);
            imeicount.setText("-");
            return;
        }

        imei_scroll.setVisible(true);
        imei_scroll.setManaged(true);

        int addQty = Integer.parseInt(newValue);
//        if (addQty > 50) return;

        VBox imeiContainer = new VBox(10);
        imeiContainer.setAlignment(Pos.TOP_CENTER);
        imeiContainer.setStyle("-fx-padding: 15; -fx-background-color: #ffffff;");

        for (int i = 1; i <= addQty; i++) {
            TextField imeiField = new TextField();
            only_number(imeiField);
            imeiField.setPromptText("Enter IMEI #" + i);
            imeiField.setPrefHeight(40);
            imeiField.setStyle("-fx-border-color: #dfe6e9; -fx-border-radius: 5;");
            imeiContainer.getChildren().add(imeiField);
        }

        imei_scroll.setContent(imeiContainer);
        imei_card.setVisible(true);
        imei_card.setManaged(true);
        if (imeicount != null) imeicount.setText("Items: " + addQty);
    }

    @FXML
    void click_on_btn_search(ActionEvent event) {
        if (!isSearchOpen) {
            txt_search.setVisible(true);
            txt_search.setManaged(true);
            Animation.Slide_right_to_left(txt_search, Duration.millis(250), 300, () -> txt_search.requestFocus());
            isSearchOpen = true;
        } else {
            txt_search.clear();
            Animation.Slide_left_to_right(txt_search, Duration.millis(250), 300, () -> {
                txt_search.setVisible(false);
                txt_search.setManaged(false);
            });
            isSearchOpen = false;
        }
    }

    @FXML
    void all_stock_click(MouseEvent event) {
        loadAllStock();
    }

    @FXML
    void instock_click(MouseEvent event) {
        loadInStock();
    }

    @FXML
    void lowstock_click(MouseEvent event) {
        loadLowStock();
    }

    @FXML
    void outstock_click(MouseEvent event) {
        loadOutStock();
    }

    @FXML
    void new_item_ckcik(MouseEvent event) {
        loadNewItems();
    }

    private void loadAllStock() {
        load_accessory_Stock("", "");
    }

    private void loadInStock() {
        load_accessory_Stock(" AND MAX(s.available_stock) >= 10", " AND s.available_stock >= 10");
    }

    private void loadLowStock() {

        load_accessory_Stock(" AND MIN(s.available_stock) > 0 AND MIN(s.available_stock) < 10",
                " AND s.available_stock > 0 AND s.available_stock < 10");
    }

    private void loadOutStock() {
        load_accessory_Stock(" AND MIN(s.available_stock) <= 0", " AND s.available_stock <= 0");
    }

    private void loadNewItems() {

        String modelCondition = " AND MIN(s.available_stock) = 0 AND MAX(p.created_at) >= DATE_SUB(NOW(), INTERVAL 1 DAY)";

        String variantCondition = " AND s.available_stock = 0 AND p.created_at >= DATE_SUB(NOW(), INTERVAL 1 DAY)";

        load_accessory_Stock(modelCondition, variantCondition);
    }

    private void load_accessory_Stock(String condition, String variantCondition) {
        card_pane.getChildren().clear();
        String searchText = txt_search.getText().trim().toLowerCase();


        StringBuilder baseQuery = new StringBuilder(
                "SELECT p.product_model FROM products p " +
                        "JOIN product_stock s ON p.product_id = s.product_id " +
                        "WHERE p.product_status = 'Active' AND p.product_type_id = 2 "
        );

        if (!searchText.isEmpty()) {
            baseQuery.append(" AND (LOWER(product_model) LIKE '%").append(searchText.replace("'", "''")).append("%')");
        }


        String finalQuery = baseQuery.toString() + " GROUP BY p.product_model HAVING 1=1 " + condition;

        List<Map<String, Object>> models = Dynamic_Method.select(finalQuery);

        for (Map<String, Object> m : models) {
            String modelName = m.get("product_model").toString();


            String variantQuery = "SELECT p.product_id, p.product_model, p.product_color, " +
                    "s.available_stock, p.product_image " +
                    "FROM products p JOIN product_stock s ON p.product_id = s.product_id " +
                    "WHERE p.product_model = '" + modelName.replace("'", "''") + "' " +
                    "AND p.product_type_id = 2 " + variantCondition;

            List<Map<String, Object>> variants = Dynamic_Method.select(variantQuery);

            if (variants.isEmpty()) continue;

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/stock-genral_card.fxml"));
                Parent card = loader.load();
                stock_general_controller c = loader.getController();

                c.setstock_controller1(this);
                c.space_box.setSpacing(16);
                c.select_circle.setVisible(true);
                c.select_circle.setManaged(true);

                c.setVariants(variants);
                c.storage_box.setManaged(false);
                c.storage_box.setVisible(false);

                card_pane.getChildren().add(card);

              
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void quick_stock_click(MouseEvent event) {
        if (!quick_stock_pane.isVisible()) {

            quick_stock_pane.setVisible(true);
            quick_stock_pane.setManaged(true);
            Animation.Slide_right_to_left(quick_stock_pane, Duration.millis(300), quick_stock_pane.getWidth(), null);
        } else {

            Animation.Slide_left_to_right(quick_stock_pane, Duration.millis(300), quick_stock_pane.getWidth(), () -> {
                quick_stock_pane.setVisible(false);
                quick_stock_pane.setManaged(false);
            });
        }
    }

    @FXML
    void restock_click(ActionEvent event) {

        if (selectedCard == null) {
            new Alert(Alert.AlertType.ERROR, "No product selected").show();
            return;

        }

        String input = txt_stock.getText().trim();

        if (!input.matches("\\d+")) {
            new Alert(Alert.AlertType.ERROR, "Please enter numbers only").show();
            return;

        }

        int addQty = Integer.parseInt(input);
        int productId = selectedCard.getProductId();

        String sql = """
                    UPDATE product_stock
                    SET available_stock = available_stock + ?,
                        total_stock = total_stock + ?
                    WHERE product_id = ?
                """;

        try (var con = DB_Connection.getConnection();
             var ps = con.prepareStatement(sql)) {

            ps.setInt(1, addQty);

            ps.setInt(2, addQty);

            ps.setInt(3, productId);

            ps.executeUpdate();

            int currentQty = selectedCard.getProductStock();
            int newQty = currentQty + addQty;

            selectedCard.lbl_stock.setText(String.valueOf(newQty));
            selectedCard.updateStockButtons(newQty);
//            new Alert(Alert.AlertType.INFORMATION, "Stock updated successfully").show();
            Notifunction.success("Update Successful", "Item " + lbl_name.getText() + "'s " + addQty + " Update Successful!!");

        } catch (Exception e) {

            e.printStackTrace();

            new Alert(Alert.AlertType.ERROR, "Stock update failed").show();

        }

        border_pane.setDisable(false);

        detail_pane.setVisible(false);

        detail_pane.setManaged(false);

        add_hbox.setVisible(false);

        add_hbox.setManaged(false);


    }

    public void displayProductDetails(stock_general_controller card) {
        VBox container;

        if (detail_card_pane.getContent() instanceof VBox) {
            container = (VBox) detail_card_pane.getContent();
        } else {
            container = new VBox(10);
            container.setPadding(new javafx.geometry.Insets(10));
            detail_card_pane.setContent(container);
        }


        for (javafx.scene.Node node : container.getChildren()) {
            if (node.getUserData() != null && node.getUserData().equals(card.getProductId())) {
                return;
            }
        }


        HBox selectedHBox = new HBox(12);
        selectedHBox.setUserData(card.getProductId());
        selectedHBox.setAlignment(Pos.CENTER_LEFT);
        selectedHBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 10; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 8, 0, 0, 4);");


        Rectangle imgView = new Rectangle(45, 45);
        imgView.setArcHeight(10);
        imgView.setArcWidth(10);
        imgView.setFill(card.product_image.getFill());


        VBox infoBox = new VBox(2);
        Label nameLabel = new Label(card.getProductName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13; -fx-text-fill: #2D3436;");
        Label detailLabel = new Label(card.getProductColor() + " • Stock: " + card.getProductStock());
        detailLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #636E72;");
        infoBox.getChildren().addAll(nameLabel, detailLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        Button closeBtn = new Button("✖");
        closeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #FF7675; -fx-cursor: hand; -fx-font-weight: bold;");
        closeBtn.setOnAction(e -> {
            container.getChildren().remove(selectedHBox);

            quick_stock_name.setText(container.getChildren().size() + " Items Selected");
            if (container.getChildren().isEmpty()) {
                detail_pane.setVisible(false);
                detail_pane.setManaged(false);
            }
        });


        selectedHBox.getChildren().addAll(imgView, infoBox, spacer, closeBtn);
        container.getChildren().add(0, selectedHBox);


        int count = container.getChildren().size();
        quick_stock_name.setText(count + " Items Selected");


        qty_pane.setVisible(true);
        qty_pane.setManaged(true);
        real_qty_pane.setVisible(false);
        real_qty_pane.setManaged(false);


        detail_pane.setVisible(true);
        detail_pane.setManaged(true);

        Animation.Popup(selectedHBox, Duration.millis(300), null);

    }

    @FXML
    void restock_all_btn_click(ActionEvent event) {
        qty_pane.setVisible(false);
        qty_pane.setManaged(false);
        real_qty_pane.setVisible(true);
        real_qty_pane.setManaged(true);
    }


    @FXML
    void main_quick_restock_btn_click(ActionEvent event) {

        String input = quick_stock_txt.getText().trim();
        if (input.isEmpty() || !input.matches("\\d+")) {
            Notifunction.error("Input Error", "Please enter a valid numeric quantity!");
            return;
        }

        int addQty = Integer.parseInt(input);
        VBox container = (VBox) detail_card_pane.getContent();

        if (container == null || container.getChildren().isEmpty()) {
            Notifunction.error("Selection Error", "No products selected for restock!");
            return;
        }


        String sql = "UPDATE product_stock SET available_stock = available_stock + ?, total_stock = total_stock + ? WHERE product_id = ?";

        try (var con = DB_Connection.getConnection()) {
            con.setAutoCommit(false);

            try (var ps = con.prepareStatement(sql)) {
                for (Node node : container.getChildren()) {
                    if (node.getUserData() instanceof Integer) {
                        int productId = (Integer) node.getUserData();
                        ps.setInt(1, addQty);
                        ps.setInt(2, addQty);
                        ps.setInt(3, productId);
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
                con.commit();


                Notifunction.success("Restock Complete", "All items in the list have been updated.");


                container.getChildren().clear();
                loadAllStock();


                if (detail_card_pane.getContent() instanceof VBox container1) {
                    container1.getChildren().clear();
//            detail_pane.setVisible(false);
//            detail_pane.setManaged(false);
                    quick_stock_name.setText("0 Items Selected");
                }
                quick_stock_pane.setVisible(true);
                quick_stock_pane.setManaged(true);

            } catch (Exception e) {
                con.rollback();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Notifunction.error("Database Error", "Failed to update stock items.");
        }
    }

    @FXML
    void minus_btn_clcik(ActionEvent event) {
        String str = quick_stock_txt.getText();
        int num = Integer.parseInt(str);
        int num1 = num - 1;
        if (num1 < 0) {
            Notifunction.error("Under 0 Warning", "Under 0 not valid");
        } else {
            quick_stock_txt.setText(String.valueOf(num1));
        }


    }

    @FXML
    void plus_btn_click(ActionEvent event) {
        String str = quick_stock_txt.getText();
        int num = Integer.parseInt(str);
        int num1 = num + 1;
        quick_stock_txt.setText(String.valueOf(num1));
    }

    @FXML
    void clear_all_click(ActionEvent event) {
        if (detail_card_pane.getContent() instanceof VBox container) {
            container.getChildren().clear();
//            detail_pane.setVisible(false);
//            detail_pane.setManaged(false);
            quick_stock_name.setText("0 Items Selected");


        }
    }

    @FXML
    void cancel_click(MouseEvent event) {
        detail_pane.setVisible(false);
        detail_pane.setManaged(false);
        border_pane.setDisable(false);
        add_hbox.setVisible(false);
        add_hbox.setManaged(false);
        add_hbox.setDisable(false);
        imei_scroll.setVisible(false);
        imei_scroll.setManaged(false);

    }

    @FXML
    void change_btn_click(ActionEvent event) {
        if (quick_stock_pane.isVisible()) {
            Notifunction.error("Invalid pane open", "Please close the pane");
        } else {

            txt_search.clear();
            phone_quick_stock_btn.setVisible(false);
            phone_quick_stock_btn.setManaged(false);
            hboxacc.setVisible(false);
            hboxacc.setManaged(false);
            Animation.Popup_Reverse(hboxacc, Duration.millis(300), () -> {
                phone_border_pane.setVisible(true);
                phone_border_pane.setManaged(true);
                Animation.Popup(phone_border_pane, Duration.millis(300), null);
            });
            phoneloadAllStock();
        }


    }

    @FXML
    void phone_change_btn_click(ActionEvent event) {
        txt_search.clear();

        phone_quick_stock_btn.setVisible(true);
        phone_quick_stock_btn.setManaged(true);
        phone_border_pane.setVisible(false);
        phone_border_pane.setManaged(false);
        Animation.Popup_Reverse(phone_border_pane, Duration.millis(300), () -> {
            hboxacc.setVisible(true);
            hboxacc.setManaged(true);
            Animation.Popup(hboxacc, Duration.millis(300), null);
        });
        loadAllStock();

    }
    //phone

    @FXML
    void phone_all_stock_click(MouseEvent event) {
        phoneloadAllStock();
    }

    @FXML
    void phone_instock_click(MouseEvent event) {
        phoneloadInStock();
    }

    @FXML
    void phone_lowstock_click(MouseEvent event) {
        phoneloadLowStock();
    }

    @FXML
    void phone_outstock_click(MouseEvent event) {
        phoneloadOutStock();
    }

    @FXML
    void phone_new_item_ckcik(MouseEvent event) {
        phoneloadNewItems();
    }

    private void phoneloadAllStock() {
        load_phone_Stock("", "");
    }

    private void phoneloadInStock() {
        load_phone_Stock(" AND MAX(s.available_stock) >= 10", " AND s.available_stock >= 10");
    }

    private void phoneloadLowStock() {

        load_phone_Stock(" AND MIN(s.available_stock) > 0 AND MIN(s.available_stock) < 10",
                " AND s.available_stock > 0 AND s.available_stock < 10");
    }

    private void phoneloadOutStock() {
        load_phone_Stock(" AND MIN(s.available_stock) <= 0", " AND s.available_stock <= 0");
    }

    private void phoneloadNewItems() {

        String modelCondition = " AND MIN(s.available_stock) = 0 AND MAX(p.created_at) >= DATE_SUB(NOW(), INTERVAL 1 DAY)";

        String variantCondition = " AND s.available_stock = 0 AND p.created_at >= DATE_SUB(NOW(), INTERVAL 1 DAY)";

        load_phone_Stock(modelCondition, variantCondition);
    }

    private void load_phone_Stock(String condition, String variantCondition) {
        card_pane.getChildren().clear();
        String searchText = txt_search.getText().trim().toLowerCase();


        StringBuilder baseQuery = new StringBuilder(
                "SELECT p.product_model FROM products p " +
                        "JOIN product_stock s ON p.product_id = s.product_id " +
                        "WHERE p.product_status = 'Active' AND p.product_type_id = 1 "
        );

        if (!searchText.isEmpty()) {
            baseQuery.append(" AND (LOWER(product_model) LIKE '%").append(searchText.replace("'", "''")).append("%')");
        }

        String finalQuery = baseQuery.toString() + " GROUP BY p.product_model HAVING 1=1 " + condition;

        List<Map<String, Object>> models = Dynamic_Method.select(finalQuery);

        for (Map<String, Object> m : models) {
            String modelName = m.get("product_model").toString();


            String variantQuery = "SELECT p.product_id, p.product_model, p.product_color, p.product_ram, p.product_rom, " +
                    "s.available_stock, p.product_image " +
                    "FROM products p JOIN product_stock s ON p.product_id = s.product_id " +
                    "WHERE p.product_model = '" + modelName.replace("'", "''") + "' " +
                    "AND p.product_type_id = 1 " + variantCondition;

            List<Map<String, Object>> variants = Dynamic_Method.select(variantQuery);

            if (variants.isEmpty()) continue;

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/stock-genral_card.fxml"));
                Parent card = loader.load();
                stock_general_controller c = loader.getController();

                c.setstock_controller1(this);
                c.space_box.setSpacing(2);
                c.select_circle.setVisible(false);
                c.select_circle.setManaged(false);

                c.setVariants(variants);
                c.storage_box.setManaged(true);
                c.storage_box.setVisible(true);

                card_pane.getChildren().add(card);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void toggleDetailsView(boolean show) {
        imei_card.setVisible(show);
        imei_card.setManaged(show);
    }

    @FXML
    void addimei_lbl_clcik(MouseEvent event) {

        boolean isCurrentlyVisible = imei_card.isVisible();
        toggleDetailsView(!isCurrentlyVisible);

    }

    @FXML
    void confirm_imei_click(ActionEvent event) {
        VBox container = (VBox) imei_scroll.getContent();

        if (container == null || container.getChildren().isEmpty()) {
            Notifunction.error("Input Error", "No IMEI fields found.");
            return;
        }

        if (selectedCard == null) {
            Notifunction.error("Selection Error", "Please select a product first.");
            return;
        }

        List<String> imeiList = new ArrayList<>();
        java.util.HashSet<String> duplicateCheckSet = new java.util.HashSet<>();

        for (Node node : container.getChildren()) {
            if (node instanceof TextField tf) {
                String imei = tf.getText().trim();


                if (imei.isEmpty()) {
                    Notifunction.error("Validation Error", "Please fill all IMEI fields!");
                    tf.requestFocus();
                    return;
                }

                if (imei.length() != 15 || !imei.matches("\\d+")) {
                    Notifunction.error("Invalid IMEI", "IMEI must be 15 digits: " + imei);
                    tf.requestFocus();
                    return;
                }


                if (!duplicateCheckSet.add(imei)) {
                    Notifunction.error("Duplicate Error", "You entered the same IMEI twice in the form: " + imei);
                    tf.requestFocus();
                    return;
                }


                if (Dynamic_Method.isExists("product_unique", "product_imei", imei)) {
                    Notifunction.error("Duplicate Error", "IMEI " + imei + " already exists in Database!");
                    tf.requestFocus();
                    return;
                }

                imeiList.add(imei);
            }
        }

        int productId = selectedCard.getProductId();
        try (java.sql.Connection conn = DB_Connection.getConnection()) {
            conn.setAutoCommit(false);

            String insertImeiSql = "INSERT INTO product_unique (product_imei, product_id, product_unique_status) VALUES (?, ?, 'AVAILABLE')";
            String updateStockSql = "UPDATE product_stock SET available_stock = available_stock + 1, total_stock = total_stock + 1 WHERE product_id = ?";

            try (java.sql.PreparedStatement psImei = conn.prepareStatement(insertImeiSql);
                 java.sql.PreparedStatement psStock = conn.prepareStatement(updateStockSql)) {

                for (String imeiValue : imeiList) {
                    psImei.setString(1, imeiValue);
                    psImei.setInt(2, productId);


                    try {
                        psImei.executeUpdate();
                    } catch (java.sql.SQLIntegrityConstraintViolationException e) {
                        conn.rollback();
                        Notifunction.error("Critical Duplicate", "Database conflict: IMEI " + imeiValue + " was just added by another process.");
                        return;
                    }

                    psStock.setInt(1, productId);
                    psStock.executeUpdate();
                }

                conn.commit();
                Notifunction.success("Success", imeiList.size() + " items added successfully.");

                imei_card.setVisible(false);
                imei_card.setManaged(false);
                phoneloadAllStock();

            } catch (java.sql.SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Notifunction.error("Database Error", "Failed to save IMEI: " + e.getMessage());
        }

    }

    public static void only_number(TextField textField) {

        textField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
    }

    public static void only_number_dot(TextField textField) {

        textField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\d*(\\.\\d*)?")) {
                return change;
            }
            return null;
        }));
    }


}
