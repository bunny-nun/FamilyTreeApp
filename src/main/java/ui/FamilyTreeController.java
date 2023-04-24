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
import model.member.person.Person;
import model.service.PersonService;
import model.tree.Tree;
import ui.view.commands.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class FamilyTreeController {

    @FXML
    private Button mainMenuButton;

    @FXML
    private TableView<Person> personTableView;

    @FXML
    private TableColumn<Person, String> firstNameColumn;

    @FXML
    private TableColumn<Person, String> patronymicNameColumn;

    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private TableColumn<Person, String> maidenNameColumn;

    @FXML
    private TableColumn<Person, String> genderColumn;

    @FXML
    private TableColumn<Person, String> birthDateColumn;

    @FXML
    private TableColumn<Person, String> deathDateColumn;

    @FXML
    private TableColumn<Person, Integer> ageColumn;
    private ObservableList<Person> list;
    private PersonService personService;
    private File file;
    private Stage stage;
    private ArrayList<Command<Person>> commandList;
    private Command<Person> command;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        this.personService = new PersonService();
        this.list = FXCollections.observableArrayList();
        showTable();
        this.personTableView.setItems(this.list);
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
            Tree<Person> tree = new Tree<>();
            try {
                this.personService.open(this.file);
                tree = this.personService.getTree();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
            for (Person person : tree) {
                this.list.add(person);
            }
        }
        this.personTableView.setItems(this.list);
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
     * Opens a dialog to create new person
     */
    @FXML
    private void createNew() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newPerson.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CreatePersonController controller = loader.getController();
        controller.setPersonService(this.personService);
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Family Tree App - Create new");
        stage.getIcons().add(new Image("D:\\УЧЕБА\\FamilyTreeApp\\src\\main\\resources\\ui\\tree.png"));
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.showAndWait();
        if (controller.getPerson() != null) {
            this.list.add(controller.getPerson());
        }
    }

    /**
     * Edits chosen person's data
     */
    @FXML
    private void edit() {
        int index = this.personTableView.getSelectionModel().getSelectedIndex();

        if (index >= 0 && this.personTableView.getSelectionModel().getSelectedItem() != null) {
            Person person = this.personTableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editPerson.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            EditPersonController controller = loader.getController();
            controller.setPerson(person);
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Family Tree App - Edit");
            stage.getIcons().add(new Image("D:\\УЧЕБА\\FamilyTreeApp\\src\\main\\resources\\ui\\tree.png"));
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            controller.showPersonData();
            stage.showAndWait();
            this.list.clear();
            for (Person element : this.personService.getTree()) {
                this.list.add(element);
            }
        }
    }

    /**
     * Sets relation connections between persons
     */
    @FXML
    private void connect() {
        int index = this.personTableView.getSelectionModel().getSelectedIndex();
        if (index >= 0 && this.personTableView.getSelectionModel().getSelectedItem() != null) {
            Person person = this.personTableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("connectPerson.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ConnectPersonController controller = loader.getController();
            controller.setPersonService(this.personService);
            controller.setPrimaryPerson(person);
            controller.setList(this.list);
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Family Tree App - Connect");
            stage.getIcons().add(new Image("D:\\УЧЕБА\\FamilyTreeApp\\src\\main\\resources\\ui\\tree.png"));
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            controller.showPersonData();
        }
    }

    /**
     * Deletes chosen person
     */
    @FXML
    private void deletePerson() {
        int index = this.personTableView.getSelectionModel().getSelectedIndex();
        if (index >= 0 && this.personTableView.getSelectionModel().getSelectedItem() != null) {
            Person person = this.personTableView.getSelectionModel().getSelectedItem();
            boolean result = this.personService.delete(person);
            if (result) System.out.printf("Person deleted: %s\n", person);
            this.personTableView.getItems().remove(index);
        }
    }

    /**
     * opens a file with list of persons saved before
     */
    @FXML
    private void open() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("D:\\УЧЕБА\\FamilyTreeApp"));
        fileChooser.setTitle("Open file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("FamilyTree file (*.fam)", "*.fam"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            Tree<Person> tree = new Tree<>();
            try {
                this.personService.open(file);
                tree = this.personService.getTree();
                System.out.printf("File uploaded: %s\n", file.getAbsoluteFile());
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
            if (!this.list.isEmpty()) {
                this.list.clear();
            }
            for (Person person : tree) {
                this.list.add(person);
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
        fileChooser.setInitialFileName("new_family_tree");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text document (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                boolean result = this.personService.saveTxt(file);
                if (result) System.out.printf("File saved: %s\n", file.getAbsoluteFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Saves the tree in .fam format
     */
    @FXML
    private void saveOut() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("D:\\УЧЕБА\\FamilyTreeApp"));
        fileChooser.setTitle("Save as");
        fileChooser.setInitialFileName("new_family_tree");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("FamilyTree file (*.fam)", "*.fam"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                boolean result = this.personService.saveOutput(file);
                if (result) System.out.printf("File saved: %s\n", file.getAbsoluteFile());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Sets a table to display
     */
    private void showTable() {
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        patronymicNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatronymicName()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        maidenNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaidenName()));
        genderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender().toString()));
        birthDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBirthDate()));
        deathDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeathDate()));
        ageColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAge()).asObject());

        personTableView.setRowFactory(tv -> {
            TableRow<Person> row = new TableRow<>();
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
            for (Command<Person> command : this.commandList) {
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
     * Runs the private function to show visual tree of selected person
     */
    @FXML
    private void build() {
        int index = this.personTableView.getSelectionModel().getSelectedIndex();
        if (index >= 0 && this.personTableView.getSelectionModel().getSelectedItem() != null) {
            Person person = this.personTableView.getSelectionModel().getSelectedItem();
            showView(person);
        }
    }

    /**
     * Shows visual tree for selected person
     *
     * @param member selected person
     */
    private void showView(Person member) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ScrollPane root = loader.getRoot();
        ViewController<Person> viewController = loader.getController();
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
