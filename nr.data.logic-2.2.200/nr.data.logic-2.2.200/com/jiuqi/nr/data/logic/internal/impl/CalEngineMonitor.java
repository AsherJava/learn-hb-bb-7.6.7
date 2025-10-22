/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.impl.FmlEngineBaseMonitor
 *  com.jiuqi.nr.dataservice.core.datastatus.monitor.IDataStatusMonitor
 *  com.jiuqi.nr.dataservice.core.datastatus.obj.DataStatusPresetInfo
 */
package com.jiuqi.nr.data.logic.internal.impl;

import com.jiuqi.np.dataengine.intf.impl.FmlEngineBaseMonitor;
import com.jiuqi.nr.data.logic.spi.ICalculateMonitor;
import com.jiuqi.nr.dataservice.core.datastatus.monitor.IDataStatusMonitor;
import com.jiuqi.nr.dataservice.core.datastatus.obj.DataStatusPresetInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalEngineMonitor
extends FmlEngineBaseMonitor
implements IDataStatusMonitor {
    private static final Logger logger = LoggerFactory.getLogger(CalEngineMonitor.class);
    private final ICalculateMonitor calculateMonitor;
    private final DataStatusPresetInfo dataStatusPresetInfo;
    private double startProgress = 0.01;
    private double endProgress = 0.99;
    private double lastProgress = 0.0;

    public CalEngineMonitor(ICalculateMonitor calculateMonitor, DataStatusPresetInfo dataStatusPresetInfo) {
        this.calculateMonitor = calculateMonitor;
        this.dataStatusPresetInfo = dataStatusPresetInfo;
    }

    public void setStartProgress(double startProgress, double endProgress) {
        if (startProgress > this.startProgress) {
            this.startProgress = startProgress;
        }
        if (this.endProgress > endProgress) {
            this.endProgress = endProgress;
        }
        this.lastProgress = this.startProgress;
        this.start();
    }

    public void onProgress(double progress) {
        super.onProgress(progress);
        if (this.calculateMonitor != null) {
            double currProgress = this.startProgress + progress * (this.endProgress - this.startProgress);
            logger.debug("taskId-{}\nstart-{}\nend-{}\ncur-{}", this.calculateMonitor.getTaskId(), this.startProgress, this.endProgress, currProgress);
            if (currProgress >= this.lastProgress) {
                this.lastProgress = currProgress;
                this.calculateMonitor.progressAndMessage(currProgress, "");
            }
        }
    }

    public boolean isCancel() {
        if (this.calculateMonitor == null) {
            return false;
        }
        return this.calculateMonitor.isCancel();
    }

    public void start() {
        if (this.start == 0L) {
            this.start = System.currentTimeMillis();
        }
    }

    public void error(String msg, Object sender) {
        if (this.calculateMonitor != null && sender instanceof Throwable) {
            this.calculateMonitor.error(msg, (Throwable)sender);
        } else {
            super.error(msg, sender);
        }
    }

    public DataStatusPresetInfo getPresetInfo() {
        return this.dataStatusPresetInfo;
    }
}

