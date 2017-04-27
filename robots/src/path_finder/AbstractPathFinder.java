package path_finder;

import java.util.ArrayList;
import java.util.Collections;

public abstract class AbstractPathFinder
{
    protected Map map;

    protected Iterable<MapCell> getNeighbours(MapCell cell)
    {
        ArrayList<MapCell> result = new ArrayList<>();
        int cellRow = cell.getCenterY() / MapCell.Size;
        int cellColumn = cell.getCenterX() / MapCell.Size;
        for (int row = -1; row <= 1; row++)
        {
            for (int column = -1; column <= 1; column++)
            {
                if (row == 0 && column == 0)
                    continue;
                int resultRow = cellRow + row;
                int resultCol = cellColumn + column;
                MapCell neighbour = map.getCell(resultRow, resultCol);
                if (neighbour.getObstacle() || resultRow >= map.Height || resultCol >= map.Width)
                    continue;
                result.add(neighbour);
            }
        }
        return result;
    }

    protected ArrayList<MapCell> getPath(MapCell finish)
    {
        ArrayList<MapCell> path = new ArrayList<>();
        path.add(finish);
        while (finish.getParent() != null)
        {
            path.add(finish.getParent());
            finish = finish.getParent();
        }
        Collections.reverse(path);
        return path;
    }

    public ArrayList<MapCell> findPath(double startX,double startY,double finishX,double finishY)
    {
        MapCell start=map.getCellFromCoords(startX,startY);
        MapCell finish=map.getCellFromCoords(finishX,finishY);
        return findPath(start,finish);
    }

    public abstract ArrayList<MapCell> findPath(MapCell start, MapCell finish);
}