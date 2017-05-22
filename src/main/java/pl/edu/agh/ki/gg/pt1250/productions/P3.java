package pl.edu.agh.ki.gg.pt1250.productions;

import org.apache.log4j.Logger;
import pl.edu.agh.ki.gg.pt1250.model.Direction;
import pl.edu.agh.ki.gg.pt1250.model.Label;
import pl.edu.agh.ki.gg.pt1250.model.Vertex;

import java.util.EnumSet;
import java.util.concurrent.CyclicBarrier;

public class P3 extends Production {

    private double dx;
    private double dy;
    private Logger LOGGER = Logger.getLogger(P1.class);

    public P3(Vertex startVertex, CyclicBarrier cyclicBarrier, double basicLength) {
        super(startVertex, cyclicBarrier, basicLength);
        this.dx = startVertex.getX();
        this.dy = startVertex.getY();
    }

    @Override
    Vertex apply(Vertex startVertex) {
        if (!checkApplicability(startVertex)) return startVertex;

        for (Direction leftDirection: EnumSet.of(Direction.NW, Direction.NE, Direction.SW, Direction.SE)) {
            Vertex leftVertex = startVertex.getNeighbourInDirection(leftDirection);
            Vertex newVertex;
            if (leftVertex.getLabel() == Label.I) {
                Vertex topVertex = startVertex.getNeighbourInDirection(leftDirection.getClockwiseNextDirection());
                Direction rightDirection = leftDirection.getClockwiseNextDirection().getClockwiseNextDirection();
                Vertex rightVertex = startVertex.getNeighbourInDirection(rightDirection);
                Vertex BVertex, twiceLeft, twiceRight;

                if (rightVertex.getLabel() == Label.I) {
                    twiceLeft = leftVertex.getNeighbourInDirection(leftDirection);
                    twiceRight = rightVertex.getNeighbourInDirection(rightDirection);
                    BVertex = twiceLeft.getNeighbourInDirection(rightDirection.getClockwiseNextDirection());

                    BVertex.setLabel(Label.NONE);
                    BVertex.removeNeighbour(leftDirection.getCounterClockwiseNextDirection());
                    BVertex.removeNeighbour(rightDirection.getClockwiseNextDirection());
                    leftVertex.addNeighbour(BVertex, rightDirection);
                    rightVertex.addNeighbour(BVertex, leftDirection);
                    topVertex.addNeighbour(BVertex, leftDirection.getClockwiseNextDirection());

                    newVertex = new Vertex(Label.B, calculateCoordinationX2(leftDirection, leftDirection.getClockwiseNextDirection()), calculateCoordinationY2(leftDirection, leftDirection.getClockwiseNextDirection()));
                    twiceLeft.addNeighbour(newVertex, rightDirection.getClockwiseNextDirection());
                    BVertex.addNeighbour(newVertex, leftDirection.getCounterClockwiseNextDirection());

                    newVertex = new Vertex(Label.B, calculateCoordinationX2(rightDirection, leftDirection.getClockwiseNextDirection()), calculateCoordinationY2(rightDirection, leftDirection.getClockwiseNextDirection()));
                    twiceRight.addNeighbour(newVertex, leftDirection.getCounterClockwiseNextDirection());
                    BVertex.addNeighbour(newVertex, rightDirection.getClockwiseNextDirection());

                    if (!leftVertex.getNeighbours().containsValue(rightDirection.getOppositeDirection())) {
                        newVertex = new Vertex(Label.NONE, calculateCoordinationX2(leftDirection.getCounterClockwiseNextDirection(), leftDirection.getCounterClockwiseNextDirection()),
                                calculateCoordinationY2(leftDirection.getCounterClockwiseNextDirection(), leftDirection.getCounterClockwiseNextDirection()));
                        leftVertex.addNeighbour(newVertex, rightDirection.getOppositeDirection());
                    }
                    if (!rightVertex.getNeighbours().containsValue(leftDirection.getOppositeDirection())) {
                        newVertex = new Vertex(Label.NONE, calculateCoordinationX2(rightDirection.getClockwiseNextDirection(), rightDirection.getClockwiseNextDirection()),
                                calculateCoordinationY2(rightDirection.getClockwiseNextDirection(), rightDirection.getClockwiseNextDirection()));
                        rightVertex.addNeighbour(newVertex, leftDirection.getOppositeDirection());
                    }
                }
            }
        }

        return startVertex;
    }

