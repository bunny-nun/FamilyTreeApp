module ui.familytreeapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.abego.treelayout.core;

    opens ui to javafx.fxml;
    exports ui;
    exports model.member.person;
    exports model.member.pet;
    exports model.tree;
    exports model.service;
    exports model.member;
    exports ui.view;
    exports ui.view.commands;
    exports ui.view.treeLayoutBuilder;
}