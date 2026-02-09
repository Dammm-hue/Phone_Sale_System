package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;
import java.util.List;
import java.util.Map;

public class stock_general_controller {

    @FXML
    private Button btn_next,restock_btn;

    @FXML
    public HBox storage_box;

    @FXML
    public Circle select_circle;

    @FXML
    public VBox card_root,space_box;

    @FXML
    public Label lbl_color,lbl_stock,lbl_name,lbl_storage;

    @FXML
    public Rectangle product_image;

    public void setstock_controller1(newstockcontroller sh) {
        this.s = sh;
    }

    private newstockcontroller s;

    public int productId;

    private String imagePath;



    public String getProductColor() {
        return lbl_color.getText();
    }


    @FXML
    public void initialize() {

        btn_next.setVisible(false);
        btn_next.setOpacity(0.0);
        btn_next.setMouseTransparent(true);

        product_image.setOnMouseEntered(event -> {
            if (productVariants != null && productVariants.size() > 1) {
                btn_next.setVisible(true);
                btn_next.setMouseTransparent(false);

                javafx.animation.FadeTransition fadeIn = new javafx.animation.FadeTransition(Duration.millis(250), btn_next);
                fadeIn.setFromValue(btn_next.getOpacity());
                fadeIn.setToValue(1.0);
                fadeIn.play();
            }
        });



        StackPane parentStack = (StackPane) product_image.getParent();
        parentStack.setOnMouseExited(event -> {
            if (btn_next.isVisible()) {
                btn_next.setMouseTransparent(true);
                javafx.animation.FadeTransition fadeOut = new javafx.animation.FadeTransition(javafx.util.Duration.millis(250), btn_next);
                fadeOut.setFromValue(btn_next.getOpacity());
                fadeOut.setToValue(0.0);
                fadeOut.setOnFinished(e -> btn_next.setVisible(false));
                fadeOut.play();
            }
        });
    }

    public void setData(
            int productId,
            String productName,
            String color,
            int stock,
            int storage,
            String imagePath
    ) {
        this.productId = productId;
        this.imagePath = imagePath;

        lbl_name.setText(productName);
        lbl_color.setText(color);
        lbl_stock.setText(String.valueOf(stock));
        lbl_storage.setText(String.valueOf(storage));


        product_image.setFill(new ImagePattern(loadImageSafe(imagePath)));
        updateStockButtons(stock);
    }
    public String getImagePath() {
        return imagePath;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return lbl_name.getText();
    }

