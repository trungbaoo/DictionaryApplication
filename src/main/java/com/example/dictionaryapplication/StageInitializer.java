package com.example.dictionaryapplication;

import com.example.dictionaryapplication.JavaFXApplication.StageReadyEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import static com.example.dictionaryapplication.DictionaryController.dictionaryManagement;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private final String applicationTitle;
    private final FxWeaver fxWeaver;

    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle,
                            FxWeaver fxWeaver) {
        this.applicationTitle = applicationTitle;
        this.fxWeaver = fxWeaver;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.getStage();
        Scene scene = new Scene(fxWeaver.loadView(DictionaryController.class), 800, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle(applicationTitle);

        stage.show();
    }
}
