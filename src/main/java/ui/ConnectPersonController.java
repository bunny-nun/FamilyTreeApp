package ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.member.Gender;
import model.member.person.Person;
import model.service.PersonService;

public class ConnectPersonController {

    @FXML
    private Label personFirstNameText;

    @FXML
    private Label personLastNameText;

    @FXML
    private Label personBirthDateText;

    @FXML
    private RadioButton motherRadioButton;

    @FXML
    private RadioButton fatherRadioButton;

    @FXML
    private RadioButton childRadioButton;

    @FXML
    private RadioButton spouseRadioButton;

    @FXML
    private RadioButton noneRadioButton;

    @FXML
    private TableView<Person> connectionTableView;

    @FXML
    private TableColumn<Person, String> firstNameColumn;

    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private TableColumn<Person, String> birthDateColumn;

    @FXML
    private Label requiredFields;
    private Person primaryPerson;
    private Person connectPerson;
    private ObservableList<Person> list;
    private PersonService personService;


    @FXML
    private void initialize() {
        this.list = FXCollections.observableArrayList();
        showTable();
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
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
        this.spouseRadioButton.setDisable(false);
        this.motherRadioButton.setSelected(false);
        this.fatherRadioButton.setSelected(false);
        this.childRadioButton.setSelected(false);
        this.spouseRadioButton.setSelected(false);
        int index = this.connectionTableView.getSelectionModel().getSelectedIndex();
        if (index >= 0 && this.connectionTableView.getSelectionModel().getSelectedItem() != null) {
            this.connectPerson = this.connectionTableView.getSelectionModel().getSelectedItem();
            if (this.primaryPerson.equals(this.connectPerson)) {
                this.noneRadioButton.setSelected(true);
                this.motherRadioButton.setDisable(true);
                this.fatherRadioButton.setDisable(true);
                this.childRadioButton.setDisable(true);
                this.spouseRadioButton.setDisable(true);
            }
            if (this.primaryPerson.getMother() != null) {
                if (this.primaryPerson.getMother().equals(this.connectPerson)) {
                    this.motherRadioButton.setSelected(true);
                }
            }
            if (this.primaryPerson.getFather() != null) {
                if (this.primaryPerson.getFather().equals(this.connectPerson)) {
                    this.fatherRadioButton.setSelected(true);
                }
            }
            if (!this.primaryPerson.getChildren().isEmpty()) {
                if (this.primaryPerson.getChildren().contains(this.connectPerson)) {
                    this.childRadioButton.setSelected(true);
                }
            }
            if (this.primaryPerson.getCouple() != null) {
                if (this.primaryPerson.getCouple().equals(this.connectPerson)) {
                    this.spouseRadioButton.setSelected(true);
                }
            }
        }
    }

    /**
     * Sets new connection between relatives
     */
    @FXML
    private void connect() {
        String message;
        if (motherRadioButton.isSelected()) {
            message = this.personService.cancelConnection(this.primaryPerson, this.connectPerson);
            this.primaryPerson.setMother(this.connectPerson);
            if (!message.isEmpty()) {
                System.out.println(message);
            }
            notification(this.primaryPerson, this.connectPerson, "mother");
        } else if (fatherRadioButton.isSelected()) {
            message = this.personService.cancelConnection(this.primaryPerson, this.connectPerson);
            this.primaryPerson.setFather(this.connectPerson);
            if (!message.isEmpty()) {
                System.out.println(message);
            }
            notification(this.primaryPerson, this.connectPerson, "father");
        } else if (childRadioButton.isSelected()) {
            message = this.personService.cancelConnection(this.primaryPerson, this.connectPerson);
            this.primaryPerson.setChild(this.connectPerson);
            if (!message.isEmpty()) {
                System.out.println(message);
            }
            notification(this.primaryPerson, this.connectPerson, "child");
        } else if (spouseRadioButton.isSelected()) {
            message = this.personService.cancelConnection(this.primaryPerson, this.connectPerson);
            this.primaryPerson.setCouple(this.connectPerson);
            if (!message.isEmpty()) {
                System.out.println(message);
            }
            notification(this.primaryPerson, this.connectPerson, "couple");
        } else if (noneRadioButton.isSelected()) {
            message = this.personService.cancelConnection(this.primaryPerson, this.connectPerson);
            if (!message.isEmpty()) {
                System.out.println(message);
            }
        } else {
            this.requiredFields.setText("*choose connection");
        }
    }

    /**
     * Displays the information of the person being settled
     */
    public void showPersonData() {
        if (this.primaryPerson != null) {
            this.personFirstNameText.setText(this.primaryPerson.getFirstName());
            this.personLastNameText.setText(this.primaryPerson.getLastName());
            this.personBirthDateText.setText(this.primaryPerson.getBirthDate());
        }
        this.connectionTableView.setItems(this.list);
    }

    /**
     * Shows the table with all persons entered
     */
    private void showTable() {
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        birthDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBirthDate()));
        connectionTableView.setRowFactory(tv -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    row.setTooltip(new Tooltip(row.getItem().toString()));
                }
            });
            return row;
        });
    }

    /**
     * Sets the person to be primary person
     *
     * @param primaryPerson the person chosen at main window
     */
    public void setPrimaryPerson(Person primaryPerson) {
        this.primaryPerson = primaryPerson;
    }

    /**
     * sets the list to be this object's list
     *
     * @param list the list of all persons entered
     */
    public void setList(ObservableList<Person> list) {
        this.list = list;
    }

    /**
     * prints a notification in console about connections successfully set
     *
     * @param primaryPerson this primary person
     * @param connectPerson the person chosen to be connected with
     * @param role          type of connection between persons
     */
    private void notification(Person primaryPerson, Person connectPerson, String role) {
        System.out.printf("%s was set as %s to %s\n", connectPerson.getName(), role, primaryPerson.getName());
    }
}
