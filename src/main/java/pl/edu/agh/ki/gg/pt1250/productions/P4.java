package pl.edu.agh.ki.gg.pt1250.productions;

import org.apache.log4j.Logger;
import pl.edu.agh.ki.gg.pt1250.model.Direction;
import pl.edu.agh.ki.gg.pt1250.model.Label;
import pl.edu.agh.ki.gg.pt1250.model.Vertex;

import java.util.EnumSet;
import java.util.concurrent.CyclicBarrier;

public class P4 extends Production {

    private double dx;
    private double dy;
    private Logger LOGGER = Logger.getLogger(P1.class);

    public P4(Vertex startVertex, CyclicBarrier cyclicBarrier, double basicLength) {
        super(startVertex, cyclicBarrier, basicLength);
        this.dx = startVertex.getX();
        this.dy = startVertex.getY();
    }

    @Override
    Vertex apply(Vertex startVertex) {
        if (!checkApplicability(startVertex)) return startVertex;
        startVertex.setLabel(Label.NONE);
        Direction startDir = null;
        Direction pointerToStart = null;
        Direction nextNeigh = null;

        if (startVertex.getNeighbours().containsValue(Direction.N)) {
            startDir = Direction.N;
            pointerToStart = Direction.SE;
            nextNeigh = Direction.SE;
        } else if(startVertex.getNeighbours().containsValue(Direction.E)) {
            startDir = Direction.E;
            pointerToStart = Direction.NE;
            nextNeigh = Direction.SW;
        }
        Vertex current = startVertex.getNeighbourInDirection(startDir).
                getNeighbourInDirection(nextNeigh);

        // sometimes I like C old school style
        for(int i =0;i<8; i++){
            if(current.getLabel().equals(Label.I)) {
                current.addNeighbour(startVertex, pointerToStart);
            }else{
                for (Direction direction: EnumSet.of(Direction.N, Direction.E, Direction.S, Direction.W)) {
                    if (current.getNeighbours().containsValue(direction)){
                        Vertex maybeF = current.getNeighbourInDirection(direction);
                        if(maybeF.getLabel().equals(Label.F1)){
                            if(!maybeF.equals(current))
                                maybeF.addNeighbour(startVertex, direction.getOppositeDirection());
                            else{
                                current.removeNeighbour(direction);
                                Vertex newV = new Vertex(Label.F1,
                                        calculateCoordinationX(direction),
                                        calculateCoordinationY(direction));
                                current.addNeighbour(newV, direction);
                                startVertex.addNeighbour(newV, direction.getOppositeDirection());
                            }
                        }
                    }
                }
            }
            while(!current.getNeighbours().containsValue(nextNeigh)) {
                nextNeigh = nextNeigh.getClockwiseNextDirection();
            }
            current = current.getNeighbourInDirection(nextNeigh);
            pointerToStart = pointerToStart.getClockwiseNextDirection();
        }
        return startVertex;
    }

    @Override
    boolean checkApplicability(Vertex v) {
        if (!v.getLabel().equals(Label.F1) || v.getNeighbours().size() != 2) {
            LOGGER.debug("Couldn't apply production P4 for " + v.getLabel() + ", because neighbours' size is not correct");
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
