package gui;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface SerializableFrame
{
    void serialize(ObjectOutputStream stream) throws IOException;
    void restore(ObjectInputStream stream);
}
