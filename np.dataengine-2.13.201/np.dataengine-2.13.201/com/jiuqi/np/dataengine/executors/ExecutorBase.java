/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.text.StringHelper
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.collector.FmlExecuteCollector;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.text.StringHelper;
import java.io.PrintStream;
import java.util.ArrayList;

public class ExecutorBase {
    public static final int PRIORITY_HIGHEST = 200;
    public static final int PRIORITY_HIGH = 160;
    public static final int PRIORITY_NORMAL = 120;
    public static final int PRIORITY_LOW = 80;
    public static final int PRIORITY_LOWEST = 40;
    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_INITIALIZED = 1;
    public static final int STATE_ASSIGNED = 2;
    public static final int STATE_PREPARED = 4;
    public static final int STATE_EXECUTING = 8;
    public static final int STATE_FINISHED = 16;
    public static final int STATE_CANCELED = 32;
    public static final int STATE_DESTROYED = 64;
    public QueryContext context;
    protected FmlExecuteCollector executeCollector;
    private int priority = 120;
    protected boolean isAccessorial = false;
    protected int state = 0;
    public static boolean CHECK_CHILD_LINK = true;
    protected ArrayList precursors = null;
    public static PrintStream logger;

    public ExecutorBase(QueryContext context) {
        this.context = context;
        this.executeCollector = context.getFmlExecuteCollector();
    }

    public String getName() {
        String result = this.getClass().getName();
        int p = result.lastIndexOf(46);
        if (p >= 0) {
            result = result.substring(p + 1);
        }
        result = result + '(' + Integer.toHexString(this.hashCode()) + ')';
        return result;
    }

    public String getFeature() {
        return null;
    }

    public final int getPriority() {
        return this.priority;
    }

    public final void setPriority(int value) {
        this.priority = value;
    }

    public final boolean isAccessorial() {
        return this.isAccessorial;
    }

    public final void setIsAccessorial(boolean value) {
        this.isAccessorial = value;
    }

    public final int getState() {
        return this.state;
    }

    public boolean doMessage(int code, Object data, boolean recurse) {
        return false;
    }

    public boolean initialize(Object initInfo) throws ExecuteException {
        if (this.state == 0 && this.doInitialization(initInfo)) {
            this.state = 1;
            return true;
        }
        return false;
    }

    protected boolean doInitialization(Object initInfo) throws ExecuteException {
        return true;
    }

    protected boolean assign(Object taskInfo) throws ExecuteException {
        if (this.state == 1) {
            if (this.doAssignment(taskInfo)) {
                this.state = 2;
                return true;
            }
            return false;
        }
        if (this.state == 0) {
            throw new ExecuteException(this.getName() + "\u6ca1\u6709\u521d\u59cb\u5316\uff0c\u4e0d\u80fd\u6307\u6d3e\u4efb\u52a1");
        }
        throw new ExecuteException(this.getName() + "\u72b6\u6001\u6ca1\u6709\u590d\u539f\uff0c\u4e0d\u80fd\u6307\u6d3e\u65b0\u4efb\u52a1");
    }

    protected boolean doAssignment(Object taskInfo) throws ExecuteException {
        return true;
    }

    protected void prepare(Object taskInfo) throws Exception {
        if (this.state == 2 && this.notifyAndCheckPrecursors(taskInfo)) {
            this.doPreparation(taskInfo);
            this.state = 4;
        }
    }

    protected void doPreparation(Object taskInfo) throws Exception {
    }

    protected boolean execute(Object taskInfo) throws Exception {
        if (this.state == 4) {
            this.state = 8;
            try {
                if (this.doExecution(taskInfo)) {
                    this.state = 16;
                }
            }
            finally {
                if (this.state == 8) {
                    this.state = 4;
                }
            }
        }
        return this.state == 16 || this.state == 32;
    }

    protected boolean doExecution(Object taskInfo) throws Exception {
        return true;
    }

    public boolean isTaskDone() {
        return this.state == 16 || this.state == 32;
    }

    public boolean isTaskFinished() {
        return this.state == 16;
    }

    public boolean isTaskCanceled() {
        return this.state == 32;
    }

    protected void cancel() throws ExecuteException {
        if (this.state == 2 || this.state == 4) {
            this.doCancel();
            this.state = 32;
        }
    }

    protected void doCancel() throws ExecuteException {
    }

    protected void reset() throws ExecuteException {
        if (this.isAccessorial && this.state != 16 && this.state != 32) {
            this.cancel();
        }
        if (this.state == 16 || this.state == 32) {
            this.doReset();
            this.state = 1;
        } else if (this.state != 1) {
            throw new ExecuteException(this.getName() + "\u4efb\u52a1\u6ca1\u6709\u5b8c\u6210\uff0c\u65e0\u6cd5\u590d\u539f");
        }
    }

    protected void doReset() throws ExecuteException {
    }

    public void resetAll() throws ExecuteException {
        this.reset();
    }

    public void destroy() throws ExecuteException {
        if (this.state != 64) {
            this.doDestruction();
            this.state = 64;
        }
    }

