package pl.edu.agh.ki.gg.pt1250.productions;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CyclicBarrier;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import pl.edu.agh.ki.gg.pt1250.model.Direction;
import pl.edu.agh.ki.gg.pt1250.model.Label;
import pl.edu.agh.ki.gg.pt1250.model.Vertex;

public class P1Test {
    private static final double BASIC_UNIT_LENGTH = 4.0;

    @Test
    public void apply_happyPath() {
        // given
        Vertex expected = prepareExpectedVertex();
        EnumSet<Direction> crosswiseDirections = EnumSet.of(Direction.NW, Direction.NE, Direction.SW, Direction.SE);
        Vertex s = new Vertex(Label.S, BASIC_UNIT_LENGTH, BASIC_UNIT_LENGTH);
        CyclicBarrier barrier = new CyclicBarrier(2);
        P1 p1 = new P1(s, barrier, BASIC_UNIT_LENGTH);

        // when
        p1.apply(s);
        ConcurrentMap<Vertex, Direction> neighbours = s.getNeighbours();

        // then

        assertThat(s.getNeighbours().keySet(), matchVertex(expected.getNeighbours().keySet()));
        assertThat(s.getLabel(), is(Label.I));
        assertThat(neighbours.size(), is(4));
        neighbours.forEach(((vertex, direction) -> assertThat(vertex.getNeighbours().size(), is(3))));

        for (Direction crosswiseDirection : crosswiseDirections) {
            Vertex neighbourInDirection = s.getNeighbourInDirection(crosswiseDirection);
            Vertex expectedNeighbourInDirection = expected.getNeighbourInDirection(crosswiseDirection);
            assertThat(neighbourInDirection.getNeighbours().keySet(),
                    matchVertex(expectedNeighbourInDirection.getNeighbours().keySet()));
        }
    }

    private static Matcher<Set<Vertex>> matchVertex(Set<Vertex> vertices) {
        return new TypeSafeMatcher<Set<Vertex>>() {
            @Override public void describeTo(Description description) {
            }

            private boolean result = false;

            @Override
            public boolean matchesSafely(Set<Vertex> kvMap) {
                kvMap.forEach((vertex) ->
                        vertices.forEach(vertex1 -> {
                            if (!result && vertex.getLabel().getTextLabel() == vertex1.getLabel().getTextLabel()
                                    && vertex.getX() == vertex1.getX() && vertex.getY() == vertex1.getY()) {
                                result = true;
                            }
                        })
                );
                return result;
            }
        };
    }

    @Test
    public void apply_vertexWithNeighbours() {
        // given
        Vertex i = new Vertex(Label.S, BASIC_UNIT_LENGTH, BASIC_UNIT_LENGTH);
        i.addNeighbour(new Vertex(Label.B, 0, 0), Direction.E);
        CyclicBarrier barrier = new CyclicBarrier(2);
        P1 p1 = new P1(i, barrier, BASIC_UNIT_LENGTH);

        // when
        Vertex result = p1.apply(i);

        // then
        assertEquals(result, i);
    }

    @Test
    public void apply_wrongLabel() {
        // given
        Vertex i = new Vertex(Label.B, BASIC_UNIT_LENGTH, BASIC_UNIT_LENGTH);
        CyclicBarrier barrier = new CyclicBarrier(2);
        P1 p1 = new P1(i, barrier, BASIC_UNIT_LENGTH);

        // when
        Vertex result = p1.apply(i);

        // then
        assertEquals(result, i);
    }

    private Vertex prepareExpectedVertex() {
        Vertex startVertex = new Vertex(Label.S, BASIC_UNIT_LENGTH, BASIC_UNIT_LENGTH);
        startVertex.setLabel(Label.I);

        Vertex v1 = new Vertex(Label.NONE, calculateCoordinationX(Direction.NW), calculateCoordinationY(Direction.NW));
        Vertex v2 = new Vertex(Label.NONE, calculateCoordinationX(Direction.NE), calculateCoordinationY(Direction.NE));
        Vertex v3 = new Vertex(Label.NONE, calculateCoordinationX(Direction.SE), calculateCoordinationY(Direction.SE));
        Vertex v4 = new Vertex(Label.NONE, calculateCoordinationX(Direction.SW), calculateCoordinationY(Direction.SW));
        startVertex.addNeighbour(v1, Direction.NW);
        startVertex.addNeighbour(v2, Direction.NE);
        startVertex.addNeighbour(v3, Direction.SE);
        startVertex.addNeighbour(v4, Direction.SW);

        Vertex b1 = new Vertex(Label.B, calculateCoordinationX(Direction.W), calculateCoordinationY(Direction.W));
        b1.addNeighbour(v4, Direction.S);
        b1.addNeighbour(v1, Direction.N);

        Vertex b2 = new Vertex(Label.B, calculateCoordinationX(Direction.N), calculateCoordinationY(Direction.N));
        b2.addNeighbour(v1, Direction.W);
        b2.addNeighbour(v2, Direction.E);

        Vertex b3 = new Vertex(Label.B, calculateCoordinationX(Direction.E), calculateCoordinationY(Direction.E));
        b3.addNeighbour(v2, Direction.N);
        b3.addNeighbour(v3, Direction.S);

        Vertex b4 = new Vertex(Label.B, calculateCoordinationX(Direction.S), calculateCoordinationY(Direction.S));
        b4.addNeighbour(v3, Direction.E);
        b4.addNeighbour(v4, Direction.W);

        return startVertex;
    }

    private double calculateCoordinationX(Direction direction) {
        return direction.getXMultiplier() * BASIC_UNIT_LENGTH + BASIC_UNIT_LENGTH;
    }

    private double calculateCoordinationY(Direction direction) {
        return direction.getYMultiplier() * BASIC_UNIT_LENGTH + BASIC_UNIT_LENGTH;
    }
}