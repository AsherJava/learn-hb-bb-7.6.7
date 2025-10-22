/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 */
package com.jiuqi.nr.data.logic.internal.impl;

import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.nr.data.logic.facade.extend.IFmlCheckListener;
import com.jiuqi.nr.data.logic.facade.extend.param.CheckEvent;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.spi.ICheckMonitor;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class DefCheckMonitor
implements ICheckMonitor {
    private static final Logger logger = LoggerFactory.getLogger(DefCheckMonitor.class);
    private final IFmlMonitor fmlMonitor;
    private final List<IFmlCheckListener> fmlCheckListeners;

    public DefCheckMonitor(IFmlMonitor fmlMonitor, List<IFmlCheckListener> fmlCheckListeners) {
        this.fmlMonitor = fmlMonitor;
        this.fmlCheckListeners = fmlCheckListeners;
    }

    @Override
    public void executeBefore(CheckEvent checkEvent) {
        if (!CollectionUtils.isEmpty(this.fmlCheckListeners)) {
            for (IFmlCheckListener fmlCheckListener : this.fmlCheckListeners) {
                try {
                    fmlCheckListener.beforeCheck(checkEvent);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public void executeAfter(CheckEvent checkEvent) {
        if (!CollectionUtils.isEmpty(this.fmlCheckListeners)) {
            for (IFmlCheckListener fmlCheckListener : this.fmlCheckListeners) {
                try {
                    fmlCheckListener.afterCheck(checkEvent);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public String getTaskId() {
        return this.fmlMonitor.getTaskId();
    }

    @Override
    public void progressAndMessage(double currProgress, String message) {
        this.fmlMonitor.progressAndMessage(currProgress, message);
    }

    @Override
    public void error(String message, Throwable sender) {
        this.fmlMonitor.error(message, sender);
    }

    @Override
    public void error(String message, Throwable sender, Object detail) {
        this.fmlMonitor.error(message, sender, detail);
    }

    @Override
    public void finish(String result, Object detail) {
        this.fmlMonitor.finish(result, detail);
    }

    @Override
    public void cancel(String message, Object detail) {
        this.fmlMonitor.cancel(message, detail);
    }

    @Override
    public boolean isCancel() {
        return this.fmlMonitor.isCancel();
    }

    @Override
    public void canceled(String result, Object detail) {
        this.fmlMonitor.canceled(result, detail);
    }

    @Override
    public AbstractMonitor getFmlEngineMonitor() {
        return this.fmlMonitor.getFmlEngineMonitor();
    }
}

