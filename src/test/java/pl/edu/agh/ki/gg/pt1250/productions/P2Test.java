package pl.edu.agh.ki.gg.pt1250.productions;

        import org.junit.Test;
        import pl.edu.agh.ki.gg.pt1250.model.Direction;
        import pl.edu.agh.ki.gg.pt1250.model.Label;
        import pl.edu.agh.ki.gg.pt1250.model.Vertex;

        import java.util.EnumSet;
        import java.util.concurrent.CyclicBarrier;

        import static org.junit.Assert.*;

/**
 * Created by Jakub Solawa on 13.05.2017.
 */
public class P2Test {

    private static final double BASIC_UNIT_LENGTH = 4.0;

    @Test
    public void shouldApplyOnlyToNodeI() throws Exception{
        Vertex  NodeI = new Vertex(Label.I, BASIC_UNIT_LENGTH, BASIC_UNIT_LENGTH);
        Vertex  NodeF1 = new Vertex(Label.F1, BASIC_UNIT_LENGTH, BASIC_UNIT_LENGTH);
        Vertex  NodeB = new Vertex(Label.B, BASIC_UNIT_LENGTH, BASIC_UNIT_LENGTH);
        Vertex  NodeNone = new Vertex(Label.NONE, BASIC_UNIT_LENGTH, BASIC_UNIT_LENGTH);

        CyclicBarrier barrier = new CyclicBarrier(2);
        P2 p2 = new P2(NodeI, barrier, BASIC_UNIT_LENGTH/2);

        assertTrue(p2.checkApplicability(NodeI));
        assertFalse(p2.checkApplicability(NodeF1));
        assertFalse(p2.checkApplicability(NodeB));
        assertFalse(p2.checkApplicability(NodeNone));
    }

    @Test
    public void shouldGenerateCorrectGraph() throws Exception{
        Vertex vertex;
        Vertex NodeS = new Vertex(Label.S, BASIC_UNIT_LENGTH, BASIC_UNIT_LENGTH);
        CyclicBarrier barrier = new CyclicBarrier(1);
        P1 p1 = new P1(NodeS, barrier, BASIC_UNIT_LENGTH);
        p1.apply(NodeS);

        barrier = new CyclicBarrier(2);
        P2 p2 = new P2(NodeS, barrier, BASIC_UNIT_LENGTH/2);
        p2.apply(NodeS);

        assertEquals(NodeS.getLabel(), Label.NONE);

        for (Direction direction: EnumSet.of(Direction.NW, Direction.NE, Direction.SW, Direction.SE)) {
            vertex = NodeS.getNeighbourInDirection(direction);
            assertNotNull(vertex);
            assertEquals(vertex.getLabel(), Label.I);
            vertex = vertex.getNeighbourInDirection(direction);
            assertNotNull(vertex);
            assertEquals(vertex.getLabel(), Label.NONE);
        }

        for (Direction direction: EnumSet.of(Direction.N, Direction.E, Direction.S, Direction.W)) {
            vertex = NodeS.getNeighbourInDirection(direction);
            assertNotNull(vertex);
            assertEquals(vertex.getLabel(), Label.F1);
        }
    }
}