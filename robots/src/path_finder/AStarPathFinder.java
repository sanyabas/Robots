package path_finder;

import gui.GameModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class AStarPathFinder extends AbstractPathFinder implements Serializable
{
    public AStarPathFinder(Map map)
    {
        this.map = map;
    }

    @Override
    public ArrayList<MapCell> findPath(MapCell start, MapCell finish)
    {
        for (MapCell[] line : map.getMapArray())
        {
            for (MapCell cell : line)
            {
                cell.setParent(null);
                cell.setDistance(0);
            }
        }
        HashSet<MapCell> open = new HashSet<>();
        HashSet<MapCell> closed = new HashSet<>();
        open.add(start);
        while (open.size() != 0)
        {
            ArrayList<MapCell> list=new ArrayList<>(open);
            Collections.sort(list);
            MapCell current = list.get(0);
            if (current.equals(finish))
                return getPath(current);
            Iterable<MapCell> neighbours = getNeighbours(current,false);
            for (MapCell neighbour : neighbours)
            {
                if (closed.contains(neighbour))
                    continue;
                if (!open.contains(neighbour))
                {
                    neighbour.setParent(current);
                    neighbour.setDistance(neighbour.getFullDistance(finish));
                    open.add(neighbour);
                } else
                {
                    int tentative = current.getDistance() + (int) GameModel.distance(neighbour.getCenterX(), neighbour.getCenterY(), current.getCenterX(), current.getCenterY());
                    if (tentative < neighbour.getDistance())
                    {
                        neighbour.setDistance(tentative);
                        neighbour.setParent(current);
                    }
                }
            }
            open.remove(current);
            closed.add(current);
        }
        return null;
    }



}
