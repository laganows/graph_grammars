package pl.edu.agh.ki.gg.pt1250.visualization;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import pl.edu.agh.ki.gg.pt1250.model.Vertex;

public class Visualizer {

    private Graph graph;
    private static final String CUSTOM_NODE_STYLE = "url('file:src/main/resources/css/stylesheet.css')";

    public Visualizer() {
        graph = new SingleGraph("PT-1250");
        graph.addAttribute("ui.stylesheet", CUSTOM_NODE_STYLE);
    }

    public void visualizeGraph(Vertex vertex) {
        if (graph.getNode(vertex.getId()) == null) addNodeToGraph(vertex);
        if (vertex.getNeighbours().size() == graph.getNode(vertex.getId()).getDegree()) return;

        vertex.getNeighbours().forEach(
                (k, v) -> {
                    if (graph.getNode(k.getId()) == null) addNodeToGraph(k);

                    if (!graph.getNode(vertex.getId()).hasEdgeBetween(k.getId())) {
                        graph.addEdge(vertex.getId() + k.getId(), vertex.getId(), k.getId());
                        visualizeGraph(k);
                    }
                }
        );
    }

    public void displayGraph() {
        this.graph.display()
                .getDefaultView()
                .setMouseManager(new EmptyMouseManager());
    }

    private void addNodeToGraph(Vertex vertex) {
        if (graph.getNode(vertex.getId()) == null) {
            graph.addNode(vertex.getId()).setAttribute("xy", vertex.getX(), vertex.getY());
            graph.getNode(vertex.getId()).addAttribute("layout.frozen");
            graph.getNode(vertex.getId()).addAttribute("ui.class", vertex.getLabel().getTextLabel());
        }
    }

}
