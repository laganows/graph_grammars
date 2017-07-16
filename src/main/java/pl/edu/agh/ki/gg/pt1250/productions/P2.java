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
    private Logger LOGGER = Logger.getLogger(P2.class);

    public P2(Vertex startVertex, CyclicBarrier cyclicBarrier, double basicLength) {
        super(startVertex, cyclicBarrier, basicLength);
        this.dx = startVertex.getX();
        this.dy = startVertex.getY();
    }

    @Override
    Vertex apply(Vertex startVertex) {
        if (!checkApplicability(startVertex)) return startVertex;

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
        if (!v.getLabel().equals(Label.I) || v.getNeighbours().size() != 4) {
            LOGGER.debug("Couldn't apply production P2 for " + v.getLabel() + ", because neighbours' size is not correct");
            return false;
        }

        if(!v.getNeighbours().containsValue(Direction.NE) || !v.getNeighbours().containsValue(Direction.NW)
                || !v.getNeighbours().containsValue(Direction.SE) || !v.getNeighbours().containsValue(Direction.SW)){
            LOGGER.debug("Couldn't apply production P2 for " + v.getLabel() + ", because a neighbour's direction is not correct");
            return false;
        }
        Vertex v1 = v.getNeighbourInDirection(Direction.SE);
        Vertex v2 = v.getNeighbourInDirection(Direction.SW);
        Vertex v3 = v.getNeighbourInDirection(Direction.NW);
        Vertex v4 = v.getNeighbourInDirection(Direction.NE);

        double distance1 = Math.sqrt(Math.pow(v.getX() - v1.getX(), 2) + Math.pow(v.getY() - v1.getY(), 2));
        double distance2 = Math.sqrt(Math.pow(v.getX() - v2.getX(), 2) + Math.pow(v.getY() - v2.getY(), 2));
        double distance3 = Math.sqrt(Math.pow(v.getX() - v3.getX(), 2) + Math.pow(v.getY() - v3.getY(), 2));
        double distance4 = Math.sqrt(Math.pow(v.getX() - v4.getX(), 2) + Math.pow(v.getY() - v4.getY(), 2));

        if(distance1 != distance2 || distance2 != distance3 || distance3 != distance4){
            LOGGER.debug("Couldn't apply production P2 for " + v.getLabel() + ", because a neighbour's distance is not correct");
            return false;
        }
        return true;
    }

    private double calculateCoordinationX(Direction direction) {
        return direction.getXMultiplier() * this.getBasicLength() + dx;
    }

    private double calculateCoordinationY(Direction direction) {
        return direction.getYMultiplier() * this.getBasicLength() + dy;
    }
}
