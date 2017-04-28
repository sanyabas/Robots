package path_finder;

import gui.GameModel;

import java.io.Serializable;

public class MapCell implements Comparable<MapCell>,Serializable
{
    private int row;
    private int column;
    private int centerX;
    private int centerY;

    private Boolean isObstacle;

    private MapCell parent;
    public static final int Size = 5;
    private int wayDistance;
    private int distance;
    public MapCell(int centerX, int centerY, Boolean isObstacle, MapCell parent)
    {
        this.centerX = centerX;
        this.centerY = centerY;
        this.isObstacle = isObstacle;
        this.parent = parent;
    }

    public MapCell(int centerX, int centerY, Boolean isObstacle)
    {
        this.centerX = centerX;
        this.centerY = centerY;
        this.isObstacle = isObstacle;
        parent = null;
    }

    public MapCell(int centerX, int centerY)
    {
        this.centerX = centerX;
        this.centerY = centerY;
        isObstacle = false;
        parent = null;
    }

    public int getFullDistance(MapCell destination)
    {
        return getPathLength(parent) + getHeuristicLength(destination);
    }

    private int getPathLength(MapCell parent)
    {
        return parent.getWayDistance() + (int) GameModel.distance(centerX, centerY, parent.centerX, parent.centerY);
    }

    private int getHeuristicLength(MapCell destination)
    {
        return Math.abs(centerX - destination.centerX) + Math.abs(centerY - destination.centerY);
    }

    @Override
    public int hashCode()
    {
        return centerX * 13 + centerY;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof MapCell))
            return false;
        MapCell other = (MapCell) obj;
        return Math.abs(centerX - other.centerX) < 0.5 && Math.abs(centerY - other.centerY) < 0.5;
    }

    @Override
    public String toString()
    {
        return String.format("%d,%d: %d %s", centerX, centerY, distance, isObstacle);
    }

    public void setParent(MapCell parent)
    {
        this.parent = parent;
    }

    public void setWayDistance(int wayDistance)
    {
        this.wayDistance = wayDistance;
    }

    public void setDistance(int distance)
    {
        this.distance = distance;
    }

    public int getCenterX()
    {
        return centerX;
    }

    public int getCenterY()
    {
        return centerY;
    }

    public Boolean getObstacle()
    {
        return isObstacle;
    }

    public MapCell getParent()
    {
        return parent;
    }

    public int getWayDistance()
    {
        return wayDistance;
    }

    public int getDistance()
    {
        return distance;
    }

    public void setObstacle(Boolean obstacle)
    {
        isObstacle = obstacle;
    }

    @Override
    public int compareTo(MapCell o)
    {
        if (distance < o.distance)
            return -1;
        else if (distance > o.distance)
            return 1;
        else
            return 0;
    }
}
