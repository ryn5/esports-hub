package tabs;

import ui.AbstractScreen;

import javax.swing.*;
import java.awt.*;

import static com.sun.javafx.fxml.expression.Expression.add;

public class Panel {
    protected AbstractScreen parent;

    protected JPanel panel;

    protected Integer maxKey;
    public Panel(AbstractScreen parent) {
        this.parent = parent;
        panel = new JPanel(new BorderLayout());
        panel.setBackground(AbstractScreen.MAIN_COLOR);
        maxKey = 0;
        add(panel, BorderLayout.CENTER);
    }

    public AbstractScreen getParent() {
        return parent;
    }

    public Integer getMaxKey() {
        return maxKey;
    }

    public Integer updateMaxKey(String key, String table) {
        maxKey = parent.getDbHandler().getMaxKey(key,table);
        return maxKey;
    }

    public JPanel getPanel() {
        return panel;
    }

    public Panel getThis() {
        return this;
    }

}
