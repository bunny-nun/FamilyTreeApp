package model.service;

import model.handler.OutputLoader;
import model.handler.OutputSaver;
import model.handler.TxtSaver;
import model.member.Member;
import model.member.comparator.ComparatorByBirth;
import model.member.comparator.ComparatorByName;
import model.member.comparator.PersonComparatorByFirstName;
import model.member.comparator.PersonComparatorByLastName;
import model.member.person.Person;
import model.member.pet.Pet;
import model.tree.Tree;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public abstract class Service<M extends Member<M>> {
    Tree<M> tree;

    /**
     * Class Service
     */
    public Service() {
        this.tree = new Tree<>();
    }

    /**
     * Returns this service's tree
     *
     * @return this service's tree
     */
    public Tree<M> getTree() {
        return this.tree;
    }

    /**
     * Sets given tree as this service's tree
     *
     * @param tree the tree of this service
     */
    public void setTree(Tree<M> tree) {
        this.tree = tree;
    }

    /**
     * Writes the full fata of this service's tree to an OutputStream and saves the given file
     *
     * @param file file to be saved
     */
    public boolean saveOutput(File file) throws IOException {
        OutputSaver outputSaver = new OutputSaver(this.tree, file.getName());
        return outputSaver.saveFile(file);
    }

    /**
     * Reads the file in format .out and returns the object with data loaded from the file
     *
     * @param file a file to load
     * @return the object of a class implemented interface Savable with data loaded from the file
     */
    public Savable readOutput(File file) throws IOException, ClassNotFoundException {
        OutputLoader outputLoader = new OutputLoader(this.tree, file.getName());
        return outputLoader.readFile(file);
    }

    /**
     * Writes the full fata of this service's tree to an OutputStream and saves the file in format .txt
     *
     * @param file file to be saved
     */
    public boolean saveTxt(File file) throws IOException {
        TxtSaver txtSaver = new TxtSaver(this.tree, file.getName());
        return txtSaver.saveFile(file);
    }

    /**
     * Sorts a tree by persons' first names
     */
    public void sortByPersonFirstName(Tree<Person> object) {
        object.getMembers().sort(new PersonComparatorByFirstName());
    }

    /**
     * Sorts a tree by persons' last names
     */
    public void sortPersonByLastName(Tree<Person> object) {
        object.getMembers().sort(new PersonComparatorByLastName());
    }

    public void sortPetByName(Tree<Pet> object) {
        object.getMembers().sort(new ComparatorByName<>());
    }

    /**
     * Sorts a tree by members' dates of birth
     */
    public void sortByBirth(Tree<M> object) {
        object.getMembers().sort(new ComparatorByBirth<>());
    }

    /**
     * Creates new member based on received HashMap
     *
     * @param map HashMap with parameters to create new member (i.e. name, gender etc.)
     * @return string value of full data of created member
     */
    public abstract M create(HashMap<String, String> map);


    /**
     * Deletes the given member from this service's tree
     *
     * @param element the member to be deleted from this service's tree
     */
    public abstract boolean delete(M element);

    /**
     * Returns Tree with data from loaded file and assigns it as this service's tree
     *
     * @param file given file in format .out
     */
    public void open(File file) throws IOException, ClassNotFoundException {
        this.tree = (Tree<M>) this.readOutput(file);
    }

    /**
     * Cancels previous members' connections
     *
     * @param primaryMember first member
     * @param connectMember second member
     * @return string value of result
     */
    public String cancelConnection(M primaryMember, M connectMember) {
        String role = "";
        if (primaryMember.getMother() != null) {
            if (primaryMember.getMother().equals(connectMember)) {
                primaryMember.unSetMother(connectMember);
                role = "mother";
            }
        }
        if (primaryMember.getFather() != null) {
            if (primaryMember.getFather().equals(connectMember)) {
                primaryMember.unSetFather(connectMember);
                role = "father";
            }
        }
        if (connectMember.getMother() != null) {
            if (connectMember.getMother().equals(primaryMember)) {
                connectMember.unSetMother(primaryMember);
                role = "child";
            }
        }
        if (connectMember.getFather() != null) {
            if (connectMember.getFather().equals(primaryMember)) {
                connectMember.unSetFather(primaryMember);
                role = "child";
            }
        }
        if (primaryMember.getCouple() != null) {
            if (primaryMember.getCouple().equals(connectMember)) {
                primaryMember.divorce(connectMember);
                role = "couple";
            }
        }
        if (role.isEmpty()) {
            return role;
        } else {
            return String.format("%s was unset as %s to %s", connectMember.getName(), role, primaryMember.getName());
        }
    }
}
