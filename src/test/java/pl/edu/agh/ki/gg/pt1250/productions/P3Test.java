package pl.edu.agh.ki.gg.pt1250.productions;

import org.junit.Test;
import pl.edu.agh.ki.gg.pt1250.model.Direction;
import pl.edu.agh.ki.gg.pt1250.model.Label;
import pl.edu.agh.ki.gg.pt1250.model.Vertex;
import pl.edu.agh.ki.gg.pt1250.visualization.Visualizer;

import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.CyclicBarrier;

import static org.junit.Assert.*;

public class P3Test {

    private static final double BASIC_UNIT_LENGTH = 4.0;
    private static final double CENTER = 0.0;

    @Test
    public void shouldGenerateCorrectGraph() throws Exception{

        final Vertex start = createInputGraph();

        final Vertex result = createFinalGraph();

        CyclicBarrier barrier = new CyclicBarrier(2);

        barrier = new CyclicBarrier(2);
        P3 p3north = new P3(start, Direction.N, barrier,  BASIC_UNIT_LENGTH/2);
        p3north.start();
        barrier.await();

        assertTrue(verticesEquals(start, result));
        //top
        assertTrue(verticesEquals(start.getNeighbourInDirection(Direction.N), result.getNeighbourInDirection(Direction.N)));
        assertTrue(verticesEquals(start.getNeighbourInDirection(Direction.N).getNeighbourInDirection(Direction.N),
                result.getNeighbourInDirection(Direction.N).getNeighbourInDirection(Direction.N)));
        assertTrue(verticesEquals(start.getNeighbourInDirection(Direction.N).getNeighbourInDirection(Direction.N)
                        .getNeighbourInDirection(Direction.W),
                result.getNeighbourInDirection(Direction.N).getNeighbourInDirection(Direction.N)
                        .getNeighbourInDirection(Direction.W)));
        assertTrue(verticesEquals(start.getNeighbourInDirection(Direction.N).getNeighbourInDirection(Direction.N)
                        .getNeighbourInDirection(Direction.E),
                result.getNeighbourInDirection(Direction.N).getNeighbourInDirection(Direction.N)
                        .getNeighbourInDirection(Direction.E)));
        //left
        assertTrue(verticesEquals(start.getNeighbourInDirection(Direction.NW), result.getNeighbourInDirection(Direction.NW)));
        assertTrue(verticesEquals(start.getNeighbourInDirection(Direction.NW).getNeighbourInDirection(Direction.NW),
                result.getNeighbourInDirection(Direction.NW).getNeighbourInDirection(Direction.NW)));
        assertTrue(verticesEquals(start.getNeighbourInDirection(Direction.NW).getNeighbourInDirection(Direction.SW),
                result.getNeighbourInDirection(Direction.NW).getNeighbourInDirection(Direction.SW)));
        assertTrue(verticesEquals(start.getNeighbourInDirection(Direction.NW).getNeighbourInDirection(Direction.NE),
                result.getNeighbourInDirection(Direction.NW).getNeighbourInDirection(Direction.NE)));
        assertTrue(verticesEquals(start.getNeighbourInDirection(Direction.NW).getNeighbourInDirection(Direction.NW)
                        .getNeighbourInDirection(Direction.E),
                result.getNeighbourInDirection(Direction.NW).getNeighbourInDirection(Direction.NW)
                        .getNeighbourInDirection(Direction.E)));

        //right
        assertTrue(verticesEquals(start.getNeighbourInDirection(Direction.NE), result.getNeighbourInDirection(Direction.NE)));
        assertTrue(verticesEquals(start.getNeighbourInDirection(Direction.NE).getNeighbourInDirection(Direction.NE)
                , result.getNeighbourInDirection(Direction.NE).getNeighbourInDirection(Direction.NE)));
        assertTrue(verticesEquals(start.getNeighbourInDirection(Direction.NE).getNeighbourInDirection(Direction.NW)
                , result.getNeighbourInDirection(Direction.NE).getNeighbourInDirection(Direction.NW)));
        assertTrue(verticesEquals(start.getNeighbourInDirection(Direction.NE).getNeighbourInDirection(Direction.SE)
                , result.getNeighbourInDirection(Direction.NE).getNeighbourInDirection(Direction.SE)));
        assertTrue(verticesEquals(start.getNeighbourInDirection(Direction.NE).getNeighbourInDirection(Direction.NE)
                .getNeighbourInDirection(Direction.W)
                , result.getNeighbourInDirection(Direction.NE).getNeighbourInDirection(Direction.NE)
                        .getNeighbourInDirection(Direction.W)));
    }

    @Test
    public void shouldFallWithNeighbours() throws Exception{
        Vertex start = new Vertex(Label.NONE, CENTER, CENTER);
        Vertex right = new Vertex(Label.I, calculateCoordinationX2(Direction.NE, Direction.NE), calculateCoordinationY2(Direction.NE, Direction.NE));
        Vertex left = new Vertex(Label.I, calculateCoordinationX2(Direction.NW,Direction.NW), calculateCoordinationY2(Direction.NW,Direction.NW));

        start.addNeighbour(left, Direction.NW);
        start.addNeighbour(right, Direction.NE);

        CyclicBarrier barrier = new CyclicBarrier(2);
        barrier = new CyclicBarrier(2);
        P3 p3north = new P3(start, Direction.N, barrier,  BASIC_UNIT_LENGTH/2);
        Vertex result = p3north.apply(start);

        assertTrue(verticesEquals(start, result));

    }

