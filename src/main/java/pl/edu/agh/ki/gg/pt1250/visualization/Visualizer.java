package pl.edu.agh.ki.gg.pt1250.visualization;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.util.MouseManager;
import pl.edu.agh.ki.gg.pt1250.model.Vertex;

import java.awt.event.MouseEvent;

public class Visualizer {

    private Graph graph;
    private final String CUSTOM_NODE_STYLE = "node.important {text-size:20;}"; //TODO Improve style for node with not-empty labels :)

    public Visualizer() {
        this.graph = new SingleGraph("PT-1250");
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
                .setMouseManager(new MouseManager() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }

                    @Override
                    public void mouseDragged(MouseEvent e) {
                    }

                    @Override
                    public void mouseMoved(MouseEvent e) {
                    }

                    @Override
                    public void init(GraphicGraph graph, View view) {
                    }

                    @Override
                    public void release() {
                    }
                });
    }

    private void addNodeToGraph(Vertex vertex) {
        if (graph.getNode(vertex.getId()) == null) {
            graph.addNode(vertex.getId()).setAttribute("xy", vertex.getX(), vertex.getY());
            graph.getNode(vertex.getId()).addAttribute("layout.frozen");
            graph.getNode(vertex.getId()).addAttribute("ui.label", vertex.getLabel().getTextLabel());
            graph.getNode(vertex.getId()).addAttribute("ui.class", "important");
        }
    }

}
