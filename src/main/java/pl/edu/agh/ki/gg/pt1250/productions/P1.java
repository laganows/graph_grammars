package pl.edu.agh.ki.gg.pt1250.productions;


import pl.edu.agh.ki.gg.pt1250.model.Direction;
import pl.edu.agh.ki.gg.pt1250.model.Label;
import pl.edu.agh.ki.gg.pt1250.model.Vertex;

import java.util.concurrent.CyclicBarrier;

public class P1 extends Production {

    public P1(Vertex vertex, CyclicBarrier cyclicBarrier, double basicLenght) {
        super(vertex, cyclicBarrier, basicLenght);
    }

    /*
        V(v1)--B(b2)--V(v2)
        |    \       /    |
        |     \     /     |
        B(b1)   I(v)   B(b3)
        |     /     \     |
        |    /       \    |
        V(v4)--B(b4)--V(v3)
     */
    @Override
    Vertex apply(Vertex v, double basicLength) {
        checkApplicability(v);
        v.setLabel(Label.I);
        double dx = v.getX();
        double dy = v.getY();

        Vertex v1 = new Vertex(Label.NONE, Direction.NW.getXMultiplier() * basicLength + dx, Direction.NW.getYMultiplier() * basicLength + dy);
        Vertex v2 = new Vertex(Label.NONE, Direction.NE.getXMultiplier() * basicLength + dx, Direction.NE.getYMultiplier() * basicLength + dy);
        Vertex v3 = new Vertex(Label.NONE, Direction.SE.getXMultiplier() * basicLength + dx, Direction.SE.getYMultiplier() * basicLength + dy);
        Vertex v4 = new Vertex(Label.NONE, Direction.SW.getXMultiplier() * basicLength + dx, Direction.SW.getYMultiplier() * basicLength + dy);
        v.addNeighbour(v1, Direction.NW);
        v.addNeighbour(v2, Direction.NE);
        v.addNeighbour(v3, Direction.SW);
        v.addNeighbour(v4, Direction.SE);

        Vertex b1 = new Vertex(Label.B, Direction.W.getXMultiplier() * basicLength + dx, Direction.W.getYMultiplier() * basicLength + dy);
        b1.addNeighbour(v4, Direction.S);
        b1.addNeighbour(v1, Direction.N);

        Vertex b2 = new Vertex(Label.B,Direction.N.getXMultiplier() * basicLength + dx, Direction.N.getYMultiplier() * basicLength + dy);
        b2.addNeighbour(v1, Direction.W);
        b2.addNeighbour(v2, Direction.E);

        Vertex b3 = new Vertex(Label.B, Direction.E.getXMultiplier() * basicLength + dx, Direction.E.getYMultiplier() * basicLength + dy);
        b3.addNeighbour(v2, Direction.N);
        b3.addNeighbour(v3, Direction.S);

        Vertex b4 = new Vertex(Label.B, Direction.S.getXMultiplier() * basicLength + dx, Direction.S.getYMultiplier() * basicLength + dy);
        b4.addNeighbour(v3, Direction.E);
        b4.addNeighbour(v4, Direction.W);

        return v;
    }

    @Override
    void checkApplicability(Vertex v) {
        if (!v.getLabel().equals(Label.S)) throw new IllegalArgumentException("Cannot apply P1 production for " + v.getLabel().getTextLabel());
    }
}
