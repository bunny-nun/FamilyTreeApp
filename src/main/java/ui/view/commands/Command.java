package ui.view.commands;

import model.member.Member;
import ui.view.TextInBox;
import ui.view.treeLayoutBuilder.TreeLayoutBuilder;

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
     * @param member          selected member
     * @param memberTextInBox box node crated for this selected member
     * @return visual tree of selected member
     */
    public abstract TreeLayoutBuilder<M> execute(M member, TextInBox<M> memberTextInBox);
}
