/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.math.Round
 *  com.jiuqi.np.dataengine.collector.FmlExecuteCollector
 *  com.jiuqi.np.dataengine.collector.GlobalInfo
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DebugLogType
 *  com.jiuqi.np.dataengine.intf.IDataChangeListener
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.nr.parallel.impl.BatchSerialMonitor
 */
package com.jiuqi.nr.data.logic.monitor.base;

import com.jiuqi.bi.syntax.function.math.Round;
import com.jiuqi.np.dataengine.collector.FmlExecuteCollector;
import com.jiuqi.np.dataengine.collector.GlobalInfo;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.intf.IDataChangeListener;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.nr.parallel.impl.BatchSerialMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class BatchSerialBaseMonitor
extends BatchSerialMonitor {
    private static final Logger fmlLog = LoggerFactory.getLogger("com.jiuqi.nr.data.logic.fml");
    private static final Logger commonLog = LoggerFactory.getLogger("com.jiuqi.nr.common.log");
    protected AbstractMonitor fmlEngineExtMonitor;

    public void message(String msg, Object sender) {
        if (fmlLog.isDebugEnabled()) {
            String message = this.getLogMessage(msg);
            fmlLog.debug(message);
        }
        if (this.fmlEngineExtMonitor != null) {
            this.fmlEngineExtMonitor.message(msg, sender);
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
        if (this.fmlEngineExtMonitor != null) {
            this.fmlEngineExtMonitor.error(errorMsg, sender);
        }
    }

    public void exception(Exception e) {
        this.errorMessage.add(e.getMessage());
        if (fmlLog.isDebugEnabled()) {
            fmlLog.debug(e.getMessage(), e);
        }
        if (this.fmlEngineExtMonitor != null) {
            this.fmlEngineExtMonitor.exception(e);
        }
    }

    public void debug(String msg, DataEngineConsts.DebugLogType type) {
        if (fmlLog.isDebugEnabled()) {
            String message = this.getLogMessage(msg);
            fmlLog.debug(message);
        }
        if (this.fmlEngineExtMonitor != null) {
            this.fmlEngineExtMonitor.debug(msg, type);
        }
    }

    public void start() {
        if (this.start == 0L) {
            this.start = System.currentTimeMillis();
            this.message(this.runType.getTitle() + "\u6267\u884c\u5f00\u59cb", null);
            if (this.fmlEngineExtMonitor != null) {
                this.fmlEngineExtMonitor.start();
            }
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
                long cost = System.currentTimeMillis() - this.start;
                String msg = this.runType.getTitle() + "\u6267\u884c\u5b8c\u6210,\u8017\u65f6" + Round.callFunction((Number)Float.valueOf((float)cost / 1000.0f), (int)2) + "s    \u5171\u6267\u884c" + this.formulaCount + "\u6761\u516c\u5f0f, \u6d89\u53ca" + this.fieldCount + "\u4e2a\u5b57\u6bb5, \u67e5\u8be2\u4e86" + this.recordCount + "\u6761\u8bb0\u5f55, \u66f4\u65b0\u4e86" + this.updateRecordCount + "\u6761\u8bb0\u5f55";
                this.message(msg, null);
                FmlExecuteCollector collector = this.getCollector();
                if (collector != null) {
                    GlobalInfo globalInfo = collector.getGlobalInfo();
                    globalInfo.setTotalCost(cost);
                    globalInfo.setFieldCount(this.fieldCount);
                    globalInfo.setFormulaCount(this.formulaCount);
                    globalInfo.setQueryRecordCount(this.recordCount);
                    collector.getErrorMessages().addAll(this.errorMessage);
                }
            }
            for (IDataChangeListener dataChangeListener : this.getDataChangeListeners()) {
                dataChangeListener.finish();
            }
            this.onProgress(1.0);
            if (this.errorMonitor != null) {
                this.errorMonitor.finish();
            }
            if (this.fmlEngineExtMonitor != null) {
                this.fmlEngineExtMonitor.finish();
            }
        }
        this.finished = true;
    }

    protected Logger getLogger() {
        return fmlLog;
    }

    public boolean isDebug() {
        return fmlLog.isDebugEnabled() || this.fmlEngineExtMonitor != null && this.fmlEngineExtMonitor.isDebug();
    }

    public FmlExecuteCollector getCollector() {
        if (this.fmlEngineExtMonitor != null) {
            return this.fmlEngineExtMonitor.getCollector();
        }
        return super.getCollector();
    }

    public void setFmlEngineExtMonitor(AbstractMonitor fmlEngineExtMonitor) {
        this.fmlEngineExtMonitor = fmlEngineExtMonitor;
    }
}

