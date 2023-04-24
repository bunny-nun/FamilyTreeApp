package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class StartController {

    @FXML
    private TextField pathWindow;

    @FXML
    private RadioButton petNewRadioButton;

    @FXML
    private Button createButton;

    private File file;

    @FXML
    private void initialize() {
    }

    /**
     * Starts a main interface of the application with empty data
     */
    @FXML
    private void create() {
        if (this.petNewRadioButton.isSelected()) {
            this.createButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pedigree.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Family Tree App");
            stage.getIcons().add(new Image("D:\\УЧЕБА\\FamilyTreeApp\\src\\main\\resources\\ui\\tree.png"));
            stage.setScene(new Scene(root));
            PedigreeController pedigreeController = loader.getController();
            pedigreeController.setStage(stage);
            stage.show();
            pedigreeController.onStart();

        } else {
            this.createButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("family-tree.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Family Tree App");
            stage.getIcons().add(new Image("D:\\УЧЕБА\\FamilyTreeApp\\src\\main\\resources\\ui\\tree.png"));
            stage.setScene(new Scene(root));
            FamilyTreeController familyTreeController = loader.getController();
            familyTreeController.setStage(stage);
            stage.show();
            familyTreeController.onStart();
        }
    }

    /**
     * Opens a dialog to choose a file for upload and saves the absolut path in text field
     */
    @FXML
    private void openClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("D:\\УЧЕБА\\FamilyTreeApp"));
        fileChooser.setTitle("Open file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Any tree", "*.fam", "*.ped"),
                new FileChooser.ExtensionFilter("FamilyTree file (*.fam)", "*.fam"),
                new FileChooser.ExtensionFilter("Pedigree file (*.ped)", "*.ped"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            this.file = file;
            this.pathWindow.setText(file.getAbsolutePath());
            System.out.printf("File uploaded: %s\n", file.getAbsoluteFile());
        }
    }

    /**
     * Starts a main interface of the application with data uploaded from the file
     */
    @FXML
    private void startClick() {
        if (!this.pathWindow.getText().isEmpty()) {
            if (this.pathWindow.getText().contains(".ped")) {
                this.createButton.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("pedigree.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Family Tree App");
                stage.getIcons().add(new Image("D:\\УЧЕБА\\FamilyTreeApp\\src\\main\\resources\\ui\\tree.png"));
                stage.setScene(new Scene(root));
                PedigreeController pedigreeController = loader.getController();
                pedigreeController.setFile(this.file);
                pedigreeController.setStage(stage);
                stage.show();
                pedigreeController.onStart();
            } else if (this.pathWindow.getText().contains(".fam")) {
                this.createButton.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("family-tree.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Family Tree App");
                stage.getIcons().add(new Image("D:\\УЧЕБА\\FamilyTreeApp\\src\\main\\resources\\ui\\tree.png"));
                stage.setScene(new Scene(root));
                FamilyTreeController familyTreeController = loader.getController();
                familyTreeController.setFile(this.file);
                familyTreeController.setStage(stage);
                stage.show();
                familyTreeController.onStart();
            }
        }
    }
}