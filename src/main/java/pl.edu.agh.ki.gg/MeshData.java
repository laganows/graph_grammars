package pl.edu.agh.ki.gg;

class MeshData {
    MeshData(double Dx, double Dy, int Nelemx, int Nelemy, int Norder) {
        m_dx = Dx;
        m_dy = Dy;
        m_nelemx = Nelemx;
        m_nelemy = Nelemy;
        m_norder = Norder;
    }

    //mesh dimension along x
    double m_dx;
    //mesh dimension along y
    double m_dy;
    //number of elements along x
    int m_nelemx;
    //number of elements along y
    int m_nelemy;
    //B-spline order
    int m_norder;
}
