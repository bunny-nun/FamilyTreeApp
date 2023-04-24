package model.member.pet;

import model.date.Date;
import model.member.Gender;
import model.member.Member;
import model.member.person.Person;

import java.util.ArrayList;

public class Pet extends Member<Pet> {
    private Type type;
    private String name;
    private Person owner;

    /**
     * Class Pet
     *
     * @param type      the value of enumerable Type (Cat, Dog)
     * @param name      the string value of this pet's name to be assigned
     * @param gender    the value of enumerable Gender (Male, Female)
     * @param birthDate string value of this pet's date of birth in format "dd.MM.yyyy" to be assigned
     */
    public Pet(Type type, String name, Gender gender, String birthDate) {
        this.type = type;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        String endDate = new Date().toString();
        this.age = countAge(birthDate, endDate);
        this.children = new ArrayList<>();
    }

    /**
     * Class Pet type of Dog, with name "Name", gender - Male and date of birth "01.01.2000"
     */
    public Pet() {
        this(Type.Dog, "Name", Gender.Male, "01.01.2000");
    }

    /**
     * Returns the type of this pet
     *
     * @return the value of this pet's type in form of the enumerable Type (Cat, Dog)
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns the name of this pet
     *
     * @return the string value of this pet's name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the person assigned as this pet's owner
     *
     * @return the person assigned as this pet's owner
     */
    public Person getOwner() {
        return this.owner;
    }

    /**
     * Assigns the enumerable Type (Cat, Dog) as this pet's type
     *
     * @param type the value of this pet's type in form of the enumerable Type (Cat, Dog)
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Assigns the name of this pet
     *
     * @param name string value to be assigned as this pet's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Assigns the owner of this pet
     *
     * @param owner the person to be assigned as this pet's owner
     */
    public void setOwner(Person owner) {
        this.owner = owner;
    }


    /**
     * Compare this pet with the pet by their names and dates of birth
     *
     * @param pet the pet to compare with this pet
     * @return true if name and date of birth of given pet equals to name,
     * and date of birth of this pet, false otherwise
     */
    @Override
    public boolean equivalents(Pet pet) {
        return this.getName().equals(pet.getName())
                && this.getBirthDate().equals(pet.getBirthDate());
    }

    /**
     * Returns string containing the name, the age of this pet and the date of birth. If this pet's date of death
     * is assigned also returns the date of death
     *
     * @return string value containing the name, the age of this pet, dates of birth and death (if date of death is
     * assigned)
     */
    @Override
    public String toString() {
        String result;
        result = String.format("%s %s, %s years old", this.type, this.name, this.age);
        if (this.deathDate != null) {
            result += String.format(" (%s - %s)", this.birthDate, this.deathDate);
        } else {
            result += String.format(" (%s)", this.birthDate);
        }
        return result;
    }

    /**
     * Returns the full data of this pet in string format:
     * Personal information,
     * Personal mother's information,
     * Personal father's information,
     * Personal children's information,
     * Personal couple's information
     *
     * @return the string value of this pet's fields assigned
     */
    @Override
    public String fullData() {
        StringBuilder data = new StringBuilder();
        data.append(petData(String.format("%s", this.type), this));
        if (this.owner != null) {
            data.append(this.owner.personData("Owner", this.owner));
        }
        if (this.mother != null) {
            data.append(petData("Mother", this.mother));
        }
        if (this.father != null) {
            data.append(petData("Father", this.father));
        }
        if (!this.children.isEmpty()) {
            int counter = 1;
            for (Pet child : this.children) {
                data.append(petData(String.format("Child_%d", counter), child));
                counter++;
            }
        }
        return data.toString();
    }

    /**
     * Private method returns the data of this member in string format:
     * - name,
     * - gender,
     * - date of birth,
     * - date of death (if assigned),
     * - age
     *
     * @param name string value to identify the pet in a list with full data
     * @param pet  the pet
     * @return string value with the data of this pet
     */
    private String petData(String name, Pet pet) {
        StringBuilder data = new StringBuilder();
        if (name.equals("Dog") || name.equals("Cat")) {
            data.append(String.format("-%s-\n", name));
        } else {
            data.append(String.format("   \n-%s-\n", name));
        }
        data.append(String.format("Name: %s\n", pet.name));
        data.append(String.format("Gender: %s\n", pet.gender.toString()));
        data.append(String.format("Date of Birth: %s\n", pet.birthDate));
        if (pet.deathDate != null) {
            data.append(String.format("Date of Death: %s\n", pet.deathDate));
        }
        data.append(String.format("Age: %d\n", pet.age));
        return data.toString();
    }
}
