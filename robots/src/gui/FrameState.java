package gui;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class FrameState implements Serializable
{
    private ArrayList<InternalFrameState> frames;

    public FrameState(MainApplicationFrame frame)
    {
        frames=new ArrayList<>();
        for (JInternalFrame internalFrame:frame.getAllFrames())
        {
            frames.add(new InternalFrameState(internalFrame));
        }
    }

    public ArrayList<InternalFrameState> getFrames()
    {
        return frames;
    }
}


