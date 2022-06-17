package io.github.splotycode.stackgraph.internal;

/**
 * @author David (_Esel)
 */
public class StackTracker {
    private StackTraceElement caller;

    private boolean isCaller(StackTraceElement traceElement) {
        return caller.getClassName().equals(traceElement.getClassName())
            && caller.getMethodName().equals(traceElement.getMethodName());
    }

    private void initializeTracker() {
        StackTraceElement[] frames = dumpFrames();
        int index = 0;
        while (frames[index].getClassName().startsWith(OUR_PACKAGE)) {
            index++;
            if (index == frames.length) {
                throw new IllegalStateException("Method was not found in current StackTrace");
            }
        }
        caller = frames[index++];
        for (int i = index; i < frames.length; i++) {
            StackTraceElement frame = frames[i];
            if (isCaller(frame)) {
                throw new IllegalStateException("Stack was captured to late");
            }
        }
    }

    private void checkInit(boolean allowInit) {
        if (caller == null) {
            if (!allowInit) {
                throw new IllegalStateException("StackTracker is not initialized");
            }
            initializeTracker();
        }
    }

    public int currentDepth(boolean allowInit) {
        checkInit(allowInit);
        StackTraceElement[] frames = dumpFrames();
        int index = 0;
        while (!isCaller(frames[index])) {
            index++;
            if (index == frames.length) {
                return 0;
            }
        }
        index++;
        int depth = 1;
        for (; index < frames.length; index++) {
            StackTraceElement frame = frames[index];
            if (!isCaller(frame)) {
                break;
            }
            depth++;
        }
        return depth;
    }

    private static final String OUR_PACKAGE = "io.github.splotycode.stackgraph.internal";

    private static StackTraceElement[] dumpFrames() {
        return new Throwable().getStackTrace();
    }
}
