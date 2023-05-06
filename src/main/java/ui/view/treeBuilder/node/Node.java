package ui.view.treeBuilder.node;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import model.member.Gender;
import model.member.Member;

import java.util.ArrayList;
import java.util.HashMap;

public class Node<M extends Member<M>> extends Group {
    private final Member<M> member;
    private final double WIDTH;
    private final double HEIGHT;
    private Rectangle rectangle;
    private Color fillColor;
    private VBox vBox;
    private final HashMap<String, Double> coordinates;
    private Node<M> mother;
    private Node<M> father;
    private final ArrayList<Node<M>> childrenNodes;
    private int level;

    public Node(M member, double width, double height) {
        this.member = member;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.setRectangle(this.WIDTH, this.HEIGHT);
        this.setText();
        this.coordinates = new HashMap<>();
        this.setCoordinates(0, 0);
        this.childrenNodes = new ArrayList<>();
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        this.rectangle.setFill(this.fillColor);
    }

    public void setStrokeWidth(double width) {
        this.rectangle.setStrokeWidth(width);
    }

    public void setCoordinates(double x, double y) {
        this.coordinates.put("x", x);
        this.coordinates.put("y", y);
        this.rectangle.relocate(x, y);
        this.vBox.relocate(x, y);
        this.relocate(x, y);
    }

    public Member<M> getMember() {
        return this.member;
    }

    public int getLevel() {
        return this.level;
    }

    public double getWIDTH() {
        return this.WIDTH;
    }

    public double getHEIGHT() {
        return this.HEIGHT;
    }

    public double getX() {
        return this.coordinates.get("x");
    }

    public double getY() {
        return this.coordinates.get("y");
    }

    public double getCenterX() {
        return this.getX() + this.rectangle.getWidth() / 2;
    }

    public double getCenterY() {
        return this.getY() + this.rectangle.getHeight() / 2;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMother(Node<M> parent) {
        this.mother = parent;
        parent.addChild(this);
    }

    public void setFather(Node<M> parent) {
        this.father = parent;
        parent.addChild(this);
    }

    private void addChild(Node<M> child) {
        if (!this.childrenNodes.contains(child)) {
            this.childrenNodes.add(child);
        }
    }

    public Node<M> getMother() {
        return this.mother;
    }

    public Node<M> getFather() {
        return this.father;
    }

    public ArrayList<Node<M>> getParentsNodes() {
        ArrayList<Node<M>> parentsNodes = new ArrayList<>();
        if (this.mother != null) {
            parentsNodes.add(this.mother);
        }
        if (this.father != null) {
            parentsNodes.add(this.father);
        }
        return parentsNodes;
    }

    public ArrayList<Node<M>> getChildrenNodes() {
        return this.childrenNodes;
    }

    public boolean hasParents() {
        return this.mother != null || this.father != null;
    }

    public boolean hasChildren() {
        return !this.childrenNodes.isEmpty();
    }

    private void setRectangle(double width, double height) {
        this.rectangle = new Rectangle(width, height);
        this.rectangle.setArcWidth(15);
        this.rectangle.setArcHeight(15);
        if (member.getGender() == Gender.Male) {
            this.fillColor = Color.web("ace7e7");
        } else {
            this.fillColor = Color.web("f9d1d7");
        }
        this.rectangle.setFill(this.fillColor);
        this.rectangle.setStroke(Color.BLACK);
        this.getChildren().add(rectangle);
    }

    private void setText() {
        this.vBox = new VBox();
        this.vBox.setAlignment(Pos.CENTER);
        this.vBox.setPrefSize(200, 80);

        String titleString = member.getName() + "\n";
        Text title = new Text(titleString);
        title.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
        title.setTextAlignment(TextAlignment.CENTER);

        String dataString = "";
        if (member.getDeathDate() != null) {
            dataString += String.format("(%s - %s)", member.getBirthDate(), member.getDeathDate());
        } else {
            dataString += String.format("(%s)", member.getBirthDate());
        }
        dataString += String.format("\n%s years old", member.getAge());
        Text data = new Text(dataString);
        data.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 12));
        data.setTextAlignment(TextAlignment.CENTER);

        this.vBox.getChildren().add(title);
        this.vBox.getChildren().add(data);
        this.getChildren().add(vBox);
    }

    @Override
    public String toString() {
        return this.member.getName();
    }
}
