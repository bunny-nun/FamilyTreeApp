package ui;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import model.member.Member;
import ui.view.commands.Command;
import ui.view.treeLayoutBuilder.TreeLayout;

public class ViewController<M extends Member<M>> {
    @FXML
    private BorderPane root;
    private M member;
    private Command<M> command;


    @FXML
    void initialize() {
    }

    /**
     * Sets the selected by user command with right button click as this command to execute
     *
     * @param command the selected by user command
     */
    public void setCommand(Command<M> command) {
        this.command = command;
    }

    /**
     * Sets the selected member in a row to build their tree as this member
     *
     * @param member the selected member in a row to build their tree
     */
    public void setMember(M member) {
        this.member = member;
    }

    /**
     * Shows the selected type of tree for selected member
     */
    public void show() {
        TreeLayout<M> tree = this.command.execute(this.member);
        this.root.setCenter(tree);
    }
}
