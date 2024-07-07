package com.cafeconnect.pos.cafeconnect;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class DashboardFragment {

    @FXML
    private AnchorPane DashboardAnchor;

    @FXML
    private ImageView DashboardStats;

    @FXML
    private Text DateTimeAndSchedule;

    @FXML
    private Circle ImageFrame;

    @FXML
    private Text InText;

    @FXML
    private ImageView MostOrdered;

    @FXML
    private BarChart<?, ?> MostOrderedChart;

    @FXML
    private ImageView MostType;

    @FXML
    private PieChart MostTypeChart;

    @FXML
    private Text OrderDateAndTime;

    @FXML
    private Text OrderNumberVal;

    @FXML
    private ImageView OrderReport;

    @FXML
    private Text OutText;

    @FXML
    private GridPane ReportGridPane;

    @FXML
    private Pane ReportPane;

    @FXML
    private ScrollPane ReportScrollPane;

    @FXML
    private Text SelectedDishName;

    @FXML
    private Text StaffName;

    @FXML
    private Circle TImeIn;

    @FXML
    private Text TimeInVal;

    @FXML
    private Circle TimeOut;

    @FXML
    private Text TimeOutVal;

    @FXML
    private Text TotalCustomerVal;

    @FXML
    private Text TotalDishVal;

    @FXML
    private Text TotalVal;

    @FXML
    private Text TotalWorkTime;

    @FXML
    void TimeInClicked(MouseEvent event) {

    }

    @FXML
    void TimeOutClicked(MouseEvent event) {

    }

}
