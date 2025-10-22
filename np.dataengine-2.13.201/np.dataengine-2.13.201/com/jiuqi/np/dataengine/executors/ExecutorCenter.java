/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.BoolValue
 *  com.jiuqi.bi.util.IntList
 *  com.jiuqi.np.util.SortedList
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.util.BoolValue;
import com.jiuqi.bi.util.IntList;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.executors.ExecutorBase;
import com.jiuqi.np.dataengine.executors.ExternalExecHandler;
import com.jiuqi.np.dataengine.executors.ExternalExecutor;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.util.SortedList;
import java.io.PrintStream;
import java.util.ArrayList;

public class ExecutorCenter
extends ExecutorBase {
    protected boolean sorted = true;
    protected ArrayList<ExecutorBase> executors = new ArrayList();
    protected Object subTask = null;
    protected boolean hasActiveSubTask = false;

    public ExecutorCenter(QueryContext context) {
        super(context);
    }

    public int size() {
        return this.executors.size();
    }

    public ExecutorBase get(int index) {
        return this.executors.get(index);
    }

    public void add(ExecutorBase executor) {
        this.executors.add(executor);
        this.sorted = false;
    }

    public void remove(ExecutorBase executor) {
        this.executors.remove(executor);
    }

    public ExternalExecutor addExternalExecutor(ExternalExecHandler preparation, ExternalExecHandler execution) {
        ExternalExecutor result = new ExternalExecutor(this.context, preparation, execution);
        this.add(result);
        return result;
    }

    @Override
    public final boolean containsChild(ExecutorBase child) {
        int count = this.executors.size();
        for (int i = 0; i < count; ++i) {
            ExecutorBase executor = this.executors.get(i);
            if (executor != child && !executor.containsChild(child)) continue;
            return true;
        }
        return false;
    }

    @Override
    public final boolean isSuccessorOf(ExecutorBase another) {
        if (super.isSuccessorOf(another)) {
            return true;
        }
        if (CHECK_CHILD_LINK) {
            int count = this.executors.size();
            for (int i = 0; i < count; ++i) {
                ExecutorBase executor = this.executors.get(i);
                if (!executor.isSuccessorOf(another)) continue;
                return true;
            }
        }
        return false;
    }

    public ExecutorBase findExecutor(Class<?> executorClass) {
        int count = this.executors.size();
        for (int i = 0; i < count; ++i) {
            ExecutorBase executor = this.executors.get(i);
            if (!executorClass.isAssignableFrom(executor.getClass())) continue;
            return executor;
        }
        return null;
    }

    public ExecutorBase findExecutor(String className) {
        try {
            Class<?> cls = Class.forName(className);
            return this.findExecutor(cls);
        }
        catch (ClassNotFoundException ex) {
            return null;
        }
    }

    public ExecutorBase findExecutorExact(Class<?> executorClass) {
        int count = this.executors.size();
        for (int i = 0; i < count; ++i) {
            ExecutorBase executor = this.executors.get(i);
            if (!executor.getClass().equals(executorClass)) continue;
            return executor;
        }
        return null;
    }

    public ExecutorBase findExecutorExact(String className) {
        try {
            Class<?> cls = Class.forName(className);
            return this.findExecutorExact(cls);
        }
        catch (ClassNotFoundException ex) {
            return null;
        }
    }

    @Override
    public boolean doMessage(int code, Object data, boolean recurse) {
        boolean result = false;
        if (recurse) {
            result = this.doChildrenMessage(code, data, recurse);
        }
        return result;
    }

    public boolean doChildrenMessage(int code, Object data, boolean recurse) {
        boolean result = false;
        int count = this.executors.size();
        for (int i = 0; i < count; ++i) {
            ExecutorBase executor = this.executors.get(i);
            if (!executor.doMessage(code, data, recurse)) continue;
            result = true;
        }
        return result;
    }

    @Override
    public boolean initialize(Object initInfo) throws ExecuteException {
        boolean result = super.initialize(initInfo);
        result = this.initializeChildren(initInfo) || result;
        return result;
    }

    protected boolean initializeChildren(Object initInfo) throws ExecuteException {
        boolean result = false;
        int count = this.executors.size();
        for (int i = 0; i < count; ++i) {
            ExecutorBase executor = this.executors.get(i);
            if (!executor.initialize(initInfo)) continue;
            result = true;
        }
        return result;
    }

    @Override
    protected void prepare(Object taskInfo) throws Exception {
        if (this.state == 2) {
            this.subTask = null;
            this.hasActiveSubTask = false;
        }
        super.prepare(taskInfo);
    }

    @Override
    protected boolean doExecution(Object taskInfo) throws Exception {
        return this.scheduleChildrenTask(taskInfo);
    }

    protected boolean assignChildren(Object taskInfo) throws ExecuteException {
        boolean result = false;
        int count = this.executors.size();
        for (int i = 0; i < count; ++i) {
            ExecutorBase executor = this.executors.get(i);
            if (!executor.assign(taskInfo)) continue;
            result = true;
        }
        return result;
    }

    protected void prepareChildren(Object taskInfo) throws Exception {
        int count = this.executors.size();
        for (int i = 0; i < count; ++i) {
            ExecutorBase executor = this.executors.get(i);
            if (executor.isAccessorial) continue;
            executor.prepare(taskInfo);
        }
    }

    @Override
    public boolean stepRun(Object taskInfo) throws Exception {
        if (this.state != 4) {
            throw new ExecuteException("\u4efb\u52a1\u6ca1\u6709\u51c6\u5907\u597d\uff0c\u4e0d\u80fd\u5355\u6b65\u6267\u884c");
        }
        if (!this.sorted) {
            this.sorted = true;
            this.sortByPriority();
            this.sortByPrecursors();
        }
        this.subTask = this.disassembleTask(taskInfo);
        if (this.subTask == null) {
            this.state = 16;
            return true;
        }
        this.resetChildren();
        this.hasActiveSubTask = true;
        this.assignChildren(this.subTask);
        return this.executeChildren(this.subTask, new BoolValue(false));
    }

    protected Object disassembleTask(Object taskInfo) throws Exception {
        return this.subTask == null ? taskInfo : null;
    }

    protected boolean scheduleChildrenTask(Object taskInfo) throws Exception {
        if (!this.sorted) {
            this.sorted = true;
            this.sortByPriority();
            this.sortByPrecursors();
        }
        BoolValue somethingDone = new BoolValue(false);
        while (true) {
            if (!this.hasActiveSubTask) {
                this.subTask = this.disassembleTask(taskInfo);
                if (this.subTask == null) {
                    return true;
                }
                this.resetChildren();
                this.hasActiveSubTask = true;
                this.assignChildren(this.subTask);
            }
            if (this.executeChildren(this.subTask, somethingDone)) {
                this.hasActiveSubTask = false;
                continue;
            }
            if (!somethingDone.value) break;
        }
        return false;
    }

    protected boolean executeChildren(Object subTask, BoolValue somethingDone) throws Exception {
        this.prepareChildren(subTask);
        boolean result = true;
        int count = this.executors.size();
        for (int i = 0; i < count; ++i) {
            ExecutorBase executor = this.executors.get(i);
            if (executor.isAccessorial) continue;
            if (executor.state == 2) {
                this.prepare(subTask);
                if (executor.state == 2) {
                    result = false;
                    continue;
                }
            }
            if (executor.state != 4) continue;
            if (!executor.execute(subTask)) {
                result = false;
                continue;
            }
            somethingDone.value = true;
        }
        return result;
    }

    @Override
    protected void cancel() throws ExecuteException {
        if (this.state == 2 || this.state == 4) {
            this.cancelChildren();
        }
        super.cancel();
    }

    protected void cancelChildren() throws ExecuteException {
        int count = this.executors.size();
        for (int i = 0; i < count; ++i) {
            ExecutorBase executor = this.executors.get(i);
            executor.cancel();
        }
    }

    @Override
    public void reset() throws ExecuteException {
        if (this.state == 16 || this.state == 32) {
            this.resetChildren();
        }
        super.reset();
    }

    protected void resetChildren() throws ExecuteException {
        int count = this.executors.size();
        for (int i = 0; i < count; ++i) {
            ExecutorBase executor = this.executors.get(i);
            executor.reset();
        }
    }

    @Override
    public void resetAll() throws ExecuteException {
        int count = this.executors.size();
        for (int i = 0; i < count; ++i) {
            ExecutorBase executor = this.executors.get(i);
            executor.resetAll();
        }
        super.reset();
    }

    @Override
    public void destroy() throws ExecuteException {
        if (this.state != 64) {
            this.destroyChildren();
        }
        super.destroy();
    }

    protected void destroyChildren() throws ExecuteException {
        int count = this.executors.size();
        for (int i = 0; i < count; ++i) {
            ExecutorBase executor = this.executors.get(i);
            executor.destroy();
        }
        this.executors.clear();
    }

    protected void sortByPriority() {
        for (int i = 1; i < this.executors.size(); ++i) {
            ExecutorBase executor = this.executors.get(i);
            int p = executor.getPriority();
            int sb = 0;
            int se = i - 1;
            int flag = -1;
            while (sb <= se) {
                int sp = (sb + se + 1) / 2;
                flag = p - this.executors.get(sp).getPriority();
                if (flag > 0) {
                    sb = sp + 1;
                    continue;
                }
                if (flag < 0) {
                    se = sp - 1;
                    continue;
                }
                sb = sp;
                if (se != sp) continue;
            }
            if (flag == 0) {
                ++sb;
            }
            if (sb == i) continue;
            for (int j = i; j > sb; --j) {
                this.executors.set(j, this.executors.get(j - 1));
            }
            this.executors.set(sb, executor);
        }
    }

    protected void sortByPrecursors() throws ExecuteException {
        boolean FF_NO_UP = true;
        int FF_NO_DOWN = 2;
        int FF_DONE = 4;
        IntList flags = new IntList();
        flags.setSize(this.executors.size());
        int count = this.executors.size();
        for (int i = 0; i < count; ++i) {
            int curIndex = 0;
            while ((flags.get(curIndex) & 4) != 0) {
                ++curIndex;
            }
            ExecutorBase executor = this.executors.get(curIndex);
            if (executor.precursors != null) {
                for (int j = curIndex + 1; j < this.executors.size(); ++j) {
                    ExecutorBase other = this.executors.get(j);
                    if (executor.precursors.indexOf(other) < 0) continue;
                    if ((flags.get(j) & 1) == 0) {
                        flags.set(j, flags.get(j) | 2);
                        flags.set(curIndex, flags.get(curIndex) | 1);
                        SortedList.listMove(this.executors, (int)j, (int)curIndex);
                        flags.move(j, curIndex);
                        ++curIndex;
                        continue;
                    }
                    if ((flags.get(curIndex) & 2) == 0) {
                        flags.set(j, flags.get(j) | 2);
                        flags.set(curIndex, flags.get(curIndex) | 1);
                        SortedList.listMove(this.executors, (int)curIndex, (int)j);
                        flags.move(curIndex, j);
                        curIndex = j;
                        continue;
                    }
                    throw new ExecuteException("\u6267\u884c\u5355\u5143\u5faa\u73af\u5f15\u7528\uff1a" + executor.getName() + "-" + other.getName());
                }
            }
            flags.set(curIndex, flags.get(curIndex) | 4);
        }
    }

    @Override
    public void printNetwork(PrintStream ps, int level) {
        super.printNetwork(ps, level);
        int c = this.executors.size();
        for (int i = 0; i < c; ++i) {
            ExecutorBase executor = this.executors.get(i);
            executor.printNetwork(ps, level + 1);
        }
    }
}

