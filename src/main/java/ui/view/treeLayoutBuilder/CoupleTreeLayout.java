package ui.view.treeLayoutBuilder;

import model.member.Member;
import ui.view.treeBuilder.FamilyTreeBuilder;
import ui.view.treeBuilder.node.CoupleNode;
import ui.view.treeBuilder.node.MainNode;


public class CoupleTreeLayout<M extends Member<M>> extends TreeLayout<M> {
    public CoupleTreeLayout(M member) {
        super(member);
    }

    /**
     * Builds the visual tree with selected member and their couple
     */
    public void build() {
        int level = 0;
        MainNode<M> memberNode = new MainNode<>(this.member, this.width, this.height, this.gap);
        memberNode.setLevel(level);
        addCouple(this.member, memberNode, this.width, this.height, level);
        FamilyTreeBuilder<M> familyTreeBuilder = new FamilyTreeBuilder<>(memberNode);
        this.getChildren().add(familyTreeBuilder);
    }

    private void addCouple(M member, MainNode<M> node, double width, double height, int level) {
        if (member.getCouple() != null) {
            CoupleNode<M> coupleNode = new CoupleNode<>(member.getCouple(), width, height);
            coupleNode.setLevel(level);
            node.setCouple(coupleNode);
        }
    }
}
