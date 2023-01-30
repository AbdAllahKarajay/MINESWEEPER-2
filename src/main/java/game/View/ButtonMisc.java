package game.View;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.util.Objects;

public class ButtonMisc {
    //adds hover and pressed img
    public static void HoverAndPress(JButton button, String imgName, boolean pressSound){
        ImageIcon normalImage = new ImageIcon(Objects.requireNonNull(ButtonMisc.class.getResource("/images/" + imgName +".png")));
        button.setIcon(normalImage);
        button.getModel().addChangeListener(new ChangeListener() {
            final ImageIcon normal = normalImage;
            final ImageIcon hover = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/" + imgName +"H.png")));
            final ImageIcon pressed = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/" + imgName +"P.png")));

            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isRollover()) {
                    button.setIcon(hover);
                } else {
                    button.setIcon(normal);
                }
                if(model.isPressed()){
                    button.setIcon(pressed);
                }
            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playSound("hover.wav");
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(pressSound) playSound("click.wav");
            }
        });
    }

    public static void HoverAndPress(JButton button, String imgName, int width, int height){
            BufferedImage bufferedNormal = null;
            BufferedImage bufferedHover = null;
            BufferedImage bufferedPressed = null;
            try{
                bufferedNormal = ImageIO.read(Objects.requireNonNull(ButtonMisc.class.getResourceAsStream("/images/" + imgName + ".png")));
                bufferedHover = ImageIO.read(Objects.requireNonNull(ButtonMisc.class.getResourceAsStream("/images/" + imgName + "H.png")));
                bufferedPressed = ImageIO.read(Objects.requireNonNull(ButtonMisc.class.getResourceAsStream("/images/" + imgName + "P.png")));
                }catch(Exception ignored){}
        assert bufferedNormal != null;
        assert bufferedHover != null;
        assert bufferedPressed != null;
        Image normalImage = bufferedNormal.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        Image hoverImage = bufferedHover.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        Image pressedImage = bufferedPressed.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        button.setIcon(new ImageIcon((normalImage)));
        button.getModel().addChangeListener(new ChangeListener() {
            final ImageIcon normal = new ImageIcon(normalImage);
            final ImageIcon hover = new ImageIcon(hoverImage);
            final ImageIcon pressed = new ImageIcon(pressedImage);

            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isRollover()) {
                    button.setIcon(hover);
                } else {
                    button.setIcon(normal);
                }
                if(model.isPressed()){
                    button.setIcon(pressed);
                }
            }
        });
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playSound("hover.wav");
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                playSound("click.wav");
            }
        });
    }
    //adds sound effect
    public static void playSound(String fileName){
        try {
            BufferedInputStream buff = new BufferedInputStream(Objects.requireNonNull(ButtonMisc.class.getResourceAsStream("/sounds/" + fileName)));
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(buff);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
