package pl.edu.agh.ki.gg.pt1250.utils;

import pl.edu.agh.ki.gg.pt1250.model.Direction;
import pl.edu.agh.ki.gg.pt1250.model.Vertex;

public class DirectionUtil {

    public static Direction getDirectionInGivenCountDirection(Direction startDirection, int sideCounter) {
        if (sideCounter > 0) {
            while (sideCounter > 0) {
                startDirection = startDirection.getClockwiseNextDirection();
                sideCounter--;
            }
        } else {
            while (sideCounter < 0) {
                startDirection = startDirection.getCounterClockwiseNextDirection();
                sideCounter++;
            }
        }
        return startDirection;
    }

    public static Vertex getVertexInGivenCountDirection(Vertex v, Direction startDirection, int sideCounter) {
        return v.getNeighbourInDirection(getDirectionInGivenCountDirection(startDirection, sideCounter));
    }
}
