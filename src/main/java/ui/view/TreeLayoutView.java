package ui.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import model.member.Member;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;

import java.awt.geom.Rectangle2D;

public class TreeLayoutView<M extends Member<M>> extends Group {
    private final TreeLayout<TextInBox<M>> treeLayout;
    private final Color BORDER_COLOR;
    private final Color TEXT_COLOR;

    public TreeLayoutView(TreeLayout<TextInBox<M>> treeLayout) {
        this.treeLayout = treeLayout;
        this.BORDER_COLOR = Color.BLACK;
        this.TEXT_COLOR = Color.BLACK;
        this.addEdges(getTree().getRoot());
        for (TextInBox<M> textInBox : treeLayout.getNodeBounds().keySet()) {
            addBox(textInBox);
        }
    }

    private TreeForTreeLayout<TextInBox<M>> getTree() {
        return this.treeLayout.getTree();
    }

    private Iterable<TextInBox<M>> getChildren(TextInBox<M> parent) {
        return this.getTree().getChildren(parent);
    }

    private Rectangle2D.Double getBoundsOfNode(TextInBox<M> node) {
        return this.treeLayout.getNodeBounds().get(node);
    }

    private void addEdges(TextInBox<M> parent) {
        if (!getTree().isLeaf(parent)) {
            Rectangle2D.Double b1 = getBoundsOfNode(parent);
            double x1 = b1.getCenterX();
            double y1 = b1.getCenterY();
            for (TextInBox<M> child : getChildren(parent)) {
                Rectangle2D.Double b2 = getBoundsOfNode(child);
                this.getChildren().add(new Line(x1, y1, b2.getCenterX(), b2.getCenterY()));
                this.addEdges(child);
            }
        }
    }

    private void addBox(TextInBox<M> textInBox) {
        Rectangle2D.Double box = getBoundsOfNode(textInBox);

        Rectangle rectangle = new Rectangle(box.x, box.y, box.width - 1, box.height - 1);
        int ARC_SIZE = 15;
        rectangle.setArcWidth(ARC_SIZE);
        rectangle.setArcHeight(ARC_SIZE);
        rectangle.setFill(textInBox.getBoxColor());
        rectangle.setStroke(BORDER_COLOR);
        this.getChildren().add(rectangle);

        Label label = new Label(textInBox.getText());
        label.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 12));
        label.setStyle("-fx-alignment: center; -fx-text-alignment: center; -fx-text-fill: " + colorStyleString(TEXT_COLOR) + ";");
        label.relocate(box.x, box.y);
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        label.setPrefSize(box.width, box.height);
        label.setMaxSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        label.setWrapText(true);
        this.getChildren().add(label);
    }

    private String colorStyleString(Color color) {
        return "rgba("
                + ((int) (color.getRed() * 255)) + ","
                + ((int) (color.getGreen() * 255)) + ","
                + ((int) (color.getBlue() * 255)) + ","
                + color.getOpacity() +
                ")";
    }
}
