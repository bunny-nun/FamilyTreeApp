package model.handler;

import model.member.Gender;
import model.member.person.Person;
import model.member.pet.Pet;
import model.member.pet.Type;
import model.service.Savable;
import model.tree.Tree;
import model.tree.TreeBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TxtLoader extends FileLoader {

    /**
     * Class TxtSaver
     *
     * @param object the object of a class implemented the Savable interface to be saved or loaded
     * @param name   string value of the file's name (recommended to use latin alphabet and no spaces)
     */
    public TxtLoader(Savable object, String name) {
        super(object, name);
    }


    /**
     * Reads the file in format .txt and returns the object of a class implemented the Savable interface
     *
     * @return an object of a class implemented the Savable interface
     */
    public Savable readFile(String fileName) {
        try {
            BufferedReader file = new BufferedReader(new FileReader(String.format("%s.txt", fileName)));
            String line;
            StringBuilder text = new StringBuilder();
            while ((line = file.readLine()) != null) {
                text.append(String.format("%s\n", line));
            }
            file.close();
            if (fileName.contains("Family")) {
                if (fileName.contains("Person")) {
                    String[] textArray = text.toString().split("--------------\n");
                    ArrayList<Person> personArray = new ArrayList<>();
                    for (String string : textArray) {
                        StringBuilder personText = new StringBuilder(string);
                        Person person = getPerson(personText);
                        personArray.add(person);
                    }
                    Tree<Person> tree = new Tree<>(personArray.get(0));
                    personArray.remove(0);
                    for (Person person : personArray) {
                        tree.addMember(person);
                    }
                    for (Person person : tree) {
                        TreeBuilder<Person> treeBuilder = new TreeBuilder<>();
                        treeBuilder.setConnections(person, tree);
                    }
                    return tree;
                } else if (fileName.contains("Pet")) {
                    String[] textArray = text.toString().split("--------------\n");
                    ArrayList<Pet> petArray = new ArrayList<>();
                    String type;
                    if (textArray[0].contains("Dog")) {
                        type = "Dog";
                    } else {
                        type = "Cat";
                    }
                    for (String string : textArray) {
                        StringBuilder petText = new StringBuilder(string);
                        Pet pet = getPet(petText, type);
                        petArray.add(pet);
                    }
                    Tree<Pet> tree = new Tree<>(petArray.get(0));
                    petArray.remove(0);
                    for (Pet pet : petArray) {
                        tree.addMember(pet);
                    }
                    for (Pet pet : tree) {
                        TreeBuilder<Pet> treeBuilder = new TreeBuilder<>();
                        treeBuilder.setConnections(pet, tree);
                    }
                    return tree;
                }
            } else {
                if (fileName.contains("Person")) {
                    return getPerson(text);
                } else if (fileName.contains("Pet")) {
                    String type;
                    if (text.toString().contains("Dog")) {
                        type = "Dog";
                    } else {
                        type = "Cat";
                    }
                    return getPet(text, type);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Reads the given file and returns the object of a class implemented the Savable interface
     *
     * @return an object of a class implemented the Savable interface
     */
    @Override
    public Savable readFile(File file) {
        return readFile(file.getAbsolutePath().replace(".txt", ""));
    }

    /**
     * Private method returns the person with assigned personal information based on parsed string with a data of the person:
     * - first name,
     * - patronymic name (if assigned),
     * - last name,
     * - maiden name (if assigned),
     * - gender,
     * - date of birth,
     * - date of death (if assigned)
     *
     * @param data HashMap object containing parsed dictionary with names of fields as keys and their values
     * @return the person with assigned personal information
     */
    private Person getPersonObject(HashMap<String, String> data) {
        String firstName = data.get("First Name");
        String patronymicName = "";
        if (data.containsKey("Patronymic Name")) {
            patronymicName = data.get("Patronymic Name");
        }
        String lastName = data.get("Last Name");
        String maidenName = "";
        if (data.containsKey("Maiden Name")) {
            maidenName = data.get("Maiden Name");
        }
        Gender gender;
        if (data.get("Gender").equals("Female")) {
            gender = Gender.Female;
        } else {
            gender = Gender.Male;
        }
        String birthDate = data.get("Date of Birth");
        String deathDate = "";
        if (data.containsKey("Date of Death")) {
            deathDate = data.get("Date of Death");
        }
        Person person = new Person(firstName, patronymicName, lastName, gender, birthDate);
        if (!maidenName.isEmpty()) {
            person.setMaidenName(maidenName);
        }
        if (!deathDate.isEmpty()) {
            person.setDeathDate(deathDate);
        }
        return person;
    }

    /**
     * Private method returns the person based on parsed string with full data of the person:
     * - Personal person's information,
     * - Personal mother's information (if assigned),
     * - Personal father's information (if assigned),
     * - Personal children's information (if assigned),
     * - Personal couple's information (if assigned)
     *
     * @param text StringBuilder object containing a string with full person's data
     * @return the person with assigned personal information and relatives if those are assigned
     */
    private Person getPerson(StringBuilder text) {
        String[] textArray = text.toString().split(" {3}\n");
        HashMap<String, HashMap<String, String>> data = new HashMap<>();
        for (String string : textArray) {
            HashMap<String, String> personData = new HashMap<>();
            String[] dataString = string.split("-\n")[1].split("\n");
            for (String substring : dataString) {
                personData.put(substring.split(": ")[0], substring.split(": ")[1]);
            }
            data.put(string.split("-\n")[0], personData);
        }
        Person person = getPersonObject(data.get("-Person"));
        data.remove("-Person");
        if (data.containsKey("-Mother")) {
            person.setMother(getPersonObject(data.get("-Mother")));
            data.remove("-Mother");
        }
        if (data.containsKey("-Father")) {
            person.setFather(getPersonObject(data.get("-Father")));
            data.remove("-Father");
        }
        if (data.containsKey("-Spouse")) {
            person.setCouple(getPersonObject(data.get("-Spouse")));
            data.remove("-Spouse");
        }
        int count = 1;
        while (!data.isEmpty()) {
            person.setChild(getPersonObject(data.get(String.format("-Child_%d", count))));
            data.remove(String.format("-Child_%d", count));
            count++;
        }
        return person;
    }

    /**
     * Private method returns the pet with assigned information based on parsed string with a data of the pet:
     * - type;
     * - name,
     * - gender,
     * - date of birth,
     * - date of death (if assigned)
     *
     * @param data     HashMap object containing parsed dictionary with names of fields as keys and their values
     * @param typeInfo string value displaying the type of this pet
     * @return the pet with assigned information
     */
    private Pet getPetObject(HashMap<String, String> data, String typeInfo) {
        Type type;
        if (typeInfo.equals("Dog")) {
            type = Type.Dog;
        } else {
            type = Type.Cat;
        }
        String name = data.get("Name");
        Gender gender;
        if (data.get("Gender").equals("Female")) {
            gender = Gender.Female;
        } else {
            gender = Gender.Male;
        }
        String birthDate = data.get("Date of Birth");
        String deathDate = "";
        if (data.containsKey("Date of Death")) {
            deathDate = data.get("Date of Death");
        }
        Pet pet = new Pet(type, name, gender, birthDate);
        if (!deathDate.isEmpty()) {
            pet.setDeathDate(deathDate);
        }
        return pet;
    }

    /**
     * Private method returns the pet based on parsed string with full data of the pet:
     * - pet's information,
     * - pet's mother's information (if assigned),
     * - pet's father's information (if assigned),
     * - pet's children's information (if assigned),
     *
     * @param text StringBuilder object containing a string with full pet's data
     * @return the pet with assigned information and relatives if those are assigned
     */
    private Pet getPet(StringBuilder text, String type) {
        String[] textArray = text.toString().split(" {3}\n");
        HashMap<String, HashMap<String, String>> data = new HashMap<>();
        for (String string : textArray) {
            HashMap<String, String> petData = new HashMap<>();
            String[] dataString = string.split("-\n")[1].split("\n");
            for (String substring : dataString) {
                petData.put(substring.split(": ")[0], substring.split(": ")[1]);
            }
            data.put(string.split("-\n")[0], petData);
        }
        String petKey = String.format("-%s", type);
        Pet pet = getPetObject(data.get(petKey), type);
        data.remove(petKey);
        if (data.containsKey("-Owner")) {
            pet.setOwner(getPersonObject(data.get("-Owner")));
            data.remove("-Owner");
        }
        if (data.containsKey("-Mother")) {
            pet.setMother(getPetObject(data.get("-Mother"), type));
            data.remove("-Mother");
        }
        if (data.containsKey("-Father")) {
            pet.setFather(getPetObject(data.get("-Father"), type));
            data.remove("-Father");
        }
        int count = 1;
        while (!data.isEmpty()) {
            pet.setChild(getPetObject(data.get(String.format("-Child_%d", count)), type));
            data.remove(String.format("-Child_%d", count));
            count++;
        }
        return pet;
    }
}
