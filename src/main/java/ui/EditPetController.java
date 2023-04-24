package ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.member.Gender;
import model.member.pet.Pet;
import model.member.pet.Type;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditPetController {

    @FXML
    private TextField petNameText;

    @FXML
    private RadioButton catRadioButton;

    @FXML
    private RadioButton dogRadioButton;

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
    private Pet pet;

    @FXML
    private void initialize() {
        requiredFields.setVisible(false);
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Shows the data of the pet being edited
     */
    public void showPetData() {
        if (this.pet != null) {
            this.petNameText.setText(this.pet.getName());
            if (this.pet.getGender().equals(Gender.Male)) {
                this.genderMaleRadioButton.setSelected(true);
            } else {
                this.genderFemaleRadioButton.setSelected(true);
            }
            if (this.pet.getType().equals(Type.Cat)) {
                this.catRadioButton.setSelected(true);
            } else {
                this.dogRadioButton.setSelected(true);
            }
            this.birthDatePicker.setValue(LocalDate.parse(this.pet.getBirthDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            if (this.pet.getDeathDate() != null) {
                if (!this.pet.getDeathDate().isBlank()) {
                    this.deathDatePicker.setValue(LocalDate.parse(this.pet.getDeathDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                }
            }
        }
    }

    /**
     * Saves the edited data of the pet
     */
    @FXML
    private void editPet() {
        if (genderMaleRadioButton.isSelected()) {
            this.pet.setGender(Gender.Male);
        } else {
            this.pet.setGender(Gender.Female);
        }
        if (catRadioButton.isSelected()) {
            this.pet.setType(Type.Cat);
        } else {
            this.pet.setType(Type.Dog);
        }
        try {
            this.pet.setBirthDate(birthDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            if (deathDatePicker.getValue() != null) {
                if (!deathDatePicker.getValue().toString().isBlank()) {
                    this.pet.setDeathDate(deathDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                }
            } else {
                this.pet.setDeathDate(null);
            }
            if (this.petNameText.getText().isBlank()) {
                requiredFields.setVisible(true);
            } else {
                this.pet.setName(petNameText.getText());
                System.out.printf("Pet edited: %s\n", this.pet);
                this.editButton.getScene().getWindow().hide();
            }
        } catch (RuntimeException e) {
            requiredFields.setVisible(true);
            System.out.println(e.getMessage());
        }
    }
}
