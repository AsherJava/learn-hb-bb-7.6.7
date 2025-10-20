/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.trace;

import com.jiuqi.bi.trace.ResourceAction;
import com.jiuqi.bi.trace.ResourceDescriptor;
import com.jiuqi.bi.trace.TraceEntry;
import com.jiuqi.bi.util.snowflake.IdWorker;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ResourceObject
extends TraceEntry {
    private final long id = idWorker.nextId();
    private final String name;
    private final ResourceDescriptor descriptor;
    private Map<String, ActionSet> actionSets;
    private static final IdWorker idWorker = new IdWorker(System.currentTimeMillis(), 0L);
    private static final Comparator<ResourceAction> actionComparator = new Comparator<ResourceAction>(){

        @Override
        public int compare(ResourceAction action1, ResourceAction action2) {
            List<StackTraceElement> stack2;
            List<StackTraceElement> stack1 = action1.getStackTrace();
            if (stack1 == (stack2 = action2.getStackTrace())) {
                return 0;
            }
            int p1 = stack1.size();
            int p2 = stack2.size();
            while (p1 > 0 && p2 > 0) {
                int c;
                if ((c = this.compareStack(stack1.get(--p1), stack2.get(--p2))) == 0) continue;
                return c;
            }
            return p1 - p2;
        }

        private int compareStack(StackTraceElement e1, StackTraceElement e2) {
            int c = e1.getClassName().compareTo(e2.getClassName());
            if (c != 0) {
                return c;
            }
            c = e1.getMethodName().compareTo(e2.getMethodName());
            if (c != 0) {
                return c;
            }
            return e1.getLineNumber() - e2.getLineNumber();
        }
    };

    public ResourceObject(ResourceDescriptor descriptor, Object resource) {
        this.name = resource.toString();
        this.descriptor = descriptor;
        this.actionSets = new HashMap<String, ActionSet>(descriptor.getActions().size());
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public ResourceDescriptor getDescriptor() {
        return this.descriptor;
    }

    synchronized void putAction(ResourceAction action) {
        int p;
        ActionSet actionSet = this.actionSets.get(action.getAction());
        if (actionSet == null) {
            actionSet = new ActionSet();
            this.actionSets.put(action.getAction(), actionSet);
        }
        if ((p = Collections.binarySearch(actionSet.actions, action, actionComparator)) >= 0) {
            actionSet.actions.get(p).addRefCount();
        } else {
            actionSet.actions.add(-(p + 1), action);
        }
        ++actionSet.refCount;
    }

    synchronized void removeAction(String action) {
        ActionSet actionSet = this.actionSets.get(action);
        if (actionSet == null) {
            return;
        }
        --actionSet.refCount;
        if (actionSet.refCount == 0L) {
            this.actionSets.remove(action);
        }
    }

    public synchronized Map<String, Long> countActions() {
        TreeMap<String, Long> actionCounts = new TreeMap<String, Long>();
        for (Map.Entry<String, ActionSet> entry : this.actionSets.entrySet()) {
            actionCounts.put(entry.getKey(), entry.getValue().refCount);
        }
        return actionCounts;
    }

    public synchronized List<ResourceAction> getActionHistories(String action) {
        ActionSet actionSet = this.actionSets.get(action);
        if (actionSet == null) {
            return new ArrayList<ResourceAction>();
        }
        return new ArrayList<ResourceAction>(actionSet.actions);
    }

    @Override
    public void printTrace(PrintStream s, int ident) {
        super.printTrace(s, ident);
        this.printActions(s, ident);
    }

    private synchronized void printActions(PrintStream s, int ident) {
        if (this.actionSets.isEmpty()) {
            return;
        }
        for (Map.Entry<String, ActionSet> entry : this.actionSets.entrySet()) {
            String action = entry.getKey();
            ActionSet invokes = entry.getValue();
            this.ident(s, ident).printf("---> %,d %s invokes is unclosing:%n", invokes.refCount, action);
            for (int i = 0; i < invokes.actions.size(); ++i) {
                if (i > 0) {
                    this.ident(s, ident + 1).println("--");
                }
                invokes.actions.get(i).printTrace(s, i + 1, ident + 1);
            }
        }
    }

    @Override
    public String toString() {
        return this.name + " at " + super.toString();
    }

    private static class ActionSet {
        public final List<ResourceAction> actions = new ArrayList<ResourceAction>();
        public long refCount = 0L;

        private ActionSet() {
        }
    }
}

