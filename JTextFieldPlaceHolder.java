package komalhealthcare;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

public class JTextFieldPlaceHolder extends BasicTextFieldUI implements FocusListener {  
    private String hint;
    private Color  hintColor;

    public JTextFieldPlaceHolder(String hint, Color hintColor) {
        this.hint = hint;
        this.hintColor = hintColor;
    }
    public JTextFieldPlaceHolder(String hint) {
        this.hint = hint;
        this.hintColor = Color.GRAY;
    }
    private void repaint() {
        if (getComponent() != null) {
            getComponent().repaint();
        }
    }
    protected void paintSafely(Graphics g) {
        // Render the default text field UI
        super.paintSafely(g);
        // Render the hint text
        JTextComponent component = getComponent();
        if (component.getText().length() == 0 && !component.hasFocus()) {
            g.setColor(hintColor);
            int padding = (component.getHeight() - component.getFont().getSize()) / 2;
            int inset = 3;
            g.drawString(hint, inset, component.getHeight() - padding - inset);
        }
    }
    public void focusGained(FocusEvent e) {
        repaint();
    }
    public void focusLost(FocusEvent e) {
        repaint();
    }
    public void installListeners() {
        super.installListeners();
        getComponent().addFocusListener(this);
    }
    public void uninstallListeners() {
        super.uninstallListeners();
        getComponent().removeFocusListener(this);
    }
}

