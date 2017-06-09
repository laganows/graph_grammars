package pl.edu.agh.ki.gg.pt1250.productions;

import static org.junit.Assert.assertTrue;
import static pl.edu.agh.ki.gg.pt1250.model.Direction.E;
import static pl.edu.agh.ki.gg.pt1250.model.Direction.N;
import static pl.edu.agh.ki.gg.pt1250.model.Direction.NE;
import static pl.edu.agh.ki.gg.pt1250.model.Direction.NW;
import static pl.edu.agh.ki.gg.pt1250.model.Direction.S;
import static pl.edu.agh.ki.gg.pt1250.model.Direction.SE;
import static pl.edu.agh.ki.gg.pt1250.model.Direction.SW;
import static pl.edu.agh.ki.gg.pt1250.model.Direction.W;

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
        expected.getNeighbourInDirection(N).getNeighbourInDirection(N).getNeighbourInDirection(N);
        assertTrue(verticesEquals(result, expected));
        assertTrue(verticesEquals(result.getNeighbourInDirection(N), expected.getNeighbourInDirection(N)));
        assertTrue(verticesEquals(result.getNeighbourInDirection(E), expected.getNeighbourInDirection(E)));
        assertTrue(verticesEquals(result.getNeighbourInDirection(S), expected.getNeighbourInDirection(S)));
        assertTrue(verticesEquals(result.getNeighbourInDirection(W), expected.getNeighbourInDirection(W)));
        assertTrue(verticesEquals(result.getNeighbourInDirection(N).getNeighbourInDirection(N),
                expected.getNeighbourInDirection(N).getNeighbourInDirection(N)));
        assertTrue(verticesEquals(result.getNeighbourInDirection(E).getNeighbourInDirection(E),
                expected.getNeighbourInDirection(E).getNeighbourInDirection(E)));
        assertTrue(verticesEquals(result.getNeighbourInDirection(S).getNeighbourInDirection(S),
                expected.getNeighbourInDirection(S).getNeighbourInDirection(S)));
        assertTrue(verticesEquals(result.getNeighbourInDirection(W).getNeighbourInDirection(W),
                expected.getNeighbourInDirection(W).getNeighbourInDirection(W)));
        assertTrue(verticesEquals(result.getNeighbourInDirection(NW), expected.getNeighbourInDirection(NW)));
        assertTrue(verticesEquals(result.getNeighbourInDirection(NE), expected.getNeighbourInDirection(NE)));
        assertTrue(verticesEquals(result.getNeighbourInDirection(SW), expected.getNeighbourInDirection(SW)));
        assertTrue(verticesEquals(result.getNeighbourInDirection(SE), expected.getNeighbourInDirection(SE)));
    }

    @Test
    public void shouldNotApplyP4BecauseStartVertexNeighboursSizeIsNotCorrect() {
        final Vertex start = createInputGraph();
        start.removeNeighbour(N);
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        final P4 p = new P4(start, cyclicBarrier, BASIC_UNIT_LENGTH);
        final Vertex result = p.apply(start);
        assertTrue(verticesEquals(start, result));
    }

    @Test
    public void shouldNotApplyP4BecauseStartVertexNeighbourLocationIsNotCorrect() {
        final Vertex start = createInputGraph();
        start.removeNeighbour(N);
        start.addNeighbour(new Vertex(Label.NONE, CENTER + 1.0, CENTER), E);
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        final P4 p = new P4(start, cyclicBarrier, BASIC_UNIT_LENGTH);
        final Vertex result = p.apply(start);
        assertTrue(verticesEquals(start, result));
    }

    @Test
    public void shouldNotApplyP4BecauseStartVertexNeighbourLabelIsNotCorrect() {
        final Vertex start = createInputGraph();
        start.getNeighbourInDirection(N).setLabel(Label.I);
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        final P4 p = new P4(start, cyclicBarrier, BASIC_UNIT_LENGTH);
        final Vertex result = p.apply(start);
        assertTrue(verticesEquals(start, result));
    }

    private static boolean verticesEquals(final Vertex v1, final Vertex v2) {
        return Objects.equals(v1.getLabel(), v2.getLabel())
//                && Objects.equals(v1.getX(), v2.getX())
//                && Objects.equals(v1.getY(), v2.getY())
                && Objects.equals(v1.getNeighbours().keySet(), v2.getNeighbours().keySet());
    }

    private static Vertex createInputGraph() {
        final Vertex start = new Vertex(Label.F1, CENTER, CENTER);
        final Vertex vN = new Vertex(Label.NONE, calcXInDirection(N), calcYInDirection(N) + BASIC_UNIT_LENGTH);
        final Vertex vS = new Vertex(Label.NONE, calcXInDirection(S), calcYInDirection(S) - BASIC_UNIT_LENGTH);
        final Vertex vW = new Vertex(Label.NONE, calcXInDirection(W) - BASIC_UNIT_LENGTH, calcYInDirection(W));
        final Vertex vE = new Vertex(Label.NONE, calcXInDirection(E) + BASIC_UNIT_LENGTH, calcYInDirection(E));
        final Vertex INW = new Vertex(Label.I, calcXInDirection(NW), calcYInDirection(NW));
        final Vertex INE = new Vertex(Label.I, calcXInDirection(NE), calcYInDirection(NE));
        final Vertex ISW = new Vertex(Label.I, calcXInDirection(SW), calcYInDirection(SW));
        final Vertex ISE = new Vertex(Label.I, calcXInDirection(SE), calcYInDirection(SE));
        final Vertex F1W = new Vertex(Label.F1, calcXInDirection(W), calcYInDirection(W));
        final Vertex F1E = new Vertex(Label.F1, calcXInDirection(E), calcYInDirection(E));
        start.addNeighbour(vN, N);
        start.addNeighbour(vS, S);
        vN.addNeighbour(INW, SW);
        vN.addNeighbour(INE, SE);
        vS.addNeighbour(ISW, NW);
        vS.addNeighbour(ISE, NE);
        vW.addNeighbour(INW, NE);
        vW.addNeighbour(ISW, SE);
        vW.addNeighbour(F1W, E);
        vE.addNeighbour(INE, NW);
        vE.addNeighbour(ISE, SW);
        vE.addNeighbour(F1E, W);
        return start;
    }

    private static Vertex createExpectedGraph() {
        final Vertex start = new Vertex(Label.NONE, CENTER, CENTER);
        final Vertex vN = new Vertex(Label.NONE, calcXInDirection(N), calcYInDirection(N) + BASIC_UNIT_LENGTH);
        final Vertex vS = new Vertex(Label.NONE, calcXInDirection(S), calcYInDirection(S) - BASIC_UNIT_LENGTH);
        final Vertex vW = new Vertex(Label.NONE, calcXInDirection(W) - BASIC_UNIT_LENGTH, calcYInDirection(W));
        final Vertex vE = new Vertex(Label.NONE, calcXInDirection(E) + BASIC_UNIT_LENGTH, calcYInDirection(E));
        final Vertex INW = new Vertex(Label.I, calcXInDirection(NW), calcYInDirection(NW));
        final Vertex INE = new Vertex(Label.I, calcXInDirection(NE), calcYInDirection(NE));
        final Vertex ISW = new Vertex(Label.I, calcXInDirection(SW), calcYInDirection(SW));
        final Vertex ISE = new Vertex(Label.I, calcXInDirection(SE), calcYInDirection(SE));
        final Vertex F1W = new Vertex(Label.F1, calcXInDirection(W), calcYInDirection(W));
        final Vertex F1E = new Vertex(Label.F1, calcXInDirection(E), calcYInDirection(E));
        final Vertex F1N = new Vertex(Label.F1, calcXInDirection(N), calcYInDirection(N));
        final Vertex F1S = new Vertex(Label.F1, calcXInDirection(S), calcYInDirection(S));
        start.addNeighbour(F1N, N);
        start.addNeighbour(F1S, S);
        start.addNeighbour(F1W, W);
        start.addNeighbour(F1E, E);
        start.addNeighbour(INW, NW);
        start.addNeighbour(INE, NE);
        start.addNeighbour(ISW, SW);
        start.addNeighbour(ISE, SE);
        vN.addNeighbour(INW, SW);
        vN.addNeighbour(INE, SE);
        vN.addNeighbour(F1N, S);
        vS.addNeighbour(ISW, NW);
        vS.addNeighbour(ISE, NE);
        vS.addNeighbour(F1S, N);
        vW.addNeighbour(INW, NE);
        vW.addNeighbour(ISW, SE);
        vW.addNeighbour(F1W, E);
        vE.addNeighbour(INE, NW);
        vE.addNeighbour(ISE, SW);
        vE.addNeighbour(F1E, W);
        return start;
    }

    private static double calcXInDirection(final Direction direction) {
        return direction.getXMultiplier() * BASIC_UNIT_LENGTH + CENTER;
    }

    private static double calcYInDirection(final Direction direction) {
        return direction.getYMultiplier() * BASIC_UNIT_LENGTH + CENTER;
    }
}
