package pl.edu.agh.ki.gg.pt1250;

public class Main {
    private static final String RENDERER_KEY = "org.graphstream.ui.renderer";
    private static final String RENDERER_NAME = "org.graphstream.ui.j2dviewer.J2DGraphRenderer";

    public static void main(String[] args) {
        System.setProperty(RENDERER_KEY, RENDERER_NAME);
        Executor s = new Executor();
        s.run();
    }
}
