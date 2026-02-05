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
    @FXML private Button btn_next,edit_btn;

    public int productId;
    private Acessories_form_controller mainController;
    private List<Map<String, Object>> variantsList;
    private String defaultImg, defaultColor;
    private int currentIndex = 0;

    public void setaccessories(Acessories_form_controller controller) {
        this.mainController = controller;
    }
    public void setVariantsData(List<Map<String, Object>> variants) {
        this.variantsList = variants;
        if (variants != null && !variants.isEmpty()) {
            this.defaultImg = variants.get(0).get("product_image").toString();
            this.defaultColor = variants.get(0).get("product_color").toString();


            card_root.setOnMouseEntered(e -> {
                if (variantsList.size() > 1) btn_next.setVisible(true);
            });
            card_root.setOnMouseExited(e -> btn_next.setVisible(false));
        }
    }
    public void updateCardDisplay(String imgPath, String colorName) {
        if (imgPath != null && !imgPath.isEmpty()) {
            product_image.setFill(new ImagePattern(loadImageSafe(imgPath)));
        }
        if (lbl_color != null) {
            String displayColor = (colorName == null || colorName.isEmpty()) ? "N/A" : colorName;
            lbl_color.setText(displayColor);
            System.out.println("Label updated to: " + displayColor);
        }
    }
    @FXML
    void onNextClick(ActionEvent event) {
        if (variantsList != null && variantsList.size() > 1) {
            currentIndex = (currentIndex + 1) % variantsList.size();
            Map<String, Object> data = variantsList.get(currentIndex);


            String nextImg = data.get("product_image") != null ? data.get("product_image").toString() : "";
            String nextColor = data.get("product_color") != null ? data.get("product_color").toString() : "";


            updateCardDisplay(nextImg, nextColor);


            if (data.get("product_id") != null) {
                this.productId = Integer.parseInt(data.get("product_id").toString());
            }
        }
    }
    @FXML
    public void edit(ActionEvent event) {
        if (mainController != null) {
            mainController.for_sapce_pane.setSpacing(12);
            mainController.variable_pane.setVisible(false);
            mainController.variable_pane.setManaged(false);
            mainController.add_update_pane.setVisible(true);
            mainController.add_update_pane.setManaged(true);
            mainController.update_btns.setVisible(true);
            mainController.add_btns.setVisible(false);
            mainController.color_box.setVisible(true);
            mainController.color_box.setManaged(true);
            mainController.toggleDetailsView(true);
            mainController.first_pane.setDisable(true);


            mainController.productId = this.productId;
            mainController.name_field.setText(lbl_name.getText());
            mainController.price_field.setText(lbl_price.getText().replaceAll("[^\\d.]", ""));
            mainController.color_field.setText(lbl_color.getText());
            mainController.type_combo.setValue(lbl_type.getText());


            List<Map<String, Object>> res = Dynamic_Method.select(
                    "SELECT p.product_image, d.accessories_text FROM products p " +
                            "JOIN accessories_detail d ON p.product_accessory_fact = d.accessories_detail_id " +
                            "WHERE p.product_id = " + this.productId
            );

            if (res != null && !res.isEmpty()) {
                String imgPath = res.get(0).get("product_image").toString();
                String detailText = res.get(0).get("accessories_text").toString();


                mainController.setSelectedImagePath(imgPath);

                mainController.detail_text_area.setText(detailText);


                mainController.product_image.setFill(product_image.getFill());
            }

            Animation.Popup(mainController.add_update_card, Duration.millis(400), () -> {});
        }
    }
    public void setData(int productId, String name, String type, String color, double price, String img) {
        this.productId = productId;
        lbl_name.setText(name);
        lbl_type.setText(type);
        lbl_color.setText(color);
        lbl_price.setText(String.format("%.2f", price));
        product_image.setFill(new ImagePattern(loadImageSafe(img)));
    }
    private Image loadImageSafe(String path) {
        try {
            if (path != null && !path.isBlank()) {
                java.io.File file = new java.io.File(path.replace("\"", ""));
                if (file.exists()) return new Image(file.toURI().toString());
                var res = getClass().getResource(path);
                if (res != null) return new Image(res.toExternalForm());
            }
        } catch (Exception e) {}
        return new Image(getClass().getResource("/image/photo.jpg").toExternalForm());
    }
}