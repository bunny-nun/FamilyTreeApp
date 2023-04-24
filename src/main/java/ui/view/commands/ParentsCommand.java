package ui.view.commands;

import model.member.Member;
import ui.view.TextInBox;
import ui.view.treeLayoutBuilder.ParentsTreeLayoutBuilder;
import ui.view.treeLayoutBuilder.TreeLayoutBuilder;

public class ParentsCommand<M extends Member<M>> extends Command<M> {
    public ParentsCommand() {
        super("Show Ancestors");
    }

    /**
     * Builds the visual tree of ancestors of selected member
     *
     * @param member          selected member
     * @param memberTextInBox box node crated for this selected member
     * @return the visual tree of ancestors of selected member
     */
    @Override
    public TreeLayoutBuilder<M> execute(M member, TextInBox<M> memberTextInBox) {
        ParentsTreeLayoutBuilder<M> tree = new ParentsTreeLayoutBuilder<>(member, memberTextInBox);
        tree.build();
        return tree;
    }
}
