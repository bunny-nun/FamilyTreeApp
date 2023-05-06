package ui.view.treeBuilder.node;

import javafx.scene.paint.Color;
import model.member.Gender;
import model.member.Member;

public class MainNode<M extends Member<M>> extends Node<M> {
    private final double GAP;
    private CoupleNode<M> coupleNode;

    public MainNode(M member, double wight, double height, double gap) {
        super(member, wight, height);
        if (member.getGender() == Gender.Female) {
            setFillColor(Color.LIGHTPINK);
        } else {
            setFillColor(Color.web("77e2e4"));
        }
        this.setStrokeWidth(1.5);
        this.GAP = gap;
    }

    public double getGAP() {
        return this.GAP;
    }

    public void setCouple(CoupleNode<M> couple) {
        this.coupleNode = couple;
    }

    public CoupleNode<M> getCoupleNode() {
        return this.coupleNode;
    }

    public boolean hasCouple() {
        return this.coupleNode != null;
    }
}
