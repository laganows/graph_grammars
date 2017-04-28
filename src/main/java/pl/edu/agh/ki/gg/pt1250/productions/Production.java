package pl.edu.agh.ki.gg.pt1250.productions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.edu.agh.ki.gg.pt1250.model.Vertex;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@AllArgsConstructor
public abstract class Production extends Thread {
    private Vertex startVertex;
    private CyclicBarrier cyclicBarrier;
    @Getter
    private double basicLength;

    abstract Vertex apply(Vertex v);

    abstract boolean checkApplicability(Vertex v);

    public void run() {
        //apply the production
        startVertex = apply(startVertex);
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