    @Override
    boolean checkApplicability(Vertex v) {
        if (!v.getLabel().equals(Label.NONE) || v.getNeighbours().size() < 3) {
            LOGGER.debug("Couldn't apply production P3 for " + v.getLabel() + ", because neighbours' size is not correct");
            return false;
        }

        for (Direction leftDirection: EnumSet.of(Direction.NW, Direction.NE, Direction.SW, Direction.SE)) {
            Direction rightDirection = leftDirection.getClockwiseNextDirection().getClockwiseNextDirection();

            if (v.getNeighbours().containsValue(leftDirection) && v.getNeighbours().containsValue(rightDirection) && v.getNeighbours().containsValue(leftDirection.getClockwiseNextDirection())) {
                Vertex leftVertex = v.getNeighbourInDirection(leftDirection);
                Vertex rightVertex = v.getNeighbourInDirection(rightDirection);
                Vertex topVertex = v.getNeighbourInDirection(leftDirection.getClockwiseNextDirection());
                Vertex twiceLeft = leftVertex.getNeighbourInDirection(leftDirection);
                Vertex twiceRight = rightVertex.getNeighbourInDirection(rightDirection);
                Vertex BVertex = twiceLeft.getNeighbourInDirection(rightDirection.getClockwiseNextDirection());

                double dist1 = Math.sqrt(Math.pow(v.getX() - leftVertex.getX(), 2) + Math.pow(v.getY() - leftVertex.getY(), 2));
                double dist2 = Math.sqrt(Math.pow(v.getX() - rightVertex.getX(), 2) + Math.pow(v.getY() - rightVertex.getY(), 2));
                double dist3 = Math.sqrt(Math.pow(v.getX() - topVertex.getX(), 2) + Math.pow(v.getY() - topVertex.getY(), 2));
                double dist4 = Math.sqrt(Math.pow(v.getX() - twiceLeft.getX(), 2) + Math.pow(v.getY() - twiceLeft.getY(), 2));
                double dist5 = Math.sqrt(Math.pow(v.getX() - twiceRight.getX(), 2) + Math.pow(v.getY() - twiceRight.getY(), 2));
                double dist6 = Math.sqrt(Math.pow(v.getX() - BVertex.getX(), 2) + Math.pow(v.getY() - BVertex.getY(), 2));

                if (dist1 == dist2 && dist3*2 == dist6 && dist4 == dist5) {
                    return true;
                }
            }
        }
        return true;
    }

    private double calculateCoordinationX(Direction direction) {
        return direction.getXMultiplier() * this.getBasicLength() + dx;
    }

    private double calculateCoordinationX2(Direction direction, Direction direction2) {
        double tmp = direction.getXMultiplier() * this.getBasicLength() + dx;
        if (direction2 == Direction.E) {
            tmp += 2;
        }
        else if (direction2 == Direction.W) {
            tmp -=2;
        }
        return tmp;
    }

    private double calculateCoordinationY(Direction direction) {
        return direction.getYMultiplier() * this.getBasicLength() + dy;
    }

    private double calculateCoordinationY2(Direction direction, Direction direction2) {
        double tmp = direction.getYMultiplier() * this.getBasicLength() + dy;
        if (direction2 == Direction.N) {
            tmp += 2;
        }
        else if (direction2 == Direction.S) {
            tmp -=2;
        }
        return tmp;
    }
}
