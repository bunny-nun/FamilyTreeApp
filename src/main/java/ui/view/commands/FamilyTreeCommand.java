package ui.view.commands;

import model.member.Member;
import ui.view.treeLayoutBuilder.FamilyTreeLayout;

public class FamilyTreeCommand<M extends Member<M>> extends Command<M> {
    public FamilyTreeCommand() {
        super("Show Family Tree");
    }

    /**
     * Builds the visual tree of ancestors of selected member
     *
     * @param member selected member
     * @return the visual tree of ancestors of selected member
     */
    @Override
    public FamilyTreeLayout<M> execute(M member) {
        FamilyTreeLayout<M> tree = new FamilyTreeLayout<>(member);
        tree.build();
        return tree;
    }
}
