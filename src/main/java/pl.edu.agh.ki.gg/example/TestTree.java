package pl.edu.agh.ki.gg.example;

class TestTree {
    TestTree() {
    }

    //test the tree
    boolean test(Vertex v, double Beg, double End) {
        boolean result;
        boolean ret = true;
        if (v.m_left == null) {
            System.out.println("[" + v.m_beg + "," + v.m_end + "]");
            if (Math.abs(v.m_beg - Beg) > 0.00000001 || Math.abs(v.m_end - End) > 0.00000001) ret = false;
        }
        if (v.m_middle == null) {
            //browse
            if (v.m_left != null) {
                result = test(v.m_left, Beg, Beg + (End - Beg) * 0.5);
                if (!result) ret = false;
            }
            if (v.m_right != null) {
                result = test(v.m_right, Beg + (End - Beg) * 0.5, End);
                if (!result) ret = false;
            }
        } else {
            //browse
            if (v.m_left != null) {
                result = test(v.m_left, Beg, Beg + (End - Beg) / 3.0);
                if (!result) ret = false;
            }
            if (v.m_middle != null) {
                result = test(v.m_middle, Beg + (End - Beg) / 3.0, Beg + 2.0 * (End - Beg) / 3.0);
                if (!result) ret = false;
            }
            if (v.m_right != null) {
                result = test(v.m_right, Beg + 2.0 * (End - Beg) / 3.0, End);
                if (!result) ret = false;
            }
        }
        return ret;
    }
}

