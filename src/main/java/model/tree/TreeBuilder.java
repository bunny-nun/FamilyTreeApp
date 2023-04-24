package model.tree;

import model.member.Member;
import model.member.person.Person;

public class TreeBuilder<M extends Member<M>> {

    /**
     * Creates the family-tree containing the extended family members (successors, ancestors,
     * only this member's couple (for class Person) of the given member
     */
    public Tree<M> getFamily(M member) {
        Tree<M> family = new Tree<>(member);
        addParents(member, family);
        addChildren(member, family);
        if (member instanceof Person) {
            addCouple(member, family);
        }
        family.setName(String.format("%s_%s", member.getName(), member.getClass().getSimpleName()));
        return family;
    }

    /**
     * Private method adds all successors of the specified member
     *
     * @param member the given member
     */
    private void addChildren(M member, Tree<M> tree) {
        tree.addMember(member);
        for (M child : member.getChildren()) {
            tree.addMember(child);
        }
    }

    /**
     * Private method adds all ancestors of the specified member
     *
     * @param member the given member
     */
    private void addParents(M member, Tree<M> tree) {
        if (member.getMother() != null) {
            tree.addMember(member.getMother());
            addParents(member.getMother(), tree);
            addChildren(member.getMother(), tree);
        }
        if (member.getFather() != null) {
            tree.addMember(member.getFather());
            addParents(member.getFather(), tree);
            addChildren(member.getFather(), tree);
        }
    }

    /**
     * Private method adds the couple of a specified member if it's assigned
     *
     * @param member an object of a class Member
     */
    private void addCouple(M member, Tree<M> tree) {
        if (member.getCouple() != null) {
            tree.addMember(member.getCouple());
        }
    }

    /**
     * Creates the tree containing brothers and sisters of the given member (including half siblings)
     */
    public Tree<M> getSiblings(M member) {
        Tree<M> siblings = new Tree<>(member);
        for (M child : member.getMother().getChildren()) {
            siblings.addMember(child);
        }
        for (M child : member.getFather().getChildren()) {
            siblings.addMember(child);
        }
        siblings.setName(String.format("%s_%s_Siblings", member.getName(), member.getClass().getSimpleName()));
        return siblings;
    }

    /**
     * Creates the tree containing the direct family connections to the member (parents, children, the spouse(for class Person), siblings)
     */
    public Tree<M> getRelatives(M member) {
        Tree<M> relatives = new Tree<>(member);
        relatives.addMember(member.getMother());
        relatives.addMember(member.getFather());
        for (M child : member.getChildren()) {
            relatives.addMember(child);
        }
        relatives.addMembers(getSiblings(member));
        relatives.addMember(member.getCouple());
        relatives.setName(String.format("%s_%s_Direct_Relatives", member.getName(), member.getClass().getSimpleName()));
        return relatives;
    }

    /**
     * Private method sets connections between members of the tree if their first names, last names and dates of birth are matching
     *
     * @param member an object of class Member which connections will be assigned
     */
    public void setConnections(M member, Tree<M> tree) {
        for (M element : tree) {
            if (member.getMother() != null && member.getMother().equivalents(element)) {
                member.setMother(element);
            }
            if (member.getFather() != null && member.getFather().equivalents(element)) {
                member.setFather(element);
            }
            if (member.getCouple() != null && member.getCouple().equivalents(element)) {
                member.setCouple(element);
            }
        }
    }
}
