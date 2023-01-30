package game.View;

import game.Controller.RulesController;

import javax.swing.*;
import java.awt.*;

public class RulesView extends JPanel {
    public RulesView(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Rules:"), BorderLayout.WEST);
        add(RulesController.r1);
        add(RulesController.r2);
        add(RulesController.r3);
        add(RulesController.r4);
        add(RulesController.r5);
    }
}
