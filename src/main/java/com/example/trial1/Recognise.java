package com.example.trial1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

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
    private ImageView photoImage;

    @FXML
    private Label similarityLabel;

    private String sketchPath;
    private String photoPath;
    private String similarity;

    @FXML
    void addSketch(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), ".sketches/Saved"));

        Stage stage = (Stage) application.getScene().getWindow();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            sketchPath = file.toURI().toString();
            sketchImage.setImage(image);
        }
    }

    @FXML
    void searchSketch(ActionEvent event) {
        try {

//.replaceAll("/", "\\\\")
            ProcessBuilder builder = new ProcessBuilder("python", System.getProperty("user.dir") + "\\Scripts\\recog.py", sketchPath.substring(6));
            Process process = builder.start();

//            Process process = Runtime.getRuntime().exec("python " +  System.getProperty("user.dir") + "\\Scripts\\recog.py" + " " + sketchPath.substring(6).replaceAll("/","\\\\"));

            Reader reader = new InputStreamReader(process.getInputStream());
            BufferedReader buffReader = new BufferedReader(reader);
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            photoPath = buffReader.readLine();
            similarity = buffReader.readLine();

            Image image = new Image(photoPath);
            photoImage.setImage(image);

            similarityLabel.setText(similarity + "% match");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

