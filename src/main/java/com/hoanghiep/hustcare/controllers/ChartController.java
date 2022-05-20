package com.hoanghiep.hustcare.controllers;

import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;

@Component
//@FxmlView("/chart.fxml")
public class ChartController {
	
	@FXML
    public LineChart<String, Double> chart;
	
}
