package ui.view.treeLayoutBuilder;

import javafx.scene.Group;
import model.member.Member;
import ui.view.TextInBox;
import ui.view.TreeLayoutView;

public abstract class TreeLayoutBuilder<M extends Member<M>> extends Group {
    TextInBox<M> memberTextInBox;
    M member;
    double gapBetweenLevels;
    double gapBetweenNodes;
    TreeLayoutView<M> treeLayoutView;

    public TreeLayoutBuilder(M member, TextInBox<M> memberTextInBox) {
        this.member = member;
        this.memberTextInBox = memberTextInBox;
        this.gapBetweenLevels = 40;
        this.gapBetweenNodes = 40;
    }

    /**
     * Builds a visual tree of selected member
     */
    public abstract void build();
}
