/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.math.Round
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DebugLogType
 *  com.jiuqi.np.dataengine.intf.IDataChangeListener
 *  com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl
 *  com.jiuqi.nr.parallel.BatchParallelExeTask
 *  com.jiuqi.nr.parallel.impl.BatchParallelMonitor
 */
package com.jiuqi.nr.data.logic.monitor.base;

import com.jiuqi.bi.syntax.function.math.Round;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.intf.IDataChangeListener;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.impl.BatchParallelMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class BatchParallelBaseMonitor
extends BatchParallelMonitor {
    private static final Logger fmlLog = LoggerFactory.getLogger("com.jiuqi.nr.data.logic.fml");
    private static final Logger commonLog = LoggerFactory.getLogger("com.jiuqi.nr.common.log");

    public BatchParallelBaseMonitor(BatchParallelExeTask task) {
        super(task);
    }

    public void error(FormulaCheckEventImpl event) {
        this.debug(event.toString(), DataEngineConsts.DebugLogType.COMMON);
    }

    public void message(String msg, Object sender) {
        if (fmlLog.isDebugEnabled()) {
            String message = this.getLogMessage(msg);
            fmlLog.debug(message);
        }
    }

    private String getLogMessage(String msg) {
        String message = msg;
        if (this.runType != null) {
            message = this.runType.getTitle() + " - " + msg;
        }
        return message;
    }

    public void error(String msg, Object sender) {
        String errorMsg = msg;
        if (sender != null) {
            errorMsg = sender.getClass().getSimpleName() + ":" + msg;
        }
        this.errorMessage.add(errorMsg);
    }

    public void exception(Exception e) {
        this.errorMessage.add(e.getMessage());
        if (fmlLog.isDebugEnabled()) {
            fmlLog.debug(e.getMessage(), e);
        }
    }

    public void debug(String msg, DataEngineConsts.DebugLogType type) {
        if (fmlLog.isDebugEnabled()) {
            String message = this.getLogMessage(msg);
            fmlLog.debug(message);
        }
    }

    public void start() {
        if (this.start == 0L) {
            this.start = System.currentTimeMillis();
            this.message(this.runType.getTitle() + "\u6267\u884c\u5f00\u59cb", null);
        }
    }

    public void finish() {
        if (!this.finished) {
            if (!CollectionUtils.isEmpty(this.errorMessage)) {
                commonLog.error("{}\u6267\u884c\u8fc7\u7a0b\u5b58\u5728\u5f02\u5e38", (Object)this.runType.getTitle());
                for (String msg : this.errorMessage) {
                    fmlLog.error(msg);
                }
            }
            if (this.start > 0L) {
                String msg = this.runType.getTitle() + "\u6267\u884c\u5b8c\u6210,\u8017\u65f6" + Round.callFunction((Number)Float.valueOf((float)(System.currentTimeMillis() - this.start) / 1000.0f), (int)2) + "s    \u5171\u6267\u884c" + this.formulaCount + "\u6761\u516c\u5f0f, \u6d89\u53ca" + this.fieldCount + "\u4e2a\u5b57\u6bb5, \u67e5\u8be2\u4e86" + this.recordCount + "\u6761\u8bb0\u5f55, \u66f4\u65b0\u4e86" + this.updateRecordCount + "\u6761\u8bb0\u5f55";
                this.message(msg, null);
            }
            for (IDataChangeListener dataChangeListener : this.getDataChangeListeners()) {
                dataChangeListener.finish();
            }
            this.onProgress(1.0);
            if (this.task != null) {
                this.task.setFinished(true);
                if (this.asyncTaskMonitor != null) {
                    this.asyncTaskMonitor.finish(this.task.getKey(), (Object)this.task.getKey());
                }
            }
        }
        this.finished = true;
    }

    protected Logger getLogger() {
        return fmlLog;
    }

    public boolean isDebug() {
        return fmlLog.isDebugEnabled();
    }
}

