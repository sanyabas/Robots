package gui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface SerializableFrame
{
    void serialize(ObjectOutputStream stream) throws IOException;
    FrameState restore(ObjectInputStream stream) throws IOException, ClassNotFoundException;
}
