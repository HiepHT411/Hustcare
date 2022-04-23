package com.hoanghiep.hustcare.controllers;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;

import net.rgielen.fxweaver.core.FxmlView;

@Component
//@FxmlView("/chart.fxml")
public class ChartController {
	
	@FXML
    public LineChart<String, Double> chart;
	
}
