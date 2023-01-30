package game.View;

import game.Controller.SaveController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Objects;

public class MenuBar extends JMenuBar{

//    static JMenuBar bar;
    // JMenu
    static JMenu save, load, rules;

    // Menu items
    static JMenuItem save1, save2, save3, load1, load2, load3;


    public MenuBar(){
        // create a menu
        save = new JMenu("Save");
        load = new JMenu("Load");

        // create menuitems
        save1 = new JMenuItem("Save 1");
        save2 = new JMenuItem("Save 2");
        save3 = new JMenuItem("Save 3");
        load1 = new JMenuItem("Load 1");
        load2 = new JMenuItem("Load 2");
        load3 = new JMenuItem("Load 3");

        // add ActionListener to menuItems
        save1.addActionListener(e -> SaveController.save(1));
        save2.addActionListener(e -> SaveController.save(2));
        save3.addActionListener(e -> SaveController.save(3));
        load1.addActionListener(e -> SaveController.load(1));
        load2.addActionListener(e -> SaveController.load(2));
        load3.addActionListener(e -> SaveController.load(3));

        // add menu items to menu
        save.add(save1);
        save.add(save2);
        save.add(save3);
        load.add(load1);
        load.add(load2);
        load.add(load3);

        rules = new JMenu("Rules");
        JPanel sidePanel = new JPanel();
//        {
//            @Override
//            public void paintComponents(Graphics g) {
//                super.paintComponents(g);
//                java.net.URL imgURL = getClass().getResource("/images/background2.png");
//                assert imgURL != null;
//                g.drawImage(new ImageIcon(imgURL).getImage(),0,0,200,300,this);
//            }
//        };
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.add(new RulesView());

        sidePanel.add(new JLabel(" "));
        sidePanel.add(new JLabel("by:"));
        sidePanel.add(new JLabel("Abd Allah Karajay"));
        sidePanel.add(new JLabel("Tareq Al Mozayek"));
        sidePanel.add(new JLabel(" "));

        rules.add(sidePanel);

        add(save);
        add(load);
        add(rules);

    }
}

