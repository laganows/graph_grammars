package pl.edu.agh.ki.gg.example;

import java.util.concurrent.CyclicBarrier;

class P1 extends Production {
    P1(Vertex Vert, CyclicBarrier Barrier, MeshData Mesh) {
        super(Vert, Barrier, Mesh);
    }

    Vertex apply(Vertex S) {
        System.out.println("p1");
        Vertex T1 = new Vertex(null, null, null, S, "T", 0, S.m_mesh.m_nelemx / 2, S.m_mesh);
        Vertex T2 = new Vertex(null, null, null, S, "T", S.m_mesh.m_nelemx / 2, S.m_mesh.m_nelemx, S.m_mesh);
        S.set_left(T1);
        S.set_right(T2);
        S.set_label("root");
        return S;
    }
}
