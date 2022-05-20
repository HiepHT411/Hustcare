package com.hoanghiep.hustcare;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.hoanghiep.hustcare.MainApp.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class StageInitializer implements ApplicationListener<MainApp.StageReadyEvent> {
	
//	@Value("classpath:/fxml/chart.fxml")
//	private Resource chartResource;
	
	private ApplicationContext applicationContext;
	
	private String applicationTitle;
	
	//private final FxWeaver fxWeaver;
	 
	public StageInitializer( @Value("${spring.application.ui.title}") String applicationTitle, ApplicationContext applicationContext) {
		this.applicationTitle = applicationTitle;
		this.applicationContext = applicationContext;
	}
	
	@Override
	public void onApplicationEvent(StageReadyEvent event) {
		try {
			Stage stage = event.getStage();
			//FXMLLoader fxmlLoader = new FXMLLoader(chartResource.getURL());
			//FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/chart.xml"));
			FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/fxml/chart.fxml").toURI().toURL());
			fxmlLoader.setControllerFactory(this.applicationContext::getBean);
			Parent parent = fxmlLoader.load();
			
			stage.setScene(new Scene(parent, 800, 600));
			stage.setTitle(applicationTitle);
			stage.show();
		} catch (IOException e) {
//			System.out.println( "Exception on FXMLLoader.load()" );
//	        System.out.println( "  * url: " + getClass().getResource("/fxml/chart.fxml"));
//	        System.out.println( "  * " + e);
//	        System.out.println( "    ----------------------------------------\n" );
			throw new RuntimeException();
		}
		
//		 Stage stage = event.getStage();
//	        stage.setScene(new Scene(fxWeaver.loadView(ChartController.class), 800, 600));
//	        stage.setTitle(applicationTitle);
//	        stage.show();
	}

}
