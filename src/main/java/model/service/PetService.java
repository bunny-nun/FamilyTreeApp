package model.service;

import model.member.Gender;
import model.member.pet.Pet;
import model.member.pet.Type;

import java.util.HashMap;

public class PetService extends Service<Pet> {

    public PetService() {
        super();
    }

    /**
     * Creates new pet based on received HashMap
     *
     * @param petMap HashMap with parameters to create new pet (i.e. name, gender etc.)
     * @return the created pet
     */
    @Override
    public Pet create(HashMap<String, String> petMap) {
        try {
            Gender gender;
            if (petMap.get("gender").equals("Male")) {
                gender = Gender.Male;
            } else {
                gender = Gender.Female;
            }
            Type type;
            if (petMap.get("type").equals("Cat")) {
                type = Type.Cat;
            } else {
                type = Type.Dog;
            }

            Pet pet = new Pet(type, petMap.get("name"), gender, petMap.get("birthDate"));
            if (petMap.containsKey("deathDate")) {
                pet.setDeathDate(petMap.get("deathDate"));
            }
            this.tree.addMember(pet);
            return pet;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }
}
