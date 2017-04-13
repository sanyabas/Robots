package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class PositionWindow extends JInternalFrame implements Observer
{
//    private final GameVisualizer visualizer;
    private GameModel gameModel;
    private JLabel xLabel;
    private JLabel yLabel;
    private JLabel angleLabel;

    public PositionWindow(GameModel model)
    {
        super("Поле координат", true, true, true, true);
        gameModel=model;
        gameModel.addObserver(this);
        xLabel = new JLabel();
        xLabel.setLocation(50,10);
        xLabel.setSize(50,20);
        xLabel.setText("xLabel");

        yLabel = new JLabel();
        yLabel.setLocation(50,40);
        yLabel.setSize(50,20);
        yLabel.setText("yLabel");

        angleLabel = new JLabel();
        angleLabel.setLocation(50,70);
        angleLabel.setSize(50,20);
        angleLabel.setText("angleLabel");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(xLabel,BorderLayout.NORTH);
        panel.add(yLabel,BorderLayout.CENTER);
        panel.add(angleLabel,BorderLayout.SOUTH);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        updateCoordinates();
    }

    private void updateCoordinates()
    {
        xLabel.setText("X: "+gameModel.getRobotPositionX());
        yLabel.setText("Y: "+gameModel.getRobotPositionY());
        angleLabel.setText("Angle: "+gameModel.getRobotDirection());
    }
}
