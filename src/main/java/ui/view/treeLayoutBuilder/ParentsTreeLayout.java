package ui.view.treeLayoutBuilder;

import model.member.Member;
import ui.view.treeBuilder.FamilyTreeBuilder;
import ui.view.treeBuilder.node.MainNode;
import ui.view.treeBuilder.node.Node;

public class ParentsTreeLayout<M extends Member<M>> extends TreeLayout<M> {
    public ParentsTreeLayout(M member) {
        super(member);
    }

    /**
     * Builds the visual tree with of ancestors of selected member
     */
    public void build() {
        int level = 0;
        MainNode<M> memberNode = new MainNode<>(this.member, this.width, this.height, this.gap);
        memberNode.setLevel(level);
        addParents(this.member, memberNode, this.width, this.height, level - 1);
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
}
