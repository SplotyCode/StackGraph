package io.github.splotycode.stackgraph.internal.render;

import io.github.splotycode.stackgraph.internal.StackFrame;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

/**
 * @author David (_Esel)
 */
public class GraphRender {
    private final Set<StackFrame> rendered = new HashSet<>();
    private final Writer writer;

    public GraphRender(Writer writer) {
        this.writer = writer;
    }

    public void render(StackFrame root) throws IOException {
        writer.append("digraph injector {\n");
        writer.append("  graph [test=dummy];\n");
        dumpFrames(root);
        dumpBindings(root);
        writer.append("}");
    }

    public void close() {
        try {
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void dumpFrames(StackFrame frame) throws IOException {
        if (rendered.add(frame)) {
            writer.append("  ")
                .append(String.valueOf(frame.getIdentifier()))
                .append(" [shape=box, label=<")
                .append(frame.getInformation())
                .append(">]")
                .append("\n");
            for (StackFrame child : frame.getChildren()) {
                dumpFrames(child);
            }
        }
    }

    private void dumpBindings(StackFrame frame) throws IOException {
        for (StackFrame child : frame.getChildren()) {
            writer.append("  ")
                .append(String.valueOf(frame.getIdentifier()))
                .append(" -> ")
                .append(String.valueOf(child.getIdentifier()))
                .append("\n");
            dumpBindings(child);
        }
    }
}
