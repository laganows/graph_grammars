package pl.edu.agh.ki.gg.pt1250.model;

import lombok.Getter;

public enum Direction {

    /*
        ^
        |
        y
            NW       N      NE
            (-1,1) (0,1) (1,1)
                  \  |  /
                   \ | /
                    \|/
          W (-1,0)-- v --(1,0) E
                    /| \
                   / |  \
                  /  |   \
           (-1,-1) (0,-1) (1,-1)
            SW       S      SE

        x - >
     */

    N(0,1), S(0, -1), W(-1, -0), E(1, 0), NW(-1, 1), NE(1, 1), SW(-1, -1), SE(1, -1);

    static {
        N.oppositeDirection = S;
        S.oppositeDirection = N;
        W.oppositeDirection = E;
        E.oppositeDirection = W;
        NW.oppositeDirection = SE;
        NE.oppositeDirection = SW;
        SW.oppositeDirection = NE;
        SE.oppositeDirection = NW;
    }

    @Getter
    private Direction oppositeDirection;

    @Getter
    private int xMultiplier;

    @Getter
    private int yMultiplier;

    Direction(int xMultiplier, int yMultiplier) {
        this.xMultiplier = xMultiplier;
        this.yMultiplier = yMultiplier;
    }

}
