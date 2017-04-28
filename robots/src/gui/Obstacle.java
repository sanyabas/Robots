package gui;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

public class Obstacle implements Serializable
{
    private int x;
    private int y;
    private int width;
    private int height;

    public Obstacle(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public static Obstacle Random(Dimension borders)
    {
        Random random = new Random();
        int width = random.nextInt((int) borders.getWidth()/5);
        int height = random.nextInt((int) borders.getHeight()/5);
        int x = random.nextInt((int) borders.getWidth()-width);
        int y = random.nextInt((int) borders.getHeight()-height);
        return new Obstacle(x, y, width, height);
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Rectangle getRectangle()
    {
        return new Rectangle(x, y, width, height);
    }
}
