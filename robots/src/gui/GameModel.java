package gui;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameModel extends java.util.Observable
{
    private final Timer m_timer = initTimer();
    private int ticksCount;

    private static Timer initTimer()
    {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    public GameModel()
    {
//        addObserver(listener);
        ticksCount = 0;
//        m_timer.schedule(new TimerTask()
//        {
//            @Override
//            public void run()
//            {
//                onModelUpdateEvent();
//            }
//        }, 0, 10);
    }

    public double getRobotPositionX()
    {
        return m_robotPositionX;
    }

    public double getRobotPositionY()
    {
        return m_robotPositionY;
    }

    public double getRobotDirection()
    {
        return m_robotDirection;
    }

    public int getTargetPositionX()
    {
        return m_targetPositionX;
    }

    public int getTargetPositionY()
    {
        return m_targetPositionY;
    }

    private volatile double m_robotPositionX = 100;
    private volatile double m_robotPositionY = 100;
    private volatile double m_robotDirection = 0;
    private volatile double targetAngle;

    private volatile int m_targetPositionX = 150;
    private volatile int m_targetPositionY = 100;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;

    private static double distance(double x1, double y1, double x2, double y2)
    {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    public void setTargetPosition(Point p)
    {
        m_targetPositionX = p.x;
        m_targetPositionY = p.y;
    }

    public double getTargetAngle()
    {
        return targetAngle;
    }

    protected void onModelUpdateEvent()
    {
        double distance = distance(m_targetPositionX, m_targetPositionY,
                m_robotPositionX, m_robotPositionY);
        double rotationRadius = maxVelocity / maxAngularVelocity;
        if (distance < 0.5)
        {
            return;
        }
        targetAngle = angleTo(m_robotPositionX, m_robotPositionY, m_targetPositionX, m_targetPositionY);
        double resAngle = targetAngle - m_robotDirection;
//        if (distance < rotationRadius*0.8  && Math.abs(resAngle)>1)
//            moveRobot(maxVelocity, 0, 10);
        if (Math.abs(resAngle)>0.05)
        {
            double velocity = maxVelocity;
            double angularVelocity = 0;
            if (Math.abs(resAngle) > Math.PI)
                resAngle = -(2 * Math.PI - Math.abs(resAngle)) * Math.signum(resAngle);
            if (Math.abs(resAngle) > 0.05 && Math.abs(resAngle) < 1.95 * Math.PI)
            {
                angularVelocity = Math.signum(resAngle) * maxAngularVelocity;
//            angularVelocity = (resAngle > 0) ? maxAngularVelocity : -maxAngularVelocity;
            }
            moveRobot(0, angularVelocity, 10);
        }
        else
        {
            moveRobot(maxVelocity, 0, 10);
        }
        ticksCount++;
        if (ticksCount % 5 == 0)
        {
            setChanged();
            notifyObservers();
        }
    }

    private static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    private void moveRobot(double velocity, double angularVelocity, double duration)
    {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = m_robotPositionX + velocity / angularVelocity *
                (Math.sin(m_robotDirection + angularVelocity * duration) -
                        Math.sin(m_robotDirection));
        if (!Double.isFinite(newX))
        {
            newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);
        }
        double newY = m_robotPositionY - velocity / angularVelocity *
                (Math.cos(m_robotDirection + angularVelocity * duration) -
                        Math.cos(m_robotDirection));
        if (!Double.isFinite(newY))
        {
            newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);
        }
        m_robotPositionX = newX;
        m_robotPositionY = newY;
        double newDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);
        m_robotDirection = newDirection;
    }

    private static double asNormalizedRadians(double angle)
    {
        while (angle < 0)
        {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI)
        {
            angle -= 2 * Math.PI;
        }
//        if (Math.abs(angle)>Math.PI)
//            angle=-(2*Math.PI-Math.abs(angle))*Math.signum(angle);
        return angle;
    }
}
