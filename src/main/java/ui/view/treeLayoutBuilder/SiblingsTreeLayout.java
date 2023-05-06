package ui.view.treeLayoutBuilder;

import model.member.Member;
import model.tree.TreeBuilder;
import ui.view.treeBuilder.FamilyTreeBuilder;
import ui.view.treeBuilder.node.MainNode;
import ui.view.treeBuilder.node.Node;


public class SiblingsTreeLayout<M extends Member<M>> extends TreeLayout<M> {
    public SiblingsTreeLayout(M member) {
        super(member);
    }

    /**
     * Builds the visual tree with of siblings of selected member
     */
    public void build() {
        int level = 0;
        MainNode<M> memberNode = new MainNode<>(this.member, this.width, this.height, this.gap);
        memberNode.setLevel(level);
        addSiblings(this.member, memberNode, this.width, this.height, level + 1);
        FamilyTreeBuilder<M> familyTreeBuilder = new FamilyTreeBuilder<>(memberNode);
        this.getChildren().add(familyTreeBuilder);
    }

    private void addSiblings(M member, Node<M> node, double width, double height, int level) {
        if (member.getMother() != null) {
            TreeBuilder<M> treeBuilder = new TreeBuilder<>();
            for (M sibling : treeBuilder.getSiblings(member)) {
                if (!sibling.equals(member)) {
                    Node<M> siblingNode = new Node<>(sibling, width, height);
                    siblingNode.setLevel(level);
                    siblingNode.setMother(node);
                }
            }
        }
    }
}
