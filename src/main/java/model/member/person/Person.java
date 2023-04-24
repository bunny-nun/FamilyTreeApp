package model.member.person;

import model.date.Date;
import model.member.Gender;
import model.member.Member;

import java.util.ArrayList;

public class Person extends Member<Person> {
    private String firstName;
    private String patronymicName;
    private String lastName;
    private String maidenName;

    /***
     * Class Person
     * @param firstName the string value of this person's first name to be assigned
     * @param patronymicName the string value of this person's patronymic name to be assigned
     * @param lastName the string value of this person's last name to be assigned
     * @param gender the value of enumerable Gender (Male, Female)
     * @param birthDate string value of this person's date of birth in format "dd.MM.yyyy" to be assigned
     */
    public Person(String firstName, String patronymicName, String lastName, Gender gender, String birthDate) {
        this.firstName = firstName;
        this.patronymicName = patronymicName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        String endDate = new Date().toString();
        this.age = countAge(birthDate, endDate);
        this.children = new ArrayList<>();
    }

    /**
     * Class Person with patronymic name represented as an empty string
     *
     * @param firstName the string value of this person's first name to be assigned
     * @param lastName  the string value of this person's last name to be assigned
     * @param gender    the value of enumerable Gender (Male, Female)
     * @param birthDate string value of this person's date of birth in format "dd.MM.yyyy" to be assigned
     */
    public Person(String firstName, String lastName, Gender gender, String birthDate) {
        this(firstName, "", lastName, gender, birthDate);
    }

    /**
     * Class Person with name "Name", patronymic name - an empty string, last name - "Last_Name", gender - Male and date of birth "01.01.1900"
     */
    public Person() {
        this("Name", "Last_Name", Gender.Male, "01.01.1900");
    }

    /**
     * Returns the first name of this person
     *
     * @return the string value of this person's first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Returns the patronymic name of this person
     *
     * @return the string value of this person's patronymic name
     */
    public String getPatronymicName() {
        return this.patronymicName;
    }

    /**
     * Returns the last name of this person
     *
     * @return the string value of this person's last name
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Returns the maiden name of this person if assigned, else returns null
     *
     * @return the string value of this person's maiden name or null
     */
    public String getMaidenName() {
        return this.maidenName;
    }

    /**
     * Returns the first and last names of this person
     *
     * @return the string value of this person's first name and last name if format "%s_%s"
     */
    @Override
    public String getName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }

    /**
     * Assigns the first name for this person
     *
     * @param firstName string value to be assigned as this person's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Assigns the patronymic name of this person
     *
     * @param patronymicName string value to be assigned as this person's patronymic name
     */
    public void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName;
    }

    /**
     * Assigns the last name of this person
     *
     * @param lastName string value to be assigned as this person's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Assigns the maiden name of this person
     *
     * @param maidenName string value to be assigned as this person's maiden name
     */
    public void setMaidenName(String maidenName) {
        if (this.gender.equals(Gender.Female)) {
            this.maidenName = maidenName;
        }
    }

    /**
     * Compare this person with another person by their first names, last names and dates of birth
     *
     * @param person the given person to compare with this person
     * @return true if first name, last name and date of birth of given person equals to first name,
     * last name and date of birth of this person, false otherwise
     */
    @Override
    public boolean equivalents(Person person) {
        return this.getFirstName().equals(person.getFirstName())
                && this.getLastName().equals(person.getLastName())
                && this.getBirthDate().equals(person.getBirthDate());
    }

    /**
     * Returns string containing the first name, the last name, the maiden name (if assigned), the age of this person
     * and date of birth. If this person's date of death is assigned also returns the date of death
     *
     * @return string value containing the first name, the last name, the maiden name (if assigned), the age of this
     * person, dates of birth and death (if date of death is assigned)
     */
    @Override
    public String toString() {
        String result;
        if (this.maidenName != null) {
            result = String.format("%s %s (%s), %s years old", this.firstName, this.lastName, this.maidenName, this.age);
        } else {
            result = String.format("%s %s, %s years old", this.firstName, this.lastName, this.age);
        }
        if (this.deathDate != null) {
            result += String.format(" (%s - %s)", this.birthDate, this.deathDate);
        } else {
            result += String.format(" (%s)", this.birthDate);
        }
        return result;
    }

    /**
     * Returns the full data of this person in string format:
     * Personal information,
     * Personal mother's information,
     * Personal father's information,
     * Personal children's information,
     * Personal spouse's information
     *
     * @return the string value of this person's fields assigned
     */
    @Override
    public String fullData() {
        StringBuilder data = new StringBuilder();
        data.append(personData("Person", this));
        if (this.mother != null) {
            data.append(personData("Mother", this.mother));
        }
        if (this.father != null) {
            data.append(personData("Father", this.father));
        }
        if (this.couple != null) {
            data.append(personData("Spouse", this.couple));
        }
        if (!this.children.isEmpty()) {
            int counter = 1;
            for (Person child : this.children) {
                data.append(personData(String.format("Child_%d", counter), child));
                counter++;
            }
        }
        return data.toString();
    }

    /**
     * Private method returns the personal data of this person in string format:
     * - first name,
     * - patronymic name (if assigned),
     * - last name,
     * - maiden name (if assigned),
     * - gender,
     * - date of birth,
     * - date of death (if assigned),
     * - age
     *
     * @param name   string value to identify the person in a list with full data
     * @param person the person
     * @return string value with the personal data of this person
     */
    public String personData(String name, Person person) {
        StringBuilder data = new StringBuilder();
        if (name.equals("Person")) {
            data.append(String.format("-%s-\n", name));
        } else {
            data.append(String.format("   \n-%s-\n", name));
        }
        data.append(String.format("First Name: %s\n", person.firstName));
        if (!person.patronymicName.isEmpty()) {
            data.append(String.format("Patronymic Name: %s\n", person.patronymicName));
        }
        data.append(String.format("Last Name: %s\n", person.lastName));
        if (person.maidenName != null) {
            data.append(String.format("Maiden Name: %s\n", person.maidenName));
        }
        data.append(String.format("Gender: %s\n", person.gender.toString()));
        data.append(String.format("Date of Birth: %s\n", person.birthDate));
        if (person.deathDate != null) {
            data.append(String.format("Date of Death: %s\n", person.deathDate));
        }
        data.append(String.format("Age: %d\n", person.age));
        return data.toString();
    }
}
