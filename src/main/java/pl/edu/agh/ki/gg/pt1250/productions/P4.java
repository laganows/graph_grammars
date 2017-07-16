package pl.edu.agh.ki.gg.pt1250.productions;

import org.apache.log4j.Logger;
import pl.edu.agh.ki.gg.pt1250.model.Direction;
import pl.edu.agh.ki.gg.pt1250.model.Label;
import pl.edu.agh.ki.gg.pt1250.model.Vertex;
import pl.edu.agh.ki.gg.pt1250.utils.DirectionUtil;

import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.CyclicBarrier;

public class P4 extends Production {
    private double dx;
    private double dy;
    private Logger LOGGER = Logger.getLogger(P4.class);

    public P4(Vertex startVertex, CyclicBarrier cyclicBarrier, double basicLength) {
        super(startVertex, cyclicBarrier, basicLength);
        this.dx = startVertex.getX();
        this.dy = startVertex.getY();
    }

    @Override
    Vertex apply(Vertex v) {
        if (!checkApplicability(v)) return v;

        Direction nextDirection1 = Direction.N;
        Direction nextDirection2 = Direction.S;

        if (Objects.isNull(v.getNeighbourInDirection(Direction.N))) {
            nextDirection1 = DirectionUtil.getDirectionInGivenCountDirection(nextDirection1, 2);
            nextDirection2 = DirectionUtil.getDirectionInGivenCountDirection(nextDirection2, 2);
        }

        connectVertexIToF1(v, nextDirection1, 3, -3);
        connectVertexIToF1(v, nextDirection1, -3, 3);
        connectVertexIToF1(v, nextDirection2, 3, -3);
        connectVertexIToF1(v, nextDirection2, -3, 3);

        connectVertexF1toF1(v, nextDirection1, 3, -2);
        connectVertexF1toF1(v, nextDirection1, -3, 2);

        addVertexF1(v, nextDirection1);
        addVertexF1(v, nextDirection2);

        v.setLabel(Label.NONE);

        return v;
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
        Vertex firstI = null, secondV = null;
        for (Direction d : EnumSet.of(Direction.N, Direction.S)) {
            if (v.getNeighbours().containsValue(d)) {
                startDir1 = d;
                dir1 = d.getClockwiseNextDirection().getClockwiseNextDirection().getClockwiseNextDirection();
            } else if (v.getNeighbours().containsValue(d.getClockwiseNextDirection().getClockwiseNextDirection())) {
                d = d.getClockwiseNextDirection().getClockwiseNextDirection();
                startDir1 = d;
                dir1 = d.getClockwiseNextDirection().getClockwiseNextDirection().getClockwiseNextDirection();
            }
            if (v.getNeighbours().containsValue(startDir1)) {
                firstI = v.getNeighbourInDirection(startDir1).getNeighbourInDirection(dir1);
                if (!firstI.getLabel().equals(Label.I)) {
                    LOGGER.debug("Couldn't apply production P4 for " + firstI.getLabel() + ", because contains wrong label");
                    return false;
                }
            }

            if (firstI.getNeighbours().containsValue(dir1)) {
                secondV = firstI.getNeighbourInDirection(dir1);
                if (!secondV.getLabel().equals(Label.NONE)) {
                    LOGGER.debug("Couldn't apply production P4 for " + secondV.getLabel() + ", because contains wrong label");
                    return false;
                }

            }
            if (secondV == null) {
                LOGGER.debug("Couldn't apply production P4 for neighbour of " + firstI.getLabel() +
                        "because no neighbour in direction " + dir1);
                return false;
            }
            Direction Fdir = dir1.getClockwiseNextDirection().getClockwiseNextDirection();
            if (!secondV.getNeighbours().containsValue(Fdir)) {
                LOGGER.debug("Couldn't apply production P4 for " + secondV.getLabel() + " because doesnt contain neighbour in direction " + dir1);
                return false;
            }
            if (secondV.getNeighbourInDirection(Fdir) == null ||
                    !secondV.getNeighbourInDirection(Fdir.getClockwiseNextDirection()).getLabel().equals(Label.F1)) {
                LOGGER.debug("Couldn't apply production P4 for " + secondV.getLabel() + " , because doesnt containt F1 neighbour.");
                return false;
            }
            if (secondV.getNeighbours().containsValue(dir1.getClockwiseNextDirection().getClockwiseNextDirection())) {
                Vertex thirdI = secondV.getNeighbourInDirection(
                        dir1.getClockwiseNextDirection().getClockwiseNextDirection());
                if (!thirdI.getLabel().equals(Label.I)) {
                    LOGGER.debug("Couldn't apply production P4 for " + thirdI.getLabel() + ", because contains wrong label");
                    return false;
                }
            }
        }
        return true;

    }

    private void connectVertexIToF1(Vertex v, Direction directionToNextVertex, int firstCountDirection, int secondCountDirection) {
        Vertex neighbourInDirection = v.getNeighbourInDirection(directionToNextVertex);
        Direction direction1 = DirectionUtil.getDirectionInGivenCountDirection(directionToNextVertex, firstCountDirection);

        Vertex vertexInGivenCountDirection = neighbourInDirection.getNeighbourInDirection(direction1);
        Direction direction2 = DirectionUtil.getDirectionInGivenCountDirection(directionToNextVertex, secondCountDirection);

        vertexInGivenCountDirection.addNeighbour(v, direction2);
    }

    private void connectVertexF1toF1(Vertex v, Direction directionToNextVertex, int firstCountDirection, int secondCountDirection) {
        Vertex neighbourInDirection = v.getNeighbourInDirection(directionToNextVertex);
        Direction direction1 = DirectionUtil.getDirectionInGivenCountDirection(directionToNextVertex, firstCountDirection);

        Vertex neighbourInDirection1 = neighbourInDirection.getNeighbourInDirection(direction1).getNeighbourInDirection(direction1);
        Direction direction2 = DirectionUtil.getDirectionInGivenCountDirection(directionToNextVertex, secondCountDirection);

        Vertex F1 = neighbourInDirection1.getNeighbourInDirection(direction2);
        F1.addNeighbour(v, direction2);
    }

    private void addVertexF1(Vertex v, Direction nextDirection) {
        Vertex F1 = new Vertex(Label.F1, calculateCoordinationX(nextDirection), calculateCoordinationY(nextDirection));

        Vertex neighbourInDirection = v.getNeighbourInDirection(nextDirection);
        v.removeNeighbour(nextDirection);
        F1.addNeighbour(neighbourInDirection, nextDirection);

        v.addNeighbour(F1, nextDirection);
    }

    private double calculateCoordinationX(Direction direction) {
        return direction.getXMultiplier() * this.getBasicLength() + dx;
    }

    private double calculateCoordinationY(Direction direction) {
        return direction.getYMultiplier() * this.getBasicLength() + dy;
    }
}
