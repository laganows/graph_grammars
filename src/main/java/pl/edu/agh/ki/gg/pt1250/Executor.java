package pl.edu.agh.ki.gg.pt1250;

import pl.edu.agh.ki.gg.pt1250.model.Direction;
import pl.edu.agh.ki.gg.pt1250.model.Label;
import pl.edu.agh.ki.gg.pt1250.model.Vertex;
import pl.edu.agh.ki.gg.pt1250.productions.P1;
import pl.edu.agh.ki.gg.pt1250.productions.P2;
import pl.edu.agh.ki.gg.pt1250.productions.P3;
import pl.edu.agh.ki.gg.pt1250.visualization.Visualizer;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Executor extends Thread {
    private static final double BASIC_UNIT_LENGTH = 4.0;

    public synchronized void run() {
        try {
            Vertex s = new Vertex(Label.S, BASIC_UNIT_LENGTH, BASIC_UNIT_LENGTH);
            //[(P1)]
            CyclicBarrier barrier = new CyclicBarrier(2);
            P1 p1 = new P1(s, barrier, BASIC_UNIT_LENGTH);
            p1.start();
            barrier.await();

            //[(P2)]
            barrier = new CyclicBarrier(2);
            P2 p2 = new P2(s, barrier, BASIC_UNIT_LENGTH/2);
            p2.start();
            barrier.await();

            //[(P3)]
            barrier = new CyclicBarrier(2);
            P3 p3 = new P3(s, barrier, BASIC_UNIT_LENGTH/2);
            p3.start();
            barrier.await();

            displayGraph(s);

        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private void displayGraph(Vertex startVertex) {
        Visualizer visualizer = new Visualizer();
        visualizer.visualizeGraph(startVertex);
        visualizer.displayGraph();
    }
}