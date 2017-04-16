package pl.edu.agh.ki.gg.pt1250.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Vertex {
    @Getter
    private String id = UUID.randomUUID().toString();

    @Getter
    private final ConcurrentMap<Vertex, Direction> neighbours;

    @Setter
    @Getter
    private Label label;

    @Getter
    @Setter
    private double x;

    @Getter
    @Setter
    private double y;

    public Vertex(Label label, double x, double y) {
        this.label = label;
        this.x = x;
        this.y = y;
        this.neighbours = new ConcurrentHashMap<>();
    }

    public void addNeighbour(Vertex vertex, Direction neighbourDirection) {
        this.neighbours.put(vertex, neighbourDirection);
        vertex.getNeighbours().put(this, neighbourDirection.getOppositeDirection());
    }
}
