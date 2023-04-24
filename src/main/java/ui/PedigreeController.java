package ui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.member.pet.Pet;
import model.service.PetService;
import model.tree.Tree;
import ui.view.commands.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class PedigreeController {

    @FXML
    private Button mainMenuButton;

    @FXML
    private TableView<Pet> petTableView;

    @FXML
    private TableColumn<Pet, String> nameColumn;

    @FXML
    private TableColumn<Pet, String> genderColumn;

    @FXML
    private TableColumn<Pet, String> typeColumn;

    @FXML
    private TableColumn<Pet, String> birthDateColumn;

    @FXML
    private TableColumn<Pet, String> deathDateColumn;

    @FXML
    private TableColumn<Pet, Integer> ageColumn;

    private ObservableList<Pet> list;
    private PetService petService;
    private File file;
    private Stage stage;
    private ArrayList<Command<Pet>> commandList;
    private Command<Pet> command;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        this.petService = new PetService();
        this.list = FXCollections.observableArrayList();
        showTable();
        this.petTableView.setItems(this.list);
        this.commandList = new ArrayList<>();
        this.commandList.add(new ParentsCommand<>());
        this.commandList.add(new ChildrenCommand<>());
        this.commandList.add(new SiblingsCommand<>());
        this.commandList.add(new CoupleCommand<>());
    }

    /**
     * Uploading a tree from given file from main menu
     */
    public void onStart() {
        if (this.file != null) {
            Tree<Pet> tree = new Tree<>();
            try {
                this.petService.open(this.file);
                tree = this.petService.getTree();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
            for (Pet pet : tree) {
                this.list.add(pet);
            }
        }
        this.petTableView.setItems(this.list);
        this.stage.setOnCloseRequest(event ->
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Family Tree App");
            alert.setHeaderText(null);
            alert.setContentText("Save before close?");

            Image image = new Image("D:\\УЧЕБА\\FamilyTreeApp\\src\\main\\resources\\ui\\tree.png");
            ImageView icon = new ImageView(image);
            icon.setFitHeight(48);
            icon.setFitWidth(48);
            alert.getDialogPane().setGraphic(icon);
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(image);

            ButtonType buttonTypeNotSave = new ButtonType("Don't save", ButtonBar.ButtonData.LEFT);
            ButtonType buttonTypeSave = new ButtonType("Save");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeNotSave, buttonTypeSave, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == buttonTypeCancel) {
                    event.consume();
                } else if (result.get() == buttonTypeSave) {
                    saveOut();
                }
            }
        });
    }

    /**
     * Returns to main menu
     */
    @FXML
    private void mainMenu() {
        this.mainMenuButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Family Tree App");
        stage.getIcons().add(new Image("D:\\УЧЕБА\\FamilyTreeApp\\src\\main\\resources\\ui\\tree.png"));
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Opens a dialog to create new pet
     */
    @FXML
    private void createNew() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newPet.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CreatePetController controller = loader.getController();
        controller.setPetService(this.petService);
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Family Tree App - Create new");
        stage.getIcons().add(new Image("D:\\УЧЕБА\\FamilyTreeApp\\src\\main\\resources\\ui\\tree.png"));
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.showAndWait();
        if (controller.getPet() != null) {
            this.list.add(controller.getPet());
        }
    }

    /**
     * Edits chosen pet's data
     */
    @FXML
    private void edit() {
        int index = this.petTableView.getSelectionModel().getSelectedIndex();

        if (index >= 0 && this.petTableView.getSelectionModel().getSelectedItem() != null) {
            Pet pet = this.petTableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editPet.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            EditPetController controller = loader.getController();
            controller.setPet(pet);
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Family Tree App - Edit");
            stage.getIcons().add(new Image("D:\\УЧЕБА\\FamilyTreeApp\\src\\main\\resources\\ui\\tree.png"));
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            controller.showPetData();
            stage.showAndWait();
            this.list.clear();
            for (Pet element : this.petService.getTree()) {
                this.list.add(element);
            }
        }
    }

    /**
     * Sets relation connections between pets
     */
    @FXML
    private void connect() {
        int index = this.petTableView.getSelectionModel().getSelectedIndex();
        if (index >= 0 && this.petTableView.getSelectionModel().getSelectedItem() != null) {
            Pet pet = this.petTableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("connectPet.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ConnectPetController controller = loader.getController();
            controller.setPrimaryPet(pet);
            controller.setList(this.list);
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Family Tree App - Connect");
            stage.getIcons().add(new Image("D:\\УЧЕБА\\FamilyTreeApp\\src\\main\\resources\\ui\\tree.png"));
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            controller.showPetData();
        }
    }

    /**
     * Deletes chosen pet
     */
    @FXML
    private void deletePet() {
        int index = this.petTableView.getSelectionModel().getSelectedIndex();

        if (index >= 0 && this.petTableView.getSelectionModel().getSelectedItem() != null) {
            Pet pet = this.petTableView.getSelectionModel().getSelectedItem();
            boolean result = this.petService.delete(pet);
            if (result) System.out.printf("Pet deleted: %s\n", pet);
            this.petTableView.getItems().remove(index);
        }
    }

    /**
     * opens a file with list of pets saved before
     */
    @FXML
    private void open() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("D:\\УЧЕБА\\FamilyTreeApp"));
        fileChooser.setTitle("Open file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pedigree file (*.ped)", "*.ped"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            Tree<Pet> tree = new Tree<>();
            try {
                this.petService.open(file);
                tree = this.petService.getTree();
                System.out.printf("File uploaded: %s\n", file.getAbsoluteFile());
            } catch (IOException | ClassNotFoundException | RuntimeException e) {
                System.out.println(e.getMessage());
            }
            if (!this.list.isEmpty()) {
                this.list.clear();
            }
            for (Pet pet : tree) {
                this.list.add(pet);
            }
        }
    }

    /**
     * Exports the tree in .txt format
     */
    @FXML
    private void saveTxt() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("D:\\УЧЕБА\\FamilyTreeApp"));
        fileChooser.setTitle("Save as");
        fileChooser.setInitialFileName("new_pedigree");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text document (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                boolean result = this.petService.saveTxt(file);
                if (result) System.out.printf("File saved: %s\n", file.getAbsoluteFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Saves the tree in .ped format
     */
    @FXML
    private void saveOut() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("D:\\УЧЕБА\\FamilyTreeApp"));
        fileChooser.setTitle("Save as");
        fileChooser.setInitialFileName("new_pedigree");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pedigree file (*.ped)", "*.ped"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                boolean result = this.petService.saveOutput(file);
                if (result) System.out.printf("File saved: %s\n", file.getAbsoluteFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Sets a table to display
     */
    private void showTable() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        genderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender().toString()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
        birthDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBirthDate()));
        deathDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeathDate()));
        ageColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAge()).asObject());
        petTableView.getItems();

        petTableView.setRowFactory(tv -> {
            TableRow<Pet> row = new TableRow<>();
            /*
            Shows a tooltip on row click and runs edit function on double click
             */
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    row.setTooltip(new Tooltip(row.getItem().toString()));
                }
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    edit();
                }
            });
            /*
            Shows a menu for building a visual tree on right button mouse click
            */
            ContextMenu showFamilyMenu = new ContextMenu();
            for (Command<Pet> command : this.commandList) {
                MenuItem menuItem = new MenuItem(command.getDescription());
                showFamilyMenu.getItems().add(menuItem);
                menuItem.setOnAction(actionEvent -> {
                    this.command = command;
                    build();
                });
            }
            row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(showFamilyMenu));
            return row;
        });
    }

    /**
     * Runs the private function to show visual tree of selected pet
     */
    @FXML
    private void build() {
        int index = this.petTableView.getSelectionModel().getSelectedIndex();
        if (index >= 0 && this.petTableView.getSelectionModel().getSelectedItem() != null) {
            Pet pet = this.petTableView.getSelectionModel().getSelectedItem();
            showView(pet);
        }
    }

    /**
     * Shows visual tree for selected pet
     *
     * @param member selected pet
     */
    private void showView(Pet member) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ScrollPane root = loader.getRoot();
        ViewController<Pet> viewController = loader.getController();
        viewController.setMember(member);
        viewController.setCommand(this.command);
        Stage stage = new Stage();
        stage.setTitle("Family Tree App - Family Tree");
        stage.getIcons().add(new Image("D:\\УЧЕБА\\FamilyTreeApp\\src\\main\\resources\\ui\\tree.png"));
        stage.setScene(new Scene(root));
        stage.show();
        viewController.show();
    }

    /**
     * Sets a file as this file
     *
     * @param file file to be assigned
     */
    public void setFile(File file) {
        this.file = file;
    }
}
