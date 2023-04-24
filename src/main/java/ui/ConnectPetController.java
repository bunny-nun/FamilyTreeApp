package ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.member.Gender;
import model.member.pet.Pet;

public class ConnectPetController {

    @FXML
    private Label petNameText;

    @FXML
    private Label petBirthDateText;

    @FXML
    private RadioButton motherRadioButton;

    @FXML
    private RadioButton fatherRadioButton;

    @FXML
    private RadioButton childRadioButton;

    @FXML
    private RadioButton coupleRadioButton;

    @FXML
    private RadioButton noneRadioButton;

    @FXML
    private TableView<Pet> connectionTableView;

    @FXML
    private TableColumn<Pet, String> nameColumn;

    @FXML
    private TableColumn<Pet, String> birthDateColumn;

    @FXML
    private Label requiredFields;
    private Pet primaryPet;
    private Pet connectPet;
    private ObservableList<Pet> list;

    @FXML
    private void initialize() {
        this.list = FXCollections.observableArrayList();
        showTable();
    }

    /**
     * Shows connections made earlier between relatives
     */
    @FXML
    private void onTableClick() {
        this.requiredFields.setText("");
        this.noneRadioButton.setSelected(true);
        this.motherRadioButton.setDisable(false);
        this.fatherRadioButton.setDisable(false);
        this.childRadioButton.setDisable(false);
        this.coupleRadioButton.setDisable(false);
        this.motherRadioButton.setSelected(false);
        this.fatherRadioButton.setSelected(false);
        this.childRadioButton.setSelected(false);
        this.coupleRadioButton.setSelected(false);
        int index = this.connectionTableView.getSelectionModel().getSelectedIndex();
        if (index >= 0 && this.connectionTableView.getSelectionModel().getSelectedItem() != null) {
            this.connectPet = this.connectionTableView.getSelectionModel().getSelectedItem();
            if (this.primaryPet.equals(this.connectPet)) {
                this.noneRadioButton.setSelected(true);
                this.motherRadioButton.setDisable(true);
                this.fatherRadioButton.setDisable(true);
                this.childRadioButton.setDisable(true);
                this.coupleRadioButton.setDisable(true);
            }
            if (this.primaryPet.getMother() != null) {
                if (this.primaryPet.getMother().equals(this.connectPet)) {
                    this.motherRadioButton.setSelected(true);
                }
            }
            if (this.primaryPet.getFather() != null) {
                if (this.primaryPet.getFather().equals(this.connectPet)) {
                    this.fatherRadioButton.setSelected(true);
                }
            }
            if (!this.primaryPet.getChildren().isEmpty()) {
                if (this.primaryPet.getChildren().contains(this.connectPet)) {
                    this.childRadioButton.setSelected(true);
                }
            }
            if (this.primaryPet.getCouple() != null) {
                if (this.primaryPet.getCouple().equals(this.connectPet)) {
                    this.coupleRadioButton.setSelected(true);
                }
            }
        }
    }

    /**
     * Sets new connection between relatives
     */
    @FXML
    private void connect() {
        if (motherRadioButton.isSelected()) {
            this.primaryPet.setMother(this.connectPet);
            notification(this.primaryPet, this.connectPet, "sets", "mother");
        } else if (fatherRadioButton.isSelected()) {
            this.primaryPet.setFather(this.connectPet);
            notification(this.primaryPet, this.connectPet, "sets", "father");
        } else if (childRadioButton.isSelected()) {
            this.primaryPet.setChild(this.connectPet);
            notification(this.primaryPet, this.connectPet, "sets", "child");
        } else if (coupleRadioButton.isSelected()) {
            this.primaryPet.setCouple(this.connectPet);
            notification(this.primaryPet, this.connectPet, "sets", "couple");
        } else if (noneRadioButton.isSelected()) {
            if (this.primaryPet.getMother() != null) {
                if (this.primaryPet.getMother().equals(this.connectPet)) {
                    this.primaryPet.unSetMother(this.connectPet);
                    notification(this.primaryPet, this.connectPet, "unsets", "mother");
                }
            }
            if (this.primaryPet.getFather() != null) {
                if (this.primaryPet.getFather().equals(this.connectPet)) {
                    this.primaryPet.unSetFather(this.connectPet);
                    notification(this.primaryPet, this.connectPet, "unsets", "father");
                }
            }
            if (!this.primaryPet.getChildren().isEmpty()) {
                if (this.primaryPet.getChildren().contains(this.connectPet)) {
                    if (this.primaryPet.getGender().equals(Gender.Female)) {
                        this.connectPet.unSetMother(this.primaryPet);
                    } else {
                        this.connectPet.unSetFather(this.primaryPet);
                    }
                    notification(this.primaryPet, this.connectPet, "unsets", "child");
                }
            }
            if (this.primaryPet.getCouple() != null) {
                if (this.primaryPet.getCouple().equals(this.connectPet)) {
                    this.primaryPet.divorce(this.connectPet);
                    notification(this.primaryPet, this.connectPet, "unsets", "couple");
                }
            }
        } else {
            this.requiredFields.setText("*choose connection");
        }
    }

    /**
     * Displays the information of the pet being settled
     */
    public void showPetData() {
        if (this.primaryPet != null) {
            this.petNameText.setText(this.primaryPet.getName());
            this.petBirthDateText.setText(this.primaryPet.getBirthDate());
        }
        this.connectionTableView.setItems(this.list);
    }

    /**
     * Shows the table with all pets entered
     */
    private void showTable() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        birthDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBirthDate()));
        connectionTableView.setRowFactory(tv -> {
            TableRow<Pet> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    row.setTooltip(new Tooltip(row.getItem().toString()));
                }
            });
            return row;
        });
    }

    /**
     * Sets the pet to be primary pet
     *
     * @param primaryPet the pet chosen at main window
     */
    public void setPrimaryPet(Pet primaryPet) {
        this.primaryPet = primaryPet;
    }

    /**
     * sets the list to be this object's list
     *
     * @param list the list of all pets entered
     */
    public void setList(ObservableList<Pet> list) {
        this.list = list;
    }

    /**
     * prints a notification in console about connections successfully set
     *
     * @param primaryPet this primary pet
     * @param connectPet the pet chosen to be connected with
     * @param action     sets / unsets
     * @param role       type of connection between pets
     */
    private void notification(Pet primaryPet, Pet connectPet, String action, String role) {
        System.out.printf("%s %s as %s to %s\n", connectPet.getName(), action, role, primaryPet.getName());
    }
}
