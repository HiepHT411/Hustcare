package com.hoanghiep.hustcare;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;

public class MainApp extends Application {

	private ConfigurableApplicationContext applicationContext;
	
	@Override
	public void init() {
		//this.applicationContext = new SpringApplicationBuilder(MainApp.class).run();
		ApplicationContextInitializer<GenericApplicationContext> initializer =
				context -> {
					context.registerBean(Application.class, () -> MainApp.this);
					context.registerBean(Parameters.class, this::getParameters);
					context.registerBean(HostServices.class, this::getHostServices);
				};
		this.applicationContext = new SpringApplicationBuilder()
				.sources(HustcareApplication.class)
				.initializers(initializer)
				.run(getParameters().getRaw().toArray(new String[0]));
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.applicationContext.publishEvent(new StageReadyEvent(stage));
	}
	
	@Override
	public void stop() {
		this.applicationContext.close();
		Platform.exit();
	}
	
	static class StageReadyEvent extends ApplicationEvent {
		private final Stage stage;
		public StageReadyEvent(Stage stage) {
			super(stage);
			this.stage = stage;
		}

		public Stage getStage() {
			return ((Stage) this.getSource());
		}
	}
}


