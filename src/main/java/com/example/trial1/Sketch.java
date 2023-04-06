package com.example.trial1;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;
import java.util.function.Consumer;
import java.awt.image.BufferedImage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;

public class Sketch implements Initializable {
    @FXML
    private AnchorPane application;


    private boolean headAdd = true;
    private boolean hairAdd = true;
    private boolean noseAdd = true;
    private boolean eyesAdd = true;
    private boolean lipsAdd = true;
    private boolean eyebrowAdd = true;
    private boolean moustacheAdd = true;
    private boolean earsAdd = true;


    private double startX;
    private double startY;


    private Image imageHead;
    private Image imageHair;
    private Image imageNose;
    private Image imageEyes;
    private Image imageLips;
    private Image imageEyebrow;
    private Image imageMoustache;

    private Image imageEars;


    private ImageView imageViewHead;
    private ImageView imageViewHair;
    private ImageView imageViewNose;
    private ImageView imageViewEyes;
    private ImageView imageViewLips;
    private ImageView imageViewEyebrow;
    private ImageView imageViewMoustache;

    private ImageView imageViewEars;


    @FXML
    private Slider headSize;
    @FXML
    private Slider hairSize;
    @FXML
    private Slider noseSize;
    @FXML
    private Slider eyesSize;
    @FXML
    private Slider lipsSize;
    @FXML
    private Slider eyebrowSize;
    @FXML
    private Slider moustacheSize;
    @FXML
    private Slider earsSize;


    @FXML
    private GridPane displayHead;
    @FXML
    private GridPane displayHair;
    @FXML
    private GridPane displayNose;
    @FXML
    private GridPane displayEyes;
    @FXML
    private GridPane displayLips;
    @FXML
    private GridPane displayEyebrow;
    @FXML
    private GridPane displayMoustache;

    @FXML
    private GridPane displayEars;


    @FXML
    private StackPane canvas;
    public SketchModel sketchmodel = new SketchModel();
    public SqliteConnection conn = new SqliteConnection();


    @FXML
    private Button headSearch;
    @FXML
    private TextField headSearchInput;
    @FXML
    void headSearch(ActionEvent event) {
        searchHead();
    }

    @FXML
    private Button headRemove;
    @FXML
    void headRemove(ActionEvent event) {
        canvas.getChildren().remove(imageViewHead);
        headAdd = true;
    }

    @FXML
    private Button hairSearch;
    @FXML
    private TextField hairSearchInput;
    @FXML
    void hairSearch(ActionEvent event) {
        searchHair();
    }

    @FXML
    private Button hairRemove;
    @FXML
    void hairRemove(ActionEvent event) {
        canvas.getChildren().remove(imageViewHair);
        hairAdd = true;
    }

    @FXML
    private Button eyesSearch;
    @FXML
    private TextField eyesSearchInput;
    @FXML
    void eyesSearch(ActionEvent event) {
        searchEyes();
    }

    @FXML
    private Button eyesRemove;
    @FXML
    void eyesRemove(ActionEvent event) {
        canvas.getChildren().remove(imageViewEyes);
        eyesAdd = true;
    }

    @FXML
    private Button noseSearch;
    @FXML
    private TextField noseSearchInput;
    @FXML
    void noseSearch(ActionEvent event) {
        searchNose();
    }

    @FXML
    private Button noseRemove;
    @FXML
    void noseRemove(ActionEvent event) {
        canvas.getChildren().remove(imageViewNose);
        noseAdd = true;
    }

    @FXML
    private Button lipsSearch;
    @FXML
    private TextField lipsSearchInput;
    @FXML
    void lipsSearch(ActionEvent event) {
        searchLips();
    }

    @FXML
    private Button lipsRemove;
    @FXML
    void lipsRemove(ActionEvent event) {
        canvas.getChildren().remove(imageViewLips);
        lipsAdd = true;
    }

    @FXML
    private Button eyebrowSearch;
    @FXML
    private TextField eyebrowSearchInput;
    @FXML
    void eyebrowSearch(ActionEvent event) {
        searchEyebrow();
    }

    @FXML
    private Button eyebrowRemove;
    @FXML
    void eyebrowRemove(ActionEvent event) {
        canvas.getChildren().remove(imageViewEyebrow);
        eyebrowAdd = true;
    }


    @FXML
    private Button moustacheSearch;
    @FXML
    private TextField moustacheSearchInput;

    @FXML
    void moustacheSearch(ActionEvent event) {
        searchMoustache();
    }

    @FXML
    private Button moustacheRemove;

