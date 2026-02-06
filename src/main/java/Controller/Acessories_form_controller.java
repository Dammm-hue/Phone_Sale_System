package Controller;

import Database.Dynamic_Method;
import Utility.Animation;
import Utility.Notifunction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;


import java.util.*;

public class Acessories_form_controller {
    @FXML
    public Button add_pre_btn, main_cancle_btn, cancel_btn, clear_btn, edit_btn, btn_search, btn_add, delete_text_area, image_select_btn, add_list_btn, new_cnancle_btn;
    @FXML
    public VBox text_area_pane, add_update_card, variable_pane, list_pane, for_sapce_pane;
    @FXML
    public HBox add_update_pane, update_btns, add_btns, color_box;
    @FXML
    public TextField name_field, price_field, color_field, txt_search, color_field1;
    @FXML
    public ComboBox<String> type_combo;
    @FXML
    public BorderPane first_pane;
    @FXML
    private FlowPane flowpane;
    @FXML
    public Rectangle product_image;
    @FXML
    public TextArea detail_text_area;

    private int currentBrandId = 0;


    @FXML
    private Label lbl_details_info, lbl_all, lbl_airpod, lbl_headphone, lbl_powerbank, lbl_charger;
    public int productId;
    private boolean isSearchOpen = false;

    private List<Label> navLabels;
    String selectedImagePath = "/image/photo.jpg";

    private String currentCategory = "";

    public void setSelectedImagePath(String path) {
        this.selectedImagePath = path;
    }

    private List<Model.colorandimage> tempVariantList = new ArrayList<>();

    public void toggleDetailsView(boolean show) {
        text_area_pane.setVisible(show);
        text_area_pane.setManaged(show);
    }

    @FXML
    void click_on_btn_add(ActionEvent event) {
        for_sapce_pane.setSpacing(20);
        variable_pane.setVisible(false);
        variable_pane.setManaged(false);
        add_update_pane.setVisible(true);
        add_update_pane.setManaged(true);
        first_pane.setDisable(true);
        add_update_pane.setVisible(true);
        add_update_pane.setManaged(true);
        update_btns.setVisible(false);
        add_btns.setVisible(true);
        name_field.clear();
        price_field.clear();
        color_box.setVisible(false);
        color_box.setManaged(false);
        product_image.setOnMouseClicked(null);
        detail_text_area.clear();
        type_combo.setValue(null);
        selectedImagePath = "/image/photo.jpg";
        try {
            product_image.setFill(new ImagePattern(new Image(getClass().getResourceAsStream(selectedImagePath))));
        } catch (Exception e) {
            product_image.setFill(Color.LIGHTGRAY);
        }

        toggleDetailsView(false);
        Animation.Popup(add_update_card, Duration.millis(400), () -> {
        });
    }

