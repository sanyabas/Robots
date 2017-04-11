package gui;

import javax.swing.*;
import java.awt.*;

public class PositionWindow extends JInternalFrame
{
    private final GameVisualizer visualizer;

    public PositionWindow(GameVisualizer visualizer)
    {
        super("Поле координат", true, true, true, true);
        this.visualizer=visualizer;
        JLabel xLabel = new JLabel();
        xLabel.setLocation(50,10);
        xLabel.setSize(50,20);
        xLabel.setText("xLabel");

        JLabel yLabel = new JLabel();
        yLabel.setLocation(50,40);
        yLabel.setSize(50,20);
        yLabel.setText("yLabel");

        JLabel angleLabel = new JLabel();
        angleLabel.setLocation(50,70);
        angleLabel.setSize(50,20);
        angleLabel.setText("angleLabel");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(xLabel,BorderLayout.NORTH);
        panel.add(yLabel,BorderLayout.CENTER);
        panel.add(angleLabel,BorderLayout.SOUTH);
        getContentPane().add(panel);
        pack();

//        visualizer.
    }
}
