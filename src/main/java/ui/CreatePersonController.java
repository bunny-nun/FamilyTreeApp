package ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.member.person.Person;
import model.service.PersonService;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class CreatePersonController {

    @FXML
    private TextField personFirstNameText;

    @FXML
    private TextField personPatronymicNameText;

    @FXML
    private TextField personLastNameText;

    @FXML
    private TextField personMaidenNameText;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private DatePicker deathDatePicker;

    @FXML
    private RadioButton genderMaleRadioButton;

    @FXML
    private Button createNewButton;

    @FXML
    private Label requiredFields;

    private HashMap<String, String> createNewMap;
    private Person person;
    private PersonService personService;

    /**
     * Returns the person assigned to this controller
     *
     * @return the person assigned to this controller
     */
    public Person getPerson() {
        return this.person;
    }

    /**
     * Creates new element of the tree when all required fields are filled, otherwise shows the warning
     */
    @FXML
    private void initialize() {
        requiredFields.setVisible(false);
        this.createNewMap = new HashMap<>();
    }

    /**
     * Sets PersonService for this class
     *
     * @param personService an object of Service to handle operations
     */
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Creates an object of the class Persons based on input information from user
     */
    @FXML
    private void createNewPerson() {
        if (genderMaleRadioButton.isSelected()) {
            this.createNewMap.put("gender", "Male");
        } else {
            this.createNewMap.put("gender", "Female");
        }
        this.createNewMap.put("firstName", personFirstNameText.getText());
        this.createNewMap.put("patronymicName", personPatronymicNameText.getText());
        this.createNewMap.put("lastName", personLastNameText.getText());
        this.createNewMap.put("maidenName", personMaidenNameText.getText());
        try {
            this.createNewMap.put("birthDate", birthDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            if (deathDatePicker.getValue() != null) {
                this.createNewMap.put("deathDate", deathDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            }
            if (this.createNewMap.get("firstName").isBlank() || this.createNewMap.get("lastName").isBlank()) {
                requiredFields.setVisible(true);
            } else {
                this.person = this.personService.create(this.createNewMap);
                System.out.printf("New person created: %s\n", this.person);
                this.createNewButton.getScene().getWindow().hide();
            }
        } catch (RuntimeException e) {
            requiredFields.setVisible(true);
            System.out.println(e.getMessage());
        }
    }
}
