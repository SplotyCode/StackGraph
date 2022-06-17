package io.github.splotycode.stackgraph.internal.graph;

import io.github.splotycode.stackgraph.StackGraph;
import io.github.splotycode.stackgraph.internal.StackFrame;
import io.github.splotycode.stackgraph.internal.StackTracker;
import io.github.splotycode.stackgraph.internal.render.GraphRender;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

/**
 * @author David (_Esel)
 */
public class DefaultStackGraph implements StackGraph {
    private final StackTracker stackTracker = new StackTracker();
    private final Stack<StackFrame> frames = new Stack<>();
    private long totalFrames;

    @Override
    public void methodEnter(String information) {
        int depth = stackTracker.currentDepth(true);
        int currentFrames = frames.size();
        if (depth - currentFrames > 1) {
            throw new IllegalStateException("Skipped at least one call to methodEnter currentFrames=" + currentFrames + " depth=" + depth);
        }
        while (depth <= frames.size()) {
            frames.pop();
        }
        StackFrame frame = new StackFrame(++totalFrames);
        frame.setInformation(information);
        if (currentFrames != 0) {
            frames.peek().addChild(frame);
        }
        frames.push(frame);
    }
}
