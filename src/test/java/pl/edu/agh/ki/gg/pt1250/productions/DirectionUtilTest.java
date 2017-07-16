package pl.edu.agh.ki.gg.pt1250.productions;

import org.junit.BeforeClass;
import org.junit.Test;
import pl.edu.agh.ki.gg.pt1250.model.Direction;
import pl.edu.agh.ki.gg.pt1250.model.Label;
import pl.edu.agh.ki.gg.pt1250.model.Vertex;
import pl.edu.agh.ki.gg.pt1250.utils.DirectionUtil;

import java.util.EnumSet;

import static org.junit.Assert.assertEquals;
public class DirectionUtilTest {

    private static Vertex middleVertex;

    @BeforeClass
    public static void init() {
        middleVertex = new Vertex(Label.S, 0, 0);
        for (Direction direction : EnumSet.of(Direction.NW, Direction.NE, Direction.SW, Direction.SE, Direction.N, Direction.E, Direction.W, Direction.S)) {
            middleVertex.addNeighbour(new Vertex(Label.S, 0, 0), direction);
        }
    }

    @Test
    public void gets1() {
        Vertex vertex = DirectionUtil.getVertexInGivenCountDirection(middleVertex, Direction.N, 1);
        assertEquals(vertex, middleVertex.getNeighbourInDirection(Direction.NE));
    }

    @Test
    public void gets2() {
        Vertex vertex = DirectionUtil.getVertexInGivenCountDirection(middleVertex, Direction.N, 3);
        assertEquals(vertex, middleVertex.getNeighbourInDirection(Direction.SE));
    }

    @Test
    public void gets3() {
        Vertex vertex = DirectionUtil.getVertexInGivenCountDirection(middleVertex, Direction.N, -2);
        assertEquals(vertex, middleVertex.getNeighbourInDirection(Direction.W));
    }

    @Test
    public void gets4() {
        Vertex vertex = DirectionUtil.getVertexInGivenCountDirection(middleVertex, Direction.N, -4);
        assertEquals(vertex, middleVertex.getNeighbourInDirection(Direction.S));
    }

}
