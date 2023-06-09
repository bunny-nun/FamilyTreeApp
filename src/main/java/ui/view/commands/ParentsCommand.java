package ui.view.commands;

import model.member.Member;
import ui.view.treeLayoutBuilder.ParentsTreeLayout;

public class ParentsCommand<M extends Member<M>> extends Command<M> {
    public ParentsCommand() {
        super("Show Ancestors");
    }

    /**
     * Builds the visual tree of ancestors of selected member
     *
     * @param member selected member
     * @return the visual tree of ancestors of selected member
     */
    @Override
    public ParentsTreeLayout<M> execute(M member) {
        ParentsTreeLayout<M> tree = new ParentsTreeLayout<>(member);
        tree.build();
        return tree;
    }
}
