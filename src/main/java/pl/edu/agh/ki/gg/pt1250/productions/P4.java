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

        if (!((v.getNeighbours().containsValue(Direction.N) &&
                v.getNeighbours().containsValue(Direction.S)) ||
                (v.getNeighbours().containsValue(Direction.E) &&
                        v.getNeighbours().containsValue(Direction.W)))) {
            LOGGER.debug("Couldn't apply production P4 for " + v.getLabel() + ", because neighbours' location is not correct");
            return false;
        }
        Direction dir1 = null;
        Direction startDir1 = null;
        for (Direction d: EnumSet.of(Direction.N, Direction.S)) {
            if (v.getNeighbours().containsValue(d)) {
                startDir1 = d;
                dir1 = d.getClockwiseNextDirection().getClockwiseNextDirection().getClockwiseNextDirection();
            } else if (v.getNeighbours().containsValue(d.getClockwiseNextDirection().getClockwiseNextDirection())) {
                d = d.getClockwiseNextDirection().getClockwiseNextDirection();
                startDir1 = d;
                dir1 = d.getClockwiseNextDirection().getClockwiseNextDirection().getClockwiseNextDirection();
            }
            Vertex firstI = v.getNeighbourInDirection(startDir1).getNeighbourInDirection(dir1);
            if (!firstI.getLabel().equals(Label.I)) {
                LOGGER.debug("Couldn't apply production P4 for " + firstI.getLabel() + ", because contains wrong label");
                return false;
            }
            Vertex secondV = firstI.getNeighbourInDirection(dir1).getNeighbourInDirection(dir1);
            if (!secondV.getLabel().equals(Label.NONE)) {
                LOGGER.debug("Couldn't apply production P4 for " + secondV.getLabel() + ", because contains wrong label");
                return false;
            }
            Direction Fdir = dir1.getClockwiseNextDirection().getClockwiseNextDirection().getClockwiseNextDirection();
            if(!secondV.getNeighbours().containsValue(Fdir)){
                LOGGER.debug("Couldn't apply production P4 for " + secondV.getLabel() + ", because doesnt containt F1 neighbour");
                return false;
            }
            if(!secondV.getNeighbourInDirection(Fdir).getLabel().equals(Label.F1)){
                LOGGER.debug("Couldn't apply production P4 for " + secondV.getLabel() + ", because doesnt containt F1 neighbour");
                return false;
            }
            
            Vertex thirdI = secondV.getNeighbourInDirection(
                    dir1.getOppositeDirection().getOppositeDirection()).getNeighbourInDirection(dir1);

            if (!thirdI.getLabel().equals(Label.I)) {
                LOGGER.debug("Couldn't apply production P4 for " + thirdI.getLabel() + ", because contains wrong label");
                return false;
            }
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
