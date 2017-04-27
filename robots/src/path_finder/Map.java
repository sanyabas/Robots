package path_finder;

import gui.Obstacle;

import java.awt.*;
import java.util.ArrayList;

public class Map
{
    private MapCell[][] mapArray;
    public final int Width;
    public final int Height;

    public Map(Dimension size, ArrayList<Obstacle> obstacles)
    {
        Width = size.width / MapCell.Size + 1;
        Height = size.height / MapCell.Size + 1;
        mapArray = new MapCell[Height][Width];
        int x = MapCell.Size / 2;
        int y = MapCell.Size / 2;
        for (int row = 0; row < Height; row++)
        {
            for (int col = 0; col < Width; col++)
            {
                mapArray[row][col] = new MapCell(x, y);
                x += MapCell.Size;
            }
            y += MapCell.Size;
            x = MapCell.Size / 2;
        }
        preprocess(obstacles);
    }

    private void preprocess(ArrayList<Obstacle> obstacles)
    {
        for (Obstacle obstacle : obstacles)
        {
            int upperLeftRow = getRow(obstacle.getY());
            int upperLeftCol = getColumn(obstacle.getX());
            int lowerRightRow = getRow(obstacle.getY() + obstacle.getHeight());
            int lowerRightCol = getColumn(obstacle.getX() + obstacle.getWidth());
            for (int row = upperLeftRow; row <= lowerRightRow; row++)
                for (int col = upperLeftCol; col <= lowerRightCol; col++)
                    mapArray[row][col].setObstacle(true);
        }
    }

    public MapCell[][] getMapArray()
    {
        return mapArray;
    }

    public MapCell getCell(int row, int column)
    {
        return mapArray[row][column];
    }

    private int getRow(double y)
    {
        int row = (int) Math.floor(y / MapCell.Size);
        return row;
    }

    private int getColumn(double x)
    {
        int column = (int) Math.floor(x / MapCell.Size);
        return column;
    }

    public MapCell getCellFromCoords(double x, double y)
    {
        int row = getRow(y);
        int column = getColumn(x);
        return mapArray[row][column];
    }
}
