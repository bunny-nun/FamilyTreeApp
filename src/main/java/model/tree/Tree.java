package model.tree;

import model.member.Member;
import model.member.person.Person;
import model.member.pet.Pet;
import model.service.Savable;

import java.util.ArrayList;
import java.util.Iterator;

public class Tree<M extends Member<M>> implements Savable, Iterable<M> {
    private String name;
    private final ArrayList<M> familyTree;

    /**
     * Class Tree
     *
     * @param name a string value for this object's name to be displayed
     */
    public Tree(String name) {
        this.name = name;
        this.familyTree = new ArrayList<>();
    }

    /**
     * Class Tree
     *
     * @param member the member to be assigned as the member with index 0
     */
    public Tree(M member) {
        this(String.format("%s_%s", member.getName(), member.getClass().getSimpleName()));
        this.addMember(member);
    }

    /**
     * Class Tree (empty)
     */
    public Tree() {
        this("New_Tree");
    }

    /**
     * Returns a string value of this object's name assigned
     *
     * @return a string value of this object's name assigned
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Assigns a string value for this object's new name to be displayed
     *
     * @param name a string value for this object's name to be displayed
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds an object of interface Member to this tree
     *
     * @param member an object of interface Member
     */
    public void addMember(M member) {
        if (member != null) {
            if (!this.familyTree.contains(member)) {
                this.familyTree.add(member);
            }
        }
    }

    /**
     * Adds all objects of interface Member from given tree to this tree
     *
     * @param family an object of interface Member to be added in this tree
     */
    public void addMembers(Tree<M> family) {
        if (!family.isEmpty()) {
            for (M member : family.familyTree) {
                if (!this.familyTree.contains(member)) {
                    this.familyTree.add(member);
                }
            }
        }
    }

    /**
     * Looking for a member in this tree by name and returns a tree with all matching results
     *
     * @param name a string value with a name of a person to find (first name and last name separated by space in any order)
     * @return new object of a class Tree containing objects of a class Member with all matching results
     */
    public Tree<M> getByName(String name) {
        Tree<M> search = new Tree<>("Search_Results");
        name = name.toLowerCase().trim().replaceAll(",", " ")
                .replaceAll("\\.", " ");
        while (name.contains("  ")) name = name.replace("  ", " ");
        name = name.replaceAll(" ", "_");
        for (M member : this.familyTree) {
            if (member.getName().toLowerCase().replaceAll(" ", "_").equals(name)) {
                search.addMember(member);
            } else {
                String[] reverse = name.split("_");
                String temp = reverse[0];
                reverse[0] = reverse[1];
                reverse[1] = temp;
                name = String.join("_", reverse);
                if (member.getName().toLowerCase().replaceAll(" ", "_").equals(name)) {
                    search.addMember(member);
                }
            }
        }
        return search;
    }

    /**
     * Returns all objects this tree contains
     *
     * @return an ArrayList that contains all objects in this tree
     */
    public ArrayList<M> getMembers() {
        return this.familyTree;
    }

    /**
     * Returns the member at the specified position in this tree
     *
     * @param num index of the member to return
     * @return the object at the specified position in this tree
     */
    public M getMember(int num) {
        return this.familyTree.get(num);
    }

    /**
     * Removes the first occurrence of the specified member from this tree
     *
     * @param member an object of interface Member
     */
    public void removeMember(M member) {
        this.familyTree.remove(member);
    }

    /**
     * Returns the number of members in this tree
     *
     * @return the number of objects in this tree
     */
    public int size() {
        return this.familyTree.size();
    }

    /**
     * Returns a string containing the name of this tree and string value of members of this tree
     *
     * @return string value containing the name of this tree and string value of all objects in this tree
     */
    @Override
    public String toString() {
        StringBuilder familyTree = new StringBuilder(String.format("\n%s_Tree:\n", this.name));
        for (M member : this.familyTree) {
            familyTree.append(String.format("%s\n", member.toString()));
        }
        return familyTree.toString();
    }

    /**
     * Returns a string containing the full information of all members of this tree
     *
     * @return string value containing the full information of all objects in this tree
     */
    @Override
    public String fullData() {
        StringBuilder data = new StringBuilder();
        for (M member : this.familyTree) {
            data.append(member.fullData())
                    .append("--------------\n");
        }
        return data.toString();
    }

    /**
     * Returns true if this tree contains no members
     *
     * @return true if this tree contains no objects
     */
    public boolean isEmpty() {
        return this.familyTree.isEmpty();
    }

    /**
     * Returns true if this tree contains a member with the same name and date of birth, otherwise returns false
     *
     * @param element an object of interface Member whose name and date of birth will be checked
     * @return true if this tree contains an object whose name and date of birth string values equal to name and date of birth string values of given object
     */
    public boolean contains(M element) {
        boolean result = false;
        for (M member : this) {
            if (element instanceof Person) {
                if (((Person) member).getFirstName().equals(((Person) element).getFirstName()) &&
                        ((Person) member).getLastName().equals(((Person) element).getLastName()) &&
                        member.getBirthDate().equals(element.getBirthDate())) {
                    result = true;
                    break;
                }
            }
            if (element instanceof Pet) {
                if (member.getName().equals(element.getName()) &&
                        ((Pet) member).getBirthDate().equals(((Pet) element).getBirthDate())) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public Iterator<M> iterator() {
        return new TreeIterator<>(this);
    }

    /**
     * Removes all the members from this tree. The tree family list will be empty after this call returns
     */
    public void clear() {
        this.familyTree.clear();
    }
}
