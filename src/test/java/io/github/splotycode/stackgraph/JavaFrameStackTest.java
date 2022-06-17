package io.github.splotycode.stackgraph;

import static org.junit.jupiter.api.Assertions.*;

import io.github.splotycode.stackgraph.internal.graph.DefaultStackGraph;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * @author David (_Esel)
 */
class JavaFrameStackTest {
    DefaultStackGraph graph = new DefaultStackGraph();

    @Test
    void testFrameCount() throws IOException {
        call(4);
        //graph.print();
    }

    private void call(int id) {
        graph.methodEnter("id=" + id);
        for (int i = 0; i < id; i++) {
            call(id - 1);
        }
    }
}