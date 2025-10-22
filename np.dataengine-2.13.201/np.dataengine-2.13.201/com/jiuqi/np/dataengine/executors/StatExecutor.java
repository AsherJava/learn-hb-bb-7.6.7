/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.executors.ExecutorBase;
import com.jiuqi.np.dataengine.executors.ExprExecContext;
import com.jiuqi.np.dataengine.executors.StatItem;
import java.util.ArrayList;

public final class StatExecutor
extends ExecutorBase {
    private ExprExecContext execContext;
    private ArrayList<StatItem> statItems = new ArrayList();

    public StatExecutor(ExprExecContext context) {
        super(context.GlobalContext);
        this.execContext = context;
    }

    public int size() {
        return this.statItems.size();
    }

    public StatItem get(int index) {
        return this.statItems.get(index);
    }

    public int add(StatItem statItem) {
        this.statItems.add(statItem);
        return this.statItems.size() - 1;
    }

    public void remove(StatItem statItem) {
        this.statItems.remove(statItem);
    }

    public void combine(StatExecutor another) {
        this.statItems.addAll(another.statItems);
    }

    public void resetStatItems() {
        int count = this.statItems.size();
        for (int i = 0; i < count; ++i) {
            StatItem statItem = this.statItems.get(i);
            statItem.reset();
        }
        this.context.getStatItemCollection().reset();
    }

    @Override
    protected boolean doExecution(Object taskInfo) throws Exception {
        StatItem statItem = null;
        int count = this.statItems.size();
        for (int i = 0; i < count; ++i) {
            try {
                statItem = this.statItems.get(i);
                if (this.context.isBatch()) {
                    statItem.runStatistic(this.context, this.context.getCurrentMasterKey());
                    continue;
                }
                statItem.runStatistic();
                continue;
            }
            catch (Exception ex) {
                StringBuilder msg = new StringBuilder();
                msg.append("\u6267\u884c\u6d6e\u52a8\u7edf\u8ba1\u5355\u5143          ");
                msg.append(statItem);
                msg.append("   \u51fa\u9519:").append(ex.getMessage());
                this.context.getMonitor().error(msg.toString(), this);
                if (!this.context.outFMLPlan()) continue;
                this.context.getMonitor().exception(ex);
            }
        }
        return true;
    }

    public boolean doStatistic() throws Exception {
        int count = this.statItems.size();
        for (int i = 0; i < count; ++i) {
            StatItem statItem = this.statItems.get(i);
            statItem.runStatistic();
        }
        return true;
    }

    public void print(StringBuilder buff) {
        for (StatItem item : this.statItems) {
            buff.append(item.toString()).append("\n");
        }
    }
}

