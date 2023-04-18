package com.example.trial1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;

public class Recognise {

    @FXML
    private AnchorPane application;

    @FXML
    private Button addSketch;

    @FXML
    private Button searchSketch;
    @FXML
    private ImageView sketchImage;

    @FXML
    void addSketch(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), ".sketches/Saved Sketches"));

        Stage stage = (Stage) application.getScene().getWindow();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            sketchImage.setImage(image);
        }
    }

    @FXML
    void searchSketch(ActionEvent event) {

    }

}

