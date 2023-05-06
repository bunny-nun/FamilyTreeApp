package ui.view.treeLayoutBuilder;

import model.member.Member;
import ui.view.treeBuilder.FamilyTreeBuilder;
import ui.view.treeBuilder.node.MainNode;
import ui.view.treeBuilder.node.Node;

public class ChildrenTreeLayout<M extends Member<M>> extends TreeLayout<M> {
    public ChildrenTreeLayout(M member) {
        super(member);
    }

    /**
     * Builds the visual tree with of successors of selected member
     */
    public void build() {
        int level = 0;
        MainNode<M> memberNode = new MainNode<>(this.member, this.width, this.height, this.gap);
        memberNode.setLevel(level);
        addChildren(this.member, memberNode, this.width, this.height, level + 1);
        FamilyTreeBuilder<M> familyTreeBuilder = new FamilyTreeBuilder<>(memberNode);
        this.getChildren().add(familyTreeBuilder);
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
}
