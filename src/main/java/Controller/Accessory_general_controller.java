package Controller;

import Database.Dynamic_Method;
import Utility.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.List;
import java.util.Map;

public class Accessory_general_controller {
    @FXML private Label lbl_name, lbl_price, lbl_type, lbl_color;
    @FXML private Rectangle product_image;
    @FXML private VBox card_root;
    @FXML private Button btn_next;

    public int productId;
    private Acessories_form_controller mainController;
    private List<Map<String, Object>> variantsList;
    private int currentIndex = 0;

    public void setaccessories(Acessories_form_controller controller) {
        this.mainController = controller;
    }


    public void setData(int productId, String name, String brandId, String color, double price, String img) {
        this.productId = productId;
        lbl_name.setText(name);


        lbl_type.setText(convertIdToBrandName(brandId));

        lbl_color.setText(color);
        lbl_price.setText(String.format("%.2f", price));
        product_image.setFill(new ImagePattern(loadImageSafe(img)));
    }

    private String convertIdToBrandName(String id) {
        if (id == null) return "Generic";
        return switch (id) {
            case "4" -> "Airpod";
            case "5" -> "Charger";
            case "6" -> "Powerbank";
            case "7" -> "Headphone";
            default -> "Generic";
        };
    }

    public void setVariantsData(List<Map<String, Object>> variants) {
        this.variantsList = variants;
        if (variants != null && !variants.isEmpty()) {
            card_root.setOnMouseEntered(e -> {
                if (variantsList.size() > 1) btn_next.setVisible(true);
            });
            card_root.setOnMouseExited(e -> btn_next.setVisible(false));
        }
    }

    @FXML
    void onNextClick(ActionEvent event) {
        if (variantsList != null && variantsList.size() > 1) {
            currentIndex = (currentIndex + 1) % variantsList.size();
            Map<String, Object> data = variantsList.get(currentIndex);

            String nextImg = String.valueOf(data.getOrDefault("product_image", ""));
            String nextColor = String.valueOf(data.getOrDefault("product_color", "N/A"));

            updateCardDisplay(nextImg, nextColor);

            if (data.get("product_id") != null) {
                this.productId = Integer.parseInt(data.get("product_id").toString());
            }
        }
    }

    public void updateCardDisplay(String imgPath, String colorName) {
        if (imgPath != null && !imgPath.isEmpty()) {
            product_image.setFill(new ImagePattern(loadImageSafe(imgPath)));
        }
        if (lbl_color != null) {
            lbl_color.setText(colorName);
        }
    }

    @FXML
    public void edit(ActionEvent event) {
        if (mainController != null) {
            // Setup the UI for editing
            mainController.add_update_pane.setVisible(true);
            mainController.add_update_pane.setManaged(true);
            mainController.update_btns.setVisible(true);
            mainController.add_btns.setVisible(false);
            mainController.first_pane.setDisable(true);

            // Transfer data to the main form
            mainController.productId = this.productId;
            mainController.name_field.setText(lbl_name.getText());
            mainController.price_field.setText(lbl_price.getText().replace(".00", ""));
            mainController.color_field.setText(lbl_color.getText());
            mainController.type_combo.setValue(lbl_type.getText());

            // Load additional details from DB
            List<Map<String, Object>> res = Dynamic_Method.select(
                    "SELECT p.product_image, d.accessories_text FROM products p " +
                            "JOIN accessories_detail d ON p.product_accessory_fact = d.accessories_detail_id " +
                            "WHERE p.product_id = " + this.productId
            );

            if (res != null && !res.isEmpty()) {
                mainController.detail_text_area.setText(String.valueOf(res.get(0).get("accessories_text")));
                mainController.setSelectedImagePath(String.valueOf(res.get(0).get("product_image")));
                mainController.product_image.setFill(product_image.getFill());
            }

            Animation.Popup(mainController.add_update_card, Duration.millis(400), () -> {});
        }
    }

    private Image loadImageSafe(String path) {
        try {
            if (path != null && !path.isBlank()) {
                java.io.File file = new java.io.File(path.replace("\"", ""));
                if (file.exists()) return new Image(file.toURI().toString());
            }
        } catch (Exception e) {}
        return new Image(getClass().getResource("/image/photo.jpg").toExternalForm());
    }
}