    public int getProductStock() {
        try {
            return Integer.parseInt(lbl_stock.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void updateStockButtons(int stock) {
        if (stock >= 10) {
            card_root.setStyle("-fx-background-color: linear-gradient(to bottom right, #D4FC79, #96E6A1); " +
                    "-fx-background-radius: 20; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(150, 230, 161, 0.4), 15, 0, 0, 8);");
        } else if (stock > 0 && stock < 10) {

            card_root.setStyle("-fx-background-color: linear-gradient(to bottom right, #FFD194, #FFB75E); " +
                    "-fx-background-radius: 20; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(255, 183, 94, 0.4), 15, 0, 0, 8);");
        } else {

            card_root.setStyle("-fx-background-color: linear-gradient(to bottom right, #FF9A9E, #FAD0C4); " +
                    "-fx-background-radius: 20; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(255, 154, 158, 0.4), 15, 0, 0, 8);");
        }
    }

    private Image loadImageSafe(String imagePath) {
        try {
            if (imagePath != null && !imagePath.isBlank()) {

                if (imagePath.startsWith("http") || imagePath.startsWith("file:")) {
                    return new Image(imagePath, true);
                }


                File file = new File(imagePath);
                if (file.exists()) {
                    return new Image(file.toURI().toString());
                }
                var url = getClass().getResource(imagePath);
                if (url != null) return new Image(url.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("Image Loading Error: " + e.getMessage());
        }
        return new Image(getClass().getResource("/image/photo.jpg").toExternalForm());
    }

    @FXML
    void restock_click(ActionEvent event) {
        if (s == null) return;

        s.setSelectedCard(this);
        s.border_pane.setDisable(true);


        s.add_hbox.setVisible(true);
        s.add_hbox.setManaged(true);
        s.detail_pane.setVisible(true);
        s.detail_pane.setManaged(true);


        s.imei_card.setVisible(false);
        s.imei_card.setManaged(false);


        s.txt_stock.clear();
        s.product_image.setFill(product_image.getFill());
        s.lbl_name.setText(lbl_name.getText());
        s.txt_color.setText(lbl_color.getText());
        s.txt_color.setEditable(false);
        s.past_stock_lbl.setText(lbl_stock.getText() + "+");


        if(storage_box.isVisible()){
            s.storage_box.setManaged(true);
            s.storage_box.setVisible(true);
            s.txt_storage.setText(lbl_storage.getText());
            s.txt_storage.setEditable(false);
            s.restock_btn.setVisible(false);
            s.restock_btn.setManaged(false);
            s.addimei_lbl.setManaged(true);
            s.addimei_lbl.setVisible(true);

        } else {
            s.storage_box.setVisible(false);
            s.storage_box.setManaged(false);
            s.restock_btn.setVisible(true);
            s.restock_btn.setManaged(true);
            s.addimei_lbl.setManaged(false);
            s.addimei_lbl.setVisible(false);
        }
        updateMainDetailStyle(Integer.parseInt(lbl_stock.getText()));

    }

    private void updateMainDetailStyle(int stock) {
        String style;
        if (stock >= 10) {
            style = "-fx-background-color: linear-gradient(to bottom right, #D4FC79, #96E6A1); -fx-background-radius: 20;";
        } else if (stock > 0 && stock < 10) {
            style = "-fx-background-color: linear-gradient(to bottom right, #FFD194, #FFB75E); -fx-background-radius: 20;";
        } else {
            style = "-fx-background-color: linear-gradient(to bottom right, #FF9A9E, #FAD0C4); -fx-background-radius: 20;";
        }
        s.card_root.setStyle(style);
    }


    @FXML
    void onCardClick(MouseEvent event) {
        if (s != null) {
            s.displayProductDetails(this);
            s.detail_pane.setManaged(false);
            s.detail_pane.setVisible(false);
        }
    }

    private List<Map<String, Object>> productVariants;
    private int currentIndex = 0;


    public void setVariants(List<Map<String, Object>> variants) {

        this.productVariants = variants;

        if (!productVariants.isEmpty()) {
            currentIndex = 0;
            displayVariant(currentIndex);
        }
    }


    private void displayVariant(int index) {
        Map<String, Object> data = productVariants.get(index);


        this.productId = (data.get("product_id") != null) ? ((Number) data.get("product_id")).intValue() : 0;
        lbl_name.setText(data.get("product_model") != null ? data.get("product_model").toString() : "Unknown");
        lbl_color.setText(data.get("product_color") != null ? data.get("product_color").toString() : "-");


        int typeId = (data.get("product_type_id") != null) ? ((Number) data.get("product_type_id")).intValue() : 1;

        if (typeId == 2) {

            lbl_storage.setVisible(false);
            lbl_storage.setManaged(false);
        } else {
            lbl_storage.setVisible(true);
            lbl_storage.setManaged(true);
            int storage_ram = (data.get("product_ram") != null) ? ((Number) data.get("product_ram")).intValue() : 0;
            int storage_rom = (data.get("product_rom") != null) ? ((Number) data.get("product_rom")).intValue() : 0;
            lbl_storage.setText(storage_ram + "GB / " + storage_rom + "GB");
        }


        Object stockObj = data.get("available_stock");
        int stock = (stockObj != null) ? ((Number) stockObj).intValue() : 0;
        lbl_stock.setText(String.valueOf(stock));


        if (data.get("product_image") != null) {
            product_image.setFill(new ImagePattern(loadImageSafe(data.get("product_image").toString())));
        }

        updateStockButtons(stock);
    }

    @FXML
    void onNextClick(ActionEvent event) {
        if (productVariants != null && productVariants.size() > 1) {
            currentIndex = (currentIndex + 1) % productVariants.size();
            displayVariant(currentIndex);
        }
    }
}
