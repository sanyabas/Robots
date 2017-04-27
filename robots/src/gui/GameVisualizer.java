package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class GameVisualizer extends JPanel
{
    private GameModel gameModel;
    private double robotX;
    private double robotY;

    private static Timer initTimer()
    {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    public GameVisualizer(GameModel model)
    {
        gameModel = model;
        Timer m_timer = initTimer();
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onRedrawEvent();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                gameModel.onModelUpdateEvent();
            }
        }, 0, 10);

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    model.setBounds(getSize());
                    setTargetPosition(e.getPoint());
                }
                else if (SwingUtilities.isRightMouseButton(e))
                    gameModel.AddObstacle(getSize());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void setTargetPosition(Point p)
    {
        gameModel.setTargetPosition(p);
    }

    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
//        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new ));
    }


    private static int round(double value)
    {
        return (int) (value + 0.5);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, round(gameModel.getRobotPositionX()), round(gameModel.getRobotPositionY()), gameModel.getRobotDirection());
        drawTarget(g2d, gameModel.getTargetPositionX(), gameModel.getTargetPositionY());
        drawObstacles(g2d);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void fillRect(Graphics g, Rectangle rectangle)
    {
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction)
    {
        int robotCenterX = round(gameModel.getRobotPositionX());
        int robotCenterY = round(gameModel.getRobotPositionY());
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }

    private void drawTarget(Graphics2D g, int x, int y)
    {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    private void drawObstacles(Graphics2D g)
    {
        g.setColor(Color.RED);
        for (Obstacle obstacle : gameModel.getObstacles())
        {
            fillRect(g, obstacle.getRectangle());
        }
    }
}
