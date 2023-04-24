package ui.view;

import model.member.Member;
import org.abego.treelayout.NodeExtentProvider;

public class BoxNodeExtentProvider<M extends Member<M>> implements NodeExtentProvider<TextInBox<M>> {
    @Override
    public double getWidth(TextInBox<M> textInBox) {
        return textInBox.getWidth();
    }

    @Override
    public double getHeight(TextInBox<M> textInBox) {
        return textInBox.getHeight();
    }
}
