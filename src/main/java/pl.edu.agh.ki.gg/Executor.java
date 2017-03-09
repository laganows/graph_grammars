package pl.edu.agh.ki.gg;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Executor extends Thread {
    public synchronized void run() {
// BUILDING ELEMENT PARTITION TREE 	
        try {
            double dx = 4.0; //mesh size along x
            double dy = 4.0;    //mesh size along y
            int n = 4; //number of elements along x axis
            int m = 4; //number of elements along y axis
            int p = 2; //polynomial order of approximation
            // Mesh
            MeshData mesh = new MeshData(dx, dy, n, m, 2);
            Vertex S = new Vertex(null, null, null, null, "S", 0, mesh.m_nelemx, mesh);
            //[(P1)]
            CyclicBarrier barrier = new CyclicBarrier(1);
            P1 p1 = new P1(S, barrier, mesh);
            p1.start();
            barrier.await();
            //[(P2)1(P2)2]
            barrier = new CyclicBarrier(2);
            P2 p2a = new P2(p1.m_vertex.m_left, barrier, mesh);
            P2 p2b = new P2(p1.m_vertex.m_right, barrier, mesh);
            p2a.start();
            p2b.start();
            barrier.await();
            //[(P3)1(P3)2(P3)3(P3)4]
            barrier = new CyclicBarrier(4);
            P3 p3a1 = new P3(p2a.m_vertex.m_left, barrier, mesh);
            P3 p3a2 = new P3(p2a.m_vertex.m_right, barrier, mesh);
            P3 p3b1 = new P3(p2b.m_vertex.m_left, barrier, mesh);
            P3 p3b2 = new P3(p2b.m_vertex.m_right, barrier, mesh);
            p3a1.start();
            p3a2.start();
            p3b1.start();
            p3b2.start();
            barrier.await();
            //Test the tree
            TestTree tester = new TestTree();
            boolean result = tester.test(S, 0, 4);
            if (result)
                System.out.println("OK");

            // TO DO
            // Please extend the tree to the mesh 8x8 with 8 leaf branches

        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}