package ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.member.Gender;
import model.member.person.Person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditPersonController {

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
    private RadioButton genderFemaleRadioButton;

    @FXML
    private Button editButton;

    @FXML
    private Label requiredFields;
    private Person person;

    @FXML
    private void initialize() {
        this.requiredFields.setVisible(false);
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return this.person;
    }

    /**
     * Shows the data of the person being edited
     */
    public void showPersonData() {
        if (this.person != null) {
            this.personFirstNameText.setText(this.person.getFirstName());
            this.personLastNameText.setText(this.person.getLastName());
            if (this.person.getPatronymicName() != null) {
                if (!this.person.getPatronymicName().isBlank()) {
                    this.personPatronymicNameText.setText(this.person.getPatronymicName());
                }
            }
            if (this.person.getMaidenName() != null) {
                if (!this.person.getMaidenName().isBlank()) {
                    this.personMaidenNameText.setText(this.person.getMaidenName());
                }
            }
            if (this.person.getGender().equals(Gender.Male)) {
                this.genderMaleRadioButton.setSelected(true);
            } else {
                this.genderFemaleRadioButton.setSelected(true);
            }
            this.birthDatePicker.setValue(LocalDate.parse(this.person.getBirthDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            if (this.person.getDeathDate() != null) {
                if (!this.person.getDeathDate().isBlank()) {
                    this.deathDatePicker.setValue(LocalDate.parse(this.person.getDeathDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                }
            }
        }
    }

    /**
     * Saves the edited data of the person
     */
    @FXML
    private void editPerson() {
        if (genderMaleRadioButton.isSelected()) {
            this.person.setGender(Gender.Male);
        } else {
            this.person.setGender(Gender.Female);
        }
        if (!this.personPatronymicNameText.getText().isBlank()) {
            this.person.setPatronymicName(personPatronymicNameText.getText());
        }
        if (!this.personMaidenNameText.getText().isBlank()) {
            this.person.setMaidenName(personMaidenNameText.getText());
        }
        try {
            this.person.setBirthDate(birthDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            if (deathDatePicker.getValue() != null) {
                if (!deathDatePicker.getValue().toString().isBlank()) {
                    this.person.setDeathDate(deathDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                }
            } else {
                this.person.setDeathDate(null);
            }
            if (this.personFirstNameText.getText().isBlank() || this.personLastNameText.getText().isBlank()) {
                requiredFields.setVisible(true);
            } else {
                this.person.setFirstName(personFirstNameText.getText());
                this.person.setLastName(personLastNameText.getText());
                System.out.printf("Person edited: %s\n", this.person);
                this.editButton.getScene().getWindow().hide();
            }
        } catch (RuntimeException e) {
            requiredFields.setVisible(true);
            System.out.println(e.getMessage());
        }
    }
}
