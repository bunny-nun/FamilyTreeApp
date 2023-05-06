package ui.view.commands;

import model.member.Member;
import ui.view.treeLayoutBuilder.CoupleTreeLayout;

public class CoupleCommand<M extends Member<M>> extends Command<M> {
    public CoupleCommand() {
        super("Show Couple");
    }

    /**
     * Builds the visual tree with selected member and their couple
     *
     * @param member selected member
     * @return the visual tree with selected member and their couple
     */
    @Override
    public CoupleTreeLayout<M> execute(M member) {
        CoupleTreeLayout<M> tree = new CoupleTreeLayout<>(member);
        tree.build();
        return tree;
    }
}
