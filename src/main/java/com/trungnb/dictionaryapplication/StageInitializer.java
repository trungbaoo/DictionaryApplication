package com.trungnb.dictionaryapplication;

import com.trungnb.dictionaryapplication.JavaFXApplication.StageReadyEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

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
        Scene scene = new Scene(fxWeaver.loadView(DictionaryController.class), 600, 600);
        scene.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());
        stage.setResizable(false);

        stage.setScene(scene);
        stage.setTitle(applicationTitle);

        stage.show();
    }
}