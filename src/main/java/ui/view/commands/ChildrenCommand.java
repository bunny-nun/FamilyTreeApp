package ui.view.commands;

import model.member.Member;
import ui.view.treeLayoutBuilder.ChildrenTreeLayout;

public class ChildrenCommand<M extends Member<M>> extends Command<M> {
    public ChildrenCommand() {
        super("Show Successors");
    }

    /**
     * Builds the visual tree of successors selected member
     *
     * @param member selected member
     * @return the visual tree of successors selected member
     */
    @Override
    public ChildrenTreeLayout<M> execute(M member) {
        ChildrenTreeLayout<M> tree = new ChildrenTreeLayout<>(member);
        tree.build();
        return tree;
    }
}
