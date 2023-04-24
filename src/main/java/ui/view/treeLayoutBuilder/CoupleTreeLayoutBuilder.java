package ui.view.treeLayoutBuilder;

import model.member.Member;
import org.abego.treelayout.Configuration;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;
import ui.view.BoxNodeExtentProvider;
import ui.view.TextInBox;
import ui.view.TreeLayoutView;

public class CoupleTreeLayoutBuilder<M extends Member<M>> extends TreeLayoutBuilder<M> {
    public CoupleTreeLayoutBuilder(M member, TextInBox<M> memberTextInBox) {
        super(member, memberTextInBox);
    }

    /**
     * Builds the visual tree with selected member and their couple
     */
    public void build() {
        DefaultTreeForTreeLayout<TextInBox<M>> tree = new DefaultTreeForTreeLayout<>(this.memberTextInBox);
        this.add(this.member, this.memberTextInBox, tree);
        DefaultConfiguration<TextInBox<M>> configuration = new DefaultConfiguration<>(this.gapBetweenLevels, this.gapBetweenNodes, Configuration.Location.Left);
        BoxNodeExtentProvider<M> nodeExtentProvider = new BoxNodeExtentProvider<>();
        TreeLayout<TextInBox<M>> treeLayout = new TreeLayout<>(tree, nodeExtentProvider, configuration);
        this.treeLayoutView = new TreeLayoutView<>(treeLayout);
        this.getChildren().add(this.treeLayoutView);
    }

    /**
     * Private function adding boxes for members to be included in the visual tree
     *
     * @param member          member to get connections
     * @param memberTextInBox member's box node
     * @param tree            the visual tree to add the connected member
     */
    private void add(M member, TextInBox<M> memberTextInBox, DefaultTreeForTreeLayout<TextInBox<M>> tree) {
        if (member.getCouple() != null) {
            TextInBox<M> coupleTextInBox = new TextInBox<>(member.getCouple());
            tree.addChild(memberTextInBox, coupleTextInBox);
        }
    }
}
