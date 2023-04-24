package ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.member.pet.Pet;
import model.service.PetService;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class CreatePetController {

    @FXML
    private TextField petNameText;

    @FXML
    private RadioButton catRadioButton;

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
    private Pet pet;
    private PetService petService;

    /**
     * Returns a pet assigned to this class
     *
     * @return a pet assigned to this class
     */
    public Pet getPet() {
        return this.pet;
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
     * Sets PetService for this class
     *
     * @param petService an object of Service to handle operations
     */
    public void setPetService(PetService petService) {
        this.petService = petService;
    }

    /**
     * Creates an object of the class Pet based on input information from user
     */
    @FXML
    private void createNewPet() {
        if (genderMaleRadioButton.isSelected()) {
            this.createNewMap.put("gender", "Male");
        } else {
            this.createNewMap.put("gender", "Female");
        }
        if (catRadioButton.isSelected()) {
            this.createNewMap.put("type", "Cat");
        } else {
            this.createNewMap.put("type", "Dog");
        }
        this.createNewMap.put("name", petNameText.getText());
        try {
            this.createNewMap.put("birthDate", birthDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            if (deathDatePicker.getValue() != null) {
                this.createNewMap.put("deathDate", deathDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            }
            if (this.createNewMap.get("name").isBlank()) {
                requiredFields.setVisible(true);
            } else {
                this.pet = this.petService.create(this.createNewMap);
                System.out.printf("New pet created: %s\n", this.pet);
                this.createNewButton.getScene().getWindow().hide();
            }
        } catch (RuntimeException e) {
            requiredFields.setVisible(true);
            System.out.println(e.getMessage());
        }
    }
}
