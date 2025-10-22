/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nvwa.midstore.core.monitor.MidstoreProgressMonitor
 */
package nr.midstore2.data.monitor;

import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nvwa.midstore.core.monitor.MidstoreProgressMonitor;

public class ReportMidstoreProgressMonitor
extends MidstoreProgressMonitor {
    private AsyncTaskMonitor monitor;
    private String message;

    public ReportMidstoreProgressMonitor(AsyncTaskMonitor monitor) {
        super(monitor.getTaskId());
        this.monitor = monitor;
    }

    protected void notify(double precent) {
        this.monitor.progressAndMessage(precent, this.message);
    }

    public void prompt(String message) {
        this.monitor.progressAndMessage(this.monitor.getLastProgress(), message);
    }

    public void cancel() {
        this.monitor.canceled(null, null);
    }

    public boolean isCanceled() {
        return this.monitor.isCancel();
    }

    public void finishTask() {
        this.monitor.finish(null, null);
    }

    public void progressAndMessage(double progress, String message) {
        this.message = message;
        this.monitor.progressAndMessage(progress, message);
    }

    public String getTaskId() {
        return this.monitor.getTaskId();
    }

    public String getTaskPoolTask() {
        return this.monitor.getTaskPoolTask();
    }

    public boolean isCancel() {
        return this.monitor.isCancel();
    }

    public void finish(String result, Object detail) {
        this.monitor.finish(result, detail);
    }

    public void finished(String result, Object detail) {
        this.monitor.finished(result, detail);
    }

    public void canceling(String result, Object detail) {
        this.monitor.canceling(result, detail);
    }

    public void canceled(String result, Object detail) {
        this.monitor.canceled(result, detail);
    }

    public void error(String result, Throwable t) {
        this.monitor.error(result, t);
    }

    public void error(String result, Throwable t, String detail) {
        this.monitor.error(result, t, detail);
    }

    public boolean isFinish() {
        return this.monitor.isFinish();
    }

    public double getLastProgress() {
        return this.monitor.getLastProgress();
    }

    public ILogger getBILogger() {
        return this.monitor.getBILogger();
    }
}

