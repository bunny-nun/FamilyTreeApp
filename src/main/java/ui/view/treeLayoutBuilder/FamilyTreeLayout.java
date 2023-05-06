package ui.view.treeLayoutBuilder;

import model.member.Member;
import ui.view.treeBuilder.FamilyTreeBuilder;
import ui.view.treeBuilder.node.CoupleNode;
import ui.view.treeBuilder.node.MainNode;
import ui.view.treeBuilder.node.Node;

public class FamilyTreeLayout<M extends Member<M>> extends TreeLayout<M> {
    public FamilyTreeLayout(M member) {
        super(member);
    }

    /**
     * Builds the visual tree with of the family of selected member
     */
    public void build() {
        int level = 0;
        MainNode<M> memberNode = new MainNode<>(this.member, this.width, this.height, this.gap);
        memberNode.setLevel(level);
        addParents(this.member, memberNode, this.width, this.height, level - 1);
        addChildren(this.member, memberNode, this.width, this.height, level + 1);
        addCouple(this.member, memberNode, this.width, this.height, level);
        FamilyTreeBuilder<M> familyTreeBuilder = new FamilyTreeBuilder<>(memberNode);
        this.getChildren().add(familyTreeBuilder);
    }

    private void addParents(M member, Node<M> node, double width, double height, int level) {
        if (member.getMother() != null) {
            Node<M> motherNode = new Node<>(member.getMother(), width, height);
            motherNode.setLevel(level);
            node.setMother(motherNode);
            addParents(member.getMother(), motherNode, width, height, level - 1);
        }
        if (member.getFather() != null) {
            Node<M> fatherNode = new Node<>(member.getFather(), width, height);
            fatherNode.setLevel(level);
            node.setFather(fatherNode);
            addParents(member.getFather(), fatherNode, width, height, level - 1);
        }
    }

    private void addChildren(M member, Node<M> node, double width, double height, int level) {
        if (!member.getChildren().isEmpty()) {
            for (M child : member.getChildren()) {
                Node<M> childNode = new Node<>(child, width, height);
                childNode.setLevel(level);
                if (member.equals(child.getMother())) {
                    childNode.setMother(node);
                } else if (member.equals(child.getFather())) {
                    childNode.setFather(node);
                }
                addChildren(child, childNode, width, height, level + 1);
            }
        }
    }

    private void addCouple(M member, MainNode<M> node, double width, double height, int level) {
        if (member.getCouple() != null) {
            CoupleNode<M> coupleNode = new CoupleNode<>(member.getCouple(), width, height);
            coupleNode.setLevel(level);
            node.setCouple(coupleNode);
        }
    }
}
