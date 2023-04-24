package ui.view.commands;

import model.member.Member;
import ui.view.TextInBox;
import ui.view.treeLayoutBuilder.ChildrenTreeLayoutBuilder;
import ui.view.treeLayoutBuilder.TreeLayoutBuilder;

public class ChildrenCommand <M extends Member<M>> extends Command<M> {
    public ChildrenCommand() {
        super("Show Successors");
    }

    /**
     * Builds the visual tree of successors selected member
     *
     * @param member          selected member
     * @param memberTextInBox box node crated for this selected member
     * @return the visual tree of successors selected member
     */
    @Override
    public TreeLayoutBuilder<M> execute(M member, TextInBox<M> memberTextInBox) {
        ChildrenTreeLayoutBuilder<M> tree = new ChildrenTreeLayoutBuilder<>(member, memberTextInBox);
        tree.build();
        return tree;
    }
}
