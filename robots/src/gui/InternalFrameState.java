package gui;

import javax.swing.*;
import java.io.Serializable;
import java.util.HashMap;

public class InternalFrameState implements Serializable
{
    public HashMap<String,Object> attributes;
    public String name;

    public InternalFrameState(JInternalFrame frame)
    {
        super();
        attributes=new HashMap<>();
        name=frame.getClass().getName();
        if (name.contains("GameWindow"))
            attributes.put("model",saveModel(frame));
        attributes.put("x",frame.getX());
        attributes.put("y",frame.getY());
        attributes.put("width",frame.getWidth());
        attributes.put("height",frame.getHeight());
    }

    private Object saveModel(JInternalFrame frame)
    {
        GameWindow window=(GameWindow) frame;
        return window.getVisualizer().getGameModel();
    }
}
