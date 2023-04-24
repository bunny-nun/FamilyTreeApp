package ui.view;

import javafx.scene.paint.Color;

import model.member.Gender;
import model.member.Member;

public class TextInBox <M extends Member<M>> {
    private final String text;
    private final int height;
    private final int width;
    private final Color boxColor;

    public TextInBox(M member) {
        StringBuilder textBuilder = new StringBuilder();
        textBuilder.append(member.getName()).append("\n");
        if (member.getDeathDate() != null) {
            textBuilder.append(String.format("(%s - %s)", member.getBirthDate(), member.getDeathDate()));
        } else {
            textBuilder.append(String.format("(%s)", member.getBirthDate()));
        }
        textBuilder.append("\n")
                .append(String.format("%s years old", member.getAge()));
        this.text = textBuilder.toString();
        this.width = 200;
        this.height = 80;
        if (member.getGender() == Gender.Male) {
            this.boxColor = Color.web("ace7e7");
        } else {
            this.boxColor = Color.web("eec4d8");
        }
    }

    public String getText() {
        return this.text;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public Color getBoxColor() {
        return this.boxColor;
    }
}
