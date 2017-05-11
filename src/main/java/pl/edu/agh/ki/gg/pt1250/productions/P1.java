package pl.edu.agh.ki.gg.pt1250.productions;


import org.apache.log4j.Logger;
import pl.edu.agh.ki.gg.pt1250.model.Direction;
import pl.edu.agh.ki.gg.pt1250.model.Label;
import pl.edu.agh.ki.gg.pt1250.model.Vertex;

import java.util.concurrent.CyclicBarrier;

public class P1 extends Production {

    private double dx;
    private double dy;
    private Logger LOGGER = Logger.getLogger(P1.class);

    public P1(Vertex startVertex, CyclicBarrier cyclicBarrier, double basicLength) {
        super(startVertex, cyclicBarrier, basicLength);
        this.dx = startVertex.getX();
        this.dy = startVertex.getY();
    }

    /*
        V(v1)--B(b2)--V(v2)
        |    \       /    |
        |     \     /     |
        B(b1)   I(v)   B(b3)
        |     /     \     |
        |    /       \    |
        V(v4)--B(b4)--V(v3)

        v -> startVertex
     */
    @Override
    Vertex apply(Vertex startVertex) {
        if (!checkApplicability(startVertex)) return startVertex;
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

    @Override
    boolean checkApplicability(Vertex v) {
        if (!v.getLabel().equals(Label.S) || v.getNeighbours().size() != 0) {
            LOGGER.debug("Couldn't apply production P1 for " + v.getLabel());
            return false;
        } else return true;
    }

    private double calculateCoordinationX(Direction direction) {
        return direction.getXMultiplier() * this.getBasicLength() + dx;
    }

    private double calculateCoordinationY(Direction direction) {
        return direction.getYMultiplier() * this.getBasicLength() + dy;
    }
}