    @FXML
    public void initialize() {
        add_update_pane.setVisible(false);
        add_update_pane.setManaged(false);
        toggleDetailsView(false);
        variable_pane.setVisible(false);
        variable_pane.setManaged(false);

        type_combo.getItems().addAll("Airpod", "Headphone", "Charger", "Powerbank", "Accessory");
        name_field.setOnAction(e -> price_field.requestFocus());
        price_field.setOnAction(e -> {
            toggleDetailsView(true);
            detail_text_area.requestFocus();
        });

        name_field.textProperty().addListener((obs, oldVal, newVal) -> {
            type_combo.setValue (determineType(newVal));
            if (isExistingModel()) {
                toggleDetailsView(false);
            }
        });
        price_field.setOnAction(e -> {
            if (isExistingModel()) {
                toggleDetailsView(false);
                color_field1.requestFocus();
            } else {
                toggleDetailsView(true);
                detail_text_area.requestFocus();
            }
        });

        price_field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                price_field.setText(oldValue);
            }
        });
        FontIcon icon = new FontIcon(FontAwesomeSolid.SEARCH);
        btn_search.setStyle("-fx-background-color:transparent");
        icon.setIconSize(20);
        icon.setIconColor(Color.BLACK);
        btn_search.setGraphic(icon);
        txt_search.setTranslateX(300);
        txt_search.textProperty().addListener((observable, oldValue, newValue) -> {
            loadacc();
        });
        currentCategory = "";
        loadacc();
        navLabels = Arrays.asList(lbl_all, lbl_airpod, lbl_headphone, lbl_powerbank, lbl_charger);

    }

    private boolean isExistingModel() {
        String modelName = name_field.getText().trim();
        if (modelName.isEmpty()) return false;

        String query = "SELECT product_id FROM products WHERE product_model = '"
                + modelName.replace("'", "''") + "' LIMIT 1";
        List<Map<String, Object>> result = Dynamic_Method.select(query);

        return result != null && !result.isEmpty();
    }


    private String determineType(String modelName) {
        if (modelName == null || modelName.isEmpty()) return "Accessory";
        String name = modelName.toLowerCase();
        if (name.contains("powerbank")) return "Powerbank";
        if (name.contains("airpod") || name.contains("buds")) return "Airpod";
        if (name.contains("headphone")) return "Headphone";
        if (name.contains("charger") || name.contains("adapter")) return "Charger";
        return "Accessory";
    }

    private boolean validateInputs() {
        if (name_field.getText().trim().isEmpty() ||
                price_field.getText().trim().isEmpty() ||
                color_field.getText().trim().isEmpty() ||
                type_combo.getValue() == null) {

            Notifunction.error("Field Empty", "PLease Fill Empty Field!");
            return false;
        }
        try {
            Double.parseDouble(price_field.getText());
        } catch (NumberFormatException e) {
            Notifunction.error("Price Error", "Price must be valid number!");
            return false;
        }
        return true;
    }


    @FXML
    void add_pre_click(ActionEvent event) {
        String modelName = name_field.getText().trim();
        if (modelName.isEmpty() || tempVariantList.isEmpty()) {
            Notifunction.error("Input Error", "Model Name and valid input please!!");
            return;
        }

        try {
            int sharedDetailId;
            String checkQuery = "SELECT product_accessory_fact FROM products WHERE product_model = '" + modelName.replace("'", "''") + "' LIMIT 1";
            List<Map<String, Object>> existingModel = Dynamic_Method.select(checkQuery);

            if (existingModel != null && !existingModel.isEmpty()) {
                sharedDetailId = Integer.parseInt(existingModel.get(0).get("product_accessory_fact").toString());
            } else {
                Map<String, Object> detailData = new HashMap<>();
                detailData.put("accessories_text", detail_text_area.getText().isEmpty() ? "No description" : detail_text_area.getText());
                Dynamic_Method.insert("accessories_detail", detailData);

                List<Map<String, Object>> res = Dynamic_Method.select("SELECT accessories_detail_id FROM accessories_detail ORDER BY accessories_detail_id DESC LIMIT 1");
                sharedDetailId = Integer.parseInt(res.get(0).get("accessories_detail_id").toString());
            }

            for (Model.colorandimage v : tempVariantList) {
                Map<String, Object> p = new HashMap<>();
                p.put("product_model", modelName);

                // FIXED: Changed 'product_category' to 'product_brand_id'
                p.put("product_brand_id", getBrandId(type_combo.getValue()));

                p.put("product_sale_price", Double.parseDouble(price_field.getText()));
                p.put("product_color", v.getColor());
                p.put("product_image", v.getImagePath());
                p.put("product_accessory_fact", sharedDetailId);
                p.put("product_type_id", 2); // Accessory type

                Dynamic_Method.insert("products", p);

                // Re-fetch the ID to create stock entry
                List<Map<String, Object>> productRes = Dynamic_Method.select("SELECT product_id FROM products ORDER BY product_id DESC LIMIT 1");
                int newProductId = Integer.parseInt(productRes.get(0).get("product_id").toString());

                Map<String, Object> s = new HashMap<>();
                s.put("product_id", newProductId);
                s.put("available_stock", 0);
                s.put("total_stock", 0);
                Dynamic_Method.insert("product_stock", s);
            }

            Notifunction.success("Success", "Variants for " + modelName + " added!");
            tempVariantList.clear();
            finalizeAction();

        } catch (Exception e) {
            e.printStackTrace();
            Notifunction.error("Database Error", "Failed to add products. Check column names.");
        }
    }

    @FXML
    void edit_clcik(ActionEvent event) {
        if (!validateInputs()) return;

        try {
            Map<String, Object> pMap = new HashMap<>();
            pMap.put("product_model", name_field.getText().trim());

            // ပြင်ဆင်ရန်- product_category အစား product_brand_id ကို သုံးပါ
            pMap.put("product_brand_id", getBrandId(type_combo.getValue()));

            pMap.put("product_sale_price", Double.parseDouble(price_field.getText()));
            pMap.put("product_color", color_field.getText().trim());
            pMap.put("product_image", selectedImagePath);

            int rowsAffected = Dynamic_Method.update("products", pMap, "product_id", productId);

            List<Map<String, Object>> res = Dynamic_Method.select("SELECT product_accessory_fact FROM products WHERE product_id = " + productId);
            if (res != null && !res.isEmpty()) {
                int detailId = Integer.parseInt(res.get(0).get("product_accessory_fact").toString());
                Map<String, Object> dMap = new HashMap<>();
                dMap.put("accessories_text", detail_text_area.getText().trim());
                Dynamic_Method.update("accessories_detail", dMap, "accessories_detail_id", detailId);
            }

            if (rowsAffected > 0) {
                currentBrandId = 0; // currentCategory အစား currentBrandId သုံးပါ
                loadacc();
                main_cancle_click(null);
                Notifunction.success("Success", "Updated successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private int getBrandId(String brandName) {
        if (brandName == null) return 1;
        return switch (brandName) {
            case "Airpod" -> 4;
            case "Charger" -> 5;
            case "Powerbank" -> 6;
            case "Headphone" -> 7;
            default -> 1;
        };
    }

    public void loadacc() {
        flowpane.getChildren().clear();
        String searchText = txt_search.getText().trim().toLowerCase();

        StringBuilder queryBuilder = new StringBuilder("SELECT product_model FROM products WHERE product_type_id = 2");

        // Filtering by brand_id instead of category
        if (currentBrandId != 0) {
            queryBuilder.append(" AND product_brand_id = ").append(currentBrandId);
        }

        if (!searchText.isEmpty()) {
            queryBuilder.append(" AND (LOWER(product_model) LIKE '%").append(searchText.replace("'", "''")).append("%')");
        }

        queryBuilder.append(" GROUP BY product_model");

        List<Map<String, Object>> models = Dynamic_Method.select(queryBuilder.toString());

        if (models != null) {
            for (Map<String, Object> modelMap : models) {
                // FIX: Null check for model name
                Object modelObj = modelMap.get("product_model");
                if (modelObj == null) continue;

                String modelName = modelObj.toString();
                String variantQuery = "SELECT * FROM products WHERE product_model = '" + modelName.replace("'", "''") + "'";
                List<Map<String, Object>> variants = Dynamic_Method.select(variantQuery);

                // loadacc() ထဲက loop ပတ်တဲ့နေရာမှာ အခုလိုစစ်ပါ
                if (variants != null && !variants.isEmpty()) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Accessory_general_card.fxml"));
                        VBox card = loader.load();
                        Accessory_general_controller controller = loader.getController();

                        Map<String, Object> first = variants.get(0);

                        controller.setData(
                                Integer.parseInt(first.getOrDefault("product_id", 0).toString()),
                                first.getOrDefault("product_model", "Unknown").toString(),
                                first.getOrDefault("product_brand_id", "1").toString(), // ဒီနေရာမှာ brand_id ကို ပို့ရပါမယ်
                                first.getOrDefault("product_color", "N/A").toString(),
                                Double.parseDouble(first.getOrDefault("product_sale_price", 0.0).toString()),
                                first.getOrDefault("product_image", "/image/photo.jpg").toString()
                        );

                        controller.setVariantsData(variants);
                        controller.setaccessories(this);
                        flowpane.getChildren().add(card);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @FXML
    void click_on_lbl_all(MouseEvent e) {
        currentBrandId = 0;
        loadacc();
    }

    @FXML
    void click_on_lbl_airpod(MouseEvent e) {
        currentBrandId = 4;
        loadacc();
    }

    @FXML
    void click_on_lbl_headphone(MouseEvent e) {
        currentBrandId = 7;
        loadacc();
    }

    @FXML
    void click_on_lbl_charger(MouseEvent e) {
        currentBrandId = 5;
        loadacc();
    }

    @FXML
    void click_on_lbl_powerbank(MouseEvent e) {
        currentBrandId = 6;
        loadacc();

    }

    @FXML
    void choose_image_click(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        java.io.File selectedFile = fileChooser.showOpenDialog(first_pane.getScene().getWindow());
        if (selectedFile != null) {
            selectedImagePath = selectedFile.getAbsolutePath();
            product_image.setFill(new ImagePattern(new Image(selectedFile.toURI().toString())));
        }
    }

    private void finalizeAction() {
        toggleDetailsView(false);
        currentCategory = "";
        loadacc();
        main_cancle_click(null);
    }

    @FXML
    void main_cancle_click(ActionEvent event) {
        Animation.Popup_Reverse(add_update_card, Duration.millis(400), () -> {
            add_update_pane.setVisible(false);
            add_update_pane.setManaged(false);
            first_pane.setDisable(false);
        });
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

    //    @FXML
//    void click_details_info(MouseEvent e) {
//        boolean isCurrentlyVisible = text_area_pane.isVisible();
//        toggleDetailsView(!isCurrentlyVisible);
//        if (!isCurrentlyVisible) {
//            detail_text_area.requestFocus();
//        }
//    }
    @FXML
    void click_details_info(MouseEvent e) {
        String modelName = name_field.getText().trim();

        if (modelName.isEmpty()) {
            Notifunction.error("Input Error", "Please enter a Model Name first.");
            return;
        }


        boolean isEditMode = update_btns.isVisible();

        if (isExistingModel() && !isEditMode) {
            Notifunction.error("Duplicate Model", "Details for '" + modelName + "' already exist.");
            toggleDetailsView(false);
            return;
        }

        boolean isCurrentlyVisible = text_area_pane.isVisible();
        toggleDetailsView(!isCurrentlyVisible);

        if (!isCurrentlyVisible) {
            detail_text_area.requestFocus();
        }
    }

    @FXML
    void cancle_click(ActionEvent e) {
        variable_pane.setVisible(true);
        variable_pane.setManaged(true);
    }

    @FXML
    void image_select_btn_click(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        java.io.File selectedFile = fileChooser.showOpenDialog(first_pane.getScene().getWindow());
        if (selectedFile != null) {
            selectedImagePath = selectedFile.getAbsolutePath();
            product_image.setFill(new ImagePattern(new Image(selectedFile.toURI().toString())));
        }
    }

    @FXML
    void clear_btn_click(ActionEvent e) {
        name_field.clear();
        price_field.clear();
        type_combo.setValue(null);
        color_field.clear();

    }

    @FXML
    void name_cancel_click(ActionEvent e) {
        name_field.clear();
    }

    @FXML
    void price_cancel_click(ActionEvent e) {
        price_field.clear();
    }

    @FXML
    void color_cancel_clcik(ActionEvent e) {
        color_field.clear();
    }

    @FXML
    void delete_text_area_click(ActionEvent e) {
        detail_text_area.clear();
    }

    @FXML
    void add_list_btn_click(ActionEvent event) {
        if (color_field1.getText().isEmpty() || selectedImagePath.equals("/image/photo.jpg")) {
            Notifunction.error("Input Error", "PLease select Color And Image");
            return;
        }


        Model.colorandimage variant = new Model.colorandimage(color_field1.getText().trim(), selectedImagePath);
        tempVariantList.add(variant);


        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: white; -fx-padding: 8; -fx-background-radius: 10; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 1);");

        Rectangle rect = new Rectangle(35, 35);
        rect.setArcHeight(10);
        rect.setArcWidth(10);
        rect.setFill(new ImagePattern(variant.getImage()));

        Label name = new Label(variant.getColor());
        name.setStyle("-fx-font-weight: bold; -fx-text-fill: #475569;");


        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        Button btnDelete = new Button();
        FontIcon deleteIcon = new FontIcon(FontAwesomeSolid.TIMES_CIRCLE);
        deleteIcon.setIconColor(Color.RED);
        btnDelete.setGraphic(deleteIcon);
        btnDelete.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        selectedImagePath = "/image/photo.jpg";
        btnDelete.setOnAction(e -> {
            product_image.setFill(new ImagePattern(new Image(getClass().getResourceAsStream(selectedImagePath))));
            try {
                ScrollPane sp = (ScrollPane) list_pane.getChildren().get(0);
                Pane content = (Pane) sp.getContent();
                content.getChildren().remove(row);

                tempVariantList.remove(variant);

                if (content.getChildren().isEmpty()) {
                    list_pane.setVisible(true);
                    list_pane.setManaged(true);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        row.getChildren().addAll(rect, name, spacer, btnDelete);

        try {
            ScrollPane sp = (ScrollPane) list_pane.getChildren().get(0);
            if (sp.getContent() instanceof VBox) {
                ((VBox) sp.getContent()).getChildren().add(row);
            } else if (sp.getContent() instanceof HBox) {
                ((HBox) sp.getContent()).getChildren().add(row);
            }
        } catch (Exception e) {
            System.out.println("UI Layout Error: " + e.getMessage());
        }

        list_pane.setVisible(true);
        list_pane.setManaged(true);
        color_field1.clear();
    }

    @FXML
    void new_cnancle_btn_click(ActionEvent event) {
        variable_pane.setVisible(false);
        variable_pane.setManaged(false);
    }

}