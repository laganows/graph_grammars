package pl.edu.agh.ki.gg.pt1250.productions;

import static org.junit.Assert.assertTrue;

import pl.edu.agh.ki.gg.pt1250.model.Direction;
import pl.edu.agh.ki.gg.pt1250.model.Label;
import pl.edu.agh.ki.gg.pt1250.model.Vertex;

import java.util.Objects;
import java.util.concurrent.CyclicBarrier;

import org.junit.Test;

public class P4Test {
    private static final double BASIC_UNIT_LENGTH = 4.0;
    private static final double CENTER = 8.0;

    @Test
    public void shouldApplyP4() throws Exception {
        final Vertex start = createInputGraph();
        final Vertex expected = createExpectedGraph();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        final P4 p4 = new P4(start, cyclicBarrier, BASIC_UNIT_LENGTH);
        final Vertex result = p4.apply(start);
        assertTrue(verticesEquals(result, expected));
    }

    private static boolean verticesEquals(final Vertex v1, final Vertex v2) {
        return Objects.equals(v1.getLabel(), v2.getLabel())
                && Objects.equals(v1.getX(), v2.getY())
                && Objects.equals(v1.getY(), v2.getY());
    }

    private static Vertex createInputGraph() {
        final Vertex start = new Vertex(Label.F1, CENTER, CENTER);
        final Vertex vN = new Vertex(Label.NONE, calculateX(Direction.N), calculateY(Direction.N) + BASIC_UNIT_LENGTH);
        final Vertex vS = new Vertex(Label.NONE, calculateX(Direction.S), calculateY(Direction.S) - BASIC_UNIT_LENGTH);
        final Vertex vW = new Vertex(Label.NONE, calculateX(Direction.W) - BASIC_UNIT_LENGTH, calculateY(Direction.W));
        final Vertex vE = new Vertex(Label.NONE, calculateX(Direction.E) + BASIC_UNIT_LENGTH, calculateY(Direction.E));
        final Vertex INW = new Vertex(Label.I, calculateX(Direction.NW), calculateY(Direction.NW));
        final Vertex INE = new Vertex(Label.I, calculateX(Direction.NE), calculateY(Direction.NE));
        final Vertex ISW = new Vertex(Label.I, calculateX(Direction.SW), calculateY(Direction.SW));
        final Vertex ISE = new Vertex(Label.I, calculateX(Direction.SE), calculateY(Direction.SE));
        final Vertex F1W = new Vertex(Label.F1, calculateX(Direction.W), calculateY(Direction.W));
        final Vertex F1E = new Vertex(Label.F1, calculateX(Direction.E), calculateY(Direction.E));
        start.addNeighbour(vN, Direction.N);
        start.addNeighbour(vS, Direction.S);
        vN.addNeighbour(INW, Direction.SW);
        vN.addNeighbour(INE, Direction.SE);
        vS.addNeighbour(ISW, Direction.NW);
        vS.addNeighbour(ISE, Direction.NE);
        vW.addNeighbour(INW, Direction.NE);
        vW.addNeighbour(ISW, Direction.SE);
        vW.addNeighbour(F1W, Direction.E);
        vE.addNeighbour(INE, Direction.NW);
        vE.addNeighbour(ISE, Direction.SW);
        vE.addNeighbour(F1E, Direction.W);
        return start;
    }

    private static Vertex createExpectedGraph() {
        final Vertex start = new Vertex(Label.NONE, CENTER, CENTER);
        final Vertex vN = new Vertex(Label.NONE, calculateX(Direction.N), calculateY(Direction.N) + BASIC_UNIT_LENGTH);
        final Vertex vS = new Vertex(Label.NONE, calculateX(Direction.S), calculateY(Direction.S) - BASIC_UNIT_LENGTH);
        final Vertex vW = new Vertex(Label.NONE, calculateX(Direction.W) - BASIC_UNIT_LENGTH, calculateY(Direction.W));
        final Vertex vE = new Vertex(Label.NONE, calculateX(Direction.E) + BASIC_UNIT_LENGTH, calculateY(Direction.E));
        final Vertex INW = new Vertex(Label.I, calculateX(Direction.NW), calculateY(Direction.NW));
        final Vertex INE = new Vertex(Label.I, calculateX(Direction.NE), calculateY(Direction.NE));
        final Vertex ISW = new Vertex(Label.I, calculateX(Direction.SW), calculateY(Direction.SW));
        final Vertex ISE = new Vertex(Label.I, calculateX(Direction.SE), calculateY(Direction.SE));
        final Vertex F1W = new Vertex(Label.F1, calculateX(Direction.W), calculateY(Direction.W));
        final Vertex F1E = new Vertex(Label.F1, calculateX(Direction.E), calculateY(Direction.E));
        final Vertex F1N = new Vertex(Label.F1, calculateX(Direction.N), calculateY(Direction.N));
        final Vertex F1S = new Vertex(Label.F1, calculateX(Direction.S), calculateY(Direction.S));
        start.addNeighbour(F1N, Direction.N);
        start.addNeighbour(F1S, Direction.S);
        start.addNeighbour(F1W, Direction.W);
        start.addNeighbour(F1E, Direction.E);
        start.addNeighbour(INW, Direction.NW);
        start.addNeighbour(INE, Direction.NE);
        start.addNeighbour(ISW, Direction.SW);
        start.addNeighbour(ISE, Direction.SE);
        vN.addNeighbour(INW, Direction.SW);
        vN.addNeighbour(INE, Direction.SE);
        vN.addNeighbour(F1N, Direction.S);
        vS.addNeighbour(ISW, Direction.NW);
        vS.addNeighbour(ISE, Direction.NE);
        vS.addNeighbour(F1S, Direction.N);
        vW.addNeighbour(INW, Direction.NE);
        vW.addNeighbour(ISW, Direction.SE);
        vW.addNeighbour(F1W, Direction.E);
        vE.addNeighbour(INE, Direction.NW);
        vE.addNeighbour(ISE, Direction.SW);
        vE.addNeighbour(F1E, Direction.W);
        return start;
    }

    private void printVertex(final Vertex v) {
        System.out.println(v.getLabel().getTextLabel() + " : " + v.getX() + " " + v.getY());
    }

    private static double calculateX(final Direction direction) {
        return direction.getXMultiplier() * BASIC_UNIT_LENGTH + CENTER;
    }

    private static double calculateY(final Direction direction) {
        return direction.getYMultiplier() * BASIC_UNIT_LENGTH + CENTER;
    }
}
