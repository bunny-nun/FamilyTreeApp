package ui.view.commands;

import model.member.Member;
import ui.view.treeLayoutBuilder.TreeLayout;

public abstract class Command<M extends Member<M>> {
    String description;

    public Command(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Builds a visual tree of selected member
     *
     * @param member selected member
     * @return visual tree of selected member
     */
    public abstract TreeLayout<M> execute(M member);
}
