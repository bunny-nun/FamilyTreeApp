package ui.view.commands;

import model.member.Member;
import ui.view.treeLayoutBuilder.SiblingsTreeLayout;

public class SiblingsCommand<M extends Member<M>> extends Command<M> {
    public SiblingsCommand() {
        super("Show Siblings");
    }

    /**
     * Builds the visual tree of siblings of selected member
     *
     * @param member selected member
     * @return the visual tree of siblings of selected member
     */
    @Override
    public SiblingsTreeLayout<M> execute(M member) {
        SiblingsTreeLayout<M> tree = new SiblingsTreeLayout<>(member);
        tree.build();
        return tree;
    }
}