    @FXML
    void moustacheRemove(ActionEvent event) {
        canvas.getChildren().remove(imageViewMoustache);
        moustacheAdd = true;
    }


//    @FXML
//    private Button earsSearch;
//    @FXML
//    private TextField earsSearchInput;
//    @FXML
//    void earsSearch(ActionEvent event) { searchEars();}
//
//    @FXML
//    private Button earsRemove;
//    @FXML
//    void earsRemove(ActionEvent event) {
//        canvas.getChildren().remove(imageViewEars);
//        earsAdd = true;
//    }


//    @FXML
//    private Label statusDb;
//
//
//    @FXML
//    private Label errorlog;
//    @FXML
//    private Label headwidth;


    @FXML
    private Button resetCanvas;

    @FXML
    void resetCanvas(ActionEvent event) {
        canvas.getChildren().clear();
        headAdd = true;
        hairAdd = true;
        noseAdd = true;
        eyesAdd = true;
        lipsAdd = true;
        eyebrowAdd = true;
        moustacheAdd = true;
        earsAdd = true;

    }


    //Save the sketch to system
    @FXML
    private Button saveSketch;
    private WritableImage sketch = new WritableImage(512, 512);

    @FXML
    void saveSketch(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();

        Stage stage = (Stage) application.getScene().getWindow();
        sketch = canvas.snapshot(null, null);

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File sketches = new File(System.getProperty("user.home"), ".sketches/Saved Sketches");
        if (!sketches.exists()) {
            sketches.mkdirs();
        }
        fileChooser.setInitialDirectory(sketches);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            SaveFile(sketch, file);
        }
    }

    private void SaveFile(Image content, File file) {
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(content, null);
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    //Start of main process
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (sketchmodel.dbConnected()) {
//            statusDb.setText("WORKS");
            headDisplay();
            hairDisplay();
        } else {
//            statusDb.setText("Dard");
        }
    }

    Connection connection = SqliteConnection.Connector();

    //Displays head features on app load
    public void headDisplay() {
        String sql = "SELECT featureThumb,featurePath FROM featureset WHERE featureType LIKE '%head%'";
        displayHead.getChildren().clear();

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
//            if (rs.next()) {
//                errorlog.setText("WORKS");
//            } else {
//                errorlog.setText("DARD MOMENT");
//            }

            String[][] headFeature = new String[2][10];

            int countHead = 0;
//            headThumbnail.add(rs.getString("featureThumb").toString());
//            headFeaturePath.add(rs.getString("featurePath").toString());
            while (rs.next()) {
                headFeature[0][countHead] = rs.getString("featureThumb").toString();
                headFeature[1][countHead] = rs.getString("featurePath").toString();
                countHead++;
//                headThumbnail.add(rs.getString("featureThumb").toString());
//                headFeaturePath.add(rs.getString("featurePath").toString());
            }

            int countFunc = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++, countFunc++) {
                    displayHead.add(new ImageView(new Image(headFeature[0][countFunc])), j, i);
                    int countFuncCopy = countFunc;
                    displayHead.getChildren().get(countFuncCopy).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent e) {
                            if (headAdd) {

                                imageHead = new Image(headFeature[1][countFuncCopy]);
                                double aspectRatio = imageHead.getWidth() / imageHead.getHeight();
                                imageViewHead = new ImageView(imageHead);
                                imageViewHead.setPreserveRatio(true);
                                imageViewHead.setFitHeight(320);
                                imageViewHead.setFitWidth(aspectRatio * 320);

                                imageViewHead.setViewOrder(-1);

                                canvas.getChildren().add(imageViewHead);
                                draggable(imageViewHead);

                                headAdd = false;
                            } else {

                                imageViewHead.setImage(new Image(headFeature[1][countFuncCopy]));

                            }
                        }
                    });

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        headSize.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            double set = (double) newValue - (double) oldValue;
            double setY = imageViewHead.getFitHeight() + set;
            double aspectRatio = imageHead.getWidth() / imageHead.getHeight();
            double setX = aspectRatio * setY;
            imageViewHead.setFitHeight(setY);
            imageViewHead.setFitWidth(setX);
        });
    }


    //Displays requested head features on button click
    public void searchHead() {

        String search = headSearchInput.getText();
        String sql = "SELECT featureThumb,featurePath FROM featureset WHERE tags LIKE '%" + search + "%' AND featureType LIKE '%head%'";
        displayHead.getChildren().clear();

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            String[][] headFeature = new String[2][10];
            int countHead = 0;
//            headThumbnail.add(rs.getString("featureThumb").toString());
//            headFeaturePath.add(rs.getString("featurePath").toString());
            while (rs.next()) {
                headFeature[0][countHead] = rs.getString("featureThumb").toString();
                headFeature[1][countHead] = rs.getString("featurePath").toString();
                countHead++;
//                headThumbnail.add(rs.getString("featureThumb").toString());
//                headFeaturePath.add(rs.getString("featurePath").toString());
            }

            int countFunc = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++, countFunc++) {

                    displayHead.add(new ImageView(new Image(headFeature[0][countFunc])), j, i);
                    int countFuncCopy = countFunc;
                    displayHead.getChildren().get(countFuncCopy).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent e) {
                            if (headAdd) {

                                imageHead = new Image(headFeature[1][countFuncCopy]);
                                double aspectRatio = imageHead.getWidth() / imageHead.getHeight();
                                imageViewHead = new ImageView(imageHead);
                                imageViewHead.setPreserveRatio(true);
                                imageViewHead.setFitHeight(320);
                                imageViewHead.setFitWidth(aspectRatio * 320);

                                imageViewHead.setViewOrder(-1);

                                canvas.getChildren().add(imageViewHead);
                                draggable(imageViewHead);

                                headAdd = false;
                            } else {

                                imageViewHead.setImage(new Image(headFeature[1][countFuncCopy]));

                            }
                        }
                    });

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Displays hair features on app load
    public void hairDisplay() {
        String sql = "SELECT featureThumb,featurePath FROM featureset WHERE featureType LIKE '%hair%'";
        displayHair.getChildren().clear();

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            String[][] hairFeature = new String[2][10];

            int countHair = 0;
            while (rs.next()) {
                hairFeature[0][countHair] = rs.getString("featureThumb").toString();
                hairFeature[1][countHair] = rs.getString("featurePath").toString();
                countHair++;
            }

            int countFunc = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++, countFunc++) {

                    displayHair.add(new ImageView(new Image(hairFeature[0][countFunc])), j, i);
                    int countFuncCopy = countFunc;
                    displayHair.getChildren().get(countFuncCopy).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent e) {
                            if (hairAdd) {

                                imageHair = new Image(hairFeature[1][countFuncCopy]);
                                double aspectRatio = imageHair.getWidth() / imageHair.getHeight();
                                imageViewHair = new ImageView(imageHair);
                                imageViewHair.setPreserveRatio(true);
                                imageViewHair.setFitHeight(230);
                                imageViewHair.setFitWidth(aspectRatio * 230);
                                imageViewHair.setLayoutY(200);

                                imageViewHair.setViewOrder(-2);

                                canvas.getChildren().add(imageViewHair);
                                draggable(imageViewHair);

                                hairAdd = false;
                            } else {

                                imageViewHair.setImage(new Image(hairFeature[1][countFuncCopy]));

                            }
                        }
                    });

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        hairSize.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            double set = (double) newValue - (double) oldValue;
            double setY = imageViewHair.getFitHeight() + set;
            double aspectRatio = imageHair.getWidth() / imageHair.getHeight();
            double setX = aspectRatio * setY;
            imageViewHair.setFitHeight(setY);
            imageViewHair.setFitWidth(setX);
        });
    }

    //Displays requested hair features on button click
    public void searchHair() {

        String search = hairSearchInput.getText();
        String sql = "SELECT featureThumb,featurePath FROM featureset WHERE tags LIKE '%" + search + "%' AND featureType LIKE '%hair%'";
        displayHair.getChildren().clear();

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            String[][] hairFeature = new String[2][10];

            int countHair = 0;
            while (rs.next()) {
                hairFeature[0][countHair] = rs.getString("featureThumb").toString();
                hairFeature[1][countHair] = rs.getString("featurePath").toString();
                countHair++;
            }

            int countFunc = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++, countFunc++) {

                    displayHair.add(new ImageView(new Image(hairFeature[0][countFunc])), j, i);
                    int countFuncCopy = countFunc;
                    displayHair.getChildren().get(countFuncCopy).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent e) {
                            if (hairAdd) {

                                imageHair = new Image(hairFeature[1][countFuncCopy]);
                                double aspectRatio = imageHair.getWidth() / imageHair.getHeight();
                                imageViewHair = new ImageView(imageHair);
                                imageViewHair.setPreserveRatio(true);
                                imageViewHair.setFitHeight(230);
                                imageViewHair.setFitWidth(aspectRatio * 230);

                                imageViewHair.setViewOrder(-1);

                                canvas.getChildren().add(imageViewHair);
                                draggable(imageViewHair);

                                hairAdd = false;
                            } else {

                                imageViewHair.setImage(new Image(hairFeature[1][countFuncCopy]));

                            }
                        }
                    });

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Displays eye features on app load
    public void eyesDisplay() {
        String sql = "SELECT featureThumb,featurePath FROM featureset WHERE featureType LIKE '%eyes%'";
        displayEyes.getChildren().clear();

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            String[][] eyesFeature = new String[2][10];

            int countEyes = 0;
            while (rs.next()) {
                eyesFeature[0][countEyes] = rs.getString("featureThumb").toString();
                eyesFeature[1][countEyes] = rs.getString("featurePath").toString();
                countEyes++;
            }

            int countFunc = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++, countFunc++) {

                    displayEyes.add(new ImageView(new Image(eyesFeature[0][countFunc])), j, i);
                    int countFuncCopy = countFunc;
                    displayEyes.getChildren().get(countFuncCopy).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent e) {
                            if (eyesAdd) {

                                imageEyes = new Image(eyesFeature[1][countFuncCopy]);
                                double aspectRatio = imageEyes.getWidth() / imageEyes.getHeight();
                                imageViewEyes = new ImageView(imageEyes);
                                imageViewEyes.setPreserveRatio(true);
                                imageViewEyes.setFitHeight(320);
                                imageViewEyes.setFitWidth(aspectRatio * 320);

                                imageViewEyes.setViewOrder(-1);

                                canvas.getChildren().add(imageViewEyes);
                                draggable(imageViewEyes);

                                eyesAdd = false;
                            } else {

                                imageViewEyes.setImage(new Image(eyesFeature[1][countFuncCopy]));

                            }
                        }
                    });

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        eyesSize.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            double set = (double) newValue - (double) oldValue;
            double setY = imageViewEyes.getFitHeight() + set;
            double aspectRatio = imageEyes.getWidth() / imageEyes.getHeight();
            double setX = aspectRatio * setY;
            imageViewEyes.setFitHeight(setY);
            imageViewEyes.setFitWidth(setX);
        });
    }

    //Displays requested eye features on button click
    public void searchEyes() {

        String search = eyesSearchInput.getText();
        String sql = "SELECT featureThumb,featurePath FROM featureset WHERE tags LIKE '%" + search + "%' AND featureType LIKE '%eyes%'";
        displayEyes.getChildren().clear();

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            String[][] eyesFeature = new String[2][10];

            int countEyes = 0;
            while (rs.next()) {
                eyesFeature[0][countEyes] = rs.getString("featureThumb").toString();
                eyesFeature[1][countEyes] = rs.getString("featurePath").toString();
                countEyes++;
            }

            int countFunc = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++, countFunc++) {

                    displayEyes.add(new ImageView(new Image(eyesFeature[0][countFunc])), j, i);
                    int countFuncCopy = countFunc;
                    displayEyes.getChildren().get(countFuncCopy).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent e) {
                            if (eyesAdd) {

                                imageEyes = new Image(eyesFeature[1][countFuncCopy]);
                                double aspectRatio = imageEyes.getWidth() / imageEyes.getHeight();
                                imageViewEyes = new ImageView(imageEyes);
                                imageViewEyes.setPreserveRatio(true);
                                imageViewEyes.setFitHeight(320);
                                imageViewEyes.setFitWidth(aspectRatio * 320);

                                imageViewEyes.setViewOrder(-1);

                                canvas.getChildren().add(imageViewEyes);
                                draggable(imageViewEyes);

                                eyesAdd = false;
                            } else {

                                imageViewEyes.setImage(new Image(eyesFeature[1][countFuncCopy]));

                            }
                        }
                    });

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Displays nose features on app load
    public void noseDisplay() {
        String sql = "SELECT featureThumb,featurePath FROM featureset WHERE featureType LIKE '%nose%'";
        displayNose.getChildren().clear();

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            String[][] noseFeature = new String[2][10];

            int countNose = 0;
            while (rs.next()) {
                noseFeature[0][countNose] = rs.getString("featureThumb").toString();
                noseFeature[1][countNose] = rs.getString("featurePath").toString();
                countNose++;
            }

            int countFunc = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++, countFunc++) {

                    displayNose.add(new ImageView(new Image(noseFeature[0][countFunc])), j, i);
                    int countFuncCopy = countFunc;
                    displayNose.getChildren().get(countFuncCopy).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent e) {
                            if (noseAdd) {

                                imageNose = new Image(noseFeature[1][countFuncCopy]);
                                double aspectRatio = imageNose.getWidth() / imageNose.getHeight();
                                imageViewNose = new ImageView(imageNose);
                                imageViewNose.setPreserveRatio(true);
                                imageViewNose.setFitHeight(320);
                                imageViewNose.setFitWidth(aspectRatio * 320);

                                imageViewNose.setViewOrder(-1);

                                canvas.getChildren().add(imageViewNose);
                                draggable(imageViewNose);

                                noseAdd = false;
                            } else {

                                imageViewNose.setImage(new Image(noseFeature[1][countFuncCopy]));

                            }
                        }
                    });

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        noseSize.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            double set = (double) newValue - (double) oldValue;
            double setY = imageViewNose.getFitHeight() + set;
            double aspectRatio = imageNose.getWidth() / imageNose.getHeight();
            double setX = aspectRatio * setY;
            imageViewNose.setFitHeight(setY);
            imageViewNose.setFitWidth(setX);
        });
    }

    //Displays requested nose features on button click
    public void searchNose() {

        String search = noseSearchInput.getText();
        String sql = "SELECT featureThumb,featurePath FROM featureset WHERE tags LIKE '%" + search + "%' AND featureType LIKE '%nose%'";
        displayNose.getChildren().clear();

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            String[][] noseFeature = new String[2][10];

            int countNose = 0;
            while (rs.next()) {
                noseFeature[0][countNose] = rs.getString("featureThumb").toString();
                noseFeature[1][countNose] = rs.getString("featurePath").toString();
                countNose++;
            }

            int countFunc = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++, countFunc++) {

                    displayNose.add(new ImageView(new Image(noseFeature[0][countFunc])), j, i);
                    int countFuncCopy = countFunc;
                    displayNose.getChildren().get(countFuncCopy).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent e) {
                            if (noseAdd) {

                                imageNose = new Image(noseFeature[1][countFuncCopy]);
                                double aspectRatio = imageNose.getWidth() / imageNose.getHeight();
                                imageViewNose = new ImageView(imageNose);
                                imageViewNose.setPreserveRatio(true);
                                imageViewNose.setFitHeight(320);
                                imageViewNose.setFitWidth(aspectRatio * 320);

                                imageViewNose.setViewOrder(-1);

                                canvas.getChildren().add(imageViewNose);
                                draggable(imageViewNose);

                                noseAdd = false;
                            } else {

                                imageViewNose.setImage(new Image(noseFeature[1][countFuncCopy]));

                            }
                        }
                    });

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Displays lips features on app load
    public void lipsDisplay() {
        String sql = "SELECT featureThumb,featurePath FROM featureset WHERE featureType LIKE '%lips%'";
        displayLips.getChildren().clear();

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            String[][] lipsFeature = new String[2][10];

            int countLips = 0;
            while (rs.next()) {
                lipsFeature[0][countLips] = rs.getString("featureThumb").toString();
                lipsFeature[1][countLips] = rs.getString("featurePath").toString();
                countLips++;
            }

            int countFunc = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++, countFunc++) {

                    displayLips.add(new ImageView(new Image(lipsFeature[0][countFunc])), j, i);
                    int countFuncCopy = countFunc;
                    displayLips.getChildren().get(countFuncCopy).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent e) {
                            if (lipsAdd) {

                                imageLips = new Image(lipsFeature[1][countFuncCopy]);
                                double aspectRatio = imageLips.getWidth() / imageLips.getHeight();
                                imageViewLips = new ImageView(imageLips);
                                imageViewLips.setPreserveRatio(true);
                                imageViewLips.setFitHeight(320);
                                imageViewLips.setFitWidth(aspectRatio * 320);

                                imageViewLips.setViewOrder(-1);

                                canvas.getChildren().add(imageViewLips);
                                draggable(imageViewLips);

                                lipsAdd = false;
                            } else {

                                imageViewLips.setImage(new Image(lipsFeature[1][countFuncCopy]));

                            }
                        }
                    });

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        lipsSize.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            double set = (double) newValue - (double) oldValue;
            double setY = imageViewLips.getFitHeight() + set;
            double aspectRatio = imageLips.getWidth() / imageLips.getHeight();
            double setX = aspectRatio * setY;
            imageViewLips.setFitHeight(setY);
            imageViewLips.setFitWidth(setX);
        });
    }

    //Displays requested lips features on button click
    public void searchLips() {

        String search = lipsSearchInput.getText();
        String sql = "SELECT featureThumb,featurePath FROM featureset WHERE tags LIKE '%" + search + "%' AND featureType LIKE '%lips%'";
        displayLips.getChildren().clear();

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            String[][] lipsFeature = new String[2][10];

            int countLips = 0;
            while (rs.next()) {
                lipsFeature[0][countLips] = rs.getString("featureThumb").toString();
                lipsFeature[1][countLips] = rs.getString("featurePath").toString();
                countLips++;
            }

            int countFunc = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++, countFunc++) {

                    displayLips.add(new ImageView(new Image(lipsFeature[0][countFunc])), j, i);
                    int countFuncCopy = countFunc;
                    displayLips.getChildren().get(countFuncCopy).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent e) {
                            if (lipsAdd) {

                                imageLips = new Image(lipsFeature[1][countFuncCopy]);
                                double aspectRatio = imageLips.getWidth() / imageLips.getHeight();
                                imageViewLips = new ImageView(imageLips);
                                imageViewLips.setPreserveRatio(true);
                                imageViewLips.setFitHeight(320);
                                imageViewLips.setFitWidth(aspectRatio * 320);

                                imageViewLips.setViewOrder(-1);

                                canvas.getChildren().add(imageViewLips);
                                draggable(imageViewLips);

                                lipsAdd = false;
                            } else {

                                imageViewLips.setImage(new Image(lipsFeature[1][countFuncCopy]));

                            }
                        }
                    });

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Displays eyebrow features on app load
    public void eyebrowDisplay() {
        String sql = "SELECT featureThumb,featurePath FROM featureset WHERE featureType LIKE '%eyebrow%'";
        displayEyebrow.getChildren().clear();

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            String[][] eyebrowFeature = new String[2][10];

            int countEyebrow = 0;
            while (rs.next()) {
                eyebrowFeature[0][countEyebrow] = rs.getString("featureThumb").toString();
                eyebrowFeature[1][countEyebrow] = rs.getString("featurePath").toString();
                countEyebrow++;
            }

            int countFunc = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++, countFunc++) {

                    displayEyebrow.add(new ImageView(new Image(eyebrowFeature[0][countFunc])), j, i);
                    int countFuncCopy = countFunc;
                    displayEyebrow.getChildren().get(countFuncCopy).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent e) {
                            if (eyebrowAdd) {

                                imageEyebrow = new Image(eyebrowFeature[1][countFuncCopy]);
                                double aspectRatio = imageEyebrow.getWidth() / imageEyebrow.getHeight();
                                imageViewEyebrow = new ImageView(imageEyebrow);
                                imageViewEyebrow.setPreserveRatio(true);
                                imageViewEyebrow.setFitHeight(320);
                                imageViewEyebrow.setFitWidth(aspectRatio * 320);

                                imageViewEyebrow.setViewOrder(-1);

                                canvas.getChildren().add(imageViewEyebrow);
                                draggable(imageViewEyebrow);

                                eyebrowAdd = false;
                            } else {

                                imageViewEyebrow.setImage(new Image(eyebrowFeature[1][countFuncCopy]));

                            }
                        }
                    });

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        eyebrowSize.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            double set = (double) newValue - (double) oldValue;
            double setY = imageViewEyebrow.getFitHeight() + set;
            double aspectRatio = imageEyebrow.getWidth() / imageEyebrow.getHeight();
            double setX = aspectRatio * setY;
            imageViewEyebrow.setFitHeight(setY);
            imageViewEyebrow.setFitWidth(setX);
        });
    }

    //Displays requested eyebrow features on button click
    public void searchEyebrow() {

        String search = eyebrowSearchInput.getText();
        String sql = "SELECT featureThumb,featurePath FROM featureset WHERE tags LIKE '%" + search + "%' AND featureType LIKE '%eyebrow%'";
        displayEyebrow.getChildren().clear();

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            String[][] eyebrowFeature = new String[2][10];

            int countEyebrow = 0;
            while (rs.next()) {
                eyebrowFeature[0][countEyebrow] = rs.getString("featureThumb").toString();
                eyebrowFeature[1][countEyebrow] = rs.getString("featurePath").toString();
                countEyebrow++;
            }

            int countFunc = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++, countFunc++) {

                    displayEyebrow.add(new ImageView(new Image(eyebrowFeature[0][countFunc])), j, i);
                    int countFuncCopy = countFunc;
                    displayEyebrow.getChildren().get(countFuncCopy).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent e) {
                            if (eyebrowAdd) {

                                imageEyebrow = new Image(eyebrowFeature[1][countFuncCopy]);
                                double aspectRatio = imageEyebrow.getWidth() / imageEyebrow.getHeight();
                                imageViewEyebrow = new ImageView(imageEyebrow);
                                imageViewEyebrow.setPreserveRatio(true);
                                imageViewEyebrow.setFitHeight(320);
                                imageViewEyebrow.setFitWidth(aspectRatio * 320);

                                imageViewEyebrow.setViewOrder(-1);

                                canvas.getChildren().add(imageViewEyebrow);
                                draggable(imageViewEyebrow);

                                eyebrowAdd = false;
                            } else {

                                imageViewEyebrow.setImage(new Image(eyebrowFeature[1][countFuncCopy]));

                            }
                        }
                    });

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Displays moustache features on app load
    public void moustacheDisplay() {
        String sql = "SELECT featureThumb,featurePath FROM featureset WHERE featureType LIKE '%moustache%'";
        displayMoustache.getChildren().clear();

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            String[][] moustacheFeature = new String[2][10];

            int countMoustache = 0;
            while (rs.next()) {
                moustacheFeature[0][countMoustache] = rs.getString("featureThumb").toString();
                moustacheFeature[1][countMoustache] = rs.getString("featurePath").toString();
                countMoustache++;
            }

            int countFunc = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++, countFunc++) {

                    displayMoustache.add(new ImageView(new Image(moustacheFeature[0][countFunc])), j, i);
                    int countFuncCopy = countFunc;
                    displayMoustache.getChildren().get(countFuncCopy).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent e) {
                            if (moustacheAdd) {

                                imageMoustache = new Image(moustacheFeature[1][countFuncCopy]);
                                double aspectRatio = imageMoustache.getWidth() / imageMoustache.getHeight();
                                imageViewMoustache = new ImageView(imageMoustache);
                                imageViewMoustache.setPreserveRatio(true);
                                imageViewMoustache.setFitHeight(100);
                                imageViewMoustache.setFitWidth(aspectRatio * 100);

                                imageViewMoustache.setViewOrder(-1);

                                canvas.getChildren().add(imageViewMoustache);
                                draggable(imageViewMoustache);

                                moustacheAdd = false;
                            } else {

                                imageViewMoustache.setImage(new Image(moustacheFeature[1][countFuncCopy]));

                            }
                        }
                    });

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        moustacheSize.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            double set = (double) newValue - (double) oldValue;
            double setY = imageViewMoustache.getFitHeight() + set;
            double aspectRatio = imageMoustache.getWidth() / imageMoustache.getHeight();
            double setX = aspectRatio * setY;
            imageViewMoustache.setFitHeight(setY);
            imageViewMoustache.setFitWidth(setX);
        });
    }

    //Displays requested moustache features on button click
    public void searchMoustache() {
        String search = moustacheSearchInput.getText();
        String sql = "SELECT featureThumb,featurePath FROM featureset WHERE tags LIKE '%" + search + "%' AND featureType LIKE '%moustache%'";
        displayMoustache.getChildren().clear();

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            String[][] moustacheFeature = new String[2][10];

            int countMoustache = 0;
            while (rs.next()) {
                moustacheFeature[0][countMoustache] = rs.getString("featureThumb").toString();
                moustacheFeature[1][countMoustache] = rs.getString("featurePath").toString();
                countMoustache++;
            }

            int countFunc = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++, countFunc++) {

                    displayMoustache.add(new ImageView(new Image(moustacheFeature[0][countFunc])), j, i);
                    int countFuncCopy = countFunc;
                    displayMoustache.getChildren().get(countFuncCopy).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent e) {
                            if (moustacheAdd) {

                                imageMoustache = new Image(moustacheFeature[1][countFuncCopy]);
                                double aspectRatio = imageMoustache.getWidth() / imageMoustache.getHeight();
                                imageViewMoustache = new ImageView(imageMoustache);
                                imageViewMoustache.setPreserveRatio(true);
                                imageViewMoustache.setFitHeight(100);
                                imageViewMoustache.setFitWidth(aspectRatio * 100);

                                imageViewMoustache.setViewOrder(-3);

                                canvas.getChildren().add(imageViewMoustache);
                                draggable(imageViewMoustache);

                                moustacheAdd = false;
                            } else {

                                imageViewMoustache.setImage(new Image(moustacheFeature[1][countFuncCopy]));

                            }
                        }
                    });

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    //Displays ear features on app load
//    public void earsDisplay() {
//        String sql = "SELECT featureThumb,featurePath FROM featureset WHERE featureType LIKE '%ears%'";
//        displayEars.getChildren().clear();
//
//        try {
//            PreparedStatement pst = connection.prepareStatement(sql);
//            ResultSet rs = pst.executeQuery();
////            if (rs.next()) {
////                errorlog.setText("WORKS");
////            } else {
////                errorlog.setText("DARD MOMENT");
////            }
//
//            String[][] earsFeature = new String[2][10];
//
//            int countEars = 0;
////            headThumbnail.add(rs.getString("featureThumb").toString());
////            headFeaturePath.add(rs.getString("featurePath").toString());
//            while (rs.next()) {
//                earsFeature[0][countEars] = rs.getString("featureThumb").toString();
//                earsFeature[1][countEars] = rs.getString("featurePath").toString();
//                countEars++;
////                headThumbnail.add(rs.getString("featureThumb").toString());
////                headFeaturePath.add(rs.getString("featurePath").toString());
//            }
//
//            int countFunc = 0;
//            for (int i = 0; i < 5; i++) {
//                for (int j = 0; j < 4; j++, countFunc++) {
//
//                    displayEars.add(new ImageView(new Image(earsFeature[0][countFunc])), j, i);
//                    int countFuncCopy = countFunc;
//                    displayEars.getChildren().get(countFuncCopy).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//
//                        public void handle(MouseEvent e) {
//                            if (earsAdd) {
//
//                                imageEars = new Image(earsFeature[1][countFuncCopy]);
//                                double aspectRatio = imageEars.getWidth() / imageEars.getHeight();
//                                imageViewEars = new ImageView(imageEars);
//                                imageViewEars.setPreserveRatio(true);
//                                imageViewEars.setFitHeight(320);
//                                imageViewEars.setFitWidth(aspectRatio * 320);
//
//                                imageViewEars.setViewOrder(-1);
//
//                                canvas.getChildren().add(imageViewEars);
//                                draggable(imageViewEars);
//
//                                earsAdd = false;
//                            } else {
//
//                                imageViewEars.setImage(new Image(earsFeature[1][countFuncCopy]));
//
//                            }
//                        }
//                    });
//
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        earsSize.valueProperty().addListener((observable, oldValue, newValue) ->
//        {
//            double set = (double) newValue - (double) oldValue;
//            double setY = imageViewEars.getFitHeight() + set;
//            double aspectRatio = imageEars.getWidth() / imageEars.getHeight();
//            double setX = aspectRatio * setY;
//            imageViewEars.setFitHeight(setY);
//            imageViewEars.setFitWidth(setX);
//        });
//    }
//
//
//    //Displays requested ear features on button click
//    public void searchEars() {
//
//        String search = earsSearchInput.getText();
//        String sql = "SELECT featureThumb,featurePath FROM featureset WHERE tags LIKE '%" + search + "%' AND featureType LIKE '%head%'";
//        displayEars.getChildren().clear();
//
//        try {
//            PreparedStatement pst = connection.prepareStatement(sql);
//            ResultSet rs = pst.executeQuery();
////            if (rs.next()) {
////                errorlog.setText("WORKS");
////            } else {
////                errorlog.setText("DARD MOMENT");
////            }
//
//            String[][] earsFeature = new String[2][10];
//
//            int countEars = 0;
////            headThumbnail.add(rs.getString("featureThumb").toString());
////            headFeaturePath.add(rs.getString("featurePath").toString());
//            while (rs.next()) {
//                earsFeature[0][countEars] = rs.getString("featureThumb").toString();
//                earsFeature[1][countEars] = rs.getString("featurePath").toString();
//                countEars++;
////                headThumbnail.add(rs.getString("featureThumb").toString());
////                headFeaturePath.add(rs.getString("featurePath").toString());
//            }
//
//            int countFunc = 0;
//            for (int i = 0; i < 5; i++) {
//                for (int j = 0; j < 4; j++, countFunc++) {
//
//                    displayEars.add(new ImageView(new Image(earsFeature[0][countFunc])), j, i);
//                    int countFuncCopy = countFunc;
//                    displayEars.getChildren().get(countFuncCopy).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//
//                        public void handle(MouseEvent e) {
//                            if (earsAdd) {
//
//                                imageEars = new Image(earsFeature[1][countFuncCopy]);
//                                double aspectRatio = imageEars.getWidth() / imageEars.getHeight();
//                                imageViewEars = new ImageView(imageEars);
//                                imageViewEars.setPreserveRatio(true);
//                                imageViewEars.setFitHeight(320);
//                                imageViewEars.setFitWidth(aspectRatio * 320);
//
//                                imageViewEars.setViewOrder(-1);
//
//                                canvas.getChildren().add(imageViewEars);
//                                draggable(imageViewEars);
//
//                                earsAdd = false;
//                            } else {
//
//                                imageViewEars.setImage(new Image(earsFeature[1][countFuncCopy]));
//
//                            }
//                        }
//                    });
//
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    //Common function to make elements draggable
    Consumer<? super Node> draggable(ImageView node) {

        try {
            node.setOnMousePressed(e -> {
                startX = e.getSceneX() - node.getTranslateX();
                startY = e.getSceneY() - node.getTranslateY();
            });

            node.setOnMouseDragged(e -> {
                if (e.getSceneX() - startX > -0.5 * (canvas.getLayoutBounds().getWidth() - node.getFitWidth()) && e.getSceneX() - startX < 0.5 * (canvas.getLayoutBounds().getWidth() - node.getFitWidth())) {
                    node.setTranslateX(e.getSceneX() - startX);
                }
                if (e.getSceneY() - startY > -0.5 * (canvas.getLayoutBounds().getHeight() - node.getFitHeight()) && e.getSceneY() - startY < 0.5 * (canvas.getLayoutBounds().getHeight() - node.getFitHeight())) {
                    node.setTranslateY(e.getSceneY() - startY);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