    protected void doDestruction() throws ExecuteException {
        if (this.precursors != null) {
            this.precursors.clear();
            this.precursors = null;
        }
    }

    public final boolean runTask(Object taskInfo) throws Exception {
        this.reset();
        if (!this.assign(taskInfo)) {
            throw new ExecuteException("\u6307\u6d3e\u7684\u4efb\u52a1\u6ca1\u6709\u63a5\u53d7\u5355\u5143");
        }
        this.prepare(taskInfo);
        if (this.state == 4) {
            this.execute(taskInfo);
        }
        return this.state == 16 || this.state == 32;
    }

    public final void checkRunTask(Object taskInfo) throws Exception {
        if (!this.runTask(taskInfo)) {
            if (logger != null) {
                this.printNetwork(logger, 1);
            }
            throw new ExecuteException(this.getName() + "\u65e0\u6cd5\u5b8c\u6210\u4efb\u52a1" + taskInfo.toString());
        }
    }

    public boolean prepareForStepByStepRun(Object taskInfo) throws Exception {
        this.reset();
        if (!this.assign(taskInfo)) {
            throw new ExecuteException("\u6307\u6d3e\u7684\u4efb\u52a1\u6ca1\u6709\u63a5\u53d7\u5355\u5143");
        }
        this.prepare(taskInfo);
        return this.state == 4;
    }

    public boolean stepRun(Object taskInfo) throws Exception {
        if (this.state != 4) {
            throw new ExecuteException("\u4efb\u52a1\u6ca1\u6709\u51c6\u5907\u597d\uff0c\u4e0d\u80fd\u5355\u6b65\u6267\u884c");
        }
        this.execute(taskInfo);
        return this.state == 16 || this.state == 32;
    }

    protected boolean notifyAndCheckPrecursors(Object taskInfo) throws Exception {
        int count = this.precursors == null ? 0 : this.precursors.size();
        for (int i = 0; i < count; ++i) {
            ExecutorBase executor = (ExecutorBase)this.precursors.get(i);
            if (executor.state == 4 || executor.state == 16 || executor.state == 32) continue;
            if (executor.isAccessorial) {
                executor.prepare(taskInfo);
                executor.execute(taskInfo);
                if (executor.state != 16 && executor.state != 32) {
                    return false;
                }
            }
            return false;
        }
        return true;
    }

    public boolean containsChild(ExecutorBase child) {
        return false;
    }

    public void print() {
    }

    private final void addPrecursor(ExecutorBase precursor) {
        if (precursor == null) {
            return;
        }
        if (this.precursors == null) {
            this.precursors = new ArrayList(4);
        }
        if (!this.precursors.contains(precursor)) {
            this.precursors.add(precursor);
        }
    }

    private final void removePrecursor(ExecutorBase precursor) {
        if (this.precursors != null) {
            this.precursors.remove(precursor);
        }
    }

    public boolean canAddPrecursor(ExecutorBase precursor) {
        if (precursor == this || this.containsChild(precursor) || precursor.containsChild(this)) {
            return false;
        }
        return !precursor.isSuccessorOf(this);
    }

    public boolean isSuccessorOf(ExecutorBase another) {
        if (this.precursors == null) {
            return false;
        }
        for (int i = 0; i < this.precursors.size(); ++i) {
            ExecutorBase executor = (ExecutorBase)this.precursors.get(i);
            if (executor != another && !executor.isSuccessorOf(another) && (!CHECK_CHILD_LINK || !executor.containsChild(another))) continue;
            return true;
        }
        return false;
    }

    public final void replacePrecursor(ExecutorBase from, ExecutorBase to) {
        if (from == null) {
            return;
        }
        if (to == null) {
            throw new IllegalArgumentException("\u4e0d\u80fd\u66ff\u6362\u6210null");
        }
        if (this.precursors == null) {
            return;
        }
        for (int i = 0; i < this.precursors.size(); ++i) {
            ExecutorBase executor = (ExecutorBase)this.precursors.get(i);
            if (executor == from) {
                this.precursors.set(i, to);
                continue;
            }
            executor.replacePrecursor(from, to);
        }
    }

    public static final boolean createLink(ExecutorBase precursor, ExecutorBase successor) {
        if (precursor == null || successor == null) {
            return true;
        }
        if (!successor.canAddPrecursor(precursor)) {
            return false;
        }
        successor.addPrecursor(precursor);
        return true;
    }

    public static final void removeLink(ExecutorBase precursor, ExecutorBase successor) {
        if (precursor != null && successor != null) {
            successor.removePrecursor(precursor);
        }
    }

    public void printNetwork(PrintStream ps, int level) {
        ps.print(StringHelper.getSpaceString((int)level));
        ps.print(this.getName());
        ps.print(" ---> [");
        if (this.precursors != null) {
            for (int i = 0; i < this.precursors.size(); ++i) {
                ExecutorBase executor = (ExecutorBase)this.precursors.get(i);
                if (i > 0) {
                    ps.print(' ');
                }
                ps.print(executor.getName());
            }
        }
        ps.println("]");
    }
}

