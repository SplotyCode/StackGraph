package io.github.splotycode.stackgraph.internal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author David (_Esel)
 */
public class StackFrame {
    private final long identifier;
    private final List<StackFrame> children = new ArrayList<>();
    private String information;

    public StackFrame(long identifier) {
        this.identifier = identifier;
    }

    public long getIdentifier() {
        return identifier;
    }

    public List<StackFrame> getChildren() {
        return children;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void addChild(StackFrame frame) {
        children.add(frame);
    }
}
