package gui;

import javax.swing.*;
import java.io.Serializable;
import java.util.HashMap;

public class InternalFrameState implements Serializable
{
    public HashMap<String,Integer> attributes;
    public String name;

    public InternalFrameState(JInternalFrame frame)
    {
        super();
        attributes=new HashMap<>();
        name=frame.getClass().getName();
        attributes.put("x",frame.getX());
        attributes.put("y",frame.getY());
        attributes.put("width",frame.getWidth());
        attributes.put("height",frame.getHeight());
    }
}
