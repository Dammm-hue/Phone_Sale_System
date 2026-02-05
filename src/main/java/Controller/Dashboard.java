package Controller;
import Utility.Animation;
import Utility.SmoothLineChart;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import Database.Dynamic_Method;

import java.io.ByteArrayInputStream;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Dashboard {


    @FXML
    private Button total_sale_icon;

    @FXML
    private Button total_refund_icon;

    @FXML
    private Button available_stock_icon;

    @FXML
    private Button total_user_icon;

    @FXML
    private VBox fourPane, twoPane;

    @FXML
    private StackPane viewport;

    @FXML
    private Circle dot1, dot2;

    @FXML
    private Label total_user_label;

    @FXML
    private Label total_accessories_label;

    @FXML
    private Label total_available_stock_label;

    @FXML
    private Label total_phone_label;

    @FXML
    private Label total_refund_label;

    @FXML
    private Button accessories_card_icon;

    @FXML
    private Button phone_card_icon;

    @FXML
    private Label total_sale_label;

    @FXML
    private StackPane f1, f2, f3, f4, t1, t2;

    private enum ViewState {FOUR, TWO}

    private ViewState currentView = ViewState.FOUR;


    //line chart
    private boolean showPhonePie = true;

    @FXML
    private SmoothLineChart salesLineChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private ComboBox<String> periodComboBox;



    //bar chart
    private boolean showPhone = true;

    @FXML
    private Label accstocklabel;
    @FXML
    private Label phstocklabel;

    @FXML
    private StackPane legendPane;
    @FXML
    private Label legendLabel;

    @FXML
    private BarChart<String, Number> phoneChart;
    @FXML
    private BarChart<String, Number> accessoryChart;

    private final String[] colors = {"#E53935", "#FBC02D", "#1E88E5", "#43A047"};
    private final String[] texts = {"Low Stock", "Warning Stock", "Normal Stock", "Enough Stock"};
    private int legendIndex = 0;


    //pie chart
    @FXML
    private PieChart accpiechart;
    @FXML
    private PieChart phonepiechart;
    @FXML
    private Label phoneacclabel2;
    @FXML
    private Label phoneacclabel3;
    @FXML
    private Label phoneandacclabel;
    @FXML
    private Label phoneandacclabel1;

    @FXML
    private HBox circle_box;

    private List<Label> phonePieLabels;

    private List<Label> accPieLabels;


//top sale
    @FXML
    private Button next_pane;

    @FXML
    private Button best_sale_icon1;

    @FXML
    private Button best_sale_icon2;

    @FXML
    private Button best_sale_icon3;

    @FXML
    private Circle best_sale_photo1;

    @FXML
    private Circle best_sale_photo2;

    @FXML
    private Circle best_sale_photo3;

    @FXML
    private Label the_best_sale_name1;

    @FXML
    private Label the_best_sale_name2;

    @FXML
    private Label the_best_sale_name3;

    @FXML
    private Label the_best_sale_qty1;

    @FXML
    private Label the_best_sale_qty2;

    @FXML
    private Label the_best_sale_qty3;

    @FXML
    private VBox top_1;

    @FXML
    private VBox top_2;

    @FXML
    private VBox top_3;

    @FXML
    private Label best_sale_title;

    @FXML
    private Button pre_pane;


    @FXML
    public void initialize() {
         loadDashboardTotals();
        fourPane.setVisible(true);
        fourPane.setManaged(true);
        fourPane.setTranslateX(0);
        twoPane.setVisible(false);
        twoPane.setManaged(false);
        t2.setVisible(false);
        viewport.widthProperty().addListener((obs, oldV, newV) -> resizePanes());
        viewport.heightProperty().addListener((obs, oldV, newV) -> resizePanes());

        total_user_icon.setStyle("-fx-background-color: transparent");
        FontIcon totaL_user_icon1=new FontIcon(FontAwesomeSolid.USER);
        total_user_icon.setGraphic(totaL_user_icon1);
        totaL_user_icon1.setIconColor(Color.web("white"));
        totaL_user_icon1.setIconSize(15);

        available_stock_icon.setStyle("-fx-background-color: transparent");
        FontIcon totaL_stock_icon1=new FontIcon(FontAwesomeSolid.BOXES);
        available_stock_icon.setGraphic(totaL_stock_icon1);
        totaL_stock_icon1.setIconColor(Color.web("white"));
        totaL_stock_icon1.setIconSize(15);

        total_sale_icon.setStyle("-fx-background-color: transparent");
        FontIcon totaL_sale_icon1=new FontIcon(FontAwesomeSolid.SHOPPING_BASKET);
       total_sale_icon.setGraphic(totaL_sale_icon1);
        totaL_sale_icon1.setIconColor(Color.web("white"));
        totaL_sale_icon1.setIconSize(20);

        total_refund_icon.setStyle("-fx-background-color: transparent");
        FontIcon totaL_refund_icon1=new FontIcon(FontAwesomeSolid.UNDO);
        total_refund_icon.setGraphic(totaL_refund_icon1);
        totaL_refund_icon1.setIconColor(Color.web("white"));
        totaL_refund_icon1.setIconSize(20);

        phone_card_icon.setStyle("-fx-background-color: transparent");
        FontIcon phone_card_icon1=new FontIcon(FontAwesomeSolid.PHONE);
        phone_card_icon.setGraphic(phone_card_icon1);
        phone_card_icon1.setIconColor(Color.web("white"));
        phone_card_icon1.setIconSize(20);

        accessories_card_icon.setStyle("-fx-background-color: transparent");
        FontIcon accessories_card_icon1=new FontIcon(FontAwesomeSolid.HEADPHONES);
        accessories_card_icon.setGraphic(accessories_card_icon1);
        accessories_card_icon1.setIconColor(Color.web("white"));
        accessories_card_icon1.setIconSize(20);


//        bar chart
        phoneChart.setLegendVisible(false);
        accessoryChart.setLegendVisible(false);

        loadPhoneStock();
        loadAccessoryStock();
        startAutoSwitch();
        updateLegend();
        startLegendTimer();

        CategoryAxis xAxisPhone = (CategoryAxis) phoneChart.getXAxis();
        xAxisPhone.setTickLabelsVisible(true);
        xAxisPhone.setTickLabelRotation(0);


//line chart
        periodComboBox.setItems(FXCollections.observableArrayList("7 Days", "30 Days", "1 Year"));
        periodComboBox.getSelectionModel().selectFirst();

        salesLineChart.setLegendVisible(false);
        salesLineChart.setCreateSymbols(true);
        salesLineChart.setAnimated(false);

        periodComboBox.setOnAction(e -> updateChart());

        Platform.runLater(this::updateChart);


//pie chart
        phonePieLabels = List.of(phoneandacclabel, phoneandacclabel1, phoneacclabel2, phoneacclabel3);
        accPieLabels = List.of(phoneandacclabel, phoneandacclabel1, phoneacclabel2, phoneacclabel3);

        loadPhoneSalesPieChart();
        loadAccSalesPieChart();

        phonepiechart.setLegendVisible(false);
        phonepiechart.setLabelsVisible(false);
        phonepiechart.setVisible(true);
        phonepiechart.setManaged(true);



        accpiechart.setLegendVisible(false);
        accpiechart.setLabelsVisible(false);
        accpiechart.setVisible(false);
        accpiechart.setManaged(false);

        updatePieLabels(phonepiechart, phonePieLabels);
        startPieSwitch();


//top sale
        best_sale_icon1.setStyle("-fx-background-color: transparent");
        FontIcon icon = new FontIcon(FontAwesomeSolid.CROWN);
        icon.setIconColor(Color.web("#ffd700"));
        best_sale_icon1.setGraphic(icon);
        icon.setIconSize(20);

        best_sale_icon2.setStyle("-fx-background-color: transparent");
        FontIcon icon1 = new FontIcon(FontAwesomeSolid.CROWN);
        icon1.setIconColor(Color.web("#c0c0c0"));
        best_sale_icon2.setGraphic(icon1);
        icon1.setIconSize(20);

        best_sale_icon3.setStyle("-fx-background-color: transparent");
        FontIcon icon2 = new FontIcon(FontAwesomeSolid.CROWN);
        icon2.setIconColor(Color.web("#cd7f32"));
        best_sale_icon3.setGraphic(icon2);
        icon2.setIconSize(20);

        next_pane.setStyle("-fx-background-color: #3D3D3D; -fx-border-radius: 25px");
         FontIcon nexticon=new FontIcon(FontAwesomeSolid.GREATER_THAN);
        next_pane.setGraphic(nexticon);
        nexticon.setIconColor(Color.web("white"));
        nexticon.setIconSize(10);

        pre_pane.setStyle("-fx-background-color: #3D3D3D; -fx-border-radius: 25px");
        FontIcon prepane=new FontIcon(FontAwesomeSolid.LESS_THAN);
        pre_pane.setGraphic(prepane);
        prepane.setIconColor(Color.web("white"));
        prepane.setIconSize(10);

        best_sale_title.setText("Top Best Sale");
        loadTopSale("All");


        current = 1;
        next_check = 2;
        pre_check = 4;


    }
    private void loadDashboardTotals() {

        try {
            List<Map<String, Object>> users = Dynamic_Method.select(
                    "users",
                    List.of("COUNT(*) AS total"),
                    null,
                    null,
                    null,
                    null
            );
            if (!users.isEmpty()) {
                total_user_label.setText(users.get(0).get("total").toString());
            }


            List<Map<String, Object>> phones = Dynamic_Method.select(
                    "products",
                    List.of("COUNT(*) AS total"),
                    "product_type_id",
                    1,
                    null,
                    null
            );
            if (!phones.isEmpty()) {
                total_phone_label.setText(phones.get(0).get("total").toString());
            }

            // Total Accessories
            List<Map<String, Object>> accessories = Dynamic_Method.select(
                    "products",
                    List.of("COUNT(*) AS total"),
                    "product_type_id",
                    2,
                    null,
                    null
            );
            if (!accessories.isEmpty()) {
                total_accessories_label.setText(accessories.get(0).get("total").toString());
            }
            List<Map<String, Object>> stock = Dynamic_Method.select(
                    "product_stock",
                    List.of("IFNULL(SUM(available_stock),0) AS total"),
                    null,
                    null,
                    null,
                    null
            );
            if (!stock.isEmpty()) {
                total_available_stock_label.setText(stock.get(0).get("total").toString());
            }

            // Total Sales
            List<Map<String, Object>> sales = Dynamic_Method.select(
                    "product_sale",
                    List.of("COUNT(*) AS total"),
                    null,
                    null,
                    null,
                    null
            );
            if (!sales.isEmpty()) {
                total_sale_label.setText(sales.get(0).get("total").toString());
            }

            // Total Refund
            List<Map<String, Object>> refunds = Dynamic_Method.select(
                    "product_refund",
                    List.of("COUNT(*) AS total"),
                    null,
                    null,
                    null,
                    null
            );
            if (!refunds.isEmpty()) {
                total_refund_label.setText(refunds.get(0).get("total").toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void resizePanes() {
        double containerWidth = viewport.getWidth();
        double containerHeight = viewport.getHeight();
        double fourCardWidth = Math.max(150, (containerWidth - 10) / 2);
        double fourCardHeight = Math.max(125, (containerHeight * 0.4 - 10) / 2);
        for (Pane p : new Pane[]{f1, f2, f3, f4}) {
            p.setPrefWidth(fourCardWidth);
            p.setPrefHeight(fourCardHeight);
        }

        double twoCardWidth = Math.max(300, containerWidth);
        double twoCardHeight = Math.max(125, (containerHeight * 0.4 - 10) / 2);
        for (Pane p : new Pane[]{t1, t2}) {
            p.setPrefWidth(twoCardWidth);
            p.setPrefHeight(twoCardHeight);
        }
    }
   @FXML
    void showFirst(MouseEvent event) {
        if (currentView == ViewState.FOUR) return;
        t2.setMouseTransparent(true);
        t1.setMouseTransparent(true);
        Animation.Popup_Reverse(t2, Duration.millis(300), () -> {
            Animation.Popup_Reverse(t1, Duration.millis(300), () -> {
                twoPane.setVisible(false);
                twoPane.setManaged(false);
                twoPane.setMouseTransparent(true);

                fourPane.setManaged(true);
                fourPane.setVisible(true);
                fourPane.setMouseTransparent(false);
                fourPane.setTranslateX(0);

                Animation.Popup(f3, Duration.millis(300), () -> {
                    Animation.Popup(f4, Duration.millis(300), () -> {
                        Animation.Popup(f2, Duration.millis(300), () -> {
                            Animation.Popup(f1, Duration.millis(300), () -> {
                                currentView = ViewState.FOUR;
                                updateDots();
                            });
                        });
                    });
                });
            });
        });
    }

    @FXML
    void showSecond(MouseEvent event) {
        if (currentView == ViewState.TWO) return;
        f1.setMouseTransparent(true);
        f2.setMouseTransparent(true);
        f3.setMouseTransparent(true);
        f4.setMouseTransparent(true);

        Animation.Popup_Reverse(f1, Duration.millis(300), () -> {
            Animation.Popup_Reverse(f2, Duration.millis(300), () -> {
                Animation.Popup_Reverse(f4, Duration.millis(300), () -> {
                    Animation.Popup_Reverse(f3, Duration.millis(300), () -> {
                        fourPane.setVisible(false);
                        fourPane.setManaged(false);
                        fourPane.setMouseTransparent(true);

                        twoPane.setVisible(true);
                        twoPane.setManaged(true);
                        twoPane.setMouseTransparent(false);
                        twoPane.setTranslateX(0);

                        Animation.Popup(t1, Duration.millis(300), () -> {
                            Animation.Popup(t2, Duration.millis(300), () -> {
                                currentView = ViewState.TWO;
                                updateDots();
                            });
                        });
                    });
                });
            });
        });
    }
    private void updateDots() {
        dot1.setStyle(currentView == ViewState.FOUR ? "-fx-fill:#666666;" : "-fx-fill:#cccccc;");
        dot2.setStyle(currentView == ViewState.TWO ? "-fx-fill:#666666;" : "-fx-fill:#cccccc;");
    }



    //  Line Chart
    private void updateChart() {

        salesLineChart.getData().clear();

        String period = periodComboBox.getValue();
        int daysLimit = period.equals("7 Days") ? 7 : (period.equals("30 Days") ? 30 : 365);
        boolean isYearly = "1 Year".equals(period);

        Map<String, Double> chartMap = new LinkedHashMap<>();
        DateTimeFormatter formatter = isYearly ?
                DateTimeFormatter.ofPattern("MMM") : DateTimeFormatter.ofPattern("MM-dd");

        int loopCount = isYearly ? 12 : daysLimit;
        for (int i = loopCount - 1; i >= 0; i--) {
            String key = isYearly ?
                    LocalDate.now().minusMonths(i).format(formatter) :
                    LocalDate.now().minusDays(i).format(formatter);
            chartMap.put(key, 0.0);
        }
        String dateFormat = isYearly ? "%b" : "%m-%d";
        String sql = "SELECT DATE_FORMAT(created_at, '" + dateFormat + "') as label, " +
                "SUM(final_amount) as total " +
                "FROM product_sale " +
                "WHERE created_at >= DATE_SUB(NOW(), INTERVAL " + daysLimit + " DAY) " +
                "GROUP BY label " +
                "ORDER BY MIN(created_at) ASC";

        try {
            List<Map<String, Object>> results = Dynamic_Method.select(sql);

            for (Map<String, Object> row : results) {
                String label = row.get("label").toString();
                double total = Double.parseDouble(row.get("total").toString());

                if (chartMap.containsKey(label)) {
                    chartMap.put(label, total);
                }
            }

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            chartMap.forEach((dateLabel, salesAmount) -> {
                series.getData().add(new XYChart.Data<>(dateLabel, salesAmount));
            });

            salesLineChart.getData().add(series);

            Platform.runLater(() -> {
                Node line = series.getNode().lookup(".chart-series-line");
                if (line != null) {

                    double lastValue = series.getData().get(series.getData().size() - 1).getYValue().doubleValue();

                    if (lastValue < 0) {
                        line.setStyle("-fx-stroke: #ff4d4d;");
                    } else {
                        line.setStyle("-fx-stroke: #2ecc71;");
                    }
                }
            });

        } catch (Exception e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        yAxis.setAutoRanging(true);
//        yAxis.setLowerBound(-1000);
//        yAxis.setUpperBound(5000);
//        yAxis.setTickUnit(500);
    }



    //Bar Chart
    private void loadPhoneStock() {
        try {

            String sql = "SELECT pb.product_brand_name AS brand, SUM(ps.available_stock) AS stock " +
                    "FROM product_stock ps " +
                    "JOIN products p ON ps.product_id = p.product_id " +
                    "JOIN product_brand pb ON p.product_brand_id = pb.product_brand_id " +
                    "JOIN product_type pt ON p.product_type_id = pt.product_type_id " +
                    "WHERE pt.product_type_id = 1 " +
                    "GROUP BY pb.product_brand_name " +
                    "ORDER BY pb.product_brand_name";


            List<Map<String, Object>> list = Dynamic_Method.select(sql);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Phone Stock");


            for (Map<String, Object> row : list) {
                String brand = row.get("brand").toString();
                int stock = ((Number) row.get("stock")).intValue();
                System.out.println("Brand: " + brand + " Stock: " + stock);
                series.getData().add(new XYChart.Data<>(brand, stock));
                addData(series, brand, stock);
            }


            Platform.runLater(() -> {
                phoneChart.getData().clear();
                phoneChart.getData().add(series);

                // 🔹 ဤနေရာတွင် ထည့်ပါ - Label များကို အတင်းပြခိုင်းခြင်း
                CategoryAxis xAxis = (CategoryAxis) phoneChart.getXAxis();
                xAxis.setTickLabelsVisible(true); // အမြဲပြရန်
                xAxis.setGapStartAndEnd(true);
                phoneChart.setAnimated(false); // animation ကြောင့် label ပျောက်ခြင်းကို ကာကွယ်ရန်
            });

            phoneChart.setCategoryGap(40);
            phoneChart.setBarGap(30);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void loadAccessoryStock() {
        try {

            String sql = "SELECT p.product_model, ps.available_stock " +
                    "FROM products p " +
                    "JOIN product_stock ps ON p.product_id = ps.product_id " +
                    "WHERE p.product_type_id = 2";

            List<Map<String, Object>> list = Dynamic_Method.select(sql);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Accessory Stock");

            for (Map<String, Object> row : list) {
                String model = row.get("product_model").toString();
                int stock = ((Number) row.get("available_stock")).intValue();
                String category;
                model = model.toLowerCase();
                if (model.contains("air")) category = "AirPods";
                else if (model.contains("char")) category = "Charging";
                else if (model.contains("power")) category = "PowerBank";
                else if (model.contains("head") || model.contains("ear")) category = "Headphone";
                else category = "Other";

               XYChart.Data<String, Number> data = new XYChart.Data<>(category, stock);
                series.getData().add(data);
                addData(series,category,stock);
            }

            Platform.runLater(() -> {
                accessoryChart.getData().clear();
                accessoryChart.getData().add(series);
            });

            accessoryChart.setCategoryGap(30);
            accessoryChart.setBarGap(30);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private void addData(XYChart.Series<String, Number> series, String brand, int stock) {
        XYChart.Data<String, Number> data = new XYChart.Data<>(brand, stock);

        data.nodeProperty().addListener((obs, oldNode, newNode) -> {
            if (newNode != null) {
                // Logic for bar colors
                if (stock < 50) newNode.setStyle("-fx-bar-fill: #E53935;");
                else if (stock < 70) newNode.setStyle("-fx-bar-fill: #FBC02D;");
                else if (stock < 150) newNode.setStyle("-fx-bar-fill: #1E88E5;");
                else newNode.setStyle("-fx-bar-fill: #43A047;");
            }
        });

        series.getData().add(data);
    }
    private void startLegendTimer() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), e -> updateLegend())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateLegend() {
        legendPane.setStyle("-fx-background-color:" + colors[legendIndex] + "; -fx-background-radius:6;");
        legendLabel.setText(texts[legendIndex]);
        legendIndex = (legendIndex + 1) % colors.length;
    }

    private void startAutoSwitch() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(4), e -> switchChart())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void switchChart() {
        showPhone = !showPhone;
        phoneChart.setVisible(showPhone);
        phoneChart.setManaged(showPhone);
        phstocklabel.setVisible(showPhone);

        accessoryChart.setVisible(!showPhone);
        accessoryChart.setManaged(!showPhone);
        accstocklabel.setVisible(!showPhone);
    }

    //Pie Chart
    private void loadPhoneSalesPieChart() {
        try {
            String sql = "SELECT pb.product_brand_name AS brand, SUM(psi.product_qty) AS total_qty " +
                    "FROM product_sale_item psi " +
                    "JOIN products p ON psi.product_id = p.product_id " +
                    "JOIN product_brand pb ON p.product_brand_id = pb.product_brand_id " +
                    "WHERE p.product_type_id = 1 " +
                    "AND MONTH(psi.created_at) = MONTH(CURRENT_DATE()) " +
                    "AND YEAR(psi.created_at) = YEAR(CURRENT_DATE()) " +
                    "GROUP BY pb.product_brand_name";

            List<Map<String, Object>> list = Dynamic_Method.select(sql);

            ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

            for (Map<String, Object> row : list) {
                String brand = row.get("brand").toString();
                double total = row.get("total_qty") instanceof Number
                        ? ((Number) row.get("total_qty")).doubleValue()
                        : Double.parseDouble(row.get("total_qty").toString());
                data.add(new PieChart.Data(brand, total));
            }

            Platform.runLater(() -> {
                phonepiechart.setData(data);
                updatePieLabels(phonepiechart, phonePieLabels); // 🔹 Move label update here
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Accessories PieChart
    private void loadAccSalesPieChart() {
        try {
            String sql = "SELECT p.product_model, SUM(psi.product_qty) AS total_qty " +
                    "FROM product_sale_item psi " +
                    "JOIN products p ON psi.product_id = p.product_id " +
                    "WHERE p.product_type_id = 2 " +
                    "GROUP BY p.product_model";

            List<Map<String, Object>> list = Dynamic_Method.select(sql);

            // Initialize category totals
            double airpodTotal = 0;
            double chargingTotal = 0;
            double powerbankTotal = 0;
            double headphoneTotal = 0;

            // Map product model to category
            for (Map<String, Object> row : list) {
                String model = row.get("product_model").toString().toLowerCase();
                double total = row.get("total_qty") instanceof Number
                        ? ((Number) row.get("total_qty")).doubleValue()
                        : Double.parseDouble(row.get("total_qty").toString());

                if (model.contains("air")) airpodTotal += total;
                else if (model.contains("char")) chargingTotal += total;
                else if (model.contains("power")) powerbankTotal += total;
                else if (model.contains("head") || model.contains("ear")) headphoneTotal += total;
            }

            // Prepare PieChart data
            ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                    new PieChart.Data("AirPod", airpodTotal),
                    new PieChart.Data("Charging", chargingTotal),
                    new PieChart.Data("PowerBank", powerbankTotal),
                    new PieChart.Data("Headphone", headphoneTotal)
            );

            Platform.runLater(() -> {
                accpiechart.setData(data);
                accpiechart.setVisible(true);

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // PieChart labels
    private void updatePieLabels(PieChart chart, List<Label> labels) {
        double total = chart.getData().stream()
                .mapToDouble(PieChart.Data::getPieValue)
                .sum();

        for (int i = 0; i < labels.size(); i++) {
            if (i < chart.getData().size()) {
                PieChart.Data d = chart.getData().get(i);
                double percent = total == 0 ? 0 : (d.getPieValue() / total) * 100;
                labels.get(i).setText(String.format("%s : %.1f%%", d.getName(), percent));
                labels.get(i).setVisible(true);
            } else {
                labels.get(i).setText("");
                labels.get(i).setVisible(false);
            }
        }
    }




    // PieChart switch animation
    private void startPieSwitch() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(8), e -> {
                    if (showPhonePie) {
                        switchPie(phonepiechart, accpiechart);
                        updatePieLabels(accpiechart, accPieLabels);
                        Circle bluecircle=new Circle();
                        bluecircle.setFill(Color.web("#1E90FF"));
                        bluecircle.setStroke(Color.BLACK);
                        bluecircle.setRadius(10);
                        circle_box.getChildren().clear();
                        circle_box.getChildren().addAll(bluecircle,phoneacclabel3);



                    } else {

                        switchPie(accpiechart, phonepiechart);
                        updatePieLabels(phonepiechart, phonePieLabels);
                        circle_box.getChildren().clear();

                    }
                    showPhonePie = !showPhonePie;
                })
        );
        timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
        timeline.play();
    }

    private void switchPie(PieChart hide, PieChart show) {

        RotateTransition rotateOut = new RotateTransition(Duration.seconds(2), hide);
        rotateOut.setFromAngle(0);
        rotateOut.setToAngle(120);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), hide);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        ParallelTransition out = new ParallelTransition(rotateOut, fadeOut);

        out.setOnFinished(e -> {
            hide.setVisible(false);
            hide.setManaged(false);

            show.setRotate(-120);
            show.setOpacity(0);
            show.setVisible(true);
            show.setManaged(true);

            RotateTransition rotateIn = new RotateTransition(Duration.seconds(2), show);
            rotateIn.setFromAngle(-120);
            rotateIn.setToAngle(0);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), show);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            new ParallelTransition(rotateIn, fadeIn).play();
        });

        out.play();
    }


    //    Top sale
    int next_check = 2;
    int current = 1;
    int pre_check = 4;

    @FXML
    void next_pane_click(ActionEvent event) {

        if (next_check == 1) {
            current++;
            Animation.Slide_left_to_right(top_1, Duration.millis(300), 500, () -> {
                Animation.Slide_left_to_right(top_2, Duration.millis(300), 500, () -> {
                    Animation.Slide_left_to_right(top_3, Duration.millis(300), 500, () -> {
                        best_sale_title.setText("Top Best Sale");
                        loadTopSale("All");
                        //top_3 data
                        best_sale_icon3.setStyle("-fx-background-color: transparent");
                        FontIcon icon2 = new FontIcon(FontAwesomeSolid.CROWN);
                        icon2.setIconColor(Color.web("#cd7f32"));
                        best_sale_icon3.setGraphic(icon2);
                        icon2.setIconSize(20);
                        Animation.Slide_right_to_left(top_3, Duration.millis(300), 500, () -> {
                            //top_2 data
                            best_sale_icon2.setStyle("-fx-background-color: transparent");
                            FontIcon icon1 = new FontIcon(FontAwesomeSolid.CROWN);
                            icon1.setIconColor(Color.web("#c0c0c0"));
                            best_sale_icon2.setGraphic(icon1);
                            icon1.setIconSize(20);
                            Animation.Slide_right_to_left(top_2, Duration.millis(300), 500, () -> {
                                //top_1 data
                                best_sale_icon1.setStyle("-fx-background-color: transparent");
                                FontIcon icon = new FontIcon(FontAwesomeSolid.CROWN);
                                icon.setIconColor(Color.web("#ffd700"));
                                best_sale_icon1.setGraphic(icon);
                                icon.setIconSize(20);
                                Animation.Slide_right_to_left(top_1, Duration.millis(300), 500, ()->{

                                });
                            });
                        });
                    });
                });
            });
        } else if (next_check == 2) {
            current++;
            Animation.Slide_left_to_right(top_1, Duration.millis(300), 500, () -> {
                Animation.Slide_left_to_right(top_2, Duration.millis(300), 500, () -> {
                    Animation.Slide_left_to_right(top_3, Duration.millis(300), 500, () -> {
                        best_sale_title.setText("I-Phone Best Sale");
                        loadTopSale("Iphone");
                        //top_3 data
                        best_sale_icon3.setStyle("-fx-background-color: transparent");
                        FontIcon icon2 = new FontIcon(FontAwesomeSolid.MEDAL);
                        icon2.setIconColor(Color.web("#cd7f32"));
                        best_sale_icon3.setGraphic(icon2);
                        icon2.setIconSize(20);
                        Animation.Slide_right_to_left(top_1, Duration.millis(300), 500, () -> {
                            //top_2 data
                            best_sale_icon2.setStyle("-fx-background-color: transparent");
                            FontIcon icon1 = new FontIcon(FontAwesomeSolid.MEDAL);
                            icon1.setIconColor(Color.web("#c0c0c0"));
                            best_sale_icon2.setGraphic(icon1);
                            icon1.setIconSize(20);
                            Animation.Slide_right_to_left(top_2, Duration.millis(300), 500, () -> {
                                //top_1 data
                                best_sale_icon1.setStyle("-fx-background-color: transparent");
                                FontIcon icon = new FontIcon(FontAwesomeSolid.MEDAL);
                                icon.setIconColor(Color.web("#ffd700"));
                                best_sale_icon1.setGraphic(icon);
                                icon.setIconSize(20);
                                Animation.Slide_right_to_left(top_3, Duration.millis(300), 500, ()->{

                                });
                            });
                        });
                    });
                });
            });
        } else if (next_check == 3) {
            current++;
            Animation.Slide_left_to_right(top_1, Duration.millis(300), 500, () -> {
                Animation.Slide_left_to_right(top_2, Duration.millis(300), 500, () -> {
                    Animation.Slide_left_to_right(top_3, Duration.millis(300), 500, () -> {
                        best_sale_title.setText("Samsung Best Sale");
                        loadTopSale("Samsung");
                        //top_3 data
                        best_sale_icon3.setStyle("-fx-background-color: transparent");
                        FontIcon icon2 = new FontIcon(FontAwesomeSolid.MEDAL);
                        icon2.setIconColor(Color.web("#cd7f32"));
                        best_sale_icon3.setGraphic(icon2);
                        icon2.setIconSize(20);
                        Animation.Slide_right_to_left(top_3, Duration.millis(300), 500, () -> {
                            //top_2 data
                            best_sale_icon2.setStyle("-fx-background-color: transparent");
                            FontIcon icon1 = new FontIcon(FontAwesomeSolid.MEDAL);
                            icon1.setIconColor(Color.web("#c0c0c0"));
                            best_sale_icon2.setGraphic(icon1);
                            icon1.setIconSize(20);
                            Animation.Slide_right_to_left(top_2, Duration.millis(300), 500, () -> {
                                //top_1 data
                                best_sale_icon1.setStyle("-fx-background-color: transparent");
                                FontIcon icon = new FontIcon(FontAwesomeSolid.MEDAL);
                                icon.setIconColor(Color.web("#ffd700"));
                                best_sale_icon1.setGraphic(icon);
                                icon.setIconSize(20);
                                Animation.Slide_right_to_left(top_1, Duration.millis(300), 500, ()->{

                                });
                            });
                        });
                    });
                });
            });
        } else if (next_check == 4) {
            current++;
            Animation.Slide_left_to_right(top_1, Duration.millis(300), 500, () -> {
                Animation.Slide_left_to_right(top_2, Duration.millis(300), 500, () -> {
                    Animation.Slide_left_to_right(top_3, Duration.millis(300), 500, () -> {
                        best_sale_title.setText("Redmi Best Sale");
                        loadTopSale("Redmi");
                        //top_3 data
                        best_sale_icon3.setStyle("-fx-background-color: transparent");
                        FontIcon icon2 = new FontIcon(FontAwesomeSolid.MEDAL);
                        icon2.setIconColor(Color.web("#cd7f32"));
                        best_sale_icon3.setGraphic(icon2);
                        icon2.setIconSize(20);
                        Animation.Slide_right_to_left(top_3, Duration.millis(300), 500, () -> {
                            //top_2 data
                            best_sale_icon2.setStyle("-fx-background-color: transparent");
                            FontIcon icon1 = new FontIcon(FontAwesomeSolid.MEDAL);
                            icon1.setIconColor(Color.web("#c0c0c0"));
                            best_sale_icon2.setGraphic(icon1);
                            icon1.setIconSize(20);
                            Animation.Slide_right_to_left(top_2, Duration.millis(300), 500, () -> {
                                //top_1 data
                                best_sale_icon1.setStyle("-fx-background-color: transparent");
                                FontIcon icon = new FontIcon(FontAwesomeSolid.MEDAL);
                                icon.setIconColor(Color.web("#ffd700"));
                                best_sale_icon1.setGraphic(icon);
                                icon.setIconSize(20);
                                Animation.Slide_right_to_left(top_1, Duration.millis(300), 500, ()->{

                                });
                            });
                        });
                    });
                });
            });
        }
        if(current <= 4){
            next_check = current+1;
            if (next_check == 5){
                next_check = 1;
            }
            pre_check = current-1;
        }
        else {
            current = 1;
            next_check = 2;
            pre_check = 4;
        }

        System.out.println("current"+current);
        System.out.println("next"+next_check);
        System.out.println("pre"+pre_check);
    }

    @FXML
    void pre_pane_click(ActionEvent event) {

        if (pre_check == 1) {
            current--;
            Animation.Slide_left_to_right(top_1, Duration.millis(300), 500, () -> {
                Animation.Slide_left_to_right(top_2, Duration.millis(300), 500, () -> {
                    Animation.Slide_left_to_right(top_3, Duration.millis(300), 500, () -> {
                        best_sale_title.setText("Top Best Sale");
                        loadTopSale("All");
                        //top_3 data

                        best_sale_icon3.setStyle("-fx-background-color: transparent");
                        FontIcon icon2 = new FontIcon(FontAwesomeSolid.CROWN);
                        icon2.setIconColor(Color.web("#cd7f32"));
                        best_sale_icon3.setGraphic(icon2);
                        icon2.setIconSize(20);

                        Animation.Slide_right_to_left(top_3, Duration.millis(300), 500, () -> {
                            //top_2 data
                            best_sale_icon2.setStyle("-fx-background-color: transparent");
                            FontIcon icon1 = new FontIcon(FontAwesomeSolid.CROWN);
                            icon1.setIconColor(Color.web("#c0c0c0"));
                            best_sale_icon2.setGraphic(icon1);
                            icon1.setIconSize(20);

                            Animation.Slide_right_to_left(top_2, Duration.millis(300), 500, () -> {
                                //top_1 data
                                best_sale_icon1.setStyle("-fx-background-color: transparent");
                                FontIcon icon = new FontIcon(FontAwesomeSolid.CROWN);
                                icon.setIconColor(Color.web("#ffd700"));
                                best_sale_icon1.setGraphic(icon);
                                icon.setIconSize(20);

                                Animation.Slide_right_to_left(top_1, Duration.millis(300), 500, ()->{

                                });
                            });
                        });
                    });
                });
            });
        } else if (pre_check == 2) {
            current--;
            Animation.Slide_left_to_right(top_1, Duration.millis(300), 500, () -> {
                Animation.Slide_left_to_right(top_2, Duration.millis(300), 500, () -> {
                    Animation.Slide_left_to_right(top_3, Duration.millis(300), 500, () -> {
                        best_sale_title.setText("I-Phone Best Sale");
                        loadTopSale("Iphone");
                        //top_3 data

                        best_sale_icon3.setStyle("-fx-background-color: transparent");
                        FontIcon icon2 = new FontIcon(FontAwesomeSolid.MEDAL);
                        icon2.setIconColor(Color.web("#cd7f32"));
                        best_sale_icon3.setGraphic(icon2);
                        icon2.setIconSize(20);

                        Animation.Slide_right_to_left(top_3, Duration.millis(300), 500, () -> {
                            //top_2 data
                            best_sale_icon2.setStyle("-fx-background-color: transparent");
                            FontIcon icon1 = new FontIcon(FontAwesomeSolid.MEDAL);
                            icon1.setIconColor(Color.web("#c0c0c0"));
                            best_sale_icon2.setGraphic(icon1);
                            icon1.setIconSize(20);

                            Animation.Slide_right_to_left(top_2, Duration.millis(300), 500, () -> {
                                //top_1 data
                                best_sale_icon1.setStyle("-fx-background-color: transparent");
                                FontIcon icon = new FontIcon(FontAwesomeSolid.MEDAL);
                                icon.setIconColor(Color.web("#ffd700"));
                                best_sale_icon1.setGraphic(icon);
                                icon.setIconSize(20);
                                Animation.Slide_right_to_left(top_1, Duration.millis(300), 500, ()->{

                                });
                            });
                        });
                    });
                });
            });
        } else if (pre_check == 3) {
            current--;
            Animation.Slide_left_to_right(top_1, Duration.millis(300), 500, () -> {
                Animation.Slide_left_to_right(top_2, Duration.millis(300), 500, () -> {
                    Animation.Slide_left_to_right(top_3, Duration.millis(300), 500, () -> {
                        best_sale_title.setText("Samsung Best Sale");
                        loadTopSale("Samsung");
                        //top_3 data
                        best_sale_icon3.setStyle("-fx-background-color: transparent");
                        FontIcon icon2 = new FontIcon(FontAwesomeSolid.MEDAL);
                        icon2.setIconColor(Color.web("#cd7f32"));
                        best_sale_icon3.setGraphic(icon2);
                        icon2.setIconSize(20);
                        Animation.Slide_right_to_left(top_3, Duration.millis(300), 500, () -> {
                            //top_2 data
                            best_sale_icon2.setStyle("-fx-background-color: transparent");
                            FontIcon icon1 = new FontIcon(FontAwesomeSolid.MEDAL);
                            icon1.setIconColor(Color.web("#c0c0c0"));
                            best_sale_icon2.setGraphic(icon1);
                            icon1.setIconSize(20);
                            Animation.Slide_right_to_left(top_2, Duration.millis(300), 500, () -> {
                                //top_1 data
                                best_sale_icon1.setStyle("-fx-background-color: transparent");
                                FontIcon icon = new FontIcon(FontAwesomeSolid.MEDAL);
                                icon.setIconColor(Color.web("#ffd700"));
                                best_sale_icon1.setGraphic(icon);
                                icon.setIconSize(20);
                                Animation.Slide_right_to_left(top_1, Duration.millis(300), 500, ()->{

                                });
                            });
                        });
                    });
                });
            });
        } else if (pre_check == 4) {
            current--;
            Animation.Slide_left_to_right(top_1, Duration.millis(300), 500, () -> {
                Animation.Slide_left_to_right(top_2, Duration.millis(300), 500, () -> {
                    Animation.Slide_left_to_right(top_3, Duration.millis(300), 500, () -> {
                        best_sale_title.setText("Redmi Best Sale");
                        loadTopSale("Redmi");
                        //top_3 data
                        best_sale_icon3.setStyle("-fx-background-color: transparent");
                        FontIcon icon2 = new FontIcon(FontAwesomeSolid.MEDAL);
                        icon2.setIconColor(Color.web("#cd7f32"));
                        best_sale_icon3.setGraphic(icon2);
                        icon2.setIconSize(20);
                        Animation.Slide_right_to_left(top_3, Duration.millis(300), 500, () -> {
                            //top_2 data
                            best_sale_icon2.setStyle("-fx-background-color: transparent");
                            FontIcon icon1 = new FontIcon(FontAwesomeSolid.MEDAL);
                            icon1.setIconColor(Color.web("#c0c0c0"));
                            best_sale_icon2.setGraphic(icon1);
                            icon1.setIconSize(20);
                            Animation.Slide_right_to_left(top_2, Duration.millis(300), 500, () -> {
                                //top_1 data
                                best_sale_icon1.setStyle("-fx-background-color: transparent");
                                FontIcon icon = new FontIcon(FontAwesomeSolid.MEDAL);
                                icon.setIconColor(Color.web("#ffd700"));
                                best_sale_icon1.setGraphic(icon);
                                icon.setIconSize(20);
                                Animation.Slide_right_to_left(top_1, Duration.millis(300), 500, ()->{

                                });
                            });
                        });
                    });
                });
            });
        }
        if(current >= 1){
            next_check = current+1;
            pre_check = current-1;
            if(pre_check == 0){
                pre_check = 4;
            }
        }
        else {
            current = 4;
            pre_check = 3;
            next_check = 1;
        }

        System.out.println("current"+current);
        System.out.println("next"+next_check);
        System.out.println("pre"+pre_check);
    }

    private void loadTopSale(String type) {
        if (type == null) return;
        type = type.toUpperCase();

        String sql = switch (type) {
            case "ALL" -> "SELECT p.product_model, p.product_image, SUM(psi.product_qty) AS total_qty FROM product_sale_item psi JOIN products p ON psi.product_id = p.product_id WHERE p.product_type_id = 1 GROUP BY p.product_id, p.product_model, p.product_image ORDER BY total_qty DESC LIMIT 3";
            case "IPHONE" -> "SELECT p.product_model, p.product_image, SUM(psi.product_qty) AS total_qty FROM product_sale_item psi JOIN products p ON psi.product_id = p.product_id JOIN product_brand pb ON p.product_brand_id = pb.product_brand_id WHERE pb.product_brand_name LIKE '%Apple%' AND p.product_type_id = 1 GROUP BY p.product_id, p.product_model, p.product_image ORDER BY total_qty DESC LIMIT 3";
            case "SAMSUNG" -> "SELECT p.product_model, p.product_image, SUM(psi.product_qty) AS total_qty FROM product_sale_item psi JOIN products p ON psi.product_id = p.product_id JOIN product_brand pb ON p.product_brand_id = pb.product_brand_id WHERE pb.product_brand_name LIKE '%Samsung%' AND p.product_type_id = 1 GROUP BY p.product_id, p.product_model, p.product_image ORDER BY total_qty DESC LIMIT 3";
            case "REDMI" -> """
    SELECT p.product_model, p.product_image, SUM(psi.product_qty) AS total_qty
    FROM product_sale_item psi
    JOIN products p ON psi.product_id = p.product_id
    JOIN product_brand pb ON p.product_brand_id = pb.product_brand_id
    WHERE (pb.product_brand_name LIKE '%Redmi%' OR pb.product_brand_name LIKE '%Xiaomi%')
    AND p.product_type_id = 1
    GROUP BY p.product_id, p.product_model, p.product_image
    ORDER BY total_qty DESC 
    LIMIT 3
""";
            default -> null;
        };

        if (sql == null) return;

        try {
            List<Map<String, Object>> results = Dynamic_Method.select(sql);
            Circle[] photos = {best_sale_photo1, best_sale_photo2, best_sale_photo3};
            Label[] names = {the_best_sale_name1, the_best_sale_name2, the_best_sale_name3};
            Label[] qtys = {the_best_sale_qty1, the_best_sale_qty2, the_best_sale_qty3};

            for (int i = 0; i < photos.length; i++) {
                if (i < results.size()) {
                    Map<String, Object> row = results.get(i);
                    names[i].setText(row.get("product_model").toString());
                    qtys[i].setText("Qty: " + row.get("total_qty").toString());


                    Object imgData = row.get("product_image");
                    if (imgData != null && imgData instanceof byte[]) {
                        byte[] bytes = (byte[]) imgData;
                        if (bytes.length > 0) {
                            Image img = new Image(new ByteArrayInputStream(bytes));


                            if (!img.isError()) {
                                photos[i].setFill(new ImagePattern(img));
                            } else {
                                photos[i].setFill(Color.web("#CCCCCC"));
                            }
                        } else {
                            photos[i].setFill(Color.web("#CCCCCC"));
                        }
                    } else {
                        photos[i].setFill(Color.web("#CCCCCC"));
                    }
                } else {
                    names[i].setText("-");
                    qtys[i].setText("0.0");
                    photos[i].setFill(Color.TRANSPARENT);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
