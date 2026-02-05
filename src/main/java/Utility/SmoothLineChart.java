package Utility;

import javafx.beans.NamedArg;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;


public class SmoothLineChart extends LineChart<String, Number> {

    private final Line hoverLine = new Line();
    private final Tooltip tooltip = new Tooltip();


    public SmoothLineChart() {
        this(new CategoryAxis(), new NumberAxis());
    }


    public SmoothLineChart(@NamedArg("xAxis") Axis<String> xAxis, @NamedArg("yAxis") Axis<Number> yAxis) {
        super(xAxis, yAxis);
        setAnimated(false);
        setCreateSymbols(false);
        setLegendVisible(false);
        initHover();
    }

    private void initHover() {
        hoverLine.setStroke(Color.web("#B0B0B0"));
        hoverLine.getStrokeDashArray().addAll(6.0, 6.0);
        hoverLine.setManaged(false);
        hoverLine.setVisible(false);

        tooltip.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: white; -fx-padding: 8px;");

        getPlotChildren().add(hoverLine);

        this.addEventHandler(MouseEvent.MOUSE_MOVED, this::handleHover);
        this.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            hoverLine.setVisible(false);
            tooltip.hide();
        });
    }

    private void handleHover(MouseEvent e) {
        if (getData().isEmpty() || getData().get(0).getData().isEmpty()) return;

        double mouseX = e.getX();
        double chartWidth = getXAxis().getWidth();
        if (mouseX < 0 || mouseX > chartWidth) {
            hoverLine.setVisible(false);
            return;
        }
        hoverLine.setStartX(mouseX);
        hoverLine.setEndX(mouseX);
        hoverLine.setStartY(0);
        hoverLine.setEndY(getYAxis().getHeight());
        hoverLine.setVisible(true);

        XYChart.Data<String, Number> nearest = null;
        double min = Double.MAX_VALUE;

        for (XYChart.Data<String, Number> d : getData().get(0).getData()) {
            double x = getXAxis().getDisplayPosition(d.getXValue());
            double dist = Math.abs(mouseX - x);
            if (dist < min) {
                min = dist;
                nearest = d;
            }
        }
        if (nearest != null) {
            tooltip.setText(nearest.getXValue() + "\nSales: " + nearest.getYValue());
            tooltip.show(this, e.getScreenX() + 10, e.getScreenY() - 10);
        }
    }
//    public void drawSmoothLine(List<XYChart.Data<String, Number>> points) {
//        getData().clear();
//        getPlotChildren().removeIf(n -> n instanceof Path);
//
//        XYChart.Series<String, Number> series = new XYChart.Series<>();
//        series.getData().addAll(points);
//        getData().add(series);
//
//        Path path = new Path();
//        path.setStroke(Color.web("#FF7043"));
//        path.setStrokeWidth(2.5);
//        path.setFill(Color.TRANSPARENT);
//
//        if (points.isEmpty()) return;
//
//        double prevX = getXAxis().getDisplayPosition(points.get(0).getXValue());
//        double prevY = getYAxis().getDisplayPosition(points.get(0).getYValue());
//
//        path.getElements().add(new MoveTo(prevX, prevY));
//
//        for (int i = 1; i < points.size(); i++) {
//            var d = points.get(i);
//            double x = getXAxis().getDisplayPosition(d.getXValue());
//            double y = getYAxis().getDisplayPosition(d.getYValue());
//
//            double midX = (prevX + x) / 2;
//            path.getElements().add(new CubicCurveTo(midX, prevY, midX, y, x, y));
//
//            prevX = x;
//            prevY = y;
//        }
//        getPlotChildren().add(path);
//    }


}