package edu.wpi.cs3733.c22.teamB.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.c22.teamB.Bapp;
import edu.wpi.cs3733.c22.teamB.entity.*;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MapEditorController{

    public javafx.scene.control.TextField idField;
    public TextField xCoordinate;
    public TextField yCoordinate;
    public JFXComboBox floor;
    public JFXComboBox nodeType;
    public TextField shortName;
    public TextField longName;
    public Label header1;
    public Label header2;
    public Label header3;
    public Label header4;
    public Label header5;
    public Label header6;
    public Label header7;
    public Label header8;
    public JFXButton deleteButton;
    public JFXComboBox statusField;
    String selectedPoint;
    Circle selectedPnt;
    double sceneWidth;
    double sceneHeight;
    double imageHeight;
    double imageWidth;
    double orgSceneX, orgSceneY;
    LocationDBI locationDBI = new LocationDBI();
    List<Location> locationList = locationDBI.getAllNodes();
    MedicalEquipmentDBI medicalDBI = new MedicalEquipmentDBI();
    List<MedicalEquipment> medicalList = medicalDBI.getAllNodes();
    CSVRestoreBackupController backupper = new CSVRestoreBackupController();
    String currentFloor = "03";
    boolean addState = false;

    Image firstFloorImage = new Image("/edu/wpi/cs3733/c22/teamB/images/thefirstfloor.png");
    Image secondFloorImage = new Image("/edu/wpi/cs3733/c22/teamB/images/thesecondfloor.png");
    Image lowerLevel2Image = new Image("/edu/wpi/cs3733/c22/teamB/images/thelowerlevel2.png");
    Image lowerLevel1Image = new Image("/edu/wpi/cs3733/c22/teamB/images/thelowerlevel1.png");
    Image thirdFloorImage = new Image("/edu/wpi/cs3733/c22/teamB/images/thirdFloorMap.png");
    Image medical = new Image("/edu/wpi/cs3733/c22/teamB/images/medical.png");
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView imageView;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton modifyButton;



    @FXML
    private JFXButton submitModifyButton;
    @FXML private JFXButton goToL1Button;
    @FXML private JFXButton goToL2Button;
    @FXML private JFXButton goTo1Button;
    @FXML private JFXButton goTo2Button;
    @FXML private JFXButton goTo3Button;
    

    void setEditFieldsVisible(boolean isVisible){
        header1.setVisible(isVisible);
        header2.setVisible(isVisible);
        header3.setVisible(isVisible);
        header4.setVisible(isVisible);
        header6.setVisible(isVisible);
        header7.setVisible(isVisible);
        header8.setVisible(isVisible);
        idField.setVisible(isVisible);
        xCoordinate.setVisible(isVisible);
        yCoordinate.setVisible(isVisible);
        floor.setVisible(isVisible);
        //statusField.setVisible(isVisible);
        //building.setVisible(isVisible);
        nodeType.setVisible(isVisible);
        longName.setVisible(isVisible);
        shortName.setVisible(isVisible);
        submitModifyButton.setVisible(isVisible);
    }

    //Scene x coordinate to image x coordinate
    double getImageX(double desiredX){
        //The width of the map in image coordinates
        double mapWidth = imageWidth*(sceneHeight/imageHeight);
        //System.out.println("mapWidth = " + mapWidth);
        //The offset from the side of the scene
        double xOffset = 0;//(sceneWidth-mapWidth)/2.0;
        //Return the new coordinate
        return desiredX*(mapWidth/imageWidth) + xOffset;
    }

    //Scene y coordinate to image y coordinate
    double getImageY(double desiredY){
        //The map is fit to the window's height
        return (desiredY/imageHeight)*sceneHeight;
    }

    //Scene x coordinate to image x coordinate
    double getMapX(double desiredX){
        //The width of the map in image coordinates
        double mapWidth = imageWidth*(sceneHeight/imageHeight);
        //System.out.println("mapWidth = " + mapWidth);
        //The offset from the side of the scene
        double xOffset = 0;//(sceneWidth-mapWidth)/2.0;
        //Return the new coordinate
        return (desiredX-xOffset)/(mapWidth/imageWidth);
    }

    //Scene y coordinate to image y coordinate
    double getMapY(double desiredY){
        //The map is fit to the window's height
        return desiredY/(sceneHeight/imageHeight);
    }

    public void onPointClick(Circle testPoint){
        //Deselect previous point, make black
        for(Location local: locationList){
            if(local.getNodeID().equals(selectedPoint)){
                //addPoint(local.getNodeID(),local.getXcoord(),local.getYcoord(),Color.BLACK);
                selectedPnt.setFill(Color.BLACK);
            }
        }
        //Select current point
        testPoint.setFill(Color.RED);
        //System.out.println(testPoint.idProperty().get());
        selectedPoint = (testPoint.idProperty().get());
        selectedPnt = testPoint;
    }

    //Add a point to the map using image coordinates. Set up onclick.
    public Circle addPoint(String ID, double x, double y, Color color){
        //Create the point
        Circle testPoint = new Circle(getImageX(x),getImageY(y),3);
        //Add the point to the anchorPane's children
        anchorPane.getChildren().add(testPoint);
        testPoint.setFill(color);
        //Set point ID
        testPoint.idProperty().set(ID);
        //Set up onclick events
        testPoint.setOnMousePressed(new EventHandler <MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                orgSceneX = event.getSceneX();
                orgSceneY = event.getSceneY();
                modifyButton.setOpacity(1);
                modifyButton.setDisable(false);
                deleteButton.setOpacity(1);
                deleteButton.setDisable(false);
                onPointClick(testPoint);
                testPoint.setMouseTransparent(true);
                event.setDragDetect(true);
            }
        });

        testPoint.setOnMouseReleased(new EventHandler <MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                if(selectedPnt == testPoint) {
                    Location temp = locationDBI.getNode(selectedPoint);
                    locationDBI.updateNode(new Location(selectedPnt.getId(), (int) getMapX(event.getX()), (int) getMapY(event.getY()), temp.getFloor(), temp.getBuilding(), temp.getNodeType(), temp.getShortName(), temp.getLongName()));
                    refresh();
                    testPoint.setMouseTransparent(false);
                }
            }
        });

        testPoint.setOnMouseDragged((t) -> {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;

            Circle c = (Circle) (t.getSource());

            c.setCenterX(c.getCenterX()+ offsetX);
            c.setCenterY(c.getCenterY() + offsetY);

            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
        });


        return testPoint;
    }

    //Add points from DB
    public void addPoints(){
        for(Location local: locationList){
            if(local.getFloor().equals(currentFloor)) {
                String ID = local.getNodeID();
                double x = local.getXcoord();
                double y = local.getYcoord();
                addPoint(ID, x, y, Color.BLACK);
            }
        }



        for(MedicalEquipment local: medicalList){
            if(local.getLocation().getFloor().equals(currentFloor)) {
                String ID = local.getEquipmentID();
                double x = local.getLocation().getXcoord() -10; //TODO fix for future iterations
                double y = local.getLocation().getYcoord();
                addPointMedical(ID, x, y, Color.BLUE);
            }
        }


    }

    public void addPointMedical(String ID, double x, double y, Color color){
        //Create the point
        //getImageX(x),getImageY(y)
        ImageView testImg = new ImageView(medical);
        //Add the point to the anchorPane's children
        anchorPane.getChildren().add(testImg);
        //Set point ID
        testImg.idProperty().set(ID);
        testImg.setX(getImageX(x));
        testImg.setY(getImageY(y));
        testImg.setPreserveRatio(true);
        testImg.setFitWidth(15);

    }

    void removeAllPoints(){
        anchorPane.getChildren().remove(1,anchorPane.getChildren().size());
    }

    void deleteSelectedNode(){
        anchorPane.getChildren().remove(selectedPnt);
        locationDBI.deleteNode(selectedPnt.getId());
    }

    @FXML public void refresh(){
        locationList = locationDBI.getAllNodes();
        removeAllPoints();
        addPoints();
    }


    @FXML public void goToL2(){
        currentFloor = "L2";
        goTo();
    }
    @FXML public void goToL1(){
        currentFloor = "L1";
        goTo();
    }
    @FXML public void goTo1(){
        currentFloor = "01";
        goTo();
    }
    @FXML public void goTo2(){
        currentFloor = "02";
        goTo();
    }
    @FXML public void goTo3(){
        currentFloor = "03";
        goTo();
    }

    @FXML public void saveToCSV(){
        try {
            backupper.Backup();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        refresh();
    }

    @FXML public void goTo(){
        goTo1Button.setStyle("-fx-background-color: #eaeaea");
        goTo2Button.setStyle("-fx-background-color: #eaeaea");
        goTo3Button.setStyle("-fx-background-color: #eaeaea");
        goToL1Button.setStyle("-fx-background-color: #eaeaea");
        goToL2Button.setStyle("-fx-background-color: #eaeaea");

        switch (currentFloor) {
            case "01":
                imageView.setImage(firstFloorImage);
                goTo1Button.setStyle("-fx-background-color: #007fff");
                break;
            case "02":
                imageView.setImage(secondFloorImage);
                goTo2Button.setStyle("-fx-background-color: #007fff");
                break;
            case"L2":
                imageView.setImage(lowerLevel2Image);
                goToL2Button.setStyle("-fx-background-color: #007fff");
                break;
            case"L1":
                imageView.setImage(lowerLevel1Image);
                goToL1Button.setStyle("-fx-background-color: #007fff");
                break;
            default:
                imageView.setImage(thirdFloorImage);
                goTo3Button.setStyle("-fx-background-color: #007fff");
            break;
        }
        selectedPoint = null;
        selectedPnt = null;
        modifyButton.setOpacity(0.5);
        modifyButton.setDisable(true);
        deleteButton.setOpacity(0.5);
        deleteButton.setDisable(true);
        refresh();
    }

    @FXML public void delete(){
        deleteSelectedNode();
    }

    @FXML
    public void initialize(){
        //Bapp.getPrimaryStage().setFullScreen(false);
        Bapp.getPrimaryStage().setMaximized(true);
        Bapp.getPrimaryStage().resizableProperty().set(false);
        sceneWidth = Bapp.getPrimaryStage().getScene().getWidth();
        sceneHeight = Bapp.getPrimaryStage().getScene().getHeight();
        //System.out.println("Scene Width = " + sceneWidth);
        //System.out.println("Scene Height = " + sceneHeight);
        imageView.setFitHeight(sceneHeight);
        imageHeight = imageView.getImage().getHeight();
        imageWidth = imageView.getImage().getWidth();
        setEditFieldsVisible(false);
        modifyButton.setOpacity(0.5);
        modifyButton.setDisable(true);
        deleteButton.setOpacity(0.5);
        deleteButton.setDisable(true);
        goTo3Button.setStyle("-fx-background-color: #007fff");
        nodeType.getItems().addAll("PATI","STOR","DIRT","HALL","ELEV","REST","STAI","DEPT","LABS","INFO","CONF","EXIT","RETL","SERV");
        floor.getItems().addAll("L2","L1","01","02","03");
        floor.setValue(currentFloor);
        addPoint("1",0,0,Color.ORANGE);
        addPoint("2",imageWidth,imageHeight, Color.RED);
        addPoints();
    }

    @FXML public void modify(){
        setEditFieldsVisible(true);
        Location local = locationDBI.getNode(selectedPoint);
        idField.setText(selectedPoint);
        xCoordinate.setText(String.valueOf(local.getXcoord()));
        yCoordinate.setText(String.valueOf(local.getYcoord()));
        floor.setValue(local.getFloor());
        nodeType.setValue(local.getNodeType());
        shortName.setText(local.getShortName());
        longName.setText(local.getLongName());
    }

    public void move(int x, int y){

    }

    public void submitModify(ActionEvent actionEvent) {
        Location changedNode = new Location(idField.getText(),Integer.valueOf(xCoordinate.getText()),Integer.valueOf(yCoordinate.getText()),floor.getValue().toString(),"TOWER",nodeType.getValue().toString(),shortName.getText(),longName.getText());
        locationDBI.updateNode(changedNode);
        refresh();
        setEditFieldsVisible(false);
        modifyButton.setOpacity(0.5);
        modifyButton.setDisable(true);
        deleteButton.setOpacity(0.5);
        deleteButton.setDisable(true);
    }

    @FXML
    void imageClicked(MouseEvent event) {
        if(addState){
            //Set up ID, coordinates, etc
            int nextID = 0;
            //Generate next unique ID
            while(locationDBI.isInTable(String.valueOf(nextID))){
                nextID++;
            }
            //Get coordinates in the space of the original map
            double xCord = getMapX(event.getSceneX());
            double yCord = getMapY(event.getSceneY());
            //Adds point to the map
            selectedPnt = addPoint(String.valueOf(nextID),xCord,yCord,Color.YELLOW);
            //Create new location
            Location newLoc = new Location(String.valueOf(nextID),(int)xCord,(int)yCord,currentFloor,"Building","Node Type","Long Name","Short Name");
            //Add new location to the database
            locationDBI.insertNode(newLoc);

            selectedPoint = newLoc.getNodeID();
            modify();

            //FEATURE ON HOLD
            //editNodeDetails(newLoc);


            //Set button back to add mode
            addButton.setOpacity(1);
            addButton.setText("Add");
            //No longer adding a node
            addState = false;
        }
    }


    @FXML
    void homeButton(ActionEvent event) {
        // Try to go home
        try {
            Bapp.getPrimaryStage().resizableProperty().set(true);
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/c22/teamB/views/Home.fxml"));
            Bapp.getPrimaryStage().getScene().setRoot(root);
            // Print stack trace if unable to go home
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML public void add(){
        if(addState){
            addState = false;
            addButton.setOpacity(1);
            addButton.setText("Add");
        } else{
            addState = true;
            addButton.setOpacity(0.5);
            addButton.setText("Cancel Add");
        }
    }

    @FXML
    void loadFromCSV(ActionEvent event) {
        try {
            backupper.Restore();
            refresh();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }




}
