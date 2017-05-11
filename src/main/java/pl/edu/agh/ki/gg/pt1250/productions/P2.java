package pl.edu.agh.ki.gg.pt1250.productions;

import org.apache.log4j.Logger;
import pl.edu.agh.ki.gg.pt1250.model.Direction;
import pl.edu.agh.ki.gg.pt1250.model.Label;
import pl.edu.agh.ki.gg.pt1250.model.Vertex;

import java.util.EnumSet;
import java.util.concurrent.CyclicBarrier;

public class P2 extends Production {

    private double dx;
    private double dy;
    private Logger LOGGER = Logger.getLogger(P1.class);

    public P2(Vertex startVertex, CyclicBarrier cyclicBarrier, double basicLength) {
        super(startVertex, cyclicBarrier, basicLength);
        this.dx = startVertex.getX();
        this.dy = startVertex.getY();
    }

    @Override
    Vertex apply(Vertex startVertex) {
        Vertex existingVertex, newVertex;

        startVertex.setLabel(Label.NONE);
        for (Direction direction: EnumSet.of(Direction.NW, Direction.NE, Direction.SW, Direction.SE)) {
            existingVertex = startVertex.getNeighbourInDirection(direction);
            existingVertex.removeNeighbour(direction.getOppositeDirection());

            newVertex = new Vertex(Label.I, calculateCoordinationX(direction), calculateCoordinationY(direction));
            existingVertex.addNeighbour(newVertex, direction.getOppositeDirection());
            startVertex.addNeighbour(newVertex, direction);
        }

        for (Direction direction: EnumSet.of(Direction.N, Direction.E, Direction.S, Direction.W)) {
            newVertex = new Vertex(Label.F1, calculateCoordinationX(direction), calculateCoordinationY(direction));
            startVertex.addNeighbour(newVertex, direction);
        }
        return startVertex;
    }

    @Override
    boolean checkApplicability(Vertex v) {
        // TO DO
        return false;
    }

    private double calculateCoordinationX(Direction direction) {
        return direction.getXMultiplier() * this.getBasicLength() + dx;
    }

    private double calculateCoordinationY(Direction direction) {
        return direction.getYMultiplier() * this.getBasicLength() + dy;
    }
}
