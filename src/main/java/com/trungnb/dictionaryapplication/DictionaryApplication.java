package com.trungnb.dictionaryapplication;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class DictionaryApplication {
    public static void main(String[] args) {
        Application.launch(JavaFXApplication.class, args);
    }

}
