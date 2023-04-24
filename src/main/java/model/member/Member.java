package model.member;

import model.date.Date;
import model.service.Savable;

import java.util.ArrayList;

public abstract class Member<M extends Member<M>> implements Savable {
    protected Gender gender;
    protected String birthDate;
    protected String deathDate;
    protected int age;
    protected M mother;
    protected M father;
    protected ArrayList<M> children;
    protected M couple;

    /**
     * Returns the gender assigned to this member
     *
     * @return the value of this member's gender in form of the enumerable Gender (Male, Female)
     */
    public Gender getGender() {
        return this.gender;
    }

    /**
     * Returns the date of birth of this member
     *
     * @return string value of this member's date of birth in format "dd.MM.yyyy"
     */
    public String getBirthDate() {
        return this.birthDate;
    }

    /**
     * Returns the date of death of this member if assigned, else returns null
     *
     * @return string value of this member's date of death in format "dd.MM.yyyy" or null
     */
    public String getDeathDate() {
        return this.deathDate;
    }

    /**
     * Returns the age of this member
     *
     * @return the int value of this member's age
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Returns the value of this member's mother assigned
     *
     * @return the member assigned as this member's mother
     */
    public M getMother() {
        return this.mother;
    }

    /**
     * Returns the value of this member's father assigned
     *
     * @return the member assigned as this member's father
     */
    public M getFather() {
        return this.father;
    }

    /**
     * Returns the value of this member's children assigned
     *
     * @return the ArrayList containing all person's children assigned
     */

    public ArrayList<M> getChildren() {
        return this.children;
    }

    /**
     * Returns the value of this member's couple assigned
     *
     * @return the object assigned as this member's couple
     */

    public M getCouple() {
        return this.couple;
    }

    /**
     * Assigns enumerable Gender to be assigned as this member's gender
     *
     * @param gender the value of this member's gender in form of the enumerable Gender (Male, Female)
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Assigns the date of birth of this member
     *
     * @param date string value of this member's date of birth in format "dd.MM.yyyy"
     */
    public void setBirthDate(String date) {
        this.birthDate = date;
    }

    /**
     * Assigns the date of death of this member
     *
     * @param date string value of this member's date of death in format "dd.MM.yyyy"
     */
    public void setDeathDate(String date) {
        this.deathDate = date;
        this.age = countAge(this.birthDate, this.deathDate);
    }

    /**
     * Returns the period in years between two dates given in string format
     *
     * @param startDate string value of start date in format "dd.MM.yyyy"
     * @param endDate   string value of end date in format "dd.MM.yyyy"
     * @return int value of years between start date and end date
     */
    public int countAge(String startDate, String endDate) {
        Date date = new Date();
        return date.countPeriod(startDate, endDate);
    }

    /**
     * Assigns the member as the mother of this member
     *
     * @param mother the member to be assigned as this member's mother
     */
    public void setMother(M mother) {
        if (this.getMother() != null) {
            this.getMother().getChildren().remove(this);
        }
        this.mother = mother;
        if (!mother.getChildren().contains(this)) {
            mother.getChildren().add((M) this);
        }
    }

    /**
     * Unassigns the member as the mother of this member
     *
     * @param mother the member to be unassigned as this member's mother
     */
    public void unSetMother(M mother) {
        if (this.getMother() == mother) {
            mother.getChildren().remove(this);
            this.mother = null;
        }
    }

    /**
     * Assigns the member as the father of this member
     *
     * @param father the member to be assigned as this member's father
     */
    public void setFather(M father) {
        if (this.getFather() != null) {
            this.getFather().getChildren().remove(this);
        }
        this.father = father;
        if (!father.getChildren().contains(this)) {
            father.getChildren().add((M) this);
        }
    }

    /**
     * Unassigns the member as the father of this member
     *
     * @param father the member to be unassigned as this member's father
     */
    public void unSetFather(M father) {
        if (this.getFather() == father) {
            father.getChildren().remove(this);
            this.father = null;
        }
    }

    /**
     * Adds the member to the ArrayList of this member's children
     *
     * @param child the member to be added to the ArrayList of children of this member
     */
    public void setChild(M child) {
        if (!this.children.contains(child)) {
            this.children.add(child);
        }
        if (this.gender.equals(Gender.Female)) {
            child.setMother((M) this);
        } else {
            child.setFather((M) this);
        }
    }

    /**
     * Assigns the member as the couple of this member
     *
     * @param couple the member to be assigned as this member's couple
     */
    public void setCouple(M couple) {
        if (this.getCouple() != null) {
            this.getCouple().divorce((M) this);
        }
        if (couple.getCouple() != null) {
            couple.getCouple().divorce(couple);
        }
        this.couple = couple;
        couple.couple = (M) this;
    }

    /**
     * Removes the member as this member's couple, removes this member as given member's couple
     *
     * @param couple the member to be unassigned as this member's couple
     */
    public void divorce(M couple) {
        if (this.getCouple().equals(couple)) {
            this.couple = null;
            couple.couple = null;
        }
    }

    /**
     * Compare this member to another member by their names and dates of birth
     *
     * @param member the member compare with this member
     * @return true if name and date of birth of given member equals to name,
     * and date of birth of this member, false otherwise
     */
    public abstract boolean equivalents(M member);

    /**
     * Returns a string containing short information about this mmeber
     *
     * @return string value containing the short information of this member
     */
    @Override
    public abstract String toString();
}
