/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.Node
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.snowflake.IdWorker
 */
package com.jiuqi.bi.core.jobs.defaultlog;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.defaultlog.LogItem;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobFactory;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.Node;
import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.snowflake.IdWorker;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Logger
implements ILogger {
    public static final int TRACE_INT = 0;
    public static final int DEBUG_INT = 10;
    public static final int INFO_INT = 20;
    public static final int INFO_END = 29;
    public static final int WARN_INT = 30;
    public static final int ERROR_INT = 40;
    private JobContext context;
    private JobModel model;
    private AbstractRealTimeJob realTimeJob;
    private String instanceId;
    private String name;
    private int level = 20;
    private IdWorker idWorker;
    private String nodeName;
    private OrderGenerator timestampGenerator = new OrderGenerator();

    public Logger(JobContext context) {
        this.context = context;
        this.model = this.context.getJob();
        this.realTimeJob = this.context.getRealTimeJob();
        this.instanceId = this.context.getInstanceId();
        this.name = "com.jiuqi.bi.core.jobs.";
        if (this.model != null) {
            this.name = this.name + this.model.getCategory() + "." + this.model.getGuid();
        } else if (this.realTimeJob != null) {
            String group = context.getParameterValue("_SYS_JOB_GROUP");
            this.name = this.name + RealTimeJobFactory.getRealTimeJobCategoryId(group);
            this.name = this.name + "." + this.instanceId;
        } else {
            this.name = this.name + "unknow";
        }
        Node self = DistributionManager.getInstance().self();
        this.nodeName = self.getName();
        this.idWorker = DistributionManager.getInstance().getSnowFlakeWorker();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void debug(String message) {
        if (this.isDebugEnabled()) {
            this.doLog(10, message, null);
        }
    }

    public void debug(String message, Throwable e) {
        if (this.isDebugEnabled()) {
            this.doLog(10, message, e);
        }
    }

    public void error(String message) {
        if (this.isErrorEnabled()) {
            this.doLog(40, message, null);
        }
    }

    public void error(String message, Throwable e) {
        if (this.isErrorEnabled()) {
            this.doLog(40, message, e);
        }
    }

    public String getName() {
        return this.name;
    }

    public void info(String message) {
        if (this.isInfoEnabled()) {
            this.doLog(20, message, null);
        }
    }

    public void info(String message, Throwable e) {
        if (this.isInfoEnabled()) {
            this.doLog(20, message, e);
        }
    }

    public void doLog(int level, String message, Throwable e) {
        LogItem item = new LogItem();
        item.setId(this.idWorker.nextId());
        item.setTimestamp(this.timestampGenerator.generateID());
        item.setLevel(level);
        item.setInstanceId(this.instanceId);
        if (message == null) {
            message = "";
        }
        if (message.length() > 100) {
            String shortMessage = message.substring(0, 97) + "...";
            item.setMessage(shortMessage);
            StringWriter sw = new StringWriter();
            sw.append(message);
            if (e != null) {
                sw.append("\n").append(e.getMessage()).append("\n");
                e.printStackTrace(new PrintWriter(sw));
            }
            item.setHasDetail(true);
            item.setDetail(sw.toString());
        } else {
            item.setMessage(message);
            if (e != null) {
                StringWriter sw = new StringWriter();
                sw.append(e.getMessage()).append("\n");
                e.printStackTrace(new PrintWriter(sw));
                item.setHasDetail(true);
                item.setDetail(sw.toString());
            }
        }
        item.setNodeName(this.nodeName);
        this.doLog(item);
    }

    void doLog(LogItem item) {
    }

    public boolean isDebugEnabled() {
        return this.level <= 10;
    }

    public boolean isErrorEnabled() {
        return this.level <= 40;
    }

    public boolean isInfoEnabled() {
        return this.level <= 20;
    }

    public boolean isTraceEnabled() {
        return this.level <= 0;
    }

    public boolean isWarnEnabled() {
        return this.level <= 30;
    }

    public void trace(String message) {
        if (this.isTraceEnabled()) {
            this.doLog(0, message, null);
        }
    }

    public void trace(String message, Throwable e) {
        if (this.isTraceEnabled()) {
            this.doLog(0, message, e);
        }
    }

    public void warn(String message) {
        if (this.isWarnEnabled()) {
            this.doLog(30, message, null);
        }
    }

    public void warn(String message, Throwable e) {
        if (this.isWarnEnabled()) {
            this.doLog(30, message, e);
        }
    }
}

