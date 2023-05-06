package model.service;

import model.member.Gender;
import model.member.person.Person;

import java.util.HashMap;

public class PersonService extends Service<Person> {

    public PersonService() {
        super();
    }

    /**
     * Creates new person based on received HashMap
     *
     * @param personMap HashMap with parameters to create new person (i.e. first name, last name, gender etc.)
     * @return the created person
     */
    @Override
    public Person create(HashMap<String, String> personMap) {
        try {
            Gender gender;
            if (personMap.get("gender").equals("Male")) {
                gender = Gender.Male;
            } else {
                gender = Gender.Female;
            }
            Person person = new Person(personMap.get("firstName"), personMap.get("lastName"), gender, personMap.get("birthDate"));
            if (!personMap.get("patronymicName").isEmpty()) {
                person.setPatronymicName(personMap.get("patronymicName"));
            }
            if (!personMap.get("maidenName").isEmpty()) {
                person.setMaidenName(personMap.get("maidenName"));
            }
            if (personMap.containsKey("deathDate")) {
                person.setDeathDate(personMap.get("deathDate"));
            }
            this.tree.addMember(person);
            return person;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }
}
