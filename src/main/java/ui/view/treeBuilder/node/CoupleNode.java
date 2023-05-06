package ui.view.treeBuilder.node;

import javafx.scene.paint.Color;
import model.member.Gender;
import model.member.Member;

import java.util.ArrayList;

public class CoupleNode<M extends Member<M>> extends Node<M> {

    public CoupleNode(M member, double wight, double height) {
        super(member, wight, height);
        if (member.getGender() == Gender.Female) {
            setFillColor(Color.LIGHTPINK);
        } else {
            setFillColor(Color.web("77e2e4"));
        }
    }
}