    private static Vertex createInputGraph() {

        Vertex start = new Vertex(Label.NONE, CENTER, CENTER);
        Vertex right = new Vertex(Label.I, calculateCoordinationX2(Direction.NE, Direction.NE), calculateCoordinationY2(Direction.NE, Direction.NE));
        Vertex left = new Vertex(Label.I, calculateCoordinationX2(Direction.NW,Direction.NW), calculateCoordinationY2(Direction.NW,Direction.NW));

        Vertex top = new Vertex(Label.F1, calculateCoordinationX2(Direction.N,Direction.N), calculateCoordinationY2(Direction.N, Direction.NE));
        Vertex leftTop = new Vertex(Label.I, calculateCoordinationX2(Direction.NW,Direction.NW)*2, calculateCoordinationY2(Direction.NW, Direction.NW)*2);
        Vertex rightTop = new Vertex(Label.I, calculateCoordinationX2(Direction.NE, Direction.NE)*2, calculateCoordinationY2(Direction.NE, Direction.NE)*2);

        Vertex b = new Vertex(Label.F1, calculateCoordinationX2(Direction.N, Direction.N), calculateCoordinationY2(Direction.N, Direction.N));

        //left side
        start.addNeighbour(left, Direction.NW);
        left.addNeighbour(leftTop, Direction.NW);
        leftTop.addNeighbour(b, Direction.E);

        start.addNeighbour(top, Direction.N);

        //right side
        start.addNeighbour(right, Direction.NE);
        right.addNeighbour(rightTop, Direction.NE);
        rightTop.addNeighbour(b, Direction.W);

        return start;

    }

    private static Vertex createFinalGraph() {

        Vertex start = new Vertex(Label.NONE, CENTER, CENTER);
        Vertex right = new Vertex(Label.I, calculateCoordinationX2(Direction.NE, Direction.NE), calculateCoordinationY2(Direction.NE, Direction.NE));
        Vertex left = new Vertex(Label.I, calculateCoordinationX2(Direction.NW,Direction.NW), calculateCoordinationY2(Direction.NW,Direction.NW));

        Vertex top = new Vertex(Label.F1, calculateCoordinationX2(Direction.N,Direction.N), calculateCoordinationY2(Direction.N, Direction.NE));

        Vertex leftTop = new Vertex(Label.I, calculateCoordinationX2(Direction.NW,Direction.NW)*2, calculateCoordinationY2(Direction.NW, Direction.NW)*2);
        Vertex rightTop = new Vertex(Label.I, calculateCoordinationX2(Direction.NE, Direction.NE)*2, calculateCoordinationY2(Direction.NE, Direction.NE)*2);

        Vertex bLeft = new Vertex(Label.B, calculateCoordinationX2(Direction.NW,Direction.NW), calculateCoordinationY2(Direction.N, Direction.N));
        Vertex bRight = new Vertex(Label.B, calculateCoordinationX2(Direction.NE,Direction.NE), calculateCoordinationY2(Direction.N, Direction.N));

        Vertex center = new Vertex(Label.NONE, calculateCoordinationX2(Direction.N, Direction.N), calculateCoordinationY2(Direction.N, Direction.N));

        Vertex leftDown = new Vertex(Label.NONE, calculateCoordinationX2(Direction.W, Direction.W), calculateCoordinationY2(Direction.W, Direction.W));
        Vertex rightDown = new Vertex(Label.NONE, calculateCoordinationX2(Direction.E, Direction.E), calculateCoordinationY2(Direction.E, Direction.E));

        //left side
        start.addNeighbour(left, Direction.NW);
        left.addNeighbour(leftTop, Direction.NW);
        leftTop.addNeighbour(bLeft, Direction.E);
        bLeft.addNeighbour(center, Direction.E);
        left.addNeighbour(leftDown, Direction.SW);


        //right side
        start.addNeighbour(right, Direction.NE);
        right.addNeighbour(rightTop, Direction.NE);
        rightTop.addNeighbour(bRight, Direction.W);
        bRight.addNeighbour(center, Direction.W);
        right.addNeighbour(rightDown, Direction.SE);

        //center
        start.addNeighbour(top, Direction.N);
        center.addNeighbour(left, Direction.SW);
        center.addNeighbour(right, Direction.SE);
        center.addNeighbour(top, Direction.S);

        return start;

    }

    private static double calculateCoordinationX2(Direction direction, Direction direction2) {
        double tmp = direction.getXMultiplier() * BASIC_UNIT_LENGTH/2 + CENTER;
        if (direction2 == Direction.E) {
            tmp += 2;
        }
        else if (direction2 == Direction.W) {
            tmp -=2;
        }
        return tmp;
    }

    private static double calculateCoordinationY2(Direction direction, Direction direction2) {
        double tmp = direction.getYMultiplier() * BASIC_UNIT_LENGTH/2 + CENTER;
        if (direction2 == Direction.N) {
            tmp += 2;
        }
        else if (direction2 == Direction.S) {
            tmp -=2;
        }
        return tmp;
    }

    private void displayGraph(Vertex startVertex) {
        Visualizer visualizer = new Visualizer();
        visualizer.visualizeGraph(startVertex);
        visualizer.displayGraph();
    }

    private static boolean verticesEquals(final Vertex v1, final Vertex v2) {
        return Objects.equals(v1.getLabel(), v2.getLabel())
                && Objects.equals(v1.getX(), v2.getX())
                && Objects.equals(v1.getY(), v2.getY());
    }


}
