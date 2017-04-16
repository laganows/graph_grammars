package pl.edu.agh.ki.gg.pt1250.productions;

import lombok.AllArgsConstructor;
import pl.edu.agh.ki.gg.pt1250.model.Vertex;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@AllArgsConstructor
public abstract class Production extends Thread {
    private Vertex middleVertex;
    private CyclicBarrier cyclicBarrier;
    private double basicLength;

    abstract Vertex apply(Vertex v, double basicLength);

    abstract void checkApplicability(Vertex v);

    public void run() {
        //apply the production
        middleVertex = apply(middleVertex, basicLength);
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
