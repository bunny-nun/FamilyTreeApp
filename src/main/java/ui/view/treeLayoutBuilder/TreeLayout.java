package ui.view.treeLayoutBuilder;

import javafx.scene.Group;
import model.member.Member;

public abstract class TreeLayout<M extends Member<M>> extends Group {
    M member;
    double width;
    double height;
    double gap;

    public TreeLayout(M member) {
        this.member = member;
        this.width = 200;
        this.height = 80;
        this.gap = 30;
    }

    /**
     * Builds the visual tree for selected member
     */
    public abstract void build();
}
