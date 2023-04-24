package ui.view.commands;

import model.member.Member;
import ui.view.TextInBox;
import ui.view.treeLayoutBuilder.CoupleTreeLayoutBuilder;
import ui.view.treeLayoutBuilder.TreeLayoutBuilder;

public class CoupleCommand<M extends Member<M>> extends Command<M> {
    public CoupleCommand() {
        super("Show Couple");
    }

    /**
     * Builds the visual tree with selected member and their couple
     *
     * @param member          selected member
     * @param memberTextInBox box node crated for this selected member
     * @return the visual tree with selected member and their couple
     */
    @Override
    public TreeLayoutBuilder<M> execute(M member, TextInBox<M> memberTextInBox) {
        CoupleTreeLayoutBuilder<M> tree = new CoupleTreeLayoutBuilder<>(member, memberTextInBox);
        tree.build();
        return tree;
    }
}
