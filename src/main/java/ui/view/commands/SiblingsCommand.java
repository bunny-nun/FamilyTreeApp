package ui.view.commands;

import model.member.Member;
import ui.view.TextInBox;
import ui.view.treeLayoutBuilder.SiblingsTreeLayoutBuilder;
import ui.view.treeLayoutBuilder.TreeLayoutBuilder;

public class SiblingsCommand<M extends Member<M>> extends Command<M> {
    public SiblingsCommand() {
        super("Show Siblings");
    }

    /**
     * Builds the visual tree of siblings of selected member
     *
     * @param member          selected member
     * @param memberTextInBox box node crated for this selected member
     * @return the visual tree of siblings of selected member
     */
    @Override
    public TreeLayoutBuilder<M> execute(M member, TextInBox<M> memberTextInBox) {
        SiblingsTreeLayoutBuilder<M> tree = new SiblingsTreeLayoutBuilder<>(member, memberTextInBox);
        tree.build();
        return tree;
    }
}
