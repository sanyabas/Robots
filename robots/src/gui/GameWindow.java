package gui;

import sun.applet.Main;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{
    private final GameVisualizer m_visualizer;
    private final GameModel gameModel;
    public GameWindow(MainApplicationFrame frame)
    {
        super("Игровое поле", true, true, true, true);
        gameModel=new GameModel();
        m_visualizer = new GameVisualizer(gameModel);
//        gameModel =new GameModel(m_visualizer);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        createCoordinatesWindow(frame);
    }

    private void createCoordinatesWindow(MainApplicationFrame frame)
    {
        PositionWindow posWindow = new PositionWindow(gameModel);
        posWindow.setSize(200,200);
        frame.addWindow(posWindow);
    }

    public GameVisualizer getVisualizer()
    {
        return m_visualizer;
    }

    public void setState(InternalFrameState state)
    {
        setLocation(state.attributes.get("x"),state.attributes.get("y"));
        setSize(state.attributes.get("width"),state.attributes.get("height"));
    }
}
