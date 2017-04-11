package gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{
    private final GameVisualizer m_visualizer;
    public GameWindow() 
    {
        super("Игровое поле", true, true, true, true);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
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
