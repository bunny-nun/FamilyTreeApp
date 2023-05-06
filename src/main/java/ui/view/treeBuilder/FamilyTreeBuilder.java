package ui.view.treeBuilder;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import model.member.Member;
import ui.view.treeBuilder.node.MainNode;
import ui.view.treeBuilder.node.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FamilyTreeBuilder<M extends Member<M>> extends Group {
    private final MainNode<M> mainNode;
    private final double GAP;
    private final double WIDTH;
    private final double HEIGHT;
    private final ArrayList<Node<M>> nodes;
    private final HashMap<Integer, ArrayList<Node<M>>> parentsLevels;
    private final HashMap<Integer, ArrayList<Node<M>>> childrenLevels;

    public FamilyTreeBuilder(MainNode<M> mainNode) {
        this.mainNode = mainNode;
        this.GAP = this.mainNode.getGAP();
        this.WIDTH = this.mainNode.getWIDTH();
        this.HEIGHT = this.mainNode.getHEIGHT();
        this.nodes = new ArrayList<>();
        this.parentsLevels = new HashMap<>();
        this.childrenLevels = new HashMap<>();
        this.nodes.add(this.mainNode);

        this.parentsLevels.put(this.mainNode.getLevel(), new ArrayList<>());
        this.parentsLevels.get(this.mainNode.getLevel()).add(this.mainNode);
        this.addParents(this.mainNode);
        if (this.mainNode.hasParents()) {
            layoutParent(this.mainNode, getTopStartX());
        }
        this.childrenLevels.put(this.mainNode.getLevel(), new ArrayList<>());
        this.childrenLevels.get(this.mainNode.getLevel()).add(this.mainNode);
        this.addChildren(this.mainNode);
        if (this.mainNode.hasChildren()) {
            layoutChild(this.mainNode, getBottomStartX());
        }
        this.addCouple(this.mainNode);
        this.addConnections();
        this.getChildren().addAll(this.nodes);
    }

    private void addParents(Node<M> node) {
        if (node.getMother() != null) {
            Node<M> mother = node.getMother();
            this.nodes.add(mother);
            this.parentsLevels.putIfAbsent(mother.getLevel(), new ArrayList<>());
            this.parentsLevels.get(mother.getLevel()).add(mother);
            this.addParents(mother);
        }
        if (node.getFather() != null) {
            Node<M> father = node.getFather();
            this.nodes.add(father);
            this.parentsLevels.putIfAbsent(father.getLevel(), new ArrayList<>());
            this.parentsLevels.get(father.getLevel()).add(father);
            this.addParents(father);
        }
    }

    private double getTopStartX() {
        ArrayList<Double> maxSizes = new ArrayList<>();
        for (int i = 0; i > Collections.min(this.parentsLevels.keySet()); i--) {
            ArrayList<Integer> sizes = new ArrayList<>();
            for (Node<M> child : this.parentsLevels.get(i)) {
                sizes.add(child.getParentsNodes().size());
            }
            double maxSize = Collections.max(sizes);
            maxSizes.add(maxSize);
        }
        double maxSize;
        double upperRowSize = 1;
        for (Double size : maxSizes) {
            upperRowSize *= size;
        }
        if (needParentAdjustment()) {
            maxSize = maxSizes.get(maxSizes.size() - 1);
        } else {
            if (maxSizes.size() > 1) {
                maxSize = maxSizes.get(maxSizes.size() - 2);
                upperRowSize = upperRowSize / maxSizes.get(maxSizes.size() - 1);
            } else {
                maxSize = maxSizes.get(maxSizes.size() - 1);
            }
        }

        System.out.println(maxSizes);
        System.out.println(needParentAdjustment());


        double upperBoxWidth = maxSize * this.WIDTH + (maxSize - 1) * this.GAP;
        double maxWidth = upperRowSize / maxSize * upperBoxWidth + (upperRowSize / maxSize - 1) * this.GAP;
        return this.mainNode.getCenterX() - maxWidth / 2;
    }

    private void layoutParent(Node<M> node, double startX) {
        if (node.hasParents()) {
            double rowWidth = Math.abs(node.getCenterX() - startX) * 2;
            double cellWidth = rowWidth / node.getParentsNodes().size();
            for (int i = 0; i < node.getParentsNodes().size(); i++) {
                double parentX = startX + cellWidth * i + (cellWidth - this.WIDTH) / 2;
                Node<M> parent = node.getParentsNodes().get(i);
                if (parent.getLevel() == Collections.min(this.parentsLevels.keySet())) {
                    if (!needParentAdjustment()) {
                        cellWidth = (this.WIDTH + this.GAP) * node.getParentsNodes().size() - this.GAP;
                        parentX = node.getCenterX() - cellWidth / 2 + (this.WIDTH + this.GAP) * i;
                    }
                }
                parent.setCoordinates(parentX, countY(parent));
                layoutParent(parent, startX + cellWidth * i);
            }
        }
    }

    private boolean needParentAdjustment() {
        boolean needAdjustment = false;
        ArrayList<Node<M>> upperLevel = this.parentsLevels.get(Collections.min(this.parentsLevels.keySet()) + 1);
        for (int i = 1; i < upperLevel.size() - 1; i++) {
            if (upperLevel.get(i).getParentsNodes().size() > 1) {
                if (upperLevel.get(i - 1).hasParents() ||
                        upperLevel.get(i + 1).hasParents()) {
                    needAdjustment = true;
                }
            }
            if (upperLevel.get(i).getParentsNodes().size() == 1) {
                if (upperLevel.get(i - 1).getParentsNodes().size() > 1 ||
                        upperLevel.get(i + 1).getParentsNodes().size() > 1) {
                    needAdjustment = true;
                }
            }
        }
        if (upperLevel.size() == 2) {
            if ((upperLevel.get(0).hasParents() && upperLevel.get(1).getParentsNodes().size() > 1) ||
                    (upperLevel.get(1).hasParents() && upperLevel.get(0).getParentsNodes().size() > 1))
                needAdjustment = true;
        }
        return needAdjustment;
    }

    private void addChildren(Node<M> node) {
        if (node.hasChildren()) {
            for (Node<M> child : node.getChildrenNodes()) {
                this.nodes.add(child);
                this.childrenLevels.putIfAbsent(child.getLevel(), new ArrayList<>());
                this.childrenLevels.get(child.getLevel()).add(child);
                this.addChildren(child);
            }
        }
    }

    private double getBottomStartX() {
        ArrayList<Double> maxSizes = new ArrayList<>();
        for (int i = 0; i < Collections.max(this.childrenLevels.keySet()); i++) {
            ArrayList<Integer> sizes = new ArrayList<>();
            for (Node<M> parent : this.childrenLevels.get(i)) {
                sizes.add(parent.getChildrenNodes().size());
            }
            double maxSize = Collections.max(sizes);
            maxSizes.add(maxSize);
        }
        double maxSize;
        double bottomRowSize = 1;
        for (Double size : maxSizes) {
            bottomRowSize *= size;
        }
        if (needChildAdjustment()) {
            maxSize = maxSizes.get(maxSizes.size() - 1);
        } else {
            if (maxSizes.size() > 1) {
                maxSize = maxSizes.get(maxSizes.size() - 2);
                bottomRowSize = bottomRowSize / maxSizes.get(maxSizes.size() - 1);
            } else {
                maxSize = maxSizes.get(maxSizes.size() - 1);
            }
        }

        double bottomBoxWidth = maxSize * this.WIDTH + (maxSize - 1) * this.GAP;
        double maxWidth = bottomRowSize / maxSize * bottomBoxWidth + (bottomRowSize / maxSize - 1) * this.GAP;
        return this.mainNode.getCenterX() - maxWidth / 2;
    }

    private void layoutChild(Node<M> node, double startX) {
        if (node.hasChildren()) {
            double rowWidth = Math.abs(node.getCenterX() - startX) * 2;
            double cellWidth = rowWidth / node.getChildrenNodes().size();
            for (int i = 0; i < node.getChildrenNodes().size(); i++) {
                double childX = startX + cellWidth * i + (cellWidth - this.WIDTH) / 2;
                Node<M> child = node.getChildrenNodes().get(i);
                if (child.getLevel() == Collections.min(this.childrenLevels.keySet())) {
                    if (!needChildAdjustment()) {
                        cellWidth = (this.WIDTH + this.GAP) * node.getChildrenNodes().size() - this.GAP;
                        childX = node.getCenterX() - cellWidth / 2 + (this.WIDTH + this.GAP) * i;
                    }
                }
                child.setCoordinates(childX, countY(child));
                layoutChild(child, startX + cellWidth * i);
            }
        }
    }

    private boolean needChildAdjustment() {
        boolean needAdjustment = false;
        ArrayList<Node<M>> bottomLevel = this.childrenLevels.get(Collections.max(this.childrenLevels.keySet()) - 1);
        for (int i = 1; i < bottomLevel.size() - 1; i++) {
            if (bottomLevel.get(i).getChildrenNodes().size() > 1) {
                if (bottomLevel.get(i - 1).hasChildren() ||
                        bottomLevel.get(i + 1).hasChildren()) {
                    needAdjustment = true;
                }
            }
            if (bottomLevel.get(i).getChildrenNodes().size() == 1) {
                if (bottomLevel.get(i - 1).getChildrenNodes().size() > 1 ||
                        bottomLevel.get(i + 1).getChildrenNodes().size() > 1) {
                    needAdjustment = true;
                }
            }
        }
        if (bottomLevel.size() == 2) {
            if ((bottomLevel.get(0).hasChildren() && bottomLevel.get(1).getChildrenNodes().size() > 1) ||
                    (bottomLevel.get(1).hasChildren() && bottomLevel.get(0).getChildrenNodes().size() > 1))
                needAdjustment = true;
        }
        return needAdjustment;
    }

    private void addCouple(MainNode<M> node) {
        if (node.hasCouple()) {
            double x;
            if (node.getFather() != null) {
                x = node.getFather().getX();
            } else {
                x = node.getX() + this.WIDTH + this.GAP;
            }
            node.getCoupleNode().setCoordinates(x, countY(node.getCoupleNode()));
            this.nodes.add(node.getCoupleNode());
        }
    }

    private double countY(Node<M> node) {
        return this.mainNode.getY() + (this.HEIGHT + this.GAP) * node.getLevel();
    }

    private Line NodeConnection(Node<M> node, Node<M> anotherNode) {
        double x1 = node.getCenterX();
        double y1 = node.getCenterY();
        double x2 = anotherNode.getCenterX();
        double y2 = anotherNode.getCenterY();
        return new Line(x1, y1, x2, y2);
    }

    private void addConnections() {
        for (Node<M> node : this.nodes) {
            for (Node<M> parent : node.getParentsNodes()) {
                this.getChildren().add(NodeConnection(node, parent));
            }
        }
        if (this.mainNode.hasCouple()) {
            this.getChildren().add(NodeConnection(this.mainNode, this.mainNode.getCoupleNode()));
        }
    }
}